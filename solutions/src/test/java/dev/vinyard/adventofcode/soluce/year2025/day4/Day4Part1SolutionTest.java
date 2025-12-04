package dev.vinyard.adventofcode.soluce.year2025.day4;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day4Part1SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day4Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2025/day4/part1/test.txt", null, null, null),
            Arguments.of("soluce/year2025/day4/input.txt", null, null, null)
        );
    }
}