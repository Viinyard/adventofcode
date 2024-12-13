package dev.vinyard.adventofcode.soluce.year2024.day12;

import lombok.Getter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.*;
import java.util.stream.Stream;

public class ASD {

    public static class Garden {

        private final Plant[][] grid;

        public Garden(Plant[][] grid) {
            this.grid = grid;
            getPlants().forEach(p -> p.setNeighbors(this));
        }

        public Rectangle getBounds() {
            return new Rectangle(grid[0].length, grid.length);
        }

        public Optional<Plant> getPlantAt(Point point) {
            return Optional.of(point).filter(getBounds()::contains).map(p -> grid[p.y][p.x]);
        }

        public List<Plant> getPlants() {
            return Arrays.stream(grid).flatMap(Arrays::stream).toList();
        }
    }

    @Getter
    public static class Plant {

        private final Point point;
        private final String name;
        private boolean visited;
        private Map<Direction, Plant> neighbors = new HashMap<>();
        private Map<Direction, Plant> diagonalNeighbors = new HashMap<>();

        public Plant(Point point, String name) {
            this.point = point;
            this.name = name;
        }

        public boolean isSame(Plant plant) {
            return Objects.equals(name, plant.getName());
        }

        public void setNeighbors(Garden garden) {
            Stream.of(Direction.NORTH, Direction.WEST, Direction.EAST, Direction.SOUTH).forEach(d -> garden.getPlantAt(d.move(point)).filter(this::isSame).ifPresent(p -> neighbors.put(d, p)));
            Stream.of(Direction.NORTH_EAST, Direction.NORTH_WEST, Direction.SOUTH_EAST, Direction.SOUTH_WEST).forEach(d -> garden.getPlantAt(d.move(point)).filter(this::isSame).ifPresent(p -> diagonalNeighbors.put(d, p)));
        }

        public List<Plant> getRegion() {
            List<Plant> region = new ArrayList<>();

            Stack<Plant> stack = new Stack<>();
            stack.push(this);

            while (!stack.isEmpty()) {
                Plant current = stack.pop();

                if (!current.isVisited()) {
                    current.visited = true;
                    current.getNeighbors().values().stream().filter(p -> !p.visited).forEach(stack::push);
                    region.add(current);
                }
            }

            return region;
        }

        public long countSides() {
            return 4 - neighbors.size();
        }

        public long countEdges() {
            long edge = 0;
            if (neighbors.keySet().stream().noneMatch(direction -> List.of(Direction.EAST, Direction.SOUTH).contains(direction))
            || !diagonalNeighbors.containsKey(Direction.SOUTH_EAST) && neighbors.keySet().containsAll(List.of(Direction.EAST, Direction.SOUTH))) {
                edge++;
            }

            if (neighbors.keySet().stream().noneMatch(direction -> List.of(Direction.SOUTH, Direction.WEST).contains(direction))
            || !diagonalNeighbors.containsKey(Direction.SOUTH_WEST) && neighbors.keySet().containsAll(List.of(Direction.SOUTH, Direction.WEST))) {
                edge++;
            }

            if (neighbors.keySet().stream().noneMatch(direction -> List.of(Direction.WEST, Direction.NORTH).contains(direction))
            || !diagonalNeighbors.containsKey(Direction.NORTH_WEST) && neighbors.keySet().containsAll(List.of(Direction.WEST, Direction.NORTH))) {
                edge++;
            }

            if (neighbors.keySet().stream().noneMatch(direction -> List.of(Direction.NORTH, Direction.EAST).contains(direction))
            || !diagonalNeighbors.containsKey(Direction.NORTH_EAST) && neighbors.keySet().containsAll(List.of(Direction.NORTH, Direction.EAST))) {
                edge++;
            }

            return edge;
        }
    }

    public enum Direction {
        NORTH(270),
        NORTH_EAST(315),
        EAST(0),
        SOUTH_EAST(45),
        SOUTH(90),
        SOUTH_WEST(135),
        WEST(180),
        NORTH_WEST(225);

        final double rotation;

        Direction(int rotation) {
            this.rotation = Math.toRadians(rotation);
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
