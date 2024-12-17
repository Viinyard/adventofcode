package dev.vinyard.adventofcode.soluce.year2023.day11;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day11Part1SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day11Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2023/day11/part1/test.txt", 374L, null, null),
            Arguments.of("soluce/year2023/day11/input.txt", 9536038L, null, null)
        );
    }
}