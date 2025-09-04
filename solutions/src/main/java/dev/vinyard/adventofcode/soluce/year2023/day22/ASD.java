package dev.vinyard.adventofcode.soluce.year2023.day22;

import lombok.Getter;
import org.apache.commons.geometry.euclidean.threed.AffineTransformMatrix3D;
import org.apache.commons.geometry.euclidean.threed.Bounds3D;
import org.apache.commons.geometry.euclidean.threed.Vector3D;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ASD {

    public static class Root {

        private List<Brick> bricks;

        public Root(List<Brick> bricks) {
            this.bricks = bricks;
        }

        public long fall() {
            bricks.sort(Brick::compareTo);
            bricks.forEach(b -> b.setOthers(bricks));
            bricks.forEach(Brick::fall);

            bricks.forEach(Brick::setSupportingBricks);
            bricks.forEach(Brick::setSupportedByBricks);
            bricks.forEach(Brick::setFallingOnBricks);

            return bricks.stream().filter(b -> b.getFallingOnBricks().isEmpty()).count();
        }
    }

    public static class Brick implements Comparable<Brick> {

        @Getter
        private Bounds3D bounds;

        private List<Brick> others;

        @Getter
        private List<Brick> supportedByBricks;

        @Getter
        private List<Brick> supportingBricks;

        private Map<Double, List<Bounds3D>> bricksByTopZ;

        private Map<Double, List<Bounds3D>> bricksByBottomZ;

        @Getter
        private List<Brick> fallingOnBricks;

        public void setOthers(List<Brick> bricks) {
            this.others = bricks.stream().filter(b -> !b.equals(this)).toList();
            this.bricksByTopZ = this.others.stream().map(Brick::getBounds).collect(Collectors.groupingBy(b -> b.getMax().getZ()));
            this.bricksByBottomZ = this.others.stream().map(Brick::getBounds).collect(Collectors.groupingBy(b -> b.getMin().getZ()));
        }

        public void setSupportingBricks() {
            AffineTransformMatrix3D up = AffineTransformMatrix3D.createTranslation(Vector3D.of(0, 0, +1));
            Bounds3D moved = Bounds3D.from(up.apply(bounds.getMin()), up.apply(bounds.getMax()));
            this.supportingBricks = others.stream().filter(b -> moved.intersects(b.bounds)).toList();
        }

        public void setSupportedByBricks() {
            AffineTransformMatrix3D down = AffineTransformMatrix3D.createTranslation(Vector3D.of(0, 0, -1));
            Bounds3D moved = Bounds3D.from(down.apply(bounds.getMin()), down.apply(bounds.getMax()));
            this.supportedByBricks = others.stream().filter(b -> moved.intersects(b.bounds)).toList();
        }

        public void setFallingOnBricks() {
            this.fallingOnBricks = supportingBricks.stream()
                    .filter(s -> s.getSupportedByBricks().stream().allMatch(this::equals))
                    .toList();
        }

        public Brick(Bounds3D bounds) {
            this.bounds = bounds;
        }

        public boolean intersects(Brick other) {
            return this.bounds.intersects(other.bounds);
        }

        public boolean canFall() {
            AffineTransformMatrix3D fall = AffineTransformMatrix3D.createTranslation(Vector3D.of(0, 0, -1));
            Bounds3D moved = Bounds3D.from(fall.apply(bounds.getMin()), fall.apply(bounds.getMax()));
            boolean collision = others.stream().map(Brick::getBounds).anyMatch(moved::intersects);
            return !collision && moved.getMin().getZ() >= 1;
        }

        public void fall() {
            AffineTransformMatrix3D fall = AffineTransformMatrix3D.createTranslation(Vector3D.of(0, 0, -1));
            while (canFall()) {
                bounds = Bounds3D.from(fall.apply(bounds.getMin()), fall.apply(bounds.getMax()));
            }
        }

        @Override
        public int compareTo(Brick o) {
            return Double.compare(this.bounds.getMin().getZ(), o.bounds.getMin().getZ());
        }
    }
}
