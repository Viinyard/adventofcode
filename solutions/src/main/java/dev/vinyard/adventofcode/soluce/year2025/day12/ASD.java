package dev.vinyard.adventofcode.soluce.year2025.day12;

import java.awt.*;
import java.util.List;

public class ASD {

    public static class Root {

        private final List<Box> boxes;

        public Root(List<Box> boxes) {
            this.boxes = boxes;
        }

        public long solution1() {
            return boxes.stream()
                    .filter(Box::isValid)
                    .count();
        }
    }

    public static class Box {

        private Rectangle bounds;
        private List<Gift> gifts;

        public Box(int x, int y, List<Gift> gifts) {
            this.bounds = new Rectangle(x, y);
            this.gifts = gifts;
        }

        public boolean isValid() {
            int area = bounds.width * bounds.height;
            long filledCells = gifts.stream()
                    .mapToLong(gift -> gift.times * gift.shape.getArea())
                    .sum();
            return filledCells <= area;
        }
    }

    public static class Gift {
        private final int times;
        private final Shape shape;

        public Gift(int times, Shape shape) {
            this.times = times;
            this.shape = shape;
        }

        public long getArea() {
            return times * shape.getArea();
        }
    }

    public static class Shape {

        private final List<Cell> cells;

        public Shape(List<Cell> cells) {
            this.cells = cells;
        }

        public long getArea() {
            return cells.size();
        }
    }

    public static sealed class Cell permits Filled, Empty {

        private final Point position;

        public Cell(int x, int y) {
            this.position = new Point(x, y);
        }
    }

    public static final class Filled extends Cell {

        public Filled(int x, int y) {
            super(x, y);
        }

    }

    public static final class Empty extends Cell {

        public Empty(int x, int y) {
            super(x, y);
        }

    }

}
