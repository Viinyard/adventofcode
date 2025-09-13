package dev.vinyard.adventofcode.soluce.year2022.day1;

import java.util.List;

public class ASD {

    public static class Root {

        private final List<Elf> elves;

        public Root(List<Elf> elves) {
            this.elves = elves;
        }

        public long part1() {
            return elves.stream().mapToLong(Elf::totalCalories).max().orElseThrow();
        }

        public long part2() {
            return elves.stream().sorted((e1, e2) -> Long.compare(e2.totalCalories(), e1.totalCalories())).mapToLong(Elf::totalCalories).limit(3).sum();
        }

    }

    public record Elf(List<Long> calories) {
        public long totalCalories() {
            return calories.stream().mapToLong(Long::longValue).sum();
        }
    }

}
