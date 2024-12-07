package dev.vinyard.adventofcode.soluce.year2024.day6;

import lombok.Getter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;

public class ASD {

    public record Map(Entity[][] grid) {

        public Rectangle getBounds() {
            return new Rectangle(grid[0].length, grid.length);
        }

        public Entity getEntity(Point point) {
            if (!getBounds().contains(point)) {
                throw new GuardianOutOfBoundsException();
            }
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

    public static abstract sealed class Entity permits Obstruction, Emplacement {

        protected Guardian guardian;
        protected final Point position;

        public Entity(Point position) {
            this.position = position;
        }

        Optional<Guardian> getGuardian() {
            return Optional.ofNullable(guardian);
        }

        void move(Guardian guardian) {
            throw new GuardianIllegalMoveException();
        }

        void setGuardian(Guardian guardian) {
            this.guardian = guardian;
        }
    }

    public static final class Guardian {

        @Getter
        private Vector vector;

        private Entity obstruction;

        private final Set<Vector> vectors = new HashSet<>();

        public Guardian(Vector vector) {
            this.vector = vector;
        }

        public Guardian obstruction(Entity obstruction) {
            this.obstruction = obstruction;
            return this;
        }

        public Guardian() {
            this(new Vector(new Point(0, 0), 0));
        }

        public void turnRight() {
            vector = new Vector(vector.position, vector.direction + 1 & 3);

        }

        public Vector getNextPosition() {
            Point nextPosition = new Point(vector.position);
            AffineTransform affineTransform = AffineTransform.getQuadrantRotateInstance(vector.direction, vector.position.x, vector.position.y);
            affineTransform.translate(0, -1);
            affineTransform.transform(vector.position, nextPosition);
            return new Vector(nextPosition, vector.direction);
        }

        private void setPosition(Point position) {
            this.vector = new Vector(position, vector.direction);
            if (vectors.contains(this.vector))
                throw new GuardianInLoopException();
            vectors.add(vector);
        }

        public Entity getEntity(Map map, Point position) {
            return Optional.ofNullable(obstruction).filter(e -> Objects.equals(e.position, position)).orElseGet(() -> map.getEntity(position));
        }

        public void move(Map map) {
            try {
                this.getEntity(map, getNextPosition().position).move(this);
            } catch (GuardianIllegalMoveException e) {
                turnRight();
            }
        }

        public Set<Vector> getVisitedPositions(Map map) {
            try {
                while (true) {
                    move(map);
                }
            } catch (GuardianOutOfBoundsException e) {
                return vectors;
            }
        }

        public boolean isStuck(Map map) {

            try {
                this.getVisitedPositions(map);
            } catch (GuardianInLoopException e) {
                return true;
            }

            return false;
        }
    }

    public static final class Obstruction extends Entity {

        public Obstruction(Point position) {
            super(position);
        }
    }

    public static final class Emplacement extends Entity {

        public Emplacement(Point position) {
            super(position);
        }

        @Override
        void move(Guardian guardian) {
            guardian.setPosition(position);
        }

        @Override
        public void setGuardian(Guardian guardian) {
            this.guardian = guardian;
            guardian.setPosition(position);
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

    public record Vector(Point position, int direction) {}
}
