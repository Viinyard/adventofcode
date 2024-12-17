package dev.vinyard.adventofcode.soluce.year2024.day16;

import lombok.Getter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class ASD {

    public record Root(Puzzle puzzle) {

        public Start findStart() {
            return puzzle.getEntities().stream().filter(Start.class::isInstance).map(Start.class::cast).findAny().orElseThrow();
        }

        public End findEnd() {
            return puzzle.getEntities().stream().filter(End.class::isInstance).map(End.class::cast).findAny().orElseThrow();
        }

        public Node AStar(Entity start, Entity target) {
            Set<Node> closeList = new HashSet<>();
            PriorityQueue<Node> openList = new PriorityQueue<>();



            openList.add(new Node(null, start.getPosition(), Direction.EAST, 0, heuristic(start.getPosition(), target.position)));

            while (!openList.isEmpty()) {
                Node current = openList.poll();

                if (current.getPosition().equals(target.getPosition())) {
                    return current;
                }

                closeList.add(current);


                for (Node node : getNeighbors(puzzle, current, target.getPosition())) {
                    if (closeList.contains(node))
                        continue;

                    if (openList.contains(node)) {
                        continue;
                    }

                    openList.add(node);
                }
            }

            return null;
        }

        public double heuristic(Point a, Point b) {
            return a.distance(b);
        }

        public List<Node> getNeighbors(Puzzle puzzle, Node current, Point target) {
            List<Node> nodes = Arrays.stream(Direction.values())
                    .filter(d -> !Objects.equals(d, current.direction))
                    .map(d -> new Node(current, current.getPosition(), d, current.g + 1000, heuristic(current.getPosition(), target))).collect(Collectors.toCollection(ArrayList::new));
            Optional.of(current.getPosition()).map(current.getDirection()::move).map(puzzle::getEntityAt).filter(e -> !(e instanceof Wall)).map(e -> new Node(current, e.getPosition(), current.getDirection(), current.g + 1, heuristic(e.getPosition(), target))).ifPresent(nodes::add);
            return nodes;
        }
    }

    @Getter
    public static class Node implements Comparable<Node> {

        private final Node parent;
        private final Point position;
        private final Direction direction;
        private final long g;
        private final double h;

        public Node(Node parent, Point position, Direction direction, long g, double h) {
            this.parent = parent;
            this.position = position;
            this.direction = direction;
            this.g = g;
            this.h = h;
        }

        public double getF() {
            return g + h;
        }

        @Override
        public String toString() {
            return "Node{" +
                    ", position=" + position +
                    ", direction=" + direction +
                    ", g=" + g +
                    ", h=" + h +
                    '}';
        }

        @Override
        public int compareTo(Node o) {
            return Double.compare(getF(), o.getF());
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

        public Entity getEntityAt(Point position) {
            return Optional.of(position).filter(getBounds()::contains).map(p -> entities[p.y][p.x]).orElse(null);
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

    @Getter
    public static class Entity {
        protected final Point position;

        public Entity(Point position) {
            this.position = position;
        }

        public List<Entity> getNeighbors(Puzzle puzzle) {
            return Arrays.stream(Direction.values()).map(d -> d.move(position)).map(puzzle::getEntityAt).filter(e -> !(e instanceof Wall)).filter(Objects::nonNull).toList();
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
