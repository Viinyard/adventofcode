package dev.vinyard.adventofcode.soluce.year2025.day6;

import lombok.Setter;

import java.util.Collection;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public class ASD {

    public static class Root {

        private Collection<Problem> problems;

        public Root(Collection<Problem> problems) {
            this.problems = problems;
        }

        public Long solution1() {
            return problems.stream()
                    .mapToLong(Problem::evaluate)
                    .sum();
        }
    }

    public static class Problem {

        private List<Long> numbers;

        @Setter
        private Operation operation;

        public Problem(long number) {
            this.numbers = List.of(number);
        }

        public Problem merge(Problem other) {
            this.numbers = Stream.concat(this.numbers.stream(), other.numbers.stream()).toList();
            return this;
        }

        public long evaluate() {
            return numbers.stream().reduce(operation.apply()).orElse(0L);
        }
    }

    public enum Operation {
        ADD(Long::sum),
        MULTIPLY((a, b) -> a * b);

        BinaryOperator<Long> operation;

        Operation(BinaryOperator<Long> operation) {
            this.operation = operation;
        }

        public BinaryOperator<Long> apply() {
            return operation;
        }
    }
}
