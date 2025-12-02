package dev.vinyard.adventofcode.soluce.year2025.day2;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class ASD {

    public static class Root {

        List<Interval> intervals;

        public Root(List<Interval> intervals) {
            this.intervals = intervals;
        }

        public long solution1() {
            return intervals.stream().map(Interval::getInvalidIds)
                    .flatMap(List::stream)
                    .mapToLong(Long::longValue)
                    .sum();
        }
    }

    public static class Interval {
        private final long start;
        private final long end;

        public Interval(long start, long end) {
            this.start = start;
            this.end = end;
        }

        public List<Long> getInvalidIds() {
            return Stream.iterate(start, n -> n <= end, n -> n + 1)
                    .filter(this::isInvalid)
                    .toList();
        }

        public boolean isInvalid(Long value) {
            String id = value.toString();

            int length = id.length();

            if (length < 2 || length % 2 != 0)
                return false;

            String firstHalf = id.substring(0, length / 2);
            String secondHalf = id.substring(length / 2);

            return Objects.equals(firstHalf, secondHalf);
        }

    }

}
