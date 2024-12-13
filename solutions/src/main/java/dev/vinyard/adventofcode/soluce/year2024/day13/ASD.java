package dev.vinyard.adventofcode.soluce.year2024.day13;

import java.util.List;

public class ASD {

    public record Root(List<Machine> machines) {
        public long solve() {
            return machines.stream().mapToLong(Machine::solve).sum();
        }
    }

    public record Button (long x, long y) {}

    public record Prize (long x, long y) {}

    public record Machine(Button a, Button b, Prize p) {

        public long solve() {
            long A = (p.x * b.y - p.y * b.x) / (a.x * b.y - a.y * b.x);
            long B = (p.x - a.x * A) / b.x;

            return A + B * 3;
        }
    }
}
