package dev.vinyard.adventofcode.soluce.year2023.day23;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ASD {

    public static class Root {

        private final Grid grid;

        public Root(Grid grid) {
            this.grid = grid;
        }

        public long findLongestPath() {
            return grid.findLongestPath();
        }
    }

    public static class Grid {

        private final List<Cell> cells;
        private final Rectangle bounds;
        private final Cell[][] matrix;

        public Grid(List<Cell> cells, Dimension dimension) {
            this.cells = cells;
            this.bounds = new Rectangle(dimension);
            this.matrix = new Cell[dimension.height][dimension.width];

            this.cells.forEach(cell -> this.matrix[cell.position.y][cell.position.x] = cell);
        }

        public Cell getCellAt(Point point) {
            if (bounds.contains(point))
                return matrix[point.y][point.x];
            return null;
        }

        private static Graph<Cell, DefaultEdge> createEmptyGraph() {
            return GraphTypeBuilder.<Cell, DefaultEdge>directed()
                    .allowingMultipleEdges(false)
                    .allowingSelfLoops(false)
                    .edgeClass(DefaultEdge.class)
                    .weighted(true)
                    .buildGraph();
        }

        private Graph<Cell, DefaultEdge> buildGraph() {
            Graph<Cell, DefaultEdge> graph = createEmptyGraph();

            this.cells.forEach(graph::addVertex);

            this.cells.forEach(cell -> cell.getNeighbours(this).forEach(n -> {
                DefaultEdge defaultEdge = graph.addEdge(cell, n);
                graph.setEdgeWeight(defaultEdge, 1);
            }));

            return graph;
        }

        private long findLongestPath() {
            Cell start = this.cells.get(1);
            Cell end = this.cells.get(this.cells.size() - 2);

            AllDirectedPaths<Cell, DefaultEdge> allDirectedPaths = new AllDirectedPaths<>(buildGraph());

            return allDirectedPaths.getAllPaths(start, end, true, null).stream().mapToLong(GraphPath::getLength).max().orElseThrow();
        }
    }

    public static abstract class Cell {
        protected final Point position;

        public Cell(Point position) {
            this.position = position;
        }

        protected List<Cell> getNeighbours(Grid grid) {
            return Arrays.stream(Direction.values())
                    .map(d -> d.move(this.position))
                    .filter(grid.bounds::contains)
                    .map(grid::getCellAt)
                    .filter(e -> !(e instanceof Forest))
                    .toList();
        }


    }

    public static class Path extends Cell {
        public Path(Point position) {
            super(position);
        }
    }

    public static class Forest extends Cell {
        public Forest(Point position) {
            super(position);
        }
    }

    public static class SteepSlopeNorth extends Cell {
        public SteepSlopeNorth(Point position) {
            super(position);
        }

        @Override
        protected List<Cell> getNeighbours(Grid grid) {
            return Stream.of(Direction.NORTH)
                    .map(d -> d.move(this.position))
                    .filter(grid.bounds::contains)
                    .map(grid::getCellAt)
                    .filter(e -> !(e instanceof Forest))
                    .toList();
        }
    }

    public static class SteepSlopeSouth extends Cell {
        public SteepSlopeSouth(Point position) {
            super(position);
        }

        @Override
        protected List<Cell> getNeighbours(Grid grid) {
            return Stream.of(Direction.SOUTH)
                    .map(d -> d.move(this.position))
                    .filter(grid.bounds::contains)
                    .map(grid::getCellAt)
                    .filter(e -> !(e instanceof Forest))
                    .toList();
        }
    }

    public static class SteepSlopeEast extends Cell {
        public SteepSlopeEast(Point position) {
            super(position);
        }

        @Override
        protected List<Cell> getNeighbours(Grid grid) {
            return Stream.of(Direction.EAST)
                    .map(d -> d.move(this.position))
                    .filter(grid.bounds::contains)
                    .map(grid::getCellAt)
                    .filter(e -> !(e instanceof Forest))
                    .toList();
        }
    }

    public static class SteepSlopeWest extends Cell {
        public SteepSlopeWest(Point position) {
            super(position);
        }

        @Override
        protected List<Cell> getNeighbours(Grid grid) {
            return Stream.of(Direction.WEST)
                    .map(d -> d.move(this.position))
                    .filter(grid.bounds::contains)
                    .map(grid::getCellAt)
                    .filter(e -> !(e instanceof Forest))
                    .toList();
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
