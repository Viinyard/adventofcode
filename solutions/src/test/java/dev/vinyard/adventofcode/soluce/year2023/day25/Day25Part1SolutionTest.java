package dev.vinyard.adventofcode.soluce.year2023.day25;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day25Part1SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day25Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2023/day25/part1/test.txt", 54L, null, null),
            Arguments.of("soluce/year2023/day25/input.txt", 550080L, null, null)
        );
    }
}