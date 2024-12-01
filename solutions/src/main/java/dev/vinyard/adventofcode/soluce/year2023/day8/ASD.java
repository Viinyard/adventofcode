package dev.vinyard.adventofcode.soluce.year2023.day8;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ASD {

    public enum Direction {
        LEFT, RIGHT
    }

    @Data
    public static class Maps {
        public LinkedList<Direction> instructions;
        public Map<String, Node> nodes;

        public Maps(LinkedList<Direction> instructions, Map<String, Node> nodes) {
            this.instructions = instructions;
            this.nodes = nodes;

            nodes.forEach((key, node) -> {
                node.setKey(key);
                node.setLeft(nodes.get(node.getKeyLeft()));
                node.setRight(nodes.get(node.getKeyRight()));
            });
        }
    }

    @Data
    public static class Node {
        private final String keyLeft;
        private final String keyRight;
        private String key;
        private Node left;
        private Node right;

        public Node(String keyLeft, String keyRight) {
            this.keyLeft = keyLeft;
            this.keyRight = keyRight;
        }

        public Node move(List<Direction> directions) {
            return directions.stream().reduce(this, Node::move, (a, b) -> b);
        }

        public Node move(Direction direction) {
            return switch (direction) {
                case LEFT -> left;
                case RIGHT -> right;
            };
        }

        @Override
        public String toString() {
            return String.format("%s = (%s, %s)", key, left.key, right.key);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Node node)) return false;
            return Objects.equals(key, node.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key);
        }
    }
}
