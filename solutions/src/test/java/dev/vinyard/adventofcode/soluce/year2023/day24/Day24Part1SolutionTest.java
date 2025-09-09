package dev.vinyard.adventofcode.soluce.year2023.day24;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day24Part1SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day24Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2023/day24/part1/test.txt", 2L, null, null),
            Arguments.of("soluce/year2023/day24/input.txt", 14672L, null, null)
        );
    }
}