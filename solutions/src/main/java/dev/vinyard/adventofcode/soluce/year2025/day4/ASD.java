package dev.vinyard.adventofcode.soluce.year2025.day4;

import lombok.Getter;

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
            this.entities.forEach(entity -> grid[entity.position.x][entity.position.y] = entity);
        }


        public Optional<Entity> getEntityAt(Point point) {
            return Optional.of(point).filter(bounds::contains)
                    .map(p -> grid[p.x][p.y]);
        }

        public long solution1() {
            return removablePapers().size();
        }

        public List<Entity> removablePapers() {
            return entities.stream()
                    .filter(Paper.class::isInstance)
                    .filter(e -> e.removable(this))
                    .toList();
        }

        public long solution2() {
            int initialCount = entities.stream().filter(Paper.class::isInstance).toList().size();

            do {
                List<Entity> removablePapers = removablePapers();
                removablePapers.forEach(entity -> {
                    grid[entity.getPosition().x][entity.getPosition().y] = null;
                    entities.remove(entity);
                });
            } while (!removablePapers().isEmpty());

            return initialCount - entities.stream().filter(Paper.class::isInstance).toList().size();
        }
    }

    public abstract static class Entity {

        @Getter
        private final Point position;

        public Entity(Point position) {
            this.position = position;
        }

        private List<Entity> getNeighbours(Root root) {
            return Arrays.stream(Direction.values()).map(direction -> direction.move(this.position)).map(root::getEntityAt).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
        }

        public boolean removable(Root root) {
            return getNeighbours(root).stream().filter(Paper.class::isInstance).count() < 4;
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
