package dev.vinyard.adventofcode.soluce.year2022.day1;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day1Part1SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day1Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2022/day1/test.txt", 24000L, null, null),
            Arguments.of("soluce/year2022/day1/input.txt", 67027L, null, null)
        );
    }
}