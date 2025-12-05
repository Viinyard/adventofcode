package dev.vinyard.adventofcode.soluce.year2025.day5;

import lombok.Getter;

import java.util.List;
import java.util.Objects;

public class ASD {

    public static class Root {

        private List<Range> ranges;
        private final List<Ingredient> ingredients;

        public Root(List<Range> ranges, List<Ingredient> ingredients) {
            this.ranges = ranges;
            this.ingredients = ingredients;
        }

        public long solution1() {
            return ingredients.stream().filter(ingredient -> ranges.stream().anyMatch(range -> range.contains(ingredient))).count();
        }

        public long solution2() {
            int sizeBefore;
            int sizeAfter;

            do {
                sizeBefore = ranges.size();
                ranges = ranges.stream().mapMulti((range1, consumer) ->
                        ranges.stream().filter(range2 -> !Objects.equals(range1, range2))
                                .forEach(range2 -> {
                                    if (range1.intersects(range2)) {
                                        range1.merge(range2);
                                        consumer.accept(range1);
                                    } else {
                                        consumer.accept(range1);
                                    }
                                })
                ).map(Range.class::cast).distinct().toList();

                sizeAfter = ranges.size();
            } while (Objects.equals(sizeBefore, sizeAfter));

            return ranges.stream().mapToLong(Range::getSum).sum();
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
        private long start;
        private long end;

        public Range(long start, long end) {
            this.start = start;
            this.end = end;
        }

        public long getSum() {
            return end - start + 1;
        }

        public boolean intersects(Range other) {
            return this.start <= other.end && other.start <= this.end;
        }

        public void merge(Range other) {
            this.start = Math.min(this.start, other.start);
            this.end = Math.max(this.end, other.end);
        }

        public boolean contains(Ingredient ingredient) {
            return ingredient.getId() >= start && ingredient.getId() <= end;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Range range)) return false;
            return start == range.start && end == range.end;
        }

        @Override
        public int hashCode() {
            return Objects.hash(start, end);
        }
    }

}
