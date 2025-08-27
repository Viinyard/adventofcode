package dev.vinyard.adventofcode.soluce.year2023.day21;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day21Part1SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day21Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2023/day21/part1/test.txt", 16L, null, null),
            Arguments.of("soluce/year2023/day21/input.txt", 3724L, null, null)
        );
    }
}