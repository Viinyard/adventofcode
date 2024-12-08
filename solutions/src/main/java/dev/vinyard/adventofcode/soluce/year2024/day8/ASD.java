package dev.vinyard.adventofcode.soluce.year2024.day8;

import lombok.Getter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

        public Optional<Entity> getEntityAt(Point position) {
            return Optional.of(position).filter(getBounds()::contains).map(p -> grid[p.y][p.x]);
        }

        /**
         * Count the number of entities that are antinodes in the grid.
         * @return number of antinodes in the grid
         */
        public long countAntinodes() {
            return findAllEntities().stream().filter(Entity::isAntinode).count();
        }

        @Override
        public String toString() {
            return Arrays.stream(grid).map(row -> Arrays.stream(row).map(Entity::toString).collect(Collectors.joining())).collect(Collectors.joining("\n"));
        }
    }


    @Getter
    public abstract static class Entity {

        protected final Point position;

        protected boolean antinode = false;

        public Entity(Point position) {
            this.position = position;
        }

        public void setAntinode() {
            this.antinode = true;
        }

        /**
         * Calculate the translation between the entity position from the given position.
         * @param position the position to translate from
         * @return the translation
         */
        public AffineTransform getTransform(Point position) {
            return AffineTransform.getTranslateInstance(this.position.x - position.x, this.position.y - position.y);
        }

        /**
         * Calculate the transformed position of the entity from the given position then apply the transformation from the given position.
         * It's equivalent to calculate the symmetrical position of the entity from the given position.
         * @param position the position to translate from
         * @return the translated position of the given position from the entity position
         */
        public Point getTransformedPosition(Point position) {
            return (Point) getTransform(position).transform(this.position, new Point());
        }

        @Override
        public String toString() {
            return antinode ? "#" : ".";
        }
    }

    public static class Empty extends Entity {
        public Empty(Point position) {
            super(position);
        }
    }

    @Getter
    public static class Antenna extends Entity {

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
