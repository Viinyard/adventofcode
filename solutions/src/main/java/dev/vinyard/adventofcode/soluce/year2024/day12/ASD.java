package dev.vinyard.adventofcode.soluce.year2024.day12;

import lombok.Getter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.*;

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

        public Plant getPlantAt(Point point) {
            return Optional.of(point).filter(getBounds()::contains).map(p -> grid[p.y][p.x]).orElse(null);
        }

        public List<Plant> getPlants() {
            return Arrays.stream(grid).flatMap(Arrays::stream).toList();
        }

        public long getFences() {
            return getPlants().stream().map(Plant::getRegion).mapToLong(region -> region.stream().mapToLong(Plant::countFence).sum() * region.size()).sum();
        }
    }

    @Getter
    public static class Plant {

        private final Point point;
        private final String name;
        private boolean visited;
        private List<Plant> neighbors;

        public Plant(Point point, String name) {
            this.point = point;
            this.name = name;
        }

        public boolean isSame(Plant plant) {
            return Objects.equals(name, plant.getName());
        }

        public void setNeighbors(Garden garden) {
            this.neighbors = Arrays.stream(Direction.values())
                    .map(d -> d.move(point))
                    .map(garden::getPlantAt)
                    .filter(Objects::nonNull)
                    .filter(this::isSame)
                    .toList();
        }

        public List<Plant> getRegion() {
            List<Plant> region = new ArrayList<>();

            Stack<Plant> stack = new Stack<>();
            stack.push(this);

            while (!stack.isEmpty()) {
                Plant current = stack.pop();

                if (!current.isVisited()) {
                    current.visited = true;
                    current.getNeighbors().stream().filter(p -> !p.visited).forEach(stack::push);
                    region.add(current);
                }
            }

            return region;
        }

        public long countFence() {
            return 4 - neighbors.size();
        }
    }

    public enum Direction {
        NORTH(270),
        EAST(0),
        SOUTH(90),
        WEST(180);

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
