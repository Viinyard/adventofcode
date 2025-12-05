package dev.vinyard.adventofcode.soluce.year2025.day5;

import lombok.Getter;

import java.util.List;

public class ASD {

    public static class Root {

        private final List<Range> ranges;
        private final List<Ingredient> ingredients;

        public Root(List<Range> ranges, List<Ingredient> ingredients) {
            this.ranges = ranges;
            this.ingredients = ingredients;
        }

        public long solution1() {
            return ingredients.stream().filter(ingredient -> ranges.stream().anyMatch(range -> range.contains(ingredient))).count();
        }
    }

    public static class Ingredient {
        @Getter
        private long id;

        public Ingredient(long id) {
            this.id = id;
        }
    }

    public static class Range {
        private final long start;
        private final long end;

        public Range(long start, long end) {
            this.start = start;
            this.end = end;
        }

        public boolean contains(Ingredient ingredient) {
            return ingredient.getId() >= start && ingredient.getId() <= end;
        }
    }

}
