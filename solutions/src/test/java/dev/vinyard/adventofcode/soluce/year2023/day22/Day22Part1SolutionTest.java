package dev.vinyard.adventofcode.soluce.year2023.day22;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day22Part1SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day22Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2023/day22/part1/test.txt", 5L, null, null),
            Arguments.of("soluce/year2023/day22/input.txt", 443L, null, null)
        );
    }
}