package dev.vinyard.adventofcode.soluce.year2024.day16;

import lombok.Data;
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

        public Node getNodeAt(Point position, Direction direction, Map<Direction, Node[][]> nodesMap) {
            return nodesMap.get(direction)[position.y][position.x];
        }

        public Map<Node, Long> dijkstra(Entity start, Entity target) {
            PriorityQueue<Vertice> queue = new PriorityQueue<>();
            Map<Node, Long> distances = new HashMap<>();

            Map<Direction, Node[][]> nodesMap = new HashMap<>();

            Arrays.stream(Direction.values()).forEach(d -> {
                Node[][] nodes = new Node[puzzle().getBounds().height][puzzle.getBounds().width];
                for (int y = 0; y < puzzle.getBounds().height; y++) {
                    for (int x = 0; x < puzzle.getBounds().width; x++) {
                        nodes[y][x] = new Node(puzzle.getEntityAt(new Point(x, y)), new Point(x, y), d);
                    }
                }
                nodesMap.put(d, nodes);
            });

            Node startNode = getNodeAt(start.position, Direction.EAST, nodesMap);
            Vertice startVertice = new Vertice(startNode, 0);
            distances.put(startNode, 0L);

            queue.add(startVertice);

            while (!queue.isEmpty()) {
                Vertice current = queue.poll();

                for (Node neighbor : getNeighbors(puzzle, current.node(), nodesMap)) {
                    long distance = current.distance() + (current.node().getPosition().equals(neighbor.getPosition()) ? 0 : 1) + (neighbor.getDirection() == current.node().getDirection() ? 0 : 1000);

                    if (!distances.containsKey(neighbor) || distance < distances.get(neighbor)) {
                        neighbor.setParent(current.node());
                        distances.put(neighbor, distance);
                        queue.add(new Vertice(neighbor, distance));
                    } else if (distance == distances.get(neighbor)) {
                        neighbor.addParent(current.node());
                        queue.add(new Vertice(neighbor, distance));
                    }
                }
            }

            return distances;
        }

        public List<Node> getNeighbors(Puzzle puzzle, Node current, Map<Direction, Node[][]> nodesMap) {
            List<Node> nodes = Arrays.stream(Direction.values())
                    .filter(d -> !Objects.equals(d, current.direction))
                    .map(d -> getNodeAt(current.getPosition(), d, nodesMap)).collect(Collectors.toCollection(ArrayList::new));
            Optional.of(current.getPosition()).map(current.getDirection()::move).map(puzzle::getEntityAt).filter(e -> !(e instanceof Wall)).map(e -> getNodeAt(e.getPosition(), current.getDirection(), nodesMap)).ifPresent(nodes::add);
            return nodes;
        }
    }

    public record Vertice (Node node, long distance) implements Comparable<Vertice> {

        @Override
        public int compareTo(Vertice o) {
            return Long.compare(distance, o.distance);
        }
    }

    @Getter
    public static class Node {
        private final Entity entity;
        private final Point position;
        private final Direction direction;

        private Set<Node> parents = new HashSet<>();

        public Node(Entity entity, Point position, Direction direction) {
            this.entity = entity;
            this.position = position;
            this.direction = direction;
        }

        public void setParent(Node node) {
            parents.clear();
            parents.add(node);
        }

        public void addParent(Node node) {
            parents.add(node);
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

    @Data
    public static class Entity {
        private Set<Entity> parent = new HashSet<>();
        protected long distance = Long.MAX_VALUE;
        protected final Point position;

        public Entity(Point position) {
            this.position = position;
        }

        public void setParent(Entity entity) {
            parent.add(entity);
        }

        public void addParent(Entity entity) {
            parent.add(entity);
        }

        public List<Entity> getNeighbors(Puzzle puzzle) {
            return Arrays.stream(Direction.values()).map(d -> d.move(position)).map(puzzle::getEntityAt).filter(e -> !(e instanceof Wall)).filter(Objects::nonNull).toList();
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Entity entity = (Entity) o;
            return distance == entity.distance && Objects.equals(position, entity.position);
        }

        @Override
        public int hashCode() {
            return Objects.hash(distance, position);
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
