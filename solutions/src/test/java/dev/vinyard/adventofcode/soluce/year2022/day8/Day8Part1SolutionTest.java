package dev.vinyard.adventofcode.soluce.year2022.day8;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day8Part1SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day8Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2022/day8/test.txt", 21L, null, null),
            Arguments.of("soluce/year2022/day8/input.txt", 1835L, null, null)
        );
    }
}