package dev.vinyard.adventofcode.soluce.year2024.day5;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day5Part2SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day5Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2024/day5/part2/test.txt", 123, null, null),
            Arguments.of("soluce/year2024/day5/input.txt", 5331, null, null)
        );
    }
}