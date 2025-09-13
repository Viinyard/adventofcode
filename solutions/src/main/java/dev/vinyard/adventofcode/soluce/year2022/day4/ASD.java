package dev.vinyard.adventofcode.soluce.year2022.day4;

import org.apache.commons.lang3.Range;

import java.util.List;

public class ASD {

    public static class Root {

        private final List<Pair> pairs;

        public Root(List<Pair> pairs) {
            this.pairs = pairs;
        }

        public long part1() {
            return pairs.stream().filter(Pair::contains).count();
        }

        public long part2() {
            return pairs.stream().filter(Pair::overlaps).count();
        }

    }

    public record Pair(Section left, Section right) {
        public boolean contains() {
            return left.contains(right) || right.contains(left);
        }

        public boolean overlaps() {
            return left.overlaps(right);
        }
    }

    public static class Section {
        private final Range<Long> range;

        public Section(long start, long end) {
            this.range = Range.between(start, end);
        }

        public boolean contains(Section section) {
            return this.range.containsRange(section.range);
        }

        public boolean overlaps(Section section) {
            return this.range.isOverlappedBy(section.range);
        }
    }

}
