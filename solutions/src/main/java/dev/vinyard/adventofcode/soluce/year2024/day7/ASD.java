package dev.vinyard.adventofcode.soluce.year2024.day7;

import java.util.List;
import java.util.Objects;

public class ASD {

    public record Root(List<Equation> equations) {}

    public record Equation(long result, List<Long> values) {

        public long solve() {
            return isSolved(values.getFirst(), 1) ? result : 0;
        }

        public boolean isSolved(long currentValue, int index) {
            if (index == values.size())
                return Objects.equals(currentValue, result);

            long nextValue = values.get(index);

            return isSolved(currentValue + nextValue, index+1) || isSolved(currentValue * nextValue, index+1);
        }
    }

}
