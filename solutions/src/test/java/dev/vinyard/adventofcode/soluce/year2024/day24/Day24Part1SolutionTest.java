package dev.vinyard.adventofcode.soluce.year2024.day24;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day24Part1SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day24Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2024/day24/part1/test1.txt", 4L, null, null),
            Arguments.of("soluce/year2024/day24/part1/test2.txt", 2024L, null, null),
            Arguments.of("soluce/year2024/day24/input.txt", 51107420031718L, null, null)
        );
    }
}