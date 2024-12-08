package dev.vinyard.adventofcode.soluce.year2024.day8;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day8Part2SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day8Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2024/day8/part2/test.txt", 34L, null, null),
            Arguments.of("soluce/year2024/day8/input.txt", 1015L, null, null)
        );
    }
}