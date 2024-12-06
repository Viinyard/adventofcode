package dev.vinyard.adventofcode.soluce.year2024.day6;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class ASD {

    public record Root(Map map) {


    }

    public sealed interface Entity permits Guardian, Obstruction, Emplacement {

    }

    public record Map(Entity[][] grid) {}

    public record Guardian() implements Entity {
        @Override
        public String toString() {
            return "^";
        }
    }
    public record Obstruction() implements Entity {
        @Override
        public String toString() {
            return "#";
        }
    }
    public record Emplacement() implements Entity {
        @Override
        public String toString() {
            return ".";
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
