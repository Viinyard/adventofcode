package dev.vinyard.adventofcode.soluce.year2024.day15;

import lombok.Getter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class ASD {

    public record Root(Warehouse warehouse, List<Direction> moves) {

    }

    @Getter
    public static class Warehouse {

        private final List<Entity> entities;

        public Warehouse(List<Entity> entities) {
            this.entities = entities;
        }

        public List<Entity> getEntityAt(Rectangle bounds) {
            return getEntities().stream().filter(e -> e.getBounds().intersects(bounds)).toList();
        }

        public Optional<Player> findPlayer() {
            return getEntities().stream().filter(Player.class::isInstance).map(Player.class::cast).findAny();
        }

        public long getScore() {
            return getEntities().stream().filter(Box.class::isInstance).map(Entity::getBounds).map(Rectangle::getLocation).mapToInt(e -> e.y * 100 + e.x).sum();
        }
    }

    @Getter
    public static abstract class Entity {
        protected final Rectangle bounds;

        public Entity(Rectangle bounds) {
            this.bounds = bounds;
        }

        public void setPosition(Point position) {
            this.bounds.setLocation(position);
        }

        public Set<Entity> push(Warehouse warehouse, Direction direction) {
            Set<Entity> entities = new HashSet<>(warehouse.getEntityAt(new Rectangle(direction.move(this.bounds.getLocation()), bounds.getSize())));
            entities.addAll(entities.stream().filter(e -> !Objects.equals(e, this)).map(e -> e.push(warehouse, direction)).flatMap(Set::stream).collect(Collectors.toCollection(HashSet::new)));
            return entities;
        }

        public void move(Warehouse warehouse, Direction direction) {
            this.setPosition(direction.move(this.bounds.getLocation()));
        }
    }

    public static class Wall extends Entity {
        public Wall(Rectangle bounds) {
            super(bounds);
        }
    }

    public static class Box extends Entity {
        public Box(Rectangle bounds) {
            super(bounds);
        }
    }

    public static class Player extends Entity {
        public Player(Rectangle bounds) {
            super(bounds);
        }

        public void move(Warehouse warehouse, Direction direction) {
            Set<Entity> entitiesToPush = this.push(warehouse, direction);

            if (entitiesToPush.stream().noneMatch(e -> e instanceof Wall)) {
                this.setPosition(direction.move(this.bounds.getLocation()));
                entitiesToPush.forEach(e -> e.move(warehouse, direction));
            }
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
