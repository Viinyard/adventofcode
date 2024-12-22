package dev.vinyard.adventofcode.soluce.year2024.day18;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day18Part1SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day18Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2024/day18/part1/test.txt", 22L, null, null),
            Arguments.of("soluce/year2024/day18/input.txt", 438L, null, null)
        );
    }
}