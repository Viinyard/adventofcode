package dev.vinyard.adventofcode.soluce.year2024.day12;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day12Part1SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day12Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2024/day12/part1/test.txt", 1930L, null, null),
            Arguments.of("soluce/year2024/day12/part1/test1.txt", 140L, null, null),
            Arguments.of("soluce/year2024/day12/part1/test2.txt", 772L, null, null),
            Arguments.of("soluce/year2024/day12/input.txt", 1400386L, null, null)
        );
    }
}