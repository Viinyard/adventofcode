package dev.vinyard.adventofcode.soluce.year2023.day22;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.Getter;
import org.apache.commons.geometry.euclidean.threed.AffineTransformMatrix3D;
import org.apache.commons.geometry.euclidean.threed.Bounds3D;
import org.apache.commons.geometry.euclidean.threed.Vector3D;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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

        public Long part2() {
            bricks.forEach(Brick::fall);
            return bricks.stream().mapToLong(b -> b.getAllFallingOnBricks(new HashSet<>()).size() - 1).sum();
        }
    }

    // Classe proxy pour Brick avec cache Caffeine
    public static class BrickProxy extends Brick {
        private final Cache<String, List<Brick>> othersCache;
        private final Cache<String, List<Brick>> supportingBricksCache;
        private final Cache<String, List<Brick>> supportedByBricksCache;
        private final Cache<String, List<Brick>> fallingOnBricksCache;
        private final Cache<String, Double> fallDistanceCache;

        public BrickProxy(Bounds3D bounds, LinkedList<Brick> bricks) {
            super(bounds, bricks);

            this.othersCache = Caffeine.newBuilder().build();
            this.supportingBricksCache = Caffeine.newBuilder().build();
            this.supportedByBricksCache = Caffeine.newBuilder().build();
            this.fallingOnBricksCache = Caffeine.newBuilder().build();
            this.fallDistanceCache = Caffeine.newBuilder().build();
        }

        @Override
        public List<Brick> getOthers() {
            return othersCache.get("others", k -> super.getOthers());
        }

        @Override
        public List<Brick> getSupportingBricks() {
            return supportingBricksCache.get("supporting", k -> super.getSupportingBricks());
        }

        @Override
        public List<Brick> getSupportedByBricks() {
            return supportedByBricksCache.get("supportedBy", k -> super.getSupportedByBricks());
        }

        @Override
        public List<Brick> getFallingOnBricks() {
            return fallingOnBricksCache.get("fallingOn", k -> super.getFallingOnBricks());
        }

        @Override
        public double getFallDistance() {
            return fallDistanceCache.get("fallDistance", k -> super.getFallDistance());
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

        public Set<Brick> getAllFallingOnBricks(Set<Brick> bricks) {
            bricks.add(this);

            getSupportingBricks().stream()
                    .filter(b -> bricks.containsAll(b.getSupportedByBricks()))
                    .forEach(b -> b.getAllFallingOnBricks(bricks));

            return bricks;
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
//            System.out.println("%s fall %s steps".formatted(this, getFallDistance()));
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
