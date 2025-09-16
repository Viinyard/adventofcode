package dev.vinyard.adventofcode.soluce.year2022.day12;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ManyToManyShortestPathsAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraManyToManyShortestPaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ASD {

    public static class Root {

        private final List<Square> squares;
        private final Dimension dimension;

        private final Square[][] grid;
        private final Rectangle bounds;

        public Root(List<Square> squares, Dimension dimension) {
            this.squares = squares;
            this.dimension = dimension;

            this.bounds = new Rectangle(0, 0, dimension.width, dimension.height);
            this.grid = new Square[dimension.height][dimension.width];

            this.squares.forEach(s -> grid[s.position.y][s.position.x] = s);
        }

        private Graph<Square, DefaultWeightedEdge> createEmptyGraph() {
            return GraphTypeBuilder.<Square, DefaultWeightedEdge>directed()
                    .allowingMultipleEdges(false)
                    .allowingSelfLoops(false)
                    .edgeClass(DefaultWeightedEdge.class)
                    .weighted(true)
                    .buildGraph();
        }

        public Optional<Square> getSquareAt(Point position) {
            return Optional.of(position).filter(bounds::contains).map(p -> grid[p.y][p.x]);
        }

        public List<Square> getNeighbors(Square square) {
            return Arrays.stream(Direction.values())
                    .map(d -> d.move(square.position))
                    .map(this::getSquareAt)
                    .flatMap(Optional::stream)
                    .filter(s -> s.getElevationValue() - square.getElevationValue() <= 1)
                    .toList();
        }

        private Graph<Square, DefaultWeightedEdge> buildGraph() {
            Graph<Square, DefaultWeightedEdge> graph = createEmptyGraph();

            this.squares.forEach(graph::addVertex);

            this.squares.forEach(s -> getNeighbors(s).forEach(n -> {
                DefaultWeightedEdge edge = graph.addEdge(s, n);
                graph.setEdgeWeight(edge, 1);
            }));

            return graph;
        }

        public long part1() {
            Square start = squares.stream().filter(s -> s.elevation.equals("S")).findAny().orElseThrow();
            Square end = squares.stream().filter(s -> s.elevation.equals("E")).findAny().orElseThrow();

            return getShortestPathLength(start, end).getLength();
        }

        public long part2() {
            Set<Square> starts = squares.stream().filter(s -> s.getElevationValue() == 0).collect(Collectors.toSet());
            Square end = squares.stream().filter(s -> s.elevation.equals("E")).findAny().orElseThrow();

            Graph<Square, DefaultWeightedEdge> graph = buildGraph();

            DijkstraManyToManyShortestPaths<Square, DefaultWeightedEdge> dijkstraManyToManyShortestPaths = new DijkstraManyToManyShortestPaths<>(graph);

            ManyToManyShortestPathsAlgorithm.ManyToManyShortestPaths<Square, DefaultWeightedEdge> manyToManyPaths = dijkstraManyToManyShortestPaths.getManyToManyPaths(starts, Set.of(end));

            return starts.stream()
                    .map(s -> manyToManyPaths.getPath(s, end))
                    .flatMap(Stream::ofNullable)
                    .mapToLong(GraphPath::getLength)
                    .min()
                    .orElseThrow();
        }

        public GraphPath<Square, DefaultWeightedEdge> getShortestPathLength(Square start, Square end) {
            Graph<Square, DefaultWeightedEdge> graph = buildGraph();

            return DijkstraShortestPath.findPathBetween(graph, start, end);
        }
    }

    public static class Square {
        public final Point position;
        public final String elevation;

        public Square(Point position, String elevation) {
            this.position = position;
            this.elevation = elevation;
        }

        public int getElevationValue()    {
            return switch (elevation) {
                case "S" -> 0;
                case "E" -> 25;
                default -> elevation.charAt(0) - 'a';
            };
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
