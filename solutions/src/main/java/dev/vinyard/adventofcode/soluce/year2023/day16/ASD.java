package dev.vinyard.adventofcode.soluce.year2023.day16;

import lombok.Getter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ASD {

    public static class Root {

        public final List<Tile> tiles;
        public final Tile[][] grid;
        public final Rectangle bounds;

        public Root(List<Tile> tiles, Dimension dimension) {
            this.tiles = tiles;
            this.bounds = new Rectangle(dimension);
            this.grid = new Tile[this.bounds.height][this.bounds.width];
            this.tiles.forEach(tile -> grid[tile.position.y][tile.position.x] = tile);
        }

        public Optional<Tile> getTileAt(Point position) {
            return Optional.of(position).filter(this.bounds::contains).map(p -> grid[p.y][p.x]);
        }

        public long getEngergizedCount() {
            return getEngergizedCountFrom(new Beam(new Point(0, 0), Direction.EAST));
        }

        private long getEngergizedCountFrom(Beam beam) {
            Set<Beam> visited = new HashSet<>();
            Stack<Beam> stack = new Stack<>();

            stack.push(beam);

            while (!stack.isEmpty()) {
                Beam current = stack.pop();

                this.getTileAt(current.position).filter(t -> visited.add(current)).ifPresent(tile -> {
                    tile.accept(stack, current.direction);
                });
            }

            return visited.stream().map(Beam::position).distinct().count();
        }

        public long getMaxEnergizedCount() {
            return this.tiles.stream().map(Tile::getPosition).filter(p -> p.x == 0 || p.x == bounds.width - 1 || p.y == 0 || p.y == bounds.height - 1)
                    .mapMulti((p, c) -> {
                        if (p.x == bounds.width - 1)
                            c.accept(new Beam(p, Direction.WEST)); // Right edge
                        if (p.x == 0)
                            c.accept(new Beam(p, Direction.EAST)); // Left
                        if (p.y == bounds.height - 1)
                            c.accept(new Beam(p, Direction.NORTH)); // Bottom edge
                        if (p.y == 0)
                            c.accept(new Beam(p, Direction.SOUTH)); // Top edge
                    })
                    .map(Beam.class::cast)
                    .mapToLong(this::getEngergizedCountFrom)
                    .max().orElse(0L);
        }


        @Override
        public String toString() {
            return Arrays.stream(grid).map(row -> Arrays.stream(row).map(Tile::toString).collect(Collectors.joining())).collect(Collectors.joining("\n"));
        }
    }

    public record Beam(Point position, Direction direction) {}

    @Getter
    public static abstract class Tile {

        protected final Point position;

        public Tile(Point position) {
            this.position = position;
        }

        protected Beam getBeam(Direction direction) {
            return new Beam(direction.move(this.position), direction);
        }

        @Override
        public abstract String toString();

        public abstract void accept(Stack<Beam> stack, Direction direction);
    }

    public static class EmptySpace extends Tile {


        public EmptySpace(Point position) {
            super(position);
        }

        @Override
        public String toString() {
            return ".";
        }

        @Override
        public void accept(Stack<Beam> stack, Direction direction) {
            stack.add(getBeam(direction));
        }
    }

    public static class PositiveMirror extends Tile {
        public PositiveMirror(Point position) {
            super(position);
        }

        @Override
        public String toString() {
            return "\\";
        }

        @Override
        public void accept(Stack<Beam> stack, Direction direction) {
            stack.add(getBeam(switch (direction) {
                case NORTH -> Direction.WEST;
                case SOUTH -> Direction.EAST;
                case EAST -> Direction.SOUTH;
                case WEST -> Direction.NORTH;
            }));
        }
    }

    public static class NegativeMirror extends Tile {
        public NegativeMirror(Point position) {
            super(position);
        }

        @Override
        public String toString() {
            return "/";
        }

        @Override
        public void accept(Stack<Beam> stack, Direction direction) {
            stack.add(getBeam(switch (direction) {
                case NORTH -> Direction.EAST;
                case SOUTH -> Direction.WEST;
                case EAST -> Direction.NORTH;
                case WEST -> Direction.SOUTH;
            }));
        }
    }

    public static class VerticalSplitter extends Tile {
        public VerticalSplitter(Point position) {
            super(position);
        }

        @Override
        public String toString() {
            return "|";
        }

        @Override
        public void accept(Stack<Beam> stack, Direction direction) {
            switch (direction) {
                case EAST, WEST -> {
                    stack.add(getBeam(Direction.NORTH));
                    stack.add(getBeam(Direction.SOUTH));
                }
                case NORTH -> stack.add(getBeam(Direction.NORTH));
                case SOUTH ->  stack.add(getBeam(Direction.SOUTH));
            }
        }
    }

    public static class HorizontalSplitter extends Tile {
        public HorizontalSplitter(Point position) {
            super(position);
        }

        @Override
        public String toString() {
            return "-";
        }

        @Override
        public void accept(Stack<Beam> stack, Direction direction) {
            switch (direction) {
                case NORTH, SOUTH -> {
                    stack.add(getBeam(Direction.EAST));
                    stack.add(getBeam(Direction.WEST));
                }
                case EAST -> stack.add(getBeam(Direction.EAST));
                case WEST -> stack.add(getBeam(Direction.WEST));
            }
        }
    }

    public enum Direction {
        NORTH(3),
        EAST(0),
        SOUTH(1),
        WEST(2);

        final int rotation;

        Direction(int rotation) {
            this.rotation = rotation;
        }

        public Point move(Point point) {
            AffineTransform affineTransform = AffineTransform.getQuadrantRotateInstance(rotation, point.x, point.y);
            affineTransform.translate(1, 0);
            return (Point) affineTransform.transform(point, new Point());
        }
    }
}
