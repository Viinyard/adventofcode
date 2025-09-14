package dev.vinyard.adventofcode.soluce.year2022.day7;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day7Part1SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day7Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2022/day7/part1/test.txt", 95437L, null, null),
            Arguments.of("soluce/year2022/day7/input.txt", 1783610L, null, null)
        );
    }
}