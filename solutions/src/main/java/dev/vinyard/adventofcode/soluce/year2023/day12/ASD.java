package dev.vinyard.adventofcode.soluce.year2023.day12;

import lombok.Getter;

import java.util.*;
import java.util.function.LongSupplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ASD {

    public static final String DAMAGED = "#";
    public static final String OPERATIONAL = ".";
    public static final String UNKNOWN = "?";

    @Getter
    public static class Root {

        private final List<Line> lines;

        public Root(List<Line> lines) {
            this.lines = lines;
        }

    }

    @Getter
    public static class Line {

        private final String springs;
        private final LinkedList<Integer> conditions;
        private final Map<String, Long> memo = new HashMap<>();

        public Line(String springs, LinkedList<Integer> conditionRecords) {
            this.springs = springs;
            this.conditions = conditionRecords;
        }

        public long countArrangementsPart1() {
            return countArrangements(springs, null, new LinkedList<>(conditions));
        }

        public long countArrangementsPart2() {
            String springs = Stream.generate(this::getSprings).limit(5).collect(Collectors.joining(UNKNOWN));

            LinkedList<Integer> conditionRecords = Stream.generate(() -> new LinkedList<>(this.conditions)).limit(5).flatMap(LinkedList::stream).collect(Collectors.toCollection(LinkedList::new));

            return countArrangements(springs, null, conditionRecords);
        }

        private long countArrangements(final String springs, final Integer condition, final LinkedList<Integer> conditionRecords) {
            final String key = Stream.of(springs, condition, conditionRecords)
                    .map(Objects::toString)
                    .collect(Collectors.joining(";"));

            if (memo.containsKey(key))
                return memo.get(key);

            if (conditionRecords.isEmpty() && springs.isEmpty() && (condition == null || condition == 0))
                return 1;

            if (springs.isEmpty())
                return 0;

            String spring = springs.substring(0, 1);

            LongSupplier canFillDamaged = () -> {
                try {
                    LinkedList<Integer> conditionRecordsCopy = new LinkedList<>(conditionRecords);

                    Integer newValue = Optional.ofNullable(condition).orElseGet(conditionRecordsCopy::pop);

                    if (newValue == 0) return 0;

                    return countArrangements(springs.substring(1), --newValue, new LinkedList<>(conditionRecordsCopy));
                } catch (NoSuchElementException e) {
                    return 0;
                }
            };

            LongSupplier canFillOperational = () -> {
                if (condition != null && condition != 0) return 0;

                return countArrangements(springs.substring(1), null, new LinkedList<>(conditionRecords));
            };

            long value = switch (spring) {
                case DAMAGED -> canFillDamaged.getAsLong();
                case OPERATIONAL -> canFillOperational.getAsLong();
                case UNKNOWN -> canFillDamaged.getAsLong() + canFillOperational.getAsLong();
                default -> throw new IllegalStateException("Unexpected value: " + spring);
            };

            memo.put(key, value);

            return value;
        }
    }
}
