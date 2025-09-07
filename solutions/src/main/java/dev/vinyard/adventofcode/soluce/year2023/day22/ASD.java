package dev.vinyard.adventofcode.soluce.year2023.day22;

import lombok.Getter;
import org.apache.commons.geometry.euclidean.threed.AffineTransformMatrix3D;
import org.apache.commons.geometry.euclidean.threed.Bounds3D;
import org.apache.commons.geometry.euclidean.threed.Vector3D;

import java.util.LinkedList;
import java.util.List;

public class ASD {

    public static class Root {

        private LinkedList<Brick> bricks;

        public Root(LinkedList<Brick> bricks) {
            this.bricks = bricks;
        }

        public long fall() {
            bricks.forEach(Brick::fall);
            return bricks.stream().filter(b -> b.getFallingOnBricks().isEmpty()).count();
        }
    }

    public static class Brick implements Comparable<Brick> {

        @Getter
        private Bounds3D bounds;

        private LinkedList<Brick> bricks;

        public List<Brick> getOthers() {
            LinkedList<Brick> others = new LinkedList<>(bricks);
            others.remove(this);
            return others;
        }

        public List<Brick> getSupportingBricks() {
            AffineTransformMatrix3D up = AffineTransformMatrix3D.createTranslation(Vector3D.of(0, 0, +1));
            Bounds3D moved = Bounds3D.from(up.apply(bounds.getMin()), up.apply(bounds.getMax()));
            return getOthers().stream().filter(b -> moved.intersects(b.bounds)).toList();
        }

        public List<Brick> getSupportedByBricks() {
            AffineTransformMatrix3D down = AffineTransformMatrix3D.createTranslation(Vector3D.of(0, 0, -1));
            Bounds3D moved = Bounds3D.from(down.apply(bounds.getMin()), down.apply(bounds.getMax()));
            return getOthers().stream().filter(b -> moved.intersects(b.bounds)).toList();
        }

        public List<Brick> getFallingOnBricks() {
            return getSupportingBricks().stream()
                    .filter(s -> s.getSupportedByBricks().stream().allMatch(this::equals))
                    .toList();
        }

        public Brick(Bounds3D bounds, LinkedList<Brick> bricks) {
            this.bounds = bounds;
            this.bricks = bricks;
        }

        public boolean intersects(Brick other) {
            return this.bounds.intersects(other.bounds);
        }

        public double getFallDistance() {
            Bounds3D moved = Bounds3D.from(
                    Vector3D.of(bounds.getMin().getX(), bounds.getMin().getY(), 1),
                    bounds.getMax()
            );

            return getOthers().stream().map(Brick::getBounds).filter(moved::intersects)
                    .map(Bounds3D::getMax).mapToDouble(Vector3D::getZ).max().orElse(0) - bounds.getMin().getZ() + 1;
        }

        public void fall() {
            AffineTransformMatrix3D fall = AffineTransformMatrix3D.createTranslation(Vector3D.of(0, 0, getFallDistance()));
            bounds = Bounds3D.from(fall.apply(bounds.getMin()), fall.apply(bounds.getMax()));
            System.out.println("%s fall %s steps".formatted(this, getFallDistance()));
        }

        @Override
        public int compareTo(Brick o) {
            return Double.compare(this.bounds.getMin().getZ(), o.bounds.getMin().getZ());
        }

        @Override
        public String toString() {
            return "Brick{" +
                    "bounds=" + bounds +
                    '}';
        }
    }
}
