package dev.vinyard.adventofcode.soluce.year2023.day18;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day18Part1SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day18Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2023/day18/part1/test.txt", 62L, null, null),
            Arguments.of("soluce/year2023/day18/input.txt", 46394L, null, null)
        );
    }
}