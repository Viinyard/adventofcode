package dev.vinyard.adventofcode.soluce.year2023.day10;

import lombok.Getter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ASD {

    public record Root(Entity[][] grid) {

        public Root(Entity[][] grid) {
            this.grid = grid;
            connectAll();
        }

        public Rectangle getBounds() {
            return new Rectangle(grid[0].length, grid.length);
        }

        public Optional<Entity> getEntityAt(Point point) {
            return Optional.of(point).filter(getBounds()::contains).map(p -> grid[p.y][p.x]);
        }

        public List<Entity> getAllEntities() {
            return Arrays.stream(grid).flatMap(Arrays::stream).toList();
        }

        private void connectAll() {
            getAllEntities().forEach(e -> e.connect(this));
        }

        public Animal findAnimal() {
            return getAllEntities().stream().filter(Animal.class::isInstance).map(Animal.class::cast).findAny().orElseThrow();
        }

        @Override
        public String toString() {
            return Arrays.stream(grid).map(row -> Arrays.stream(row).map(Entity::toString).collect(Collectors.joining())).collect(Collectors.joining("\n" ));
        }
    }

    @Getter
    public abstract static class Entity {
        private final Point position;
        private List<Entity> connectedEntities = new ArrayList<>(4);
        private boolean visited = false;

        public Entity(Point position) {
            this.position = position;
        }

        public List<Entity> getNeighbours(Root root) {
            return Arrays.stream(this.getDirections()).map(direction -> direction.move(this.position)).map(root::getEntityAt).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
        }

        public boolean isConnected(Entity entity, Root root) {
            return this.getNeighbours(root).stream().anyMatch(e -> e.equals(entity));
        }

        public void connect(Root root) {
            connectedEntities = getNeighbours(root).stream().filter(e -> e.isConnected(this, root)).collect(Collectors.toList());
        }

        public Direction next(Direction from) {
            return null;
        }

        public Entity getNext(Entity from) {
            return connectedEntities.stream().filter(e -> e != from).findAny().orElseThrow();
        }

        public Path2D getPath(Root root) {
            Path2D path2D = new Path2D.Double();
            path2D.moveTo(position.x, position.y);

            Entity previous = this;
            this.visited = true;
            Entity current = this.getNext(previous);

            do {
                current.visited = true;
                path2D.lineTo(current.position.x, current.position.y);
                Entity next = current.getNext(previous);
                previous = current;
                previous.visited = true;
                current = next;
                current.visited = true;
            } while (current != this);

            return path2D;
        }

        public long countUnvisitedInPath(Root root, Path2D path) {
            return root.getAllEntities().stream().filter(e -> !e.visited).map(Entity::getPosition).filter(path::contains).count();
        }

        public abstract Direction[] getDirections();
    }

    public static class NorthSouthPipe extends Entity {

        private static final Direction[] directions = new Direction[]{Direction.NORTH, Direction.SOUTH};

        public NorthSouthPipe(Point position) {
            super(position);
        }

        @Override
        public Direction[] getDirections() {
            return directions;
        }

        public Direction next(Direction from) {
            return switch (from) {
                case NORTH -> Direction.SOUTH;
                case SOUTH -> Direction.NORTH;
                default -> null;
            };
        }
    }

    public static class EastWestPipe extends Entity {

        private static final Direction[] directions = new Direction[]{Direction.EAST, Direction.WEST};

        public EastWestPipe(Point position) {
            super(position);
        }

        @Override
        public Direction[] getDirections() {
            return directions;
        }

        public Direction next(Direction from) {
            return switch (from) {
                case EAST -> Direction.WEST;
                case WEST -> Direction.EAST;
                default -> null;
            };
        }
    }

    public static class NorthEastPipe extends Entity {

        private static final Direction[] directions = new Direction[]{Direction.NORTH, Direction.EAST};

        public NorthEastPipe(Point position) {
            super(position);
        }

        @Override
        public Direction[] getDirections() {
            return directions;
        }

        public Direction next(Direction from) {
            return switch (from) {
                case NORTH -> Direction.EAST;
                case EAST -> Direction.NORTH;
                default -> null;
            };
        }
    }

    public static class NorthWestPipe extends Entity {

        private static final Direction[] directions = new Direction[]{Direction.NORTH, Direction.WEST};

        public NorthWestPipe(Point position) {
            super(position);
        }

        @Override
        public Direction[] getDirections() {
            return directions;
        }

        public Direction next(Direction from) {
            return switch (from) {
                case NORTH -> Direction.WEST;
                case WEST -> Direction.NORTH;
                default -> null;
            };
        }
    }

    public static class SouthWestPipe extends Entity {

        private static final Direction[] directions = new Direction[]{Direction.SOUTH, Direction.WEST};

        public SouthWestPipe(Point position) {
            super(position);
        }

        @Override
        public Direction[] getDirections() {
            return directions;
        }

        public Direction next(Direction from) {
            return switch (from) {
                case SOUTH -> Direction.WEST;
                case WEST -> Direction.SOUTH;
                default -> null;
            };
        }
    }

    public static class SouthEastPipe extends Entity {

        private static final Direction[] directions = new Direction[]{Direction.SOUTH, Direction.EAST};

        public SouthEastPipe(Point position) {
            super(position);
        }

        @Override
        public Direction[] getDirections() {
            return directions;
        }

        public Direction next(Direction from) {
            return switch (from) {
                case SOUTH -> Direction.EAST;
                case EAST -> Direction.SOUTH;
                default -> null;
            };
        }
    }

    public static class Ground extends Entity {

        private static final Direction[] directions = new Direction[0];

        public Ground(Point position) {
            super(position);
        }

        @Override
        public Direction[] getDirections() {
            return directions;
        }
    }

    public static class Animal extends Entity {

        private static final Direction[] directions = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

        public Animal(Point position) {
            super(position);
        }

        @Override
        public Direction[] getDirections() {
            return directions;
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
