package dev.vinyard.adventofcode.soluce.year2023.day14;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day14Part1SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day14Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2023/day14/part1/test.txt", 136L, null, null),
            Arguments.of("soluce/year2023/day14/input.txt", 106186L, null, null)
        );
    }
}