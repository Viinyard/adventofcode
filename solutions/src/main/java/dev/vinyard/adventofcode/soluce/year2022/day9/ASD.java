package dev.vinyard.adventofcode.soluce.year2022.day9;

import lombok.Setter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

public class ASD {

    public static class Root {

        private final List<Move> moves;

        public Root(List<Move> moves) {
            this.moves = moves;
        }

        public long part1() {
            Head head = new Head();
            Tail tail = new Tail();
            head.setNext(tail);

            moves.forEach(move -> IntStream.range(0, move.distance()).forEach(i -> head.move(move.direction)));

            return tail.visited.size();
        }

        public long part2() {
            Head head = new Head();

            Knot previous = head;
            for (int i = 0; i < 9; i++) {
                Tail tail = new Tail();
                previous.setNext(tail);
                previous = tail;
            }

            moves.forEach(move -> IntStream.range(0, move.distance()).forEach(i -> head.move(move.direction)));

            return previous.visited.size();
        }
    }

    public static abstract class Knot {

        protected Point position = new Point(0, 0);

        protected final Set<Point> visited = new HashSet<>();

        @Setter
        protected Knot next;

        public Knot() {
            visited.add(new Point(position));
        }

        public abstract void move(Point point);
    }

    public static class Tail extends  Knot {

        @Override
        public void move(Point point) {
            int dx = point.x - position.x;
            int dy = point.y - position.y;

            if (Math.abs(dx) <= 1 && Math.abs(dy) <= 1)
                return;

            if (dx == 0)
                position.translate(0, dy / 2);
            else if (dy == 0)
                position.translate(dx / 2, 0);
            else
                position.translate(dx > 0 ? 1 : -1, dy > 0 ? 1 : -1);

            visited.add(new Point(position));

            Optional.ofNullable(next).ifPresent(next -> next.move(position));
        }
    }

    public static class Head extends Knot {

        public void move(Direction direction) {
            this.move(direction.move(this.position));
        }

        public void move(Point point) {
            position = point;
            visited.add(new Point(position));
            Optional.ofNullable(next).ifPresent(next -> next.move(position));
        }
    }

    public record Move(Direction direction, int distance) {

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
            return move(point, 1);
        }

        public Point move(Point point, int distance) {
            AffineTransform affineTransform = AffineTransform.getRotateInstance(rotation, point.x, point.y);
            affineTransform.translate(distance, 0);
            Point moved = new Point();
            affineTransform.transform(point, moved);
            return moved;
        }
    }
}
