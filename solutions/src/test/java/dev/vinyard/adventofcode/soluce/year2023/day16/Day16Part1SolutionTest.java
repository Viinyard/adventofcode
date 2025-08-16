package dev.vinyard.adventofcode.soluce.year2023.day16;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day16Part1SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day16Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2023/day16/part1/test.txt", 46L, null, null),
            Arguments.of("soluce/year2023/day16/input.txt", 7482L, null, null)
        );
    }
}