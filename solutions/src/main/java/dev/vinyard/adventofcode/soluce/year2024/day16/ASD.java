package dev.vinyard.adventofcode.soluce.year2024.day16;

import lombok.Data;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.AStarShortestPath;
import org.jgrapht.alg.shortestpath.EppsteinShortestPathIterator;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ASD {

    public static class Root {

        private final Puzzle puzzle;

        private final Node startNode;
        private final Node endNode;

        private final Graph<Node, DefaultWeightedEdge> graph;

        public Root(Puzzle puzzle) {
            this.puzzle = puzzle;
            this.graph = buildGraph();

            Entity endEntity = findEnd();
            Entity startEntity = findStart();

            List<Node> endNodes = graph.vertexSet().stream().filter(n -> n.position().equals(endEntity.getPosition())).toList();

            this.endNode = new Node(endEntity);

            graph.addVertex(endNode);
            endNodes.stream().map(n -> graph.addEdge(n, endNode)).forEach(e -> graph.setEdgeWeight(e, 0));

            this.startNode = graph.vertexSet().stream().filter(n -> n.position().equals(startEntity.getPosition())).filter(n -> n.direction() == Direction.EAST).findAny().orElseThrow();
        }

        private Start findStart() {
            return puzzle.getEntities().stream().filter(Start.class::isInstance).map(Start.class::cast).findAny().orElseThrow();
        }

        private End findEnd() {
            return puzzle.getEntities().stream().filter(End.class::isInstance).map(End.class::cast).findAny().orElseThrow();
        }

        private static Graph<Node, DefaultWeightedEdge> createEmptyGraph() {
            return GraphTypeBuilder.<Node, DefaultWeightedEdge>directed()
                    .allowingMultipleEdges(false)
                    .allowingSelfLoops(false)
                    .edgeClass(DefaultWeightedEdge.class)
                    .weighted(true)
                    .buildGraph();
        }

        private Graph<Node, DefaultWeightedEdge> buildGraph() {
            Graph<Node, DefaultWeightedEdge> graph = createEmptyGraph();

            Node[][][] nodes = new Node[Direction.values().length][puzzle.getBounds().height][puzzle.getBounds().width];
            Arrays.stream(Direction.values()).flatMap(d -> puzzle.getEntities().stream().map(e -> new Node(e, d)))
                    .forEach(n -> nodes[n.direction.ordinal()][n.position().y][n.position().x] = n);

            Arrays.stream(nodes).flatMap(Arrays::stream).flatMap(Arrays::stream).forEach(graph::addVertex);

            Predicate<Node> isWall = n -> n.entity() instanceof Wall;
            Predicate<Node> isWalkable = isWall.negate();

            graph.vertexSet().stream().filter(isWalkable).forEach(node -> {
                Optional.of(node.position()).map(node.direction()::move)
                        .flatMap(puzzle::getEntityAt)
                        .map(e -> nodes[node.direction().ordinal()][e.getPosition().y][e.getPosition().x])
                        .filter(isWalkable)
                        .map(e -> graph.addEdge(node, e))
                        .ifPresent(e ->
                                graph.setEdgeWeight(e, 1)
                        );

                Node clockwise = nodes[(node.direction().ordinal() + 1) % Direction.values().length][node.position().y][node.position().x];
                graph.setEdgeWeight(graph.addEdge(node, clockwise), 1000);

                Node counterClockwise = nodes[(node.direction().ordinal() + 3) % Direction.values().length][node.position().y][node.position().x];
                graph.setEdgeWeight(graph.addEdge(node, counterClockwise), 1000);
            });

            return graph;
        }

        public long getShortestPath() {
            ShortestPathAlgorithm<Node, DefaultWeightedEdge> aStarShortestPath = new AStarShortestPath<>(this.graph, (node, v1) -> node.position().distance(v1.position()));

            return (long) aStarShortestPath.getPath(startNode, endNode).getWeight();
        }

        public long countEntitiesOfShortestsPath() {
            EppsteinShortestPathIterator<Node, DefaultWeightedEdge> eppsteinShortestPathIterator = new EppsteinShortestPathIterator<>(graph, startNode, endNode);

            final double shortestPathWeight = getShortestPath();

            return Stream.generate(eppsteinShortestPathIterator::next).takeWhile(e -> e.getWeight() <= shortestPathWeight)
                    .map(GraphPath::getVertexList).flatMap(List::stream).map(Node::entity).distinct().count();
        }
    }

    public record Node (Entity entity, Direction direction) {

        public Node(Entity entity) {
            this(entity, null);
        }

        public Point position() {
            return entity.getPosition();
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

        @Override
        public String toString() {
            return Arrays.stream(entities).map(e -> Arrays.stream(e).map(Entity::toString).collect(Collectors.joining())).collect(Collectors.joining("\n"));
        }
    }

    @Data
    public static sealed class Entity permits Wall, Empty, Start, End {
        protected final Point position;

        public Entity(Point position) {
            this.position = position;
        }
    }

    public static final class Wall extends Entity {
        public Wall(Point position) {
            super(position);
        }

        @Override
        public String toString() {
            return "#";
        }
    }

    public static final class Empty extends Entity {
        public Empty(Point position) {
            super(position);
        }

        @Override
        public String toString() {
            return ".";
        }
    }

    public static final class Start extends Entity {
        public Start(Point position) {
            super(position);
        }

        @Override
        public String toString() {
            return "S";
        }
    }

    public static final class End extends Entity {
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
