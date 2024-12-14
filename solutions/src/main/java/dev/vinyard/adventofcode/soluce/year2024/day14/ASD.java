package dev.vinyard.adventofcode.soluce.year2024.day14;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ASD {

    public record Root(List<Robot> robots) {

        public Rectangle getBounds() {
            int width = robots.stream().map(Robot::position).mapToInt(p -> p.x).max().orElse(0) + 1;
            int height = robots.stream().map(Robot::position).mapToInt(p -> p.y).max().orElse(0) + 1;

            return new Rectangle(0, 0, width, height);
        }

        public List<Rectangle> getQuadrants(Rectangle bounds) {
            int width = bounds.width;
            int height = bounds.height;

            Rectangle topLeft = new Rectangle(0, 0, Math.floorDiv(width, 2), Math.floorDiv(height, 2));
            Rectangle topRight = new Rectangle(Math.ceilDiv(width, 2), 0, Math.floorDiv(width, 2), Math.floorDiv(height, 2));
            Rectangle bottomLeft = new Rectangle(0, Math.ceilDiv(height, 2), Math.floorDiv(width, 2), Math.floorDiv(height, 2));
            Rectangle bottomRight = new Rectangle(Math.ceilDiv(width, 2), Math.ceilDiv(height, 2), Math.floorDiv(width, 2), Math.floorDiv(height, 2));

            return List.of(topLeft, topRight, bottomLeft, bottomRight);
        }

        public long getSafetyFactor(Rectangle bounds) {
            Map<Rectangle, Long> safetyFactorByQuadrant = getQuadrants(bounds).stream().collect(Collectors.groupingBy(Function.identity(),
                    Collectors.mapping(quadrant -> robots.stream().map(Robot::position).filter(quadrant::contains).count(), Collectors.summingLong(Long::longValue))));

            return safetyFactorByQuadrant.values().stream().reduce(1L, (a, b) -> a * b);
        }
    }

    public record Robot(Point position, Point velocity) {

        public void move(int times, Rectangle bounds) {
            this.position.setLocation(this.moveX(times, bounds), this.moveY(times, bounds));
        }

        private int moveY(int times, Rectangle bounds) {
            int y = (this.position.y + this.velocity.y * times) % bounds.height;

            return y < 0 ? bounds.height + y : y;
        }

        private int moveX(int times, Rectangle bounds) {
            int x = (this.position.x + this.velocity.x * times) % bounds.width;

            return x < 0 ? bounds.width + x : x;
        }
    }
}
