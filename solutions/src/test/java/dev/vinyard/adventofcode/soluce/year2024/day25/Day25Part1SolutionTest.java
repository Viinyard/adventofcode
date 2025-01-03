package dev.vinyard.adventofcode.soluce.year2024.day25;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day25Part1SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day25Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2024/day25/part1/test.txt", 3L, null, null),
            Arguments.of("soluce/year2024/day25/input.txt", 3287L, null, null)
        );
    }
}