package dev.vinyard.adventofcode.soluce.year2024.day18;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.AStarShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ASD {

    public static class Root {

        private final List<Point> coordinates;
        private final Rectangle bounds;
        private final int nbBits;

        public Root(List<Point> coordinates, int nbBits) {
            this.coordinates = coordinates;
            this.bounds = new Rectangle(
                    coordinates.stream().mapToInt(p -> p.x).max().orElseThrow() + 1,
                    coordinates.stream().mapToInt(p -> p.y).max().orElseThrow() + 1);
            this.nbBits = nbBits;
        }

        public List<Point> getNeighbours(Point point) {
            return Arrays.stream(Direction.values()).map(d -> d.move(point)).filter(bounds::contains).toList();
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

            Point start = new Point(bounds.x, bounds.y);
            Point end = new Point(bounds.width - 1, bounds.height - 1);

            this.coordinates.stream().limit(nbBits).forEach(graph::removeVertex);

            ShortestPathAlgorithm<Point, DefaultEdge> aStar = new AStarShortestPath<>(graph, Point2D::distance);

            return aStar.getPath(start, end).getLength();
        }

        public Optional<Point> getBlockingPoint() {
            Graph<Point, DefaultEdge> graph = buildGraph();

            Point start = new Point(bounds.x, bounds.y);
            Point end = new Point(bounds.width - 1, bounds.height - 1);

            ShortestPathAlgorithm<Point, DefaultEdge> aStar = new AStarShortestPath<>(graph, Point2D::distance);

            GraphPath<Point, DefaultEdge> path = aStar.getPath(start, end);

            for (Point point : this.coordinates) {
                graph.removeVertex(point);

                if (path.getVertexList().contains(point)) {
                    path = aStar.getPath(start, end);

                    if (path == null) {
                        return Optional.of(point);
                    }
                }
            }

            return Optional.empty();
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
