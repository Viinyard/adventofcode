package dev.vinyard.adventofcode.soluce.year2022.day9;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day9Part1SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day9Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2022/day9/part1/test.txt", 13L, null, null),
            Arguments.of("soluce/year2022/day9/input.txt", 6311L, null, null)
        );
    }
}