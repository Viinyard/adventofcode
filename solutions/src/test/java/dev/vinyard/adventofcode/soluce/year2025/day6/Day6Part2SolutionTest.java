package dev.vinyard.adventofcode.soluce.year2025.day6;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day6Part2SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day6Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2025/day6/part1/test.txt", 3263827L, null, null),
            Arguments.of("soluce/year2025/day6/input.txt", 9876636978528L, null, null)
        );
    }
}