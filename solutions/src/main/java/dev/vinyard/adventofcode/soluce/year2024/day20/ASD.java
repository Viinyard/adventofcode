package dev.vinyard.adventofcode.soluce.year2024.day20;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.AStarShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.IntPredicate;

public class ASD {

    public static class Root {

        private final Node[][] raceTrack;
        private final List<Node> nodes;
        private final Rectangle bounds;

        private final IntPredicate cheat;

        public Root(List<List<Node>> raceTrack, IntPredicate cheat) {
            this.raceTrack = raceTrack.stream().map(l -> l.toArray(Node[]::new)).toArray(Node[][]::new);
            this.nodes = raceTrack.stream().flatMap(List::stream).toList();
            this.bounds = new Rectangle(this.raceTrack.length, this.raceTrack[0].length);
            this.cheat = cheat;
        }

        private static Graph<Node, DefaultEdge> createEmptyGraph() {
            return GraphTypeBuilder.<Node, DefaultEdge>directed()
                    .allowingMultipleEdges(false)
                    .allowingSelfLoops(false)
                    .edgeClass(DefaultEdge.class)
                    .weighted(false)
                    .buildGraph();
        }

        private Node getNodeAt(Point point) {
            return raceTrack[point.y][point.x];
        }

        private List<Node> getNeighbours(Node node) {
            return getNeighbours(node, 1);
        }

        private List<Node> getNeighbours(Node node, int distance) {
            return Arrays.stream(Direction.values())
                    .map(d -> d.move(node.position, distance))
                    .filter(bounds::contains)
                    .map(this::getNodeAt)
                    .filter(Empty.class::isInstance)
                    .toList();
        }

        private Graph<Node, DefaultEdge> buildGraph() {
            Graph<Node, DefaultEdge> graph = createEmptyGraph();

            this.nodes.stream()
                    .filter(Empty.class::isInstance)
                    .forEach(graph::addVertex);

            graph.vertexSet().forEach(node -> getNeighbours(node).forEach(n -> graph.addEdge(node, n)));

            return graph;
        }

        private GraphPath<Node, DefaultEdge> getShortestPath() {
            Graph<Node, DefaultEdge> graph = buildGraph();

            Node start = nodes.stream().filter(Start.class::isInstance).findFirst().orElseThrow();
            Node end = nodes.stream().filter(End.class::isInstance).findFirst().orElseThrow();

            ShortestPathAlgorithm<Node, DefaultEdge> shortestPathAlgorithm = new AStarShortestPath<>(graph, (n1, n2) -> n1.position.distance(n2.position));

            return shortestPathAlgorithm.getPath(start, end);
        }

        public long countCheat() {
            GraphPath<Node, DefaultEdge> shortestPath = getShortestPath();

            List<Node> shortestPathNodes = shortestPath.getVertexList();

            long count = 0;

            for (Node node : shortestPathNodes) {
                count += this.getNeighbours(node, 2).stream()
                        .filter(Empty.class::isInstance)
                        .mapToInt(n -> shortestPathNodes.indexOf(n) - shortestPathNodes.indexOf(node) - 2)
                        .filter(cheat)
                        .count();
            }

            return count;
        }

    }

    public static sealed class Node permits Empty, Wall {

        protected final Point position;

        public Node(Point position) {
            this.position = position;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Node node)) return false;
            return Objects.equals(position, node.position);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(position);
        }
    }

    public static sealed class Empty extends Node permits End, Start {

        public Empty(Point position) {
            super(position);
        }

        @Override
        public String toString() {
            return ".";
        }
    }

    public static final class Start extends Empty {

        public Start(Point position) {
            super(position);
        }

        @Override
        public String toString() {
            return "S";
        }
    }

    public static final class End extends Empty {

        public End(Point position) {
            super(position);
        }

        @Override
        public String toString() {
            return "E";
        }
    }

    public static final class Wall extends Node {

        public Wall(Point position) {
            super(position);
        }

        @Override
        public String toString() {
            return "#";
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
