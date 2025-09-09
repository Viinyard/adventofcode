package dev.vinyard.adventofcode.soluce.year2023.day24;

import java.util.List;

public class ASD {

    public static class Root {
        private final List<Hail> hails;
        private final Bounds bounds;

        public Root(List<Hail> hails, Bounds bounds) {
            this.hails = hails;
            this.bounds = bounds;
        }

        public Long getCollisionsCountInBounds() {
            long count = 0;

            for (int i = 0; i < hails.size(); i++) {
                for (int j = i + 1; j < hails.size(); j++) {
                    Hail first = hails.get(i);
                    Hail second = hails.get(j);

                    if (getHailCollisionPosition(first, second)) {
                        count++;
                    }
                }
            }

            return count;
        }

        public boolean getHailCollisionPosition(Hail first, Hail second) {
            double a1 = first.a();
            double b1 = first.b();
            double c1 = first.c();

            double a2 = second.a();
            double b2 = second.b();
            double c2 = second.c();

            if (a1 * b2 == a2 * b1)
                return false; // Parallel lines

            double x = (c1 * b2 - c2 * b1) / (a1 * b2 - a2 * b1);
            double y = (c1 * a2 - c2 * a1) / (a2 * b1 - a1 * b2);

            if (first.velocity.x * (x - first.position.x) < 0 || first.velocity.y * (y - first.position.y) < 0)
                return false; // In the past

            if (second.velocity.x * (x - second.position.x) < 0 || second.velocity.y * (y - second.position.y) < 0)
                return false; // In the past

            return bounds.contains(x) && bounds.contains(y);
        }
    }

    public record Bounds(double min, double max) {

        public boolean contains(double value) {
            return value >= min && value <= max;
        }
    }

    public record Hail(Position position, Velocity velocity) {
        public double a() {
            return -velocity.y;
        }

        public double b() {
            return velocity.x;
        }

        public double c() {
            return velocity.x * position.y - velocity.y * position.x;
        }

        @Override
        public String toString() {
            return "%s @ %s".formatted(position, velocity);
        }
    }
    public record Position(double x, double y, double z) {
        @Override
        public String toString() {
            return "%s, %s, %s".formatted(x, y, z);
        }
    }
    public record Velocity(double x, double y, double z) {
        @Override
        public String toString() {
            return "%s, %s, %s".formatted(x, y, z);
        }
    }

}
