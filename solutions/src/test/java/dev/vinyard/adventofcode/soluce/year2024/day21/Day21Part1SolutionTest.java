package dev.vinyard.adventofcode.soluce.year2024.day21;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day21Part1SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day21Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2024/day21/part1/test.txt", 126384L, null, null),
            Arguments.of("soluce/year2024/day21/part1/test1.txt", 68L * 29, null, null),
            Arguments.of("soluce/year2024/day21/part1/test2.txt", 60L * 980, null, null),
            Arguments.of("soluce/year2024/day21/part1/test3.txt", 68L * 179, null, null),
            Arguments.of("soluce/year2024/day21/part1/test4.txt", 64L * 456, null, null),
            Arguments.of("soluce/year2024/day21/part1/test5.txt", 64L * 379, null, null),
            Arguments.of("soluce/year2024/day21/input.txt", null, null, null)
        );
    }
}