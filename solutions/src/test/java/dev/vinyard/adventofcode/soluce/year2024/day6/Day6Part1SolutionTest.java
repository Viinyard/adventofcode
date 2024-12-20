package dev.vinyard.adventofcode.soluce.year2024.day6;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day6Part1SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day6Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2024/day6/part1/test.txt", 41, null, null),
            Arguments.of("soluce/year2024/day6/input.txt", 4890, null, null)
        );
    }
}