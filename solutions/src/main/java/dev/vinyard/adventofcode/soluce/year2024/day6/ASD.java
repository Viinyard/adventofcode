package dev.vinyard.adventofcode.soluce.year2024.day6;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.Optional;

public class ASD {

    public record Root(Map map) {

    }

    public static abstract sealed class Entity permits Obstruction, Emplacement {

        protected Optional<Guardian> guardian = Optional.empty();

        Optional<Guardian> getGuardian() {
            return guardian;
        }

        void setGuardian(Guardian guardian) {
            throw new IllegalStateException();
        }

    }

    public static final class Guardian {

        private final Point position;
        private AffineTransform affineTransform = AffineTransform.getQuadrantRotateInstance(0);

        public Guardian(Point position) {
            this.position = position;
        }

        public void turnRight() {
            affineTransform.quadrantRotate(1, position.x, position.y);
        }

        public boolean move(Map map) {
            affineTransform.translate(0, -1);
            affineTransform.transform(position, position);

            System.out.println("Can move = " + map.getBounds().contains(position));
            if (map.getBounds().contains(position)) {
                try {
                    map.getEntity(position).setGuardian(this);
                } catch (IllegalStateException e) {
                    System.err.println("CAN'T MOVE");
                    turnRight();
                }
            } else {
                return false;
            }
            return true;
        }

        public Point getPosition() {
            return this.position;
        }

        @Override
        public String toString() {
            return "^";
        }
    }

    public static final class Obstruction extends Entity {
        @Override
        public String toString() {
            return "#";
        }
    }

    public static final class Emplacement extends Entity {

        @Override
        void setGuardian(Guardian guardian) {
            System.out.println("SET GUARDIAN");
            this.guardian = Optional.of(guardian);
        }

        @Override
        public String toString() {
            return this.guardian.map(Guardian::toString).orElse(".");
        }
    }

    public record Map(Entity[][] grid) {

        public Rectangle getBounds() {
            return new Rectangle(grid[0].length, grid.length);
        }

        public Entity getEntity(Point point) {
            return grid[point.y][point.x];
        }

        public Guardian findGuardian() {
            return Arrays.stream(grid).flatMap(Arrays::stream)
                    .map(Entity::getGuardian)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .findAny().orElseThrow();
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

        public Point move(Point point, int translation) {
            AffineTransform affineTransform = AffineTransform.getRotateInstance(rotation, point.x, point.y);
            affineTransform.translate(translation, 0);
            Point moved = new Point();
            affineTransform.transform(point, moved);
            return moved;
        }
    }

}
