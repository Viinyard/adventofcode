package dev.vinyard.adventofcode.soluce.year2024.day10;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ASD {

    public record TopographicMap(Entity[][] grid) {

        public Rectangle getBounds() {
            return new Rectangle(grid[0].length, grid.length);
        }

        public List<Entity> getAllTrailHead() {
            return Arrays.stream(grid).flatMap(Arrays::stream).filter(e -> Objects.equals(e.value(), 0)).toList();
        }

        public Optional<ASD.Entity> getEntityAt(Point position) {
            return Optional.of(position).filter(getBounds()::contains).map(p -> grid[p.y][p.x]);
        }

    }

    public record Entity(Point position, Integer value) {

        public List<Entity> getAllHikingTrail(Entity from, TopographicMap topographicMap) {
            if (Objects.equals(this.value, 9))
                return List.of(this);

            return Arrays.stream(Direction.values()).map(d -> d.move(this.position, 1)).map(topographicMap::getEntityAt)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .filter(entity -> Objects.equals(entity.value(), this.value + 1))
                    .map(e -> e.getAllHikingTrail(this, topographicMap))
                    .flatMap(List::stream)
                    .toList();
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

        public Point move(Point point, int translation) {
            AffineTransform affineTransform = AffineTransform.getRotateInstance(rotation, point.x, point.y);
            affineTransform.translate(translation, 0);
            Point moved = new Point();
            affineTransform.transform(point, moved);
            return moved;
        }
    }

}
