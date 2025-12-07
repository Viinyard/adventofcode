package dev.vinyard.adventofcode.soluce.year2025.day6;

import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ASD {

    public static class Root {

        private final List<Problem> problems;

        public Root(char[][] matrix) {
            this.problems = split(matrix).stream().map(Problem::new).toList();
        }
        
        public List<char[][]> split(char[][] matrix) {
            LinkedList<Integer> list = Stream.iterate(0, i -> i + 1)
                    .limit(matrix[0].length)
                    .filter(col -> isBlank(matrix, col))
                    .collect(Collectors.toCollection(LinkedList::new));
            
            list.addFirst(-1);
            list.addLast(matrix[0].length);
            
            List<char[][]> result = new ArrayList<>();
            while (list.size() > 1) {
                int fromX = list.pollFirst() + 1;
                int toX = list.getFirst();
                
                result.add(getSubMatrix(matrix, fromX, toX));
            }

            return result;
        }

        private char[][] getSubMatrix(char[][] matrix, int fromX, int toX) {
            return Arrays.stream(matrix)
                    .map(row -> ArrayUtils.subarray(row, fromX, toX))
                    .toArray(char[][]::new);
        }
        
        private boolean isBlank(char[][] matrix, int column) {
            return Stream.iterate(0, y -> y + 1)
                    .limit(matrix.length)
                    .map(y -> matrix[y][column])
                    .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                    .toString().isBlank();
        }

        public Long solution1() {
            return problems.stream().mapToLong(Problem::solve).sum();
        }

        public Long solution2() {
            return problems.stream().peek(Problem::transpose).mapToLong(Problem::solve).sum();
        }
    }

    public static class Problem {

        private char[][] matrix;

        @Getter
        private final Operation operation;

        private Problem(char[][] matrix, Operation operation) {
            this.matrix = matrix;
            this.operation = operation;
        }
        
        public Problem(char[][] matrix) {
            char[][] numbers = ArrayUtils.subarray(matrix, 0, matrix.length - 1);

            char[] operations = matrix[matrix.length - 1];
            String operation = String.valueOf(operations).trim();
            
            this(numbers, Operation.fromString(operation));
        }

        private List<Long> getNumbers() {
            return Arrays.stream(matrix)
                    .map(String::new)
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(Long::valueOf)
                    .toList();
        }

        public long solve() {
            return getNumbers().stream().reduce(operation.apply()).orElse(0L);
        }

        public void transpose() {
            char[][] transposed = new char[matrix[0].length][matrix.length];
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    transposed[j][i] = matrix[i][j];
                }
            }
            this.matrix = transposed;
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
