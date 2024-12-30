package dev.vinyard.adventofcode.soluce.year2024.day21;

import lombok.Getter;
import lombok.Setter;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.shortestpath.EppsteinShortestPathIterator;
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
            final Digicode digicode = new Digicode().withRobots(numberOfRobots);

            return codes.stream()
                    .mapToLong(code -> {
                        Long pathLength = digicode.handleRequest(code.getCode());
                        return pathLength * code.getDigits();
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

        public abstract long handleRequest(String command);

    }

    public static abstract class AbstractKeypad extends RequestHandler {

        private final Character[][] keypad;

        protected final Map<Character, Point> buttons;

        private final ShortestPathAlgorithm<DepthVertex, KeyEdge> shortestPathAlgorithm;
        private final Graph<DepthVertex, KeyEdge> graph;

        private final Rectangle bounds;

        private final Map<String, Long> cache = new HashMap<>();

        private final static int PRESSED_LAYER = 0;
        private final static int RELEASED_LAYER = 1;

        private char key = 'A';

        public AbstractKeypad(Character[][] keypad) {
            this.keypad = keypad;
            this.buttons = buildButtons();
            this.bounds = new Rectangle(keypad[0].length, keypad.length);
            this.graph = buildGraph();
            this.shortestPathAlgorithm = new DijkstraShortestPath<>(this.graph);
        }

        private Character getKeyAt(Point position) {
            return keypad[position.y][position.x];
        }

        private Map<Character, Point> buildButtons() {
            Map<Character, Point> buttons = new HashMap<>();
            for (int y = 0; y < keypad.length; y++) {
                for (int x = 0; x < keypad[y].length; x++) {
                    if (keypad[y][x] != null)
                        buttons.put(keypad[y][x], new Point(x, y));
                }
            }
            return buttons;
        }

        public Graph<DepthVertex, KeyEdge> buildGraph() {
            Graph<DepthVertex, KeyEdge> graph = createEmptyGraph();

            buttons.keySet().stream().map(k -> new DepthVertex(k, PRESSED_LAYER)).forEach(graph::addVertex);
            buttons.keySet().stream().map(k -> new DepthVertex(k, RELEASED_LAYER)).forEach(graph::addVertex);

            graph.vertexSet().stream().filter(v -> Objects.equals(v.depth(), PRESSED_LAYER)).forEach(v -> graph.addEdge(v, new DepthVertex(v.key, RELEASED_LAYER), new KeyEdge()));
            graph.vertexSet().stream().filter(v -> Objects.equals(v.depth(), RELEASED_LAYER)).forEach(v -> graph.addEdge(v, new DepthVertex(v.key, PRESSED_LAYER), new KeyEdge('A', 1)));

            buttons.forEach((key, position) ->
                    getNeighbours(position).forEach((direction, neighbourKey) ->
                            graph.addEdge(
                                    new DepthVertex(key, RELEASED_LAYER),
                                    new DepthVertex(neighbourKey, RELEASED_LAYER),
                                    new KeyEdge(direction.symbol, 1))
                    )
            );

            return graph;
        }

        private Map<Direction, Character> getNeighbours(Point position) {
            return Arrays.stream(Direction.values())
                    .filter(d -> {
                        Point p = d.move(position);
                        return bounds.contains(p) && getKeyAt(p) != null;
                    })
                    .collect(Collectors.toMap(Function.identity(), d -> getKeyAt(d.move(position))));
        }

        private Graph<DepthVertex, KeyEdge> createEmptyGraph() {
            return GraphTypeBuilder.<DepthVertex, KeyEdge>directed()
                    .allowingMultipleEdges(false)
                    .allowingSelfLoops(false)
                    .edgeClass(KeyEdge.class)
                    .weighted(true)
                    .buildGraph();
        }

        private List<String> getShortestsPathsTo(Character digit) {
            EppsteinShortestPathIterator<DepthVertex, KeyEdge> eppsteinShortestPathIterator = new EppsteinShortestPathIterator<>(this.graph, new DepthVertex(this.key, RELEASED_LAYER), new DepthVertex(digit, PRESSED_LAYER));

            final double shortestPathWeight = this.shortestPathAlgorithm.getPath(new DepthVertex(this.key, RELEASED_LAYER), new DepthVertex(digit, PRESSED_LAYER)).getWeight();

            key = digit;

            return Stream.generate(eppsteinShortestPathIterator::next).takeWhile(e -> e.getWeight() == shortestPathWeight)
                    .map(GraphPath::getEdgeList).map(l -> l.stream().map(KeyEdge::toString).collect(Collectors.joining())).collect(Collectors.toList());
        }

        public long handleRequest(String command) {
            if (cache.containsKey(command))
                return cache.get(command);

            long length = 0L;
            for (char c : command.toCharArray()) {
                List<String> paths = getShortestsPathsTo(c);

                length += Optional.ofNullable(next)
                        .flatMap(n -> paths.stream().map(n::handleRequest).min(Long::compareTo))
                        .orElseGet(() -> paths.stream().map(String::length).min(Integer::compareTo).map(Long::valueOf).orElse(Long.MAX_VALUE));
            }

            cache.put(command, length);

            return length;
        }

    }

    public static class Digicode extends AbstractKeypad {

        public Digicode() {
            super(new Character[][]{
                    {'7', '8', '9'},
                    {'4', '5', '6'},
                    {'1', '2', '3'},
                    {null, '0', 'A'}
            });
        }

        public Digicode withRobots(int numberOfRobots) {
            Stack<RequestHandler> handlers = Stream.generate(Robot::new).limit(numberOfRobots).collect(Collectors.toCollection(Stack::new));

            handlers.stream().reduce((first, second) -> {
                first.setNext(second);
                return second;
            });

            Optional.of(handlers).filter(h -> !h.isEmpty())
                    .map(Stack::getFirst)
                    .ifPresent(this::setNext);

            return this;
        }
    }

    public static class Robot extends AbstractKeypad {

        public Robot() {
            super(new Character[][]{
                    {null, '^', 'A'},
                    {'<', 'v', '>'},
            });
        }
    }

    public record DepthVertex(Character key, int depth, String origin) {

        public DepthVertex(Character key, int depth) {
            this(key, depth, "");
        }
    }

    public static class KeyEdge extends DefaultWeightedEdge {
        @Getter
        private final Character key;
        private final double weight;

        public KeyEdge(Character key, double weight) {
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
            return Optional.ofNullable(key).map(Objects::toString).orElse("");
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
