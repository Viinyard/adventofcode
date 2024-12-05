package dev.vinyard.adventofcode.soluce.year2024.day4;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day4Part1SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day4Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2024/day4/part1/test.txt", 18L, null, null),
            Arguments.of("soluce/year2024/day4/input.txt", 2633L, null, null)
        );
    }
}