package dev.vinyard.adventofcode.soluce.year2025.day1;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day1Part2SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day1Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2025/day1/part1/test.txt", 6L, null, null),
            Arguments.of("soluce/year2025/day1/input.txt", 6228L, null, null)
        );
    }
}