package dev.vinyard.adventofcode.soluce.year2022.day8;

import lombok.Getter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

public class ASD {

    public static class Root {

        private final List<Tree> trees;
        private final Tree[][] forest;
        private final Rectangle bounds;

        public Root(Dimension dimension, List<Tree> trees) {
            this.trees = trees;
            this.forest = new Tree[dimension.height][dimension.width];
            this.bounds = new Rectangle(dimension);
            this.trees.forEach(tree -> this.forest[tree.position.y][tree.position.x] = tree);
        }

        public long part1() {

            final BiFunction<Tree, Direction, Optional<Tree>> treeProvider = (tree, direction) -> getTreeAt(direction.move(tree.position));
            VisibleVisitor visitor = new VisibleVisitor(treeProvider);

            IntStream.range(0, bounds.width).forEach(x -> {
                getTreeAt(new Point(x, 0)).ifPresent(tree -> visitor.visit(tree, Direction.SOUTH, -1));
                getTreeAt(new Point(x, bounds.height - 1)).ifPresent(tree -> visitor.visit(tree, Direction.NORTH, -1));
            });

            IntStream.range(0, bounds.height).forEach(y -> {
                getTreeAt(new Point(0, y)).ifPresent(tree -> visitor.visit(tree, Direction.EAST, -1));
                getTreeAt(new Point(bounds.width - 1, y)).ifPresent(tree -> visitor.visit(tree, Direction.WEST, -1));
            });

            return visitor.getVisible().size();
        }

        public long part2() {
            final BiFunction<Tree, Direction, Optional<Tree>> treeProvider = (tree, direction) -> getTreeAt(direction.move(tree.position));

            return trees.stream().mapToLong(tree ->
                Arrays.stream(Direction.values()).map(direction -> {
                    ScenicVisitor scenicVisitor = new ScenicVisitor(treeProvider);
                    treeProvider.apply(tree, direction).ifPresent(t -> scenicVisitor.visit(t, direction, tree.height()));
                    return scenicVisitor.getCount();
                }).reduce((a, b) -> a * b).orElse(0)
            ).max().orElseThrow();
        }

        public Optional<Tree> getTreeAt(Point point) {
            return Optional.of(point).filter(bounds::contains).map(p -> forest[point.y][point.x]);
        }
    }

    public static class VisibleVisitor implements TreeVisitor {

        private final BiFunction<Tree, Direction, Optional<Tree>> treeProvider;

        @Getter
        private final Set<Tree> visible = new HashSet<>();

        public VisibleVisitor(BiFunction<Tree, Direction, Optional<Tree>> treeProvider) {
            this.treeProvider = treeProvider;
        }

        public void visit(Tree tree, Direction direction, final int height) {
            if (tree.height > height)
                visible.add(tree);

            treeProvider.apply(tree, direction).ifPresent(t -> visit(t, direction, Math.max(tree.height, height)));
        }
    }

    public static class ScenicVisitor implements TreeVisitor {

        private final BiFunction<Tree, Direction, Optional<Tree>> treeProvider;

        @Getter
        private int count = 0;

        public ScenicVisitor(BiFunction<Tree, Direction, Optional<Tree>> treeProvider) {
            this.treeProvider = treeProvider;
        }

        public void visit(Tree tree, Direction direction, final int height) {
            count++;

            if (tree.height >= height)
                return;

            treeProvider.apply(tree, direction).ifPresent(t -> visit(t, direction, height));
        }
    }

    public interface TreeVisitor {
        void visit(Tree tree, Direction direction, int height);
    }

    public record Tree(Point position, int height) {
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
