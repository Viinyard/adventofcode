package dev.vinyard.adventofcode.soluce.year2023.day14;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            return this.roundedRocks.stream().mapToLong(e -> (long) e.position.distance(e.position.x, this.bounds.height)).sum();
        }

        public Optional<Entity> getEntityAt(Point point) {
            return Optional.of(point).filter(this.bounds::contains).map(p -> this.grid[p.y][p.x]);
        }

        @Override
        public String toString() {
            return Arrays.stream(grid).map(row -> Arrays.stream(row).map(Entity::toString).collect(Collectors.joining())).collect(Collectors.joining("\n"));
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
    }

    public static class CubeShapedRock extends Entity {

        public CubeShapedRock(Point position) {
            super(position);
        }

        @Override
        public String toString() {
            return "#";
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
