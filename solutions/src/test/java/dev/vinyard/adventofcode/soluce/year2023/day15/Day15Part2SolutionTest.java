package dev.vinyard.adventofcode.soluce.year2023.day15;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day15Part2SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day15Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2023/day15/part2/test.txt", 145L, null, null),
            Arguments.of("soluce/year2023/day15/input.txt", 221627L, null, null)
        );
    }
}