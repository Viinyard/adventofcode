package dev.vinyard.adventofcode.soluce.year2024.day13;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day13Part1SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day13Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2024/day13/part1/test.txt", null, null, null),
            Arguments.of("soluce/year2024/day13/input.txt", null, null, null)
        );
    }
}