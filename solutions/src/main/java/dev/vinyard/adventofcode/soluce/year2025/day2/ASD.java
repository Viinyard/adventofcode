package dev.vinyard.adventofcode.soluce.year2025.day2;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ASD {

    public static class Root {

        List<Interval> intervals;

        public Root(List<Interval> intervals) {
            this.intervals = intervals;
        }

        public long solution1() {
            return intervals.stream().map(i -> i.getInvalidIds(i::isInvalidP1))
                    .flatMap(List::stream)
                    .mapToLong(Long::longValue)
                    .sum();
        }

        public Object solution2() {
            return intervals.stream().map(i -> i.getInvalidIds(i::isInvalidP2))
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

        public List<Long> getInvalidIds(Predicate<Long> isInvalid) {
            return Stream.iterate(start, n -> n <= end, n -> n + 1)
                    .filter(isInvalid)
                    .toList();
        }

        public boolean isInvalidP1(Long value) {
            String id = value.toString();

            int length = id.length();

            if (length < 2 || length % 2 != 0)
                return false;

            String firstHalf = id.substring(0, length / 2);
            String secondHalf = id.substring(length / 2);

            return Objects.equals(firstHalf, secondHalf);
        }

        public boolean isInvalidP2(Long value) {
            String s = Long.toString(value);
            String motif = s + s;
            return motif.indexOf(s, 1) != s.length();
        }

    }

}
