package dev.vinyard.adventofcode.soluce.year2024.day18;

import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.AStarShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.List;

public class ASD {

    public static class Root {

        private final List<Point> coordinates;
        private final boolean bits[][];
        private final Rectangle bounds;
        private final int nbBits;

        public Root(List<Point> coordinates, Dimension dimension, int nbBits) {
            this.coordinates = coordinates;
            this.bounds = new Rectangle(dimension);
            this.nbBits = nbBits;
            this.bits = new boolean[dimension.width][dimension.height];
            this.coordinates.stream().limit(nbBits).forEach(p -> bits[p.x][p.y] = true);
        }

        public Root(List<Point> coordinates) {
            this(coordinates, new Dimension(71, 71), 1024);
        }

        public boolean isCorrupted(Point point) {
            return bits[point.x][point.y];
        }

        public List<Point> getNeighbours(Point point) {
            return Arrays.stream(Direction.values()).map(d -> d.move(point)).filter(bounds::contains).filter(p -> !isCorrupted(p)).toList();
        }

        private static Graph<Point, DefaultEdge> createEmptyGraph() {
            return GraphTypeBuilder.<Point, DefaultEdge>directed()
                    .allowingMultipleEdges(false)
                    .allowingSelfLoops(false)
                    .edgeClass(DefaultEdge.class)
                    .weighted(false)
                    .buildGraph();
        }

        private Graph<Point, DefaultEdge> buildGraph() {
            Graph<Point, DefaultEdge> graph = createEmptyGraph();

            for (int x = 0; x < bounds.width; x++) {
                for (int y = 0; y < bounds.height; y++) {
                    Point point = new Point(x, y);
                    graph.addVertex(point);
                }
            }

            for (Point point : graph.vertexSet()) {
                getNeighbours(point).forEach(n -> graph.addEdge(point, n));
            }

            return graph;
        }

        public long calculateShortestPath() {
            Graph<Point, DefaultEdge> graph = buildGraph();

            Point start = graph.vertexSet().stream().filter(p -> p.equals(new Point(bounds.x, bounds.y))).findAny().orElseThrow();
            Point end = graph.vertexSet().stream().filter(p -> p.equals(new Point(bounds.width - 1, bounds.height - 1))).findAny().orElseThrow();

            ShortestPathAlgorithm<Point, DefaultEdge> aStar = new AStarShortestPath<>(graph, (s, e) -> s.distance(e));

            return aStar.getPath(start, end).getLength();
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
            AffineTransform affineTransform = AffineTransform.getRotateInstance(rotation, point.x, point.y);
            affineTransform.translate(1, 0);
            Point moved = new Point();
            affineTransform.transform(point, moved);
            return moved;
        }
    }

}
