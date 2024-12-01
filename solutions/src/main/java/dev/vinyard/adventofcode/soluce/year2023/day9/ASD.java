package dev.vinyard.adventofcode.soluce.year2023.day9;

import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class ASD {

    @Data
    public static class Report {
        private final List<Suite> suites;

        public Report(List<Suite> suites) {
            this.suites = suites;

            this.suites.forEach(Suite::generateNextSuite);
        }
    }

    @Data
    public static class Suite {
        private final List<Long> values;

        private Suite sub;

        public Suite(List<Long> values) {
            this.values = values;
        }

        public boolean isFinal() {
            return values.stream().allMatch(v -> v == 0);
        }

        public Long getNextValue() {
            return values.getLast() + Optional.ofNullable(sub).map(Suite::getNextValue).orElse(0L);
        }

        public Long getPreviousValue() {
            return values.getFirst() - Optional.ofNullable(sub).map(Suite::getPreviousValue).orElse(0L);
        }

        public void generateNextSuite() {
            if (this.isFinal())
                return;

            this.setSub(new Suite(IntStream.range(0, values.size() - 1).mapToObj(i -> values.get(i + 1) - values.get(i)).toList()));
            this.sub.generateNextSuite();
        }
    }
}
