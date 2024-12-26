package dev.vinyard.adventofcode.soluce.year2024.day21;

import lombok.Getter;
import lombok.Setter;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ASD {

    public static class Root {

        private final List<Code> codes;

        public Root(List<Code> codes) {
            this.codes = codes;
        }

        private RequestHandler getRequestHandler() {
            Digicode digicodeDepressurized = new Digicode();
            Robot robot1 = new Robot(digicodeDepressurized);

            return digicodeDepressurized;
        }

        public String getCommandForCode(Code code) {
            RequestHandler requestHandler = getRequestHandler();

            return Arrays.stream(code.getCode().split(""))
                    .map(requestHandler::type)
                    .collect(Collectors.joining());
        }

        private long calculateComplexityOfCode(Code code) {
            String command = getCommandForCode(code);
            System.out.println("Code : " + code.getCode() + " - Command : " + command);
            return getCommandForCode(code).length() * code.getDigits();
        }

        public long calculateComplexity() {
            return codes.stream()
                    .mapToLong(this::calculateComplexityOfCode)
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

        public abstract String type(String digit);
    }

    public static abstract class AbstractKeypad extends RequestHandler {

        private final String[][] keypad;

        protected final Map<String, Point> buttons;

        private final Rectangle bounds;

        private final Graph<DepthVertex, KeyEdge> graph;
        private final ShortestPathAlgorithm<DepthVertex, KeyEdge> shortestPathAlgorithm;

        private final Point position;

        protected AbstractKeypad previous;

        public AbstractKeypad(String[][] keypad, AbstractKeypad previous) {
            this.keypad = keypad;
            this.previous = previous;
            this.buttons = buildButtons();
            this.position = new Point(buttons.get("A"));
            this.bounds = new Rectangle(keypad[0].length, keypad.length);
            this.graph = buildGraph();
            this.shortestPathAlgorithm = new DijkstraShortestPath<>(graph);
        }

        public AbstractKeypad(String[][] keypad) {
            this(keypad, null);
        }

        private String getKeyAt(Point position) {
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

        protected Graph<DepthVertex, KeyEdge> buildGraph() {
            Graph<DepthVertex, KeyEdge> graph = Optional.ofNullable(previous).map(p -> p.graph).orElse(createEmptyGraph());

            int pressedLayer = graph.vertexSet().stream().mapToInt(DepthVertex::depth).max().orElse(-1) + 1;
            int releasedLayer = pressedLayer + 1;

            // Layer 0 : The pressed button
            buttons.keySet().stream().map(k -> new DepthVertex(k, pressedLayer)).forEach(graph::addVertex);

            // Layer 1 : The released button (position of the arm)
            buttons.keySet().stream().map(k -> new DepthVertex(k, releasedLayer)).forEach(graph::addVertex);

            // Before adding the edges between the pressed and released buttons, we need to replace all the edges from the previous graph
            List<KeyEdge> edges = graph.edgeSet().stream().filter(e -> Objects.nonNull(e.getKey())).toList();

            edges.forEach(e -> {
                if (Objects.equals(e.getKey(), "A")) {
                    graph.addEdge(new DepthVertex("A", pressedLayer), graph.getEdgeTarget(e), new KeyEdge("A", 1));
                } else {
                    graph.addEdge(new DepthVertex(e.getKey(), pressedLayer), graph.getEdgeTarget(e));
                }
                graph.removeEdge(e);
            });

            // Last layer of the previous graph are all connected to the "A" pressed layer
            graph.vertexSet().stream().filter(v -> Objects.equals(v.depth(), pressedLayer - 1)).forEach(v -> graph.addEdge(v, new DepthVertex("A", pressedLayer), new KeyEdge()));

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

            return graph;
        }

        private String getShortestPathTo(String digit) {
            String command = shortestPathAlgorithm.getPath(new DepthVertex(getKeyAt(this.position), 0), new DepthVertex(digit, 0)).getEdgeList().stream().map(KeyEdge::toString).collect(Collectors.joining(""));
            position.setLocation(buttons.get(digit));
            return command;
        }

        @Override
        public String type(String digit) {
            String command = Optional.ofNullable(next).map(h -> h.type(digit)).orElse(digit);

            return Arrays.stream(command.split(""))
                    .filter(s -> !s.isBlank())
                    .map(this::getShortestPathTo)
                    .collect(Collectors.joining());
        }
    }

    public static class Digicode extends AbstractKeypad {

        public Digicode() {
            super(new String[][]{
                    {"7", "8", "9"},
                    {"4", "5", "6"},
                    {"1", "2", "3"},
                    {null, "0", "A"}
            });
        }

        protected Graph<DepthVertex, KeyEdge> buildGraph() {
            Graph<DepthVertex, KeyEdge> graph = createEmptyGraph();

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

            return graph;
        }

    }

    public static class Robot extends AbstractKeypad {

        public Robot() {
            super(new String[][]{
                    {null, "^", "A"},
                    {"<", "v", ">"},
            });
        }

        public Robot(AbstractKeypad previous) {
            super(new String[][]{
                    {null, "^", "A"},
                    {"<", "v", ">"},
            }, previous);
        }

        protected Graph<DepthVertex, KeyEdge> buildGraph() {
            Graph<DepthVertex, KeyEdge> graph = Optional.ofNullable(previous).map(p -> p.graph).orElse(createEmptyGraph());

            int precedingLayer = graph.vertexSet().stream().mapToInt(DepthVertex::depth).max().orElseThrow(); // 1
            //int pressedLayer = precedingLayer + 1; // 2
            int currentLayer = precedingLayer + 1; // 3

            // Before adding the edges between the pressed and released buttons, we need to replace all the edges from the previous graph
            List<KeyEdge> edges = graph.edgeSet().stream().toList();

            edges.stream().filter(e -> Objects.nonNull(e.getKey())).forEach(e -> { // Edge <
                DepthVertex source = graph.getEdgeSource(e); // source A
                DepthVertex target = graph.getEdgeTarget(e); // target 0

                {
                    // < A
                    graph.addVertex(new DepthVertex(e.getKey(), currentLayer, source.key()));
//                    graph.addVertex(new DepthVertex(e.getKey(), pressedLayer, source.key()));
//                    graph.addEdge(new DepthVertex(e.getKey(), currentLayer, source.key()), new DepthVertex(e.getKey(), pressedLayer, source.key()), new KeyEdge("A", 1));

                    // < 0
                    graph.addVertex(new DepthVertex(e.getKey(), currentLayer, target.key()));
//                    graph.addVertex(new DepthVertex(e.getKey(), pressedLayer, target.key()));
//                    graph.addEdge(new DepthVertex(e.getKey(), currentLayer, target.key()), new DepthVertex(e.getKey(), pressedLayer, target.key()), new KeyEdge("A", 1));

                    // A <
                    graph.addVertex(new DepthVertex(source.key(), source.depth(), e.getKey()));

                    // 0 <
                    graph.addVertex(new DepthVertex(target.key(), target.depth(), e.getKey()));

                    // < A pressed 0 <
                    if (Objects.equals(source.depth(), target.depth())) {
                        graph.addEdge(new DepthVertex(e.getKey(), currentLayer, source.key()), new DepthVertex(e.getKey(), currentLayer, target.key()), new KeyEdge("A", 1));
                    } else {
                        graph.addEdge(new DepthVertex(e.getKey(), currentLayer, source.key()), new DepthVertex(target.key(), target.depth()), new KeyEdge("A", 1));
                    }
                }

                graph.removeEdge(e);
            });


            graph.vertexSet().stream().filter(v -> Objects.equals(v.depth(), currentLayer))
//                    .filter(v -> buttons.containsKey(v.key()))
                            .forEach(v -> {
                                getNeighbours(buttons.get(v.key())).forEach((direction, neighbourKey) -> {
                                    graph.addEdge(v, new DepthVertex(neighbourKey, currentLayer, v.origin()), new KeyEdge(direction.symbol, currentLayer));
                                });

                            });

            edges.stream().filter(e -> Objects.isNull(e.getKey())).forEach(e -> {
                DepthVertex source = graph.getEdgeSource(e); // source A
                DepthVertex target = graph.getEdgeTarget(e); // target 0
                // Last layer of the previous graph are all connected to the "A" pressed layer
                graph.addEdge(source, new DepthVertex("A", currentLayer, source.key()), new KeyEdge());
                graph.removeEdge(e);
            });

            return graph;
        }
    }

    public record DepthVertex(String key, int depth, String origin) {

        public DepthVertex(String key) {
            this(key, 0, null);
        }

        public DepthVertex(String key, int depth) {
            this(key, depth, null);
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
