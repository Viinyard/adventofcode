package dev.vinyard.adventofcode.soluce.year2025.day4;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ASD {

    public static class Root {

        private final List<Entity> entities;
        private final Rectangle bounds;

        private Entity[][] grid;

        public Root(List<Entity> entities, Dimension dimension) {
            this.entities = entities;
            this.bounds = new Rectangle(dimension);

            this.grid = new Entity[dimension.width][dimension.height];
            this.entities.forEach(entity -> {
                grid[entity.position.x][entity.position.y] = entity;
            });
        }


        public Optional<Entity> getEntityAt(Point point) {
            return Optional.of(point).filter(bounds::contains)
                    .map(p -> grid[p.x][p.y]);
        }

        public long solution1() {
            return entities.stream()
                    .filter(Paper.class::isInstance)
                    .mapToLong(e -> e.getNeighbours(this).stream().filter(Paper.class::isInstance).count())
                    .filter(count -> count < 4)
                    .count();
        }
    }

    public abstract static class Entity {
        private final Point position;

        public Entity(Point position) {
            this.position = position;
        }

        public List<Entity> getNeighbours(Root root) {
            return Arrays.stream(Direction.values()).map(direction -> direction.move(this.position)).map(root::getEntityAt).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
        }
    }

    public static class Paper extends Entity {
        public Paper(Point position) {
            super(position);
        }
    }

    public static class Empty extends Entity {
        public Empty(Point position) {
            super(position);
        }
    }

    public enum Direction {
        NORTH(270),
        NORTH_EAST(315),
        EAST(0),
        SOUTH_EAST(45),
        SOUTH(90),
        SOUTH_WEST(135),
        WEST(180),
        NORTH_WEST(225);

        final double rotation;

        Direction(int rotation) {
            this.rotation = Math.toRadians(rotation);
        }

        public Point move(Point point) {
            AffineTransform affineTransform = AffineTransform.getRotateInstance(rotation, point.x, point.y);
            affineTransform.translate(1, 0);
            Point moved = new Point();
            affineTransform.transform(point, moved);
            return moved;
        }
    }

}
