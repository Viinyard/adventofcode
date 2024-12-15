package dev.vinyard.adventofcode.soluce.year2024.day9;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day9Part2SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day9Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2024/day9/part2/test.txt", 2858L, null, null),
            Arguments.of("soluce/year2024/day9/input.txt", 6227018762750L, null, null)
        );
    }
}