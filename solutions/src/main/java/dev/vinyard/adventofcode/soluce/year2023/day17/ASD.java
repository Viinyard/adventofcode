package dev.vinyard.adventofcode.soluce.year2023.day17;

import lombok.Getter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ASD {

    public static class Root {
        private final List<Block> blocks;
        private final Rectangle bounds;
        private final Block[][] grid;

        public Root(List<Block> blocks, Dimension dimension) {
            this.blocks = blocks;
            this.bounds = new Rectangle(dimension);
            this.grid = new Block[this.bounds.height][this.bounds.width];
            this.blocks.forEach(block -> grid[block.position.y][block.position.x] = block);
        }

        private Optional<Block> getBlockAt(Point position) {
            return Optional.of(position).filter(this.bounds::contains).map(p -> grid[p.y][p.x]);
        }

        public long getMinHeatLost() {
            PriorityQueue<GraphNode> nodes = new PriorityQueue<>();
            Map<Point, Integer> minHeatLossByPosition = new HashMap<>();

            nodes.add(new GraphNode(blocks.getFirst().position));

            System.out.println(this);

            GraphNode dest = new GraphNode(blocks.getLast().position);

            while (!nodes.isEmpty()) {
                GraphNode current = nodes.poll();

//                System.out.println(current);

                if (minHeatLossByPosition.containsKey(current.position) && minHeatLossByPosition.get(current.position) <= current.heatLoss)
                    continue; // Skip if we already found a better path to this position

                minHeatLossByPosition.put(current.position, current.heatLoss);

                if (Objects.equals(current.position, dest.position))
                    return current.heatLoss;

                Arrays.stream(Direction.values()).map(d -> d.move(current.position))
                        .map(this::getBlockAt)
                        .flatMap(Optional::stream)
                        .mapMulti((block, consumer) -> {
                            GraphNode graphNode = new GraphNode(block.position, current.heatLoss + block.getHeatLoss(), current);
                            if (graphNode.isAcceptable())
                                consumer.accept(graphNode);
                            else {
                                System.out.println(graphNode);
                            }
                        })
                        .map(GraphNode.class::cast)
                        .forEach(nodes::add);
            }

            return -1; // No path found
        }

        @Override
        public String toString() {
            return Arrays.stream(grid).map(row -> Arrays.stream(row).map(b -> Integer.toString(b.getHeatLoss())).collect(Collectors.joining())).collect(Collectors.joining("\n"));
        }
    }

    @Getter
    public static class GraphNode implements Comparable<GraphNode> {
        private Integer heatLoss = 0;
        private final Point position;
        private GraphNode parent;

        public GraphNode(Point position) {
            this.position = position;
        }

        public GraphNode(Point position, Integer heatLoss, GraphNode parent) {
            this(position);
            this.heatLoss = heatLoss;
            this.parent = parent;
        }

        public Direction getDirection() {
            if (parent == null) return null;
           return Arrays.stream(Direction.values())
                    .filter(d -> Objects.equals(d.move(parent.position), position))
                    .findAny()
                    .orElse(null);
        }

        public boolean isAcceptable() {
            return Stream.iterate(this, Objects::nonNull, GraphNode::getParent)
                    .limit(3)
                    .map(GraphNode::getDirection)
                    .filter(Objects::nonNull)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .values().stream().noneMatch(count -> count >= 3);
        }

        @Override
        public int compareTo(GraphNode o) {
            return Integer.compare(this.heatLoss, o.heatLoss);
        }

        @Override
        public String toString() {
            return "GraphNode{" +
                    "position=" + position +
                    ", heatLoss=" + heatLoss +
                    '}';
        }
    }

    @Getter
    public static class Block {
        private Point position;
        private int heatLoss;

        public Block(Point position, int heatLoss) {
            this.position = position;
            this.heatLoss = heatLoss;
        }

        @Override
        public String toString() {
            return Integer.toString(heatLoss);
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
