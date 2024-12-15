package dev.vinyard.adventofcode.soluce.year2024.day15;

import lombok.Getter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ASD {

    public record Root(Warehouse warehouse, List<Direction> moves) {

    }

    public record Warehouse(Entity[][] warehouse) {

        public Rectangle getBounds() {
            return new Rectangle(warehouse[0].length, warehouse.length);
        }

        public void setEntityAt(Point point, Entity entity) {
            warehouse[point.y][point.x] = entity;
            entity.position.setLocation(point);
        }

        public Optional<Entity> getEntityAt(Point point) {
            return Optional.of(point).filter(getBounds()::contains).map(p -> warehouse[p.y][p.x]);
        }

        public List<Entity> getEntities() {
            return Arrays.stream(warehouse).flatMap(Arrays::stream).collect(Collectors.toList());
        }

        public Optional<Player> findPlayer() {
            return getEntities().stream().filter(Player.class::isInstance).map(Player.class::cast).findAny();
        }

        @Override
        public String toString() {
            Entity[][] warehouseTmp = new Entity[this.warehouse.length][this.warehouse[0].length];
            getEntities().forEach(e -> warehouseTmp[e.position.y][e.position.x] = e);
            return Arrays.stream(warehouseTmp).map(row -> Arrays.stream(row).filter(Objects::nonNull).map(Entity::toString).collect(Collectors.joining())).collect(Collectors.joining("\n"));
        }

        public long getScore() {
            return getEntities().stream().filter(Box.class::isInstance).map(Entity::getPosition).mapToInt(e -> e.y * 100 + e.x).sum();
        }
    }

    @Getter
    public static abstract class Entity {
        protected final Point position;

        public Entity(Point position) {
            this.position = position;
        }

        public abstract Entity move(Warehouse warehouse, Direction direction);
    }

    public static class Wall extends Entity {
        public Wall(Point position) {
            super(position);
        }

        @Override
        public Entity move(Warehouse warehouse, Direction direction) {
            throw new UnsupportedOperationException("Cannot move into a wall");
        }

        @Override
        public String toString() {
            return "#";
        }
    }

    public static class Empty extends Entity {
        public Empty(Point position) {
            super(position);
        }

        @Override
        public Entity move(Warehouse warehouse, Direction direction) {
            return this;
        }

        @Override
        public String toString() {
            return ".";
        }
    }

    public static class Box extends Entity {
        public Box(Point position) {
            super(position);
        }

        @Override
        public Entity move(Warehouse warehouse, Direction direction) {
            Point next = direction.move(this.position);
            Entity moved = warehouse.getEntityAt(next).map(e -> e.move(warehouse, direction)).orElseThrow();
            warehouse.setEntityAt(next, this);
            return moved;
        }

        @Override
        public String toString() {
            return "O";
        }
    }

    public static class Player extends Entity {
        public Player(Point position) {
            super(position);
        }

        @Override
        public Entity move(Warehouse warehouse, Direction direction) {
            try {
                Point next = direction.move(this.position);
                Entity moved = warehouse.getEntityAt(next).map(e -> e.move(warehouse, direction)).orElseThrow();
                warehouse.setEntityAt(this.position, moved);
                warehouse.setEntityAt(next, this);
            } catch (UnsupportedOperationException e) {
                // do nothing
            }
            return this;
        }

        @Override
        public String toString() {
            return "@";
        }
    }

    public enum Direction {
        UP(270),
        RIGHT(0),
        DOWN(90),
        LEFT(180);

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
