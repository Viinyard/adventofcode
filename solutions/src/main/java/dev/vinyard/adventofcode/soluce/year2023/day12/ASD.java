package dev.vinyard.adventofcode.soluce.year2023.day12;

import java.util.LinkedList;
import java.util.List;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ASD {

    public static class Root {

        private List<Line> lines;

        public Root(List<Line> lines) {
            this.lines = lines;
        }

        public List<Line> getLines() {
            return lines;
        }

    }

    public static class Line {

        private LinkedList<Spring> springs;
        private LinkedList<Integer> conditions;

        public Line(LinkedList<Spring> springs, LinkedList<Integer> conditionRecords) {
            this.springs = springs;
            this.conditions = conditionRecords;
        }

        public long canFillConditionRecords() {
            return canFillConditionRecords(new LinkedList<>(springs), null, new LinkedList<>(conditions));
        }

        public long canFillConditionRecordsPart2() {
            LinkedList<Spring> springs = Stream.generate(() -> {
                LinkedList<Spring> springsCopy = new LinkedList<>(this.springs);
                springsCopy.addLast(Spring.UNKNOWN);
                return springsCopy;
            }).limit(5).flatMap(LinkedList::stream).collect(Collectors.toCollection(LinkedList::new));

            LinkedList<Integer> conditionRecords = Stream.generate(() -> new LinkedList<>(this.conditions)).limit(5).flatMap(LinkedList::stream).collect(Collectors.toCollection(LinkedList::new));

            return canFillConditionRecords(springs, null, conditionRecords);
        }

        private long canFillConditionRecords(LinkedList<Spring> springs, Integer condition, LinkedList<Integer> conditionRecords) {
            if (conditionRecords.isEmpty() && springs.isEmpty() && (condition == null || condition == 0))
                return 1;

            if (springs.isEmpty())
                return 0;

            Spring spring = springs.pop();

            ToLongFunction<Integer> canFillDamaged = v -> {
                LinkedList<Integer> conditionRecordsCopy = new LinkedList<>(conditionRecords);
                if (v == null) {
                    if (conditionRecordsCopy.isEmpty())
                        return 0;
                    v = conditionRecordsCopy.pop();
                }
                if (v == 0) return 0;

                return canFillConditionRecords(new LinkedList<>(springs), --v, new LinkedList<>(conditionRecordsCopy));
            };

            ToLongFunction<Integer> canFillOperational = v -> {
                if (v != null && v != 0) return 0;

                return canFillConditionRecords(new LinkedList<>(springs), null, new LinkedList<>(conditionRecords));
            };

            return switch (spring) {
                case DAMAGED -> canFillDamaged.applyAsLong(condition);
                case OPERATIONAL -> canFillOperational.applyAsLong(condition);
                case UNKNOWN -> {
                    if (condition == null)
                        yield canFillDamaged.applyAsLong(condition) + canFillOperational.applyAsLong(condition);
                    if (condition == 0)
                        yield canFillOperational.applyAsLong(condition);
                    else
                        yield canFillDamaged.applyAsLong(condition);
                }
            };
        }
    }


    public static enum Spring {
        DAMAGED("#"),
        OPERATIONAL("."),
        UNKNOWN("?");

        private final String symbol;

        Spring(String symbol) {
            this.symbol = symbol;
        }
    }
}
