package dev.vinyard.adventofcode.soluce.year2024.day21;

import lombok.Getter;
import lombok.Setter;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.AStarShortestPath;
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
            Robot robotDegrees = new Robot();
            Robot robotRadiation = new Robot();
            Digicode digicodeDepressurized = new Digicode();

            robotDegrees.setNext(robotRadiation);
            robotRadiation.setNext(digicodeDepressurized);

            return robotDegrees;
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

    public static abstract class RequestHandler {

        @Setter
        protected RequestHandler next;

        private RequestHandler() {

        }

        public abstract String type(String digit);
    }

    public static class Robot extends RequestHandler {

        private static final String[][] keypad = {
                {null, "^", "A"},
                {"<", "v", ">"},
        };

        private static final DirectionNode[][][] nodes = buildKeypadNodes();


        private static final Map<String, Point> buttons = buildButtons();

        private static final Rectangle bounds = new Rectangle(keypad[0].length, keypad.length);

        private static final Graph<DirectionNode, DirectionEdge> graph = buildGraph();
        private static final ShortestPathAlgorithm<DirectionNode, DirectionEdge> shortestPathAlgorithm = new AStarShortestPath<>(graph, (b1, b2) -> buttons.get(b1.key).distance(buttons.get(b2.key)));

        private final Point position = new Point(buttons.get("A"));

        private static String getKeyAt(Point position) {
            return keypad[position.y][position.x];
        }

        private static Map<String, Point> buildButtons() {
            Map<String, Point> buttons = new HashMap<>();
            for (int y = 0; y < keypad.length; y++) {
                for (int x = 0; x < keypad[y].length; x++) {
                    if (keypad[y][x] != null)
                        buttons.put(keypad[y][x], new Point(x, y));
                }
            }
            return buttons;
        }

        private static DirectionNode[][][] buildKeypadNodes() {
            DirectionNode[][][] nodes = new DirectionNode[Direction.values().length][keypad.length][keypad[0].length];
            for (int y = 0; y < keypad.length; y++) {
                for (int x = 0; x < keypad[y].length; x++) {
                    if (keypad[y][x] == null) continue;
                    for (Direction direction : Direction.values()) {
                        nodes[direction.ordinal()][y][x] = new DirectionNode(keypad[y][x], direction);
                    }
                }
            }
            return nodes;
        }

        private static Map<Direction, String> getNeighbours(Point position) {
            return Arrays.stream(Direction.values())
                    .filter(d -> {
                        Point p = d.move(position);
                        return bounds.contains(p) && getKeyAt(p) != null;
                    })
                    .collect(Collectors.toMap(Function.identity(), d -> getKeyAt(d.move(position))));
        }

        private static Graph<DirectionNode, DirectionEdge> buildGraph() {
            Graph<DirectionNode, DirectionEdge> graph = createEmptyGraph();

            Arrays.stream(nodes).flatMap(Arrays::stream).flatMap(Arrays::stream).filter(Objects::nonNull).forEach(graph::addVertex);
            buttons.keySet().stream().map(DirectionNode::new).forEach(graph::addVertex);

            graph.vertexSet().forEach(n -> {
                if (n.direction == null) {
                    Arrays.stream(Direction.values()).map(Direction::ordinal)
                            .map(i -> nodes[i][buttons.get(n.key).y][buttons.get(n.key).x])
                            .forEach(neighbour -> graph.addEdge(n, neighbour, new DirectionEdge(null, 1000)));
                } else {
                    graph.addEdge(n, new DirectionNode(n.key, null), new DirectionEdge(null, 0));
                    Arrays.stream(Direction.values())
                            .filter(d -> !Objects.equals(n.direction, d))
                            .map(Direction::ordinal)
                            .map(i -> nodes[i][buttons.get(n.key).y][buttons.get(n.key).x])
                            .forEach(neighbour -> graph.addEdge(n, neighbour, new DirectionEdge(null, 1000)));

                    Point moved = n.direction.move(buttons.get(n.key));
                    if (bounds.contains(moved) && getKeyAt(moved) != null) {
                        if (n.direction == Direction.NORTH || n.direction == Direction.SOUTH) {
                            graph.addEdge(n, nodes[n.direction.ordinal()][moved.y][moved.x], new DirectionEdge(n.direction, 1));
                        } else {
                            graph.addEdge(n, nodes[n.direction.ordinal()][moved.y][moved.x], new DirectionEdge(n.direction, 1));
                        }
                    }
                }
            });

            return graph;
        }

        private String getShortestPathTo(String digit) {
            String command = shortestPathAlgorithm.getPath(new DirectionNode(getKeyAt(this.position), null), new DirectionNode(digit, null)).getEdgeList().stream().map(DirectionEdge::toString).collect(Collectors.joining(""));
            position.setLocation(buttons.get(digit));
            return command + "A";
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

    public static class Digicode extends RequestHandler {

        private static final String[][] digicode = {
                {"7", "8", "9"},
                {"4", "5", "6"},
                {"1", "2", "3"},
                {null, "0", "A"}
        };

        private static final DirectionNode[][][] nodes = buildDigicodeNodes();

        private static final Map<String, Point> buttons = buildButtons();

        private static final Rectangle bounds = new Rectangle(digicode[0].length, digicode.length);

        private static final Graph<DirectionNode, DirectionEdge> graph = buildGraph();
        private static final ShortestPathAlgorithm<DirectionNode, DirectionEdge> shortestPathAlgorithm = new AStarShortestPath<>(graph, (b1, b2) -> buttons.get(b1.key).distance(buttons.get(b2.key)));

        private final Point position = new Point(buttons.get("A"));

        private static String getDigitAt(Point position) {
            return digicode[position.y][position.x];
        }

        private static Map<String, Point> buildButtons() {
            Map<String, Point> buttons = new HashMap<>();
            for (int y = 0; y < digicode.length; y++) {
                for (int x = 0; x < digicode[y].length; x++) {
                    if (digicode[y][x] != null)
                        buttons.put(digicode[y][x], new Point(x, y));
                }
            }
            return buttons;
        }

        private static DirectionNode[][][] buildDigicodeNodes() {
            DirectionNode[][][] nodes = new DirectionNode[Direction.values().length][digicode.length][digicode[0].length];
            for (int y = 0; y < digicode.length; y++) {
                for (int x = 0; x < digicode[y].length; x++) {
                    if (digicode[y][x] == null) continue;
                    for (Direction direction : Direction.values()) {
                        nodes[direction.ordinal()][y][x] = new DirectionNode(digicode[y][x], direction);
                    }
                }
            }
            return nodes;
        }

        private static Map<Direction, String> getNeighbours(Point position) {
            return Arrays.stream(Direction.values())
                    .filter(d -> {
                        Point p = d.move(position);
                        return bounds.contains(p) && getDigitAt(p) != null;
                    })
                    .collect(Collectors.toMap(Function.identity(), d -> getDigitAt(d.move(position))));
        }

        private static Graph<DirectionNode, DirectionEdge> buildGraph() {
            Graph<DirectionNode, DirectionEdge> graph = createEmptyGraph();

            Arrays.stream(nodes).flatMap(Arrays::stream).flatMap(Arrays::stream).filter(Objects::nonNull).forEach(graph::addVertex);
            buttons.keySet().stream().map(DirectionNode::new).forEach(graph::addVertex);

            graph.vertexSet().forEach(n -> {
                if (n.direction == null) {
                    Arrays.stream(Direction.values()).map(Direction::ordinal)
                            .map(i -> nodes[i][buttons.get(n.key).y][buttons.get(n.key).x])
                            .forEach(neighbour -> graph.addEdge(n, neighbour, new DirectionEdge(null, 1000)));
                } else {
                    graph.addEdge(n, new DirectionNode(n.key, null), new DirectionEdge(null, 0));
                    Arrays.stream(Direction.values())
                            .filter(d -> !Objects.equals(n.direction, d))
                            .map(Direction::ordinal)
                            .map(i -> nodes[i][buttons.get(n.key).y][buttons.get(n.key).x])
                            .forEach(neighbour -> graph.addEdge(n, neighbour, new DirectionEdge(null, 1000)));

                    Point moved = n.direction.move(buttons.get(n.key));
                    if (bounds.contains(moved) && getDigitAt(moved) != null) {
                        graph.addEdge(n, nodes[n.direction.ordinal()][moved.y][moved.x], new DirectionEdge(n.direction, 1));
                    }
                }
            });

            return graph;
        }

        private String getShortestPathTo(String digit) {
            String command = shortestPathAlgorithm.getPath(new DirectionNode(getDigitAt(this.position), null), new DirectionNode(digit, null)).getEdgeList().stream().map(DirectionEdge::toString).collect(Collectors.joining(""));
            position.setLocation(buttons.get(digit));
            return command + "A";
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

    private static Graph<DirectionNode, DirectionEdge> createEmptyGraph() {
        return GraphTypeBuilder.<DirectionNode, DirectionEdge>directed()
                .allowingMultipleEdges(false)
                .allowingSelfLoops(false)
                .edgeClass(DirectionEdge.class)
                .weighted(true)
                .buildGraph();
    }

    public static class DirectionNode {
        private final String key;
        private final Direction direction;

        public DirectionNode(String key, Direction direction) {
            this.key = key;
            this.direction = direction;
        }

        public DirectionNode(String key) {
            this(key, null);
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof DirectionNode that)) return false;
            return Objects.equals(key, that.key) && direction == that.direction;
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, direction);
        }
    }

    public static class DirectionEdge extends DefaultWeightedEdge {
        @Getter
        private final Direction direction;
        private final double weight;

        public DirectionEdge(Direction direction, double weight) {
            this.direction = direction;
            this.weight = weight;
        }

        @Override
        protected double getWeight() {
            return this.weight;
        }

        @Override
        public String toString() {
            return Optional.ofNullable(direction).map(d -> d.symbol).orElse("");
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
