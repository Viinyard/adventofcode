package dev.vinyard.adventofcode.soluce.year2023.day21;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.jgrapht.traverse.BreadthFirstIterator;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ASD {

    public static class Root {

        private final int steps;
        private final Grid grid;

        public Root(Grid grid, int steps) {
            this.grid = grid;
            this.steps = steps;
        }

        public long countReachablePlotsFromElve() {
            Cell elve = grid.cells.stream().filter(Elve.class::isInstance).findAny().orElseThrow();
            Set<Cell> reachable = grid.findNodesWithinSteps(elve, steps);

            return reachable.size();
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

        private static Graph<Cell, DefaultEdge> createEmptyGraph() {
            return GraphTypeBuilder.<Cell, DefaultEdge>directed()
                    .allowingMultipleEdges(false)
                    .allowingSelfLoops(true)
                    .edgeClass(DefaultEdge.class)
                    .weighted(false)
                    .buildGraph();
        }

        private Cell getCellAt(Point point) {
            return grid[point.y][point.x];
        }

        private List<Cell> getNeighbours(Cell cell) {
            return Arrays.stream(Direction.values())
                    .map(d -> d.move(cell.position))
                    .filter(bounds::contains)
                    .map(this::getCellAt)
                    .filter(e -> (e instanceof Plot || e instanceof Elve))
                    .toList();
        }

        private Graph<Cell, DefaultEdge> buildGraph() {
            Graph<Cell, DefaultEdge> graph = createEmptyGraph();

            this.cells.forEach(graph::addVertex);

            graph.vertexSet().forEach(cell -> getNeighbours(cell).forEach(n -> graph.addEdge(cell, n)));

            return graph;
        }

        public Set<Cell> findNodesWithinSteps(Cell startNode, int maxSteps) {
            Graph<Cell, DefaultEdge> graph = buildGraph();
            BreadthFirstIterator<Cell, DefaultEdge> iterator = new BreadthFirstIterator<>(graph, startNode);

            Set<Cell> result = new HashSet<>();

            while (iterator.hasNext()) {
                Cell currentCell = iterator.next();

                if (iterator.getDepth(currentCell) % 2 == maxSteps % 2)
                    result.add(currentCell);

                if (iterator.getDepth(currentCell) > maxSteps)
                    break;
            }

            return result;
        }
    }

    public static abstract class Cell {

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
