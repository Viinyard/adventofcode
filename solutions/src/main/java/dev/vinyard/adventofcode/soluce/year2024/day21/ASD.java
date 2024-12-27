package dev.vinyard.adventofcode.soluce.year2024.day21;

import lombok.Getter;
import lombok.Setter;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.BFSShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ASD {

    public static class Root {

        private final List<Code> codes;

        public Root(List<Code> codes) {
            this.codes = codes;
        }

        public long calculateComplexity(int numberOfRobots) {
            final Digicode digicode = new Digicode().withRobots(numberOfRobots).build();

            return codes.stream()
                    .mapToLong(code -> {
                        String command = digicode.type(code.getCode());
                        System.out.println("Code : " + code.getCode() + " - Command : " + command);
                        return command.length() * code.getDigits();
                    })
                    .sum();
        }
    }

    public static class Code {

        private final String code;

        public Code(String code) {
            this.code = code;
        }

        public Long getDigits() {
            return Long.parseLong(code);
        }

        public String getCode() {
            return code + "A";
        }
    }

    @Setter
    public static abstract class RequestHandler {

        protected RequestHandler next;

        private RequestHandler() {

        }

        public abstract Graph<DepthVertex, KeyEdge> buildGraph(Graph<DepthVertex, KeyEdge> graph);
    }

    public static abstract class AbstractKeypad extends RequestHandler {

        private final String[][] keypad;

        protected final Map<String, Point> buttons;

        private final Rectangle bounds;

        public AbstractKeypad(String[][] keypad) {
            this.keypad = keypad;
            this.buttons = buildButtons();
            this.bounds = new Rectangle(keypad[0].length, keypad.length);
        }

        protected String getKeyAt(Point position) {
            return keypad[position.y][position.x];
        }

        private Map<String, Point> buildButtons() {
            Map<String, Point> buttons = new HashMap<>();
            for (int y = 0; y < keypad.length; y++) {
                for (int x = 0; x < keypad[y].length; x++) {
                    if (keypad[y][x] != null)
                        buttons.put(keypad[y][x], new Point(x, y));
                }
            }
            return buttons;
        }

        protected Map<Direction, String> getNeighbours(Point position) {
            return Arrays.stream(Direction.values())
                    .filter(d -> {
                        Point p = d.move(position);
                        return bounds.contains(p) && getKeyAt(p) != null;
                    })
                    .collect(Collectors.toMap(Function.identity(), d -> getKeyAt(d.move(position))));
        }

        protected Graph<DepthVertex, KeyEdge> createEmptyGraph() {
            return GraphTypeBuilder.<DepthVertex, KeyEdge>directed()
                    .allowingMultipleEdges(false)
                    .allowingSelfLoops(false)
                    .edgeClass(KeyEdge.class)
                    .weighted(true)
                    .buildGraph();
        }


    }

    public static class Digicode extends AbstractKeypad {

        private String key = "A";

        private ShortestPathAlgorithm<DepthVertex, KeyEdge> shortestPathAlgorithm;

        public Digicode() {
            super(new String[][]{
                    {"7", "8", "9"},
                    {"4", "5", "6"},
                    {"1", "2", "3"},
                    {null, "0", "A"}
            });
        }

        public Digicode withRobots(int numberOfRobots) {
            Stack<RequestHandler> handlers = Stream.generate(Robot::new).limit(numberOfRobots).collect(Collectors.toCollection(Stack::new));

            handlers.stream().reduce((first, second) -> {
                first.setNext(second);
                return second;
            });

            this.setNext(handlers.getFirst());

            return this;
        }

        public Digicode build() {
            Graph<DepthVertex, KeyEdge> graph = buildGraph(createEmptyGraph());
            System.out.println("Vertices = " + graph.vertexSet().size());
            System.out.println("Edges = " + graph.edgeSet().size());
            this.shortestPathAlgorithm = new BFSShortestPath<>(graph);

            return this;
        }

        public Graph<DepthVertex, KeyEdge> buildGraph(Graph<DepthVertex, KeyEdge> graph) {
            int pressedLayer = 0;
            int releasedLayer = 1;

            // Layer 0 : The pressed button
            buttons.keySet().stream().map(k -> new DepthVertex(k, pressedLayer)).forEach(graph::addVertex);

            // Layer 1 : The released button (position of the arm)
            buttons.keySet().stream().map(k -> new DepthVertex(k, releasedLayer)).forEach(graph::addVertex);

            // Add the edges between the pressed and released buttons
            // Going from pressed to released cost 0
            buttons.keySet().forEach(n -> graph.addEdge(new DepthVertex(n, pressedLayer), new DepthVertex(n, releasedLayer), new KeyEdge()));
            // Going from released to pressed cost 1 (A)
            buttons.keySet().forEach(n -> graph.addEdge(new DepthVertex(n, releasedLayer), new DepthVertex(n, pressedLayer), new KeyEdge("A", 1)));

            // Add the edges between the released buttons
            buttons.forEach((key, position) ->
                    getNeighbours(position).forEach((direction, neighbourKey) ->
                            graph.addEdge(
                                    new DepthVertex(key, releasedLayer),
                                    new DepthVertex(neighbourKey, releasedLayer),
                                    new KeyEdge(direction.symbol, releasedLayer))
                    )
            );

            return Optional.ofNullable(next).map(n -> n.buildGraph(graph)).orElse(graph);
        }

        private String getShortestPathTo(String digit) {
            String command = this.shortestPathAlgorithm.getPath(new DepthVertex(this.key, 0), new DepthVertex(digit, 0)).getEdgeList().stream().map(KeyEdge::toString).collect(Collectors.joining(""));
            key = digit;
            return command;
        }

        public String type(String command) {
            return Arrays.stream(command.split(""))
                    .filter(s -> !s.isBlank())
                    .map(this::getShortestPathTo)
                    .collect(Collectors.joining());
        }

    }

    public static class Robot extends AbstractKeypad {

        public Robot() {
            super(new String[][]{
                    {null, "^", "A"},
                    {"<", "v", ">"},
            });
        }

        @Override
        public Graph<DepthVertex, KeyEdge> buildGraph(Graph<DepthVertex, KeyEdge> graph) {
            int precedingLayer = graph.vertexSet().stream().mapToInt(DepthVertex::depth).max().orElseThrow();
            int currentLayer = precedingLayer + 1;

            // Get all the vertices of the previous layer
            List<DepthVertex> vertices = graph.vertexSet().stream().filter(v -> Objects.equals(v.depth(), precedingLayer)).toList();

            // For each vertex of the previous layer, we add the vertices of the current layer
            // Each vertex of the previous layer will have a vertex of the current layer for each button
            vertices.stream().filter(v -> Objects.equals(v.depth(), precedingLayer)).forEach(v ->
                    buttons.keySet().stream().map(k -> new DepthVertex(k, currentLayer, v.origin() + v.key())).forEach(graph::addVertex)
            );

            // For each button of the current layer, we add the edges between those buttons, with a cost of 1 and the symbol of the direction is the key to press to go from the source to the target
            graph.vertexSet().stream().filter(v -> Objects.equals(v.depth(), currentLayer))
                    .forEach(v ->
                            getNeighbours(buttons.get(v.key())).forEach(
                                    (direction, neighbourKey) ->
                                            graph.addEdge(
                                                    v,
                                                    new DepthVertex(neighbourKey, currentLayer, v.origin()),
                                                    new KeyEdge(direction.symbol, 1))
                            )
                    );


            // Before adding the edges between the pressed and released buttons, we need to replace all the edges from the previous graph
            List<KeyEdge> edges = graph.edgeSet().stream().toList();

            // Root layer to the current layer (it's key of the root layer to "A" of the current layer, without any cost)
            edges.stream().filter(e -> Objects.equals(graph.getEdgeSource(e).depth(), 0))
                    .filter(e -> Objects.equals(graph.getEdgeTarget(e).depth(), precedingLayer))
                    .forEach(e -> {
                        DepthVertex source = graph.getEdgeSource(e); // source A
                        DepthVertex target = graph.getEdgeTarget(e);

                        graph.removeEdge(e);
                        graph.removeVertex(target);

                        // Add the edge between the key of the root layer and the "A" of the current layer
                        graph.addEdge(new DepthVertex(source.key(), 0), new DepthVertex("A", currentLayer, target.origin() + target.key()), new KeyEdge());
                    });

            // Current layer to the root layer (it's "A" of the current layer to the key of the root layer, with a cost of 1 (A))
            edges.stream().filter(e -> Objects.equals(graph.getEdgeSource(e).depth(), precedingLayer))
                    .filter(e -> Objects.equals(graph.getEdgeTarget(e).depth(), 0))
                    .forEach(e -> {
                        DepthVertex source = graph.getEdgeSource(e); // source A
                        DepthVertex target = graph.getEdgeTarget(e);

                        graph.removeEdge(e);
                        graph.removeVertex(source);

                        // Add the edge between the "A" of the current layer and the key of the root layer
                        graph.addEdge(new DepthVertex("A", currentLayer, source.origin() + source.key()), new DepthVertex(target.key(), 0), new KeyEdge("A", 1));
                    });

            edges.stream().filter(e -> Objects.nonNull(e.getKey()))
                    .filter(e -> Objects.equals(graph.getEdgeSource(e).depth(), precedingLayer))
                    .filter(e -> Objects.equals(graph.getEdgeTarget(e).depth(), precedingLayer))
                    .forEach(e -> { // Edge <
                        DepthVertex source = graph.getEdgeSource(e); // source A
                        DepthVertex target = graph.getEdgeTarget(e); // target 0

                        graph.removeEdge(e);

                        // Add the edge between each edges of the previous layer to the current layer
                        graph.addEdge(new DepthVertex(e.getKey(), currentLayer, source.origin() + source.key()), new DepthVertex(e.getKey(), currentLayer, target.origin() + target.key()), new KeyEdge("A", 1));
                    });

            return Optional.ofNullable(next).map(n -> n.buildGraph(graph)).orElse(graph);
        }
    }

    public record DepthVertex(String key, int depth, String origin) {

        public DepthVertex(String key, int depth) {
            this(key, depth, "");
        }

    }


    public static class KeyEdge extends DefaultWeightedEdge {
        @Getter
        private final String key;
        private final double weight;

        public KeyEdge(String key, double weight) {
            this.key = key;
            this.weight = weight;
        }

        public KeyEdge() {
            this(null, 0);
        }

        @Override
        protected double getWeight() {
            return this.weight;
        }

        @Override
        public String toString() {
            return Optional.ofNullable(key).orElse("");
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