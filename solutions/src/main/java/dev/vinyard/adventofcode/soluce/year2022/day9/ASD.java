package dev.vinyard.adventofcode.soluce.year2022.day9;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class ASD {

    public static class Root {

        private final List<Move> moves;

        public Root(List<Move> moves) {
            this.moves = moves;
        }

        public long part1() {
            Rope rope = new Rope();

            moves.forEach(move -> IntStream.range(0, move.distance()).forEach(i -> rope.move(move.direction)));

            return rope.visited.size();
        }
    }

    public static class Rope {
        Point head = new Point(0, 0);
        Point tail = new Point(0, 0);

        private final Set<Point> visited = new HashSet<>();

        public Rope() {
            visited.add(new Point(tail));
        }

        public void move(Direction direction) {
            Point previousHead = new Point(head);
            head = direction.move(head);

            if (head.distance(tail) >= 2) {
                tail = previousHead;
                visited.add(new Point(tail));
            }
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
