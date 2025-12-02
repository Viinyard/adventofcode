package dev.vinyard.adventofcode.soluce.year2025.day2;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day2Part1SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day2Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2025/day2/part1/test.txt", 1227775554L, null, null),
            Arguments.of("soluce/year2025/day2/input.txt", 28146997880L, null, null)
        );
    }
}