package dev.vinyard.adventofcode.soluce.year2024.day19;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day19Part1SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day19Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2024/day19/part1/test.txt", 6L, null, null),
            Arguments.of("soluce/year2024/day19/input.txt", 340L, null, null)
        );
    }
}