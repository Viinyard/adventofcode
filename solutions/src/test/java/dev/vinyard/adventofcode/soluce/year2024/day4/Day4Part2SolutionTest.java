package dev.vinyard.adventofcode.soluce.year2024.day4;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day4Part2SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day4Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2024/day4/part2/test.txt", 9L, null, null),
            Arguments.of("soluce/year2024/day4/input.txt", 1936L, null, null)
        );
    }
}