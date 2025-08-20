package dev.vinyard.adventofcode.soluce.year2023.day18;

import dev.vinyard.adventofcode.utils.PathUtils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.List;

public class ASD {

    public static class Root {

        private final List<Digger> diggers;

        public Root(List<Digger> diggers) {
            this.diggers = diggers;
        }

        public long calculateArea() {
            Path2D path = new Path2D.Double(Path2D.WIND_EVEN_ODD);

            Point origin = new Point(0, 0);
            path.moveTo(origin.x, origin.y);

            for (Digger digger : diggers) {
                Point current = new Point((int) path.getCurrentPoint().getX(), (int) path.getCurrentPoint().getY());
                Point dest = digger.direction.move(current, digger.length);
                path.lineTo(dest.x, dest.y);
            }

            path.closePath();

            return (long) (PathUtils.getArea(path) + PathUtils.getLength(path) / 2 + 1);
        }
    }



    public static class Digger {
        private Direction direction;
        private int length;

        public Digger(int color) {
            this.direction = switch (color & 0xF) {
                case 0 -> Direction.EAST;
                case 1 -> Direction.SOUTH;
                case 2 -> Direction.WEST;
                case 3 -> Direction.NORTH;
                default -> throw new IllegalArgumentException("Invalid direction color: " + color);
            };
            this.length = (color >> 4 & 0xFFFFF);
        }

        public Digger(Direction direction, int length) {
            this.direction = direction;
            this.length = length;
        }
    }

    public enum Direction {
        NORTH(270, '^'),
        EAST(0, '>'),
        SOUTH(90, 'v'),
        WEST(180, '<');

        final double rotation;
        final Character symbol;

        Direction(int rotation, Character symbol) {
            this.rotation = Math.toRadians(rotation);
            this.symbol = symbol;
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
