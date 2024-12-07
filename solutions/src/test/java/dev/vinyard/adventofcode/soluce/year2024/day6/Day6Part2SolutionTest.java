package dev.vinyard.adventofcode.soluce.year2024.day6;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day6Part2SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day6Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2024/day6/part2/test.txt", 6L, null, null),
            Arguments.of("soluce/year2024/day6/input.txt", 1995L, null, null)
        );
    }
}