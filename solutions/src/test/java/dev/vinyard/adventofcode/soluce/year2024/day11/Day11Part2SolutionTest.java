package dev.vinyard.adventofcode.soluce.year2024.day11;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day11Part2SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day11Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2024/day11/part2/test.txt", 65601038650482L, null, null),
            Arguments.of("soluce/year2024/day11/input.txt", 224869647102559L, null, null)
        );
    }
}