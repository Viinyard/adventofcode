package dev.vinyard.adventofcode.soluce.year2023.day23;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ASD {

    public static class Root {

        private final Grid grid;

        public Root(Grid grid) {
            this.grid = grid;
        }

        public long findLongestPathPart1() {
            return grid.findLongestPathPart1();
        }

        public Long findLongestPathPart2() {
            return grid.findLongestPathPart2();
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

        private static Graph<Cell, DefaultWeightedEdge> createEmptyGraph() {
            return GraphTypeBuilder.<Cell, DefaultWeightedEdge>directed()
                    .allowingMultipleEdges(false)
                    .allowingSelfLoops(false)
                    .edgeClass(DefaultWeightedEdge.class)
                    .weighted(true)
                    .buildGraph();
        }

        private Graph<Cell, DefaultWeightedEdge> buildGraph(Function<Cell, List<Cell>> getNeighbours) {
            Graph<Cell, DefaultWeightedEdge> graph = createEmptyGraph();

            Set<Cell> vertices = this.cells.stream().filter(Path.class::isInstance).filter(cell -> cell.getNeighbours(this).size() != 2).collect(Collectors.toSet());

            vertices.forEach(graph::addVertex);

            for (Cell from : vertices) {
                for (Cell neighbour : getNeighbours.apply(from)) {
                    Node to = getPathLengthToNextIntersection(new Node(neighbour, 1), new HashSet<>(Set.of(from)), getNeighbours);
                    if (Objects.nonNull(to)) {
                        DefaultWeightedEdge defaultWeightedEdge = graph.addEdge(from, to.cell);
                        graph.setEdgeWeight(defaultWeightedEdge, to.length);
                    }
                }
            }

            return graph;
        }

        public Node getPathLengthToNextIntersection(Node node, Set<Cell> visited, Function<Cell, List<Cell>> getNeighbours) {
            Cell current = node.cell;
            visited.add(current);

            List<Cell> neighbours = getNeighbours.apply(current).stream().filter(n -> !visited.contains(n)).toList();

            if (neighbours.isEmpty()) {
                if (current instanceof Path)
                    return node;
            } else if (neighbours.size() > 1) {
                return node;
            } else {
                return getPathLengthToNextIntersection(new Node(neighbours.getFirst(), node.length + 1), visited, getNeighbours);
            }

            return null;
        }

        public record Node(Cell cell, long length) {}

        private long findLongestPathPart1() {
            Cell start = this.cells.get(1);
            Cell end = this.cells.get(this.cells.size() - 2);

            AllDirectedPaths<Cell, DefaultWeightedEdge> allDirectedPaths = new AllDirectedPaths<>(buildGraph(n -> n.getNeighbours(this)));

            return (long) allDirectedPaths.getAllPaths(start, end, true, null).stream().mapToDouble(GraphPath::getWeight).max().orElseThrow();
        }

        protected List<Cell> getNeighbours(Cell cell) {
            return Arrays.stream(Direction.values())
                    .map(d -> d.move(cell.position))
                    .filter(this.bounds::contains)
                    .map(this::getCellAt)
                    .filter(e -> !(e instanceof Forest))
                    .toList();
        }

        private long findLongestPathPart2() {
            Cell start = this.cells.get(1);
            Cell end = this.cells.get(this.cells.size() - 2);

            AllDirectedPaths<Cell, DefaultWeightedEdge> allDirectedPaths = new AllDirectedPaths<>(buildGraph(this::getNeighbours));

            return (long) allDirectedPaths.getAllPaths(start, end, true, null).stream().mapToDouble(GraphPath::getWeight).max().orElseThrow();
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

        @Override
        public String toString() {
            return position.toString();
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
