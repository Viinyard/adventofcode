package dev.vinyard.adventofcode.soluce.year2022.day12;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day12Part1SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day12Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2022/day12/part1/test.txt", 31L, null, null),
            Arguments.of("soluce/year2022/day12/input.txt", 484L, null, null)
        );
    }
}