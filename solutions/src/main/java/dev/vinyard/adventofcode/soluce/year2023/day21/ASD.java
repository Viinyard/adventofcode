package dev.vinyard.adventofcode.soluce.year2023.day21;

import lombok.Getter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.List;
import java.util.function.Function;

public class ASD {

    public static class Root {

        private final int steps;
        private final Grid grid;

        public Root(Grid grid, int steps) {
            this.grid = grid;
            this.steps = steps;
        }

        public long countReachablePlotsFromElvePart1() {
            return grid.findNodesWithinSteps(steps);
        }

        public long countReachablePlotsFromElvePart2() {
            int steps = 26_501_365;

            LinkedList<Long> values = new LinkedList<>();

            int x = steps % (2 * grid.bounds.width);

            int offset = 0;
            while (true) {
                values.add(grid.findNodesWithinSteps(x));
                x += 2 * grid.bounds.width;

                if (values.size() >= 4) {
                    List<Long> fd = List.of(values.get(1) - values.get(0), values.get(2) - values.get(1), values.get(3) - values.get(2));
                    List<Long> sd = List.of(fd.get(1) - fd.get(0), fd.get(2) - fd.get(1));
                    if (Objects.equals(sd.get(0), sd.get(1))) {
                        break;
                    } else {
                        values.removeFirst();
                        offset++;
                    }
                }
            }

            long alpha = values.get(0);
            long beta = values.get(1);
            long gamma = values.get(2);

            long c = alpha;
            long a = (gamma - 2 * beta + c) / 2;
            long b = beta - c - a;

            Function<Integer, Long> quadratic = n -> a * n * n + b * n + c;

            int n = steps / (2 * grid.bounds.width);

            return quadratic.apply(n - offset);
        }

    }

    public static class Grid {

        private final List<Cell> cells;
        private final Cell[][] grid;
        private final Rectangle bounds;

        public Grid(List<Cell> cells, Dimension dimension) {
            this.cells = cells;
            this.bounds = new Rectangle(dimension);
            this.grid = new Cell[dimension.height][dimension.width];
            cells.forEach(cell -> this.grid[cell.position.y][cell.position.x] = cell);
        }

        private Cell getCellAt(Point point) {
            return grid[((point.y % bounds.height) + bounds.height) % bounds.height][((point.x % bounds.width) + bounds.width) % bounds.width];
        }

        private List<Point> getNeighbours(Point point) {
            return Arrays.stream(Direction.values())
                    .map(d -> d.move(point))
                    .filter(p -> !(getCellAt(p) instanceof Rock))
                    .toList();
        }

        public long findNodesWithinSteps(int maxSteps) {
            Point startNode = cells.stream().filter(Elve.class::isInstance).map(Cell::getPosition).findAny().orElseThrow();

            Set<Point> result = new HashSet<>();
            Set<Point> visited = new HashSet<>();
            Deque<Node> queue = new LinkedList<>();

            visited.add(startNode);
            queue.add(new Node(startNode, 0));

            while (!queue.isEmpty()) {
                Node current = queue.pollFirst();

                if (current.steps() % 2 == maxSteps % 2)
                    result.add(current.cell());

                if (current.steps() >= maxSteps)
                    continue;

                for (Point neighbor : getNeighbours(current.cell())) {
                    if (!visited.add(neighbor))
                        continue;

                    queue.add(new Node(neighbor, current.steps() + 1));
                }
            }

            return result.size();
        }
    }

    public record Node(Point cell, int steps) {}

    public static abstract class Cell {

        @Getter
        protected final Point position;

        public Cell(Point position) {
            this.position = position;
        }
    }

    public static class Rock extends Cell {
        public Rock(Point position) {
            super(position);
        }

        @Override
        public String toString() {
            return "#";
        }
    }

    public static class Plot extends Cell {
        public Plot(Point position) {
            super(position);
        }

        @Override
        public String toString() {
            return ".";
        }
    }

    public static class Elve extends Cell {
        public Elve(Point position) {
            super(position);
        }

        @Override
        public String toString() {
            return "S";
        }
    }

    public enum Direction {
        NORTH(270),
        EAST(0),
        SOUTH(90),
        WEST(180);

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
