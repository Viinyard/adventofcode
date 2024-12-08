package dev.vinyard.adventofcode.soluce.year2024.day8;

import lombok.Getter;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class ASD {

    public record Root(Entity[][] grid) {

        public List<Entity> findAllEntities() {
            return Arrays.stream(grid).flatMap(Arrays::stream).toList();
        }

        public List<Antenna> findAllAntennas() {
            return findAllEntities().stream().filter(entity -> entity instanceof Antenna).map(entity -> (Antenna) entity).toList();
        }

        public Map<String, List<Antenna>> getAllAntennasByFrequencies() {
            return findAllAntennas().stream().collect(Collectors.groupingBy(Antenna::getFrequency));
        }

        public Rectangle getBounds() {
            return new Rectangle(grid[0].length, grid.length);
        }

        public Optional<Entity> getEntity(Point position) {
            return Optional.of(position).filter(getBounds()::contains).map(p -> grid[p.y][p.x]);
        }

        public Root reducingAntennas() {
            BiFunction<Point, Point, Point> getSymmetricPoint = (a, b) -> new Point(2 * b.x - a.x, 2 * b.y - a.y);
            getAllAntennasByFrequencies().values().forEach(antennas -> {
                antennas.forEach(a -> antennas.stream()
                        .filter(b -> !Objects.equals(a, b))
                        .map(b -> getSymmetricPoint.apply(a.position, b.position))
                        .map(this::getEntity)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .forEach(e -> e.addAntinode(a.frequency)));
            });


            return this;
        }

        public long countAntinodes() {
            return findAllEntities().stream().map(Entity::getAntinodes).filter(s -> !s.isEmpty()).count();
        }

        @Override
        public String toString() {
            return Arrays.stream(grid).map(row -> Arrays.stream(row).map(Entity::toString).collect(Collectors.joining())).collect(Collectors.joining("\n"));
        }
    }

    public abstract static class Entity {

        protected final Point position;

        @Getter
        protected final Set<String> antinodes = new HashSet<>();

        public Entity(Point position) {
            this.position = position;
        }

        public void addAntinode(String frequency) {
            antinodes.add(frequency);
        }

        @Override
        public String toString() {
            return antinodes.isEmpty() ? "." : "#";
        }
    }

    public static class Empty extends Entity {
        public Empty(Point position) {
            super(position);
        }
    }

    public static class Antenna extends Entity {

        @Getter
        private final String frequency;

        public Antenna(Point position, String frequency) {
            super(position);
            this.frequency = frequency;
        }

        @Override
        public String toString() {
            return frequency;
        }
    }
}
