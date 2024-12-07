package dev.vinyard.adventofcode.soluce.year2024.day6;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ASD {

    public record Map(Entity[][] grid) {

        public Rectangle getBounds() {
            return new Rectangle(grid[0].length, grid.length);
        }

        public Entity getEntity(Point point) {
            return Optional.of(point).filter(getBounds()::contains).map(p -> grid[p.y][p.x]).orElseThrow(GuardianOutOfBoundsException::new);
        }

        public Guardian findGuardian() {
            return Arrays.stream(grid).flatMap(Arrays::stream)
                    .map(Entity::getGuardian)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .findAny().orElseThrow();
        }

        @Override
        public String toString() {
            return Arrays.stream(grid).map(Arrays::stream).map(e -> e.map(Entity::toString).collect(Collectors.joining(""))).collect(Collectors.joining("\n"));
        }
    }

    public static abstract sealed class Entity permits Obstruction, Emplacement {

        protected Guardian guardian;
        protected final Point position;

        public Entity(Point position) {
            this.position = position;
        }

        Optional<Guardian> getGuardian() {
            return Optional.ofNullable(guardian);
        }

        void setGuardian(Guardian guardian) {
            throw new GuardianIllegalMoveException();
        }

    }

    public static final class Guardian {

        private Point position;
        private int direction = 0;

        public void turnRight() {
            direction = ++direction & 3;
        }

        private Point getNextPosition() {
            Point move = new Point(position);
            AffineTransform affineTransform = AffineTransform.getQuadrantRotateInstance(direction, position.x, position.y);
            affineTransform.translate(0, -1);
            affineTransform.transform(position, move);
            return move;
        }

        public void move(Map map) {
            try {
                map.getEntity(getNextPosition()).setGuardian(this);
            } catch (GuardianIllegalMoveException e) {
                turnRight();
            }
        }

        public Set<Point> getVisitedPositions(Map map) {
            Set<Point> moves = new HashSet<>();

            try {
                while (true) {
                    moves.add(this.position);
                    move(map);
                }
            } catch (GuardianOutOfBoundsException e) {
                System.out.println(map);
                return moves;
            }
        }

        @Override
        public String toString() {
            return "^";
        }
    }

    public static final class Obstruction extends Entity {

        public Obstruction(Point position) {
            super(position);
        }

        @Override
        public String toString() {
            return "#";
        }
    }

    public static final class Emplacement extends Entity {

        private final Set<Integer> directions = new HashSet<>();

        public Emplacement(Point position) {
            super(position);
        }

        private void addDirection(int direction) {
            if (directions.contains(direction))
                throw new GuardianInLoopException();

            directions.add(direction);
        }

        @Override
        void setGuardian(Guardian guardian) {
            guardian.position = this.position;
            this.addDirection(guardian.direction);
            this.guardian = guardian;
        }

        @Override
        public String toString() {
            return getGuardian().map(Guardian::toString).orElseGet(() -> directions.stream().map(dir -> switch (dir) {
                case 0, 2 -> "|";
                case 1, 3 -> "-";
                default -> throw new IllegalStateException("Unexpected value: " + dir);
            }).distinct().reduce((a, b) -> "+").orElse("."));
        }
    }



    public static class GuardianInLoopException extends RuntimeException {
        public GuardianInLoopException() {
            super("Guardian in loop");
        }
    }

    public static class GuardianIllegalMoveException extends RuntimeException {
        public GuardianIllegalMoveException() {
            super("Guardian illegal move");
        }
    }

    public static class GuardianOutOfBoundsException extends RuntimeException {
        public GuardianOutOfBoundsException() {
            super("Guardian out of bounds");
        }
    }

}
