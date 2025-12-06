package dev.vinyard.adventofcode.soluce.year2025.day6;

import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public class ASD {

    public static class Root {

        private final List<Problem> problems = new ArrayList<>();

        public Root(char[][] matrix) {
            int from = 0;
            for (int x = 0; x < matrix[0].length; x++) {

                if (isDelimiterColumn(matrix, x)) {
                    problems.add(getProblem(matrix, from, x));
                    from = x + 1;
                }
            }

            problems.add(getProblem(matrix, from, matrix[0].length + 1));
        }

        private Problem getProblem(char[][] matrix, int fromX, int toX) {
            char[][] array = getSubMatrix(matrix, fromX, toX);

            char[][] numbers = ArrayUtils.subarray(array, 0, array.length - 1);

            char[] operations = array[array.length - 1];
            String operation = String.valueOf(operations).trim();

            return new Problem(numbers, Operation.fromString(operation));
        }

        private char[][] getSubMatrix(char[][] matrix, int fromX, int toX) {
            return Arrays.stream(matrix)
                    .map(row -> ArrayUtils.subarray(row, fromX, toX))
                    .toArray(char[][]::new);
        }

        private boolean isDelimiterColumn(char[][] matrix, int column) {
            return Stream.iterate(0, y -> y + 1)
                    .limit(matrix.length)
                    .map(y -> matrix[y][column])
                    .noneMatch(ch -> ch != ' ');
        }

        public Long solution1() {
            return problems.stream().mapToLong(p -> p.getNumbers().stream().reduce(p.getOperation().apply()).orElse(0L)).sum();
        }

        public Long solution2() {
            return problems.stream().mapToLong(p -> p.getCephalopodNumbers().stream().reduce(p.getOperation().apply()).orElse(0L)).sum();
        }
    }

    public static class Problem {

        private final char[][] matrix;

        @Getter
        private final Operation operation;

        public Problem(char[][] matrix, Operation operation) {
            this.matrix = matrix;
            System.out.println("Problem:");
            Arrays.stream(matrix).map(Arrays::toString).forEach(System.out::println);
            this.operation = operation;

            System.out.println("Numbers: " + getNumbers());
            System.out.println("Cephalopod Numbers: " + getCephalopodNumbers());
        }

        public List<Long> getNumbers() {
            return Arrays.stream(matrix)
                    .map(String::new)
                    .map(String::trim)
                    .map(Long::valueOf)
                    .toList();
        }

        private char[][] transposeMatrix(char[][] matrix) {
            char[][] transposed = new char[matrix[0].length][matrix.length];

            for (int i = 0; i < matrix.length; i++)
                for (int j = 0; j < matrix[0].length; j++)
                    transposed[j][i] = matrix[i][j];

            return transposed;
        }

        public List<Long> getCephalopodNumbers() {
            return Arrays.stream(transposeMatrix(matrix))
                    .map(String::new)
                    .map(String::trim)
                    .map(Long::valueOf)
                    .toList();
        }
    }

    public enum Operation {
        ADD(Long::sum, "+"),
        MULTIPLY((a, b) -> a * b, "*");

        private final BinaryOperator<Long> operation;
        private final String operator;

        Operation(BinaryOperator<Long> operation, String operator) {
            this.operation = operation;
            this.operator = operator;
        }

        public static Operation fromString(String str) {
            return Arrays.stream(Operation.values()).filter(op -> op.operator.equals(str)).findAny()
                    .orElseThrow(() -> new IllegalArgumentException("No operation for string: " + str));
        }

        public BinaryOperator<Long> apply() {
            return operation;
        }


    }
}
