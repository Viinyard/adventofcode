package dev.vinyard.adventofcode.soluce.year2024.day13;

import java.util.List;

public class ASD {

    public record Root(List<Machine> machines) {
        public long solve() {
            return (long) machines.stream().mapToDouble(Machine::solve).sum();
        }
    }

    public record Button (double x, double y) {}

    public static class Prize {

        private double x;
        private double y;

        public Prize(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public void addToPosition(long adjustment) {
            this.x += adjustment;
            this.y += adjustment;
        }
    }

    public record Machine(Button a, Button b, Prize p) {

        public double solve() {
            if (a.x * b.y == a.y * b.x)
                return 0;

            double A = (p.x * b.y - p.y * b.x) / (a.x * b.y - a.y * b.x);
            double B = (p.x - a.x * A) / b.x;

            if (A % 1 != 0 || B % 1 != 0)
                return 0;

            return A * 3 + B ;
        }
    }
}
