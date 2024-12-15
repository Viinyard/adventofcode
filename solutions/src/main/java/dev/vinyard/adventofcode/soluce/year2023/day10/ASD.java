package dev.vinyard.adventofcode.soluce.year2023.day10;

import lombok.Getter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ASD {

    public record Root(Entity[][] grid) {

        public Rectangle getBounds() {
            return new Rectangle(grid[0].length, grid.length);
        }

        public Optional<Entity> getEntityAt(Point point) {
            return Optional.of(point).filter(getBounds()::contains).map(p -> grid[p.y][p.x]);
        }

        public long countUnvisitedInPath(Path2D path) {
            return getAllEntities().stream().filter(e -> !e.visited).map(Entity::getPosition).filter(path::contains).count();
        }

        public List<Entity> getAllEntities() {
            return Arrays.stream(grid).flatMap(Arrays::stream).toList();
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

        public Entity getNext(Root root) {
            return getNeighbours(root).stream().filter(e -> !e.visited).filter(e -> e.isConnected(this, root)).findAny().orElse(null);
        }

        public Path2D getPath(Root root) {
            Path2D path2D = new Path2D.Double();
            path2D.moveTo(position.x, position.y);

            for (Entity currentEntity = this; currentEntity != null; currentEntity = currentEntity.getNext(root)) {
                currentEntity.visited = true;
                path2D.lineTo(currentEntity.position.x, currentEntity.position.y);
            }

            path2D.closePath();

            return path2D;
        }

        public abstract Direction[] getDirections();
    }

    public static class NorthSouthPipe extends Entity {

        public NorthSouthPipe(Point position) {
            super(position);
        }

        @Override
        public Direction[] getDirections() {
            return new Direction[]{Direction.NORTH, Direction.SOUTH};
        }
    }

    public static class EastWestPipe extends Entity {

        public EastWestPipe(Point position) {
            super(position);
        }

        @Override
        public Direction[] getDirections() {
            return new Direction[]{Direction.EAST, Direction.WEST};
        }
    }

    public static class NorthEastPipe extends Entity {

        public NorthEastPipe(Point position) {
            super(position);
        }

        @Override
        public Direction[] getDirections() {
            return new Direction[]{Direction.NORTH, Direction.EAST};
        }
    }

    public static class NorthWestPipe extends Entity {

        public NorthWestPipe(Point position) {
            super(position);
        }

        @Override
        public Direction[] getDirections() {
            return new Direction[]{Direction.NORTH, Direction.WEST};
        }
    }

    public static class SouthWestPipe extends Entity {

        public SouthWestPipe(Point position) {
            super(position);
        }

        @Override
        public Direction[] getDirections() {
            return new Direction[]{Direction.SOUTH, Direction.WEST};
        }
    }

    public static class SouthEastPipe extends Entity {

        public SouthEastPipe(Point position) {
            super(position);
        }

        @Override
        public Direction[] getDirections() {
            return new Direction[]{Direction.SOUTH, Direction.EAST};
        }
    }

    public static class Ground extends Entity {

        public Ground(Point position) {
            super(position);
        }

        @Override
        public Direction[] getDirections() {
            return new Direction[0];
        }
    }

    public static class Animal extends Entity {

        public Animal(Point position) {
            super(position);
        }

        @Override
        public Direction[] getDirections() {
            return new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
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
