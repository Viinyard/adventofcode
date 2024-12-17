package dev.vinyard.adventofcode.soluce.year2024.day16;

import lombok.Data;
import lombok.Getter;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.AStarShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ASD {

    public record Root(Puzzle puzzle) {

        public Start findStart() {
            return puzzle.getEntities().stream().filter(Start.class::isInstance).map(Start.class::cast).findAny().orElseThrow();
        }

        public End findEnd() {
            return puzzle.getEntities().stream().filter(End.class::isInstance).map(End.class::cast).findAny().orElseThrow();
        }

        public Node getNodeAt(Point position, Direction direction, Map<Direction, Node[][]> nodesMap) {
            return nodesMap.get(direction)[position.y][position.x];
        }

        public static Graph<Node, DefaultWeightedEdge> createEmptyGraph() {
            return GraphTypeBuilder.<Node, DefaultWeightedEdge>undirected()
                    .allowingMultipleEdges(true)
                    .allowingSelfLoops(false)
                    .edgeClass(DefaultWeightedEdge.class)
                    .weighted(true)
                    .buildGraph();
        }

        public Graph<Node, DefaultWeightedEdge> buildGraph() {
            Graph<Node, DefaultWeightedEdge> graph = createEmptyGraph();

            Node[][][] nodes = new Node[Direction.values().length][puzzle.getBounds().height][puzzle.getBounds().width];
            Arrays.stream(Direction.values()).flatMap(d -> puzzle.getEntities().stream().map(e -> new Node(e, e.position, d)))
                    .forEach(n -> nodes[n.direction.ordinal()][n.position.y][n.position.x] = n);

            Arrays.stream(nodes).flatMap(Arrays::stream).flatMap(Arrays::stream).forEach(graph::addVertex);

            Predicate<Node> isWall = n -> n.getEntity() instanceof Wall;
            Predicate<Node> isWalkable = isWall.negate();

            graph.vertexSet().stream().filter(isWalkable).forEach(node -> {
                Optional.of(node.getPosition()).map(node.getDirection()::move)
                        .flatMap(puzzle()::getEntityAt)
                        .map(e -> nodes[node.getDirection().ordinal()][e.getPosition().y][e.getPosition().x])
                        .filter(isWalkable)
                        .map(e -> graph.addEdge(node, e))
                        .ifPresent(e ->
                                graph.setEdgeWeight(e, 1)
                        );

                Node clockwise = nodes[(node.getDirection().ordinal() + 1) % Direction.values().length][node.getPosition().y][node.getPosition().x];
                graph.setEdgeWeight(graph.addEdge(node, clockwise), 1000);

                Node counterClockwise = nodes[(node.getDirection().ordinal() + 3) % Direction.values().length][node.getPosition().y][node.getPosition().x];
                graph.setEdgeWeight(graph.addEdge(node, counterClockwise), 1000);
            });

            return graph;
        }

        public long getShortestPath(Point start, Point end) {
            Graph<Node, DefaultWeightedEdge> graph = buildGraph();

            List<Node> endNodes = graph.vertexSet().stream().filter(n -> n.getPosition().equals(end)).toList();

            Entity endEntity = puzzle.getEntityAt(end).orElseThrow();
            Node endNode = new Node(endEntity, end);
            graph.addVertex(endNode);

            endNodes.stream().map(n -> graph.addEdge(n, endNode)).forEach(e -> graph.setEdgeWeight(e, 0));

            Node startNode = graph.vertexSet().stream().filter(n -> n.getPosition().equals(start)).filter(n -> n.getDirection() == Direction.EAST).findAny().orElseThrow();

            ShortestPathAlgorithm<Node, DefaultWeightedEdge> aStarShortestPath = new AStarShortestPath<>(graph, (node, v1) -> node.getPosition().distance(v1.getPosition()));

            return (long) aStarShortestPath.getPath(startNode, endNode).getWeight();
        }
    }

    @Getter
    public static class Node {
        private final Entity entity;
        private final Point position;
        private final Direction direction;

        public Node(Entity entity, Point position) {
            this(entity, position, null);
        }

        public Node(Entity entity, Point position, Direction direction) {
            this.entity = entity;
            this.position = position;
            this.direction = direction;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(position, node.position) && direction == node.direction;
        }

        @Override
        public int hashCode() {
            return Objects.hash(position, direction);
        }
    }

    public record Puzzle(Entity[][] entities) {

        public Rectangle getBounds() {
            return new Rectangle(0, 0, entities[0].length, entities.length);
        }

        public Optional<Entity> getEntityAt(Point position) {
            return Optional.of(position).filter(getBounds()::contains).map(p -> entities[p.y][p.x]);
        }

        public List<Entity> getEntities() {
            return Arrays.stream(entities).flatMap(Arrays::stream).toList();
        }

        public String[][] toStringArray() {
            return Arrays.stream(entities).map(e -> Arrays.stream(e).map(Entity::toString).toArray(String[]::new)).toArray(String[][]::new);
        }

        @Override
        public String toString() {
            return Arrays.stream(entities).map(e -> Arrays.stream(e).map(Entity::toString).collect(Collectors.joining())).collect(Collectors.joining("\n"));
        }
    }

    @Data
    public static class Entity {
        protected long distance = Long.MAX_VALUE;
        protected final Point position;

        public Entity(Point position) {
            this.position = position;
        }
    }

    public static class Wall extends Entity {
        public Wall(Point position) {
            super(position);
        }

        @Override
        public String toString() {
            return "#";
        }
    }

    public static class Empty extends Entity {
        public Empty(Point position) {
            super(position);
        }

        @Override
        public String toString() {
            return ".";
        }
    }

    public static class Start extends Entity {
        public Start(Point position) {
            super(position);
        }

        @Override
        public String toString() {
            return "S";
        }
    }

    public static class End extends Entity {
        public End(Point position) {
            super(position);
        }

        @Override
        public String toString() {
            return "E";
        }
    }

    public enum Direction {
        NORTH(270, "^"),
        EAST(0, ">"),
        SOUTH(90, "v"),
        WEST(180, "<");

        final double rotation;
        final String symbol;

        Direction(int rotation, String symbol) {
            this.rotation = Math.toRadians(rotation);
            this.symbol = symbol;
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
