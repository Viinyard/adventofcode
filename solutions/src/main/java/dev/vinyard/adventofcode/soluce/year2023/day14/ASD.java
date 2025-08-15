package dev.vinyard.adventofcode.soluce.year2023.day14;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ASD {

    @Getter
    public static class Root {

        private Platform platform;

        public Root(Platform platform) {
            this.platform = platform;
        }

    }

    @Getter
    public static class Platform {

        private final List<Entity> entities;
        private Rectangle bounds;
        private Entity[][] grid;
        private List<RoundedRock> roundedRocks;
        private List<Direction> directions = Arrays.asList(Direction.NORTH, Direction.WEST, Direction.SOUTH, Direction.EAST);


        public Platform(List<Entity> entities, Rectangle bounds) {
            this.entities = entities;
            this.bounds = bounds;
            this.grid = new Entity[bounds.width][bounds.height];
            this.entities.forEach(e -> e.setPlatform(this));
            this.entities.forEach(entity -> this.grid[entity.position.y][entity.position.x] = entity);
            this.roundedRocks = this.entities.stream().filter(RoundedRock.class::isInstance).map(RoundedRock.class::cast).collect(Collectors.toList());
        }

        public long tilt() {
            this.roundedRocks.forEach(e -> e.moveTo(Direction.NORTH));
            return this.calculateScore();
        }

        public long getRemainingIterationsAfterLoop(long totalIterations) {
            Map<Platform, Long> hash = new HashMap<>();

            long currentIteration = 0;
            while (currentIteration < totalIterations) {
                currentIteration++;
                directions.forEach(direction -> this.roundedRocks.forEach(rock -> rock.moveTo(direction)));

                Long cycle = hash.put(this, currentIteration);
                if (cycle != null)
                    return (totalIterations - currentIteration) % (currentIteration - cycle);
            }

            throw new RuntimeException("No cycle detected after 1_000_000_000 iterations.");
        }

        public long tiltCycle(long totalIterations) {
            long remainingIterations = this.getRemainingIterationsAfterLoop(totalIterations);

            Stream.generate(() -> directions)
                    .limit(remainingIterations)
                    .flatMap(List::stream)
                    .forEach(direction -> this.roundedRocks.forEach(rock -> rock.moveTo(direction)));

            return this.calculateScore();
        }

        private long calculateScore() {
            return this.roundedRocks.stream().mapToLong(e -> (long) e.position.distance(e.position.x, this.bounds.height)).sum();
        }

        public Optional<Entity> getEntityAt(Point point) {
            return Optional.of(point).filter(this.bounds::contains).map(p -> this.grid[p.y][p.x]);
        }

        @Override
        public String toString() {
            return Arrays.stream(grid).map(row -> Arrays.stream(row).map(Entity::toString).collect(Collectors.joining())).collect(Collectors.joining("\n"));
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Platform platform && Arrays.deepEquals(this.grid, platform.grid);
        }

        @Override
        public int hashCode() {
            return Objects.hash(Arrays.deepHashCode(grid));
        }
    }

    @Getter
    @Setter
    public static abstract class Entity {

        protected Point position;
        protected Platform platform;

        public Entity(Point position) {
            this.position = position;
        }

        public void setPosition(Point position) {
            this.position = position;
            this.platform.grid[this.position.y][this.position.x] = this;
        }

        public boolean move(Direction direction) {
            return false;
        }

        public void moveTo(Direction direction) {
            while (this.move(direction));
        }

        @Override
        public int hashCode() {
            return Objects.hash(position) + 31 * this.toString().hashCode();
        }
    }

    public static class RoundedRock extends Entity {

        public RoundedRock(Point position) {
            super(position);
        }

        @Override
        public boolean move(Direction direction) {
            Point newPosition = direction.move(this.position);

            Optional<Entity> entityAt = this.platform.getEntityAt(newPosition);
            if (entityAt.isPresent()) {
                if (entityAt.get() instanceof RoundedRock roundedRock) {
                    return roundedRock.move(direction);
                } else if (entityAt.get() instanceof EmptySpace emptySpace) {
                    emptySpace.setPosition(this.position);
                    this.setPosition(newPosition);
                    return true;
                }
            }

            return false;
        }

        @Override
        public String toString() {
            return "O";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o instanceof RoundedRock roundedRock) return Objects.equals(this.position, roundedRock.position);

            return false;
        }
    }

    public static class CubeShapedRock extends Entity {

        public CubeShapedRock(Point position) {
            super(position);
        }

        @Override
        public String toString() {
            return "#";
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj instanceof CubeShapedRock cubeShapedRock) return Objects.equals(this.position, cubeShapedRock.position);

            return false;
        }
    }

    public static class EmptySpace extends Entity {

        public EmptySpace(Point position) {
            super(position);
        }

        @Override
        public String toString() {
            return ".";
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj instanceof EmptySpace emptySpace) return Objects.equals(this.position, emptySpace.position);

            return false;
        }
    }

    public enum Direction {
        NORTH(3),
        EAST(0),
        SOUTH(1),
        WEST(2);

        final int rotation;

        Direction(int rotation) {
            this.rotation = rotation;
        }

        public Point move(Point point) {
            AffineTransform affineTransform = AffineTransform.getQuadrantRotateInstance(rotation, point.x, point.y);
            affineTransform.translate(1, 0);
            return (Point) affineTransform.transform(point, new Point());
        }
    }
}
