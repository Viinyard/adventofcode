package dev.vinyard.adventofcode.soluce.year2024.day7;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

public class ASD {

    public record Root(List<Equation> equations) {}

    public record Equation(long result, List<Long> values) {

        @SafeVarargs
        public final long solve(BiFunction<Long, Long, Long>... operators) {
            return isSolved(values.getFirst(), 1, operators) ? result : 0;
        }

        public boolean isSolved(long currentValue, int index, BiFunction<Long, Long, Long>... operators) {
            if (index == values.size())
                return Objects.equals(currentValue, result);

            return Arrays.stream(operators).map(op -> op.apply(currentValue, values.get(index))).anyMatch(value -> isSolved(value, index+1, operators));
        }
    }
}
