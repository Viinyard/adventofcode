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

        public int findEasterEgg(Rectangle bounds) {
            int minEntropyTime = 0;
            double minEntropy = Double.MAX_VALUE;

            for (int time = 0; time < bounds.width * bounds.height; time++) {
                double entropy = calculateEntropy();
                if (entropy < minEntropy) {
                    minEntropy = entropy;
                    minEntropyTime = time;
                }
                robots().forEach(r -> r.move(1, bounds));
            }

            return minEntropyTime;
        }

        /**
         * Calculate the moment of inertia of the robots.
         * When the moment of inertia is at its minimum, the robots have a great chance to form a meaningful shape.
         * @return the moment of inertia of the robots
         */
        public double calculateEntropy() {
            // Calculate the center of mass
            double centerX = robots().stream().mapToDouble(r -> r.position().x).average().orElse(0);
            double centerY = robots().stream().mapToDouble(r -> r.position().y).average().orElse(0);
            Point centerOfMass = new Point((int) centerX, (int) centerY);

            // Calculate the variance of distances from the center of mass
            return robots().stream()
                    .map(Robot::position)
                    .mapToDouble(centerOfMass::distance)
                    .map(e -> Math.pow(e, 2))
                    .average()
                    .orElse(0);
        }

        public String toString(Rectangle bounds) {
            StringBuilder sb = new StringBuilder();

            for (int y = 0; y < bounds.height; y++) {
                for (int x = 0; x < bounds.width; x++) {
                    Point point = new Point(x, y);
                    if (robots.stream().map(Robot::position).anyMatch(p -> p.equals(point))) {
                        sb.append("#");
                    } else {
                        sb.append(".");
                    }
                }
                sb.append("\n");
            }

            return sb.toString();
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
