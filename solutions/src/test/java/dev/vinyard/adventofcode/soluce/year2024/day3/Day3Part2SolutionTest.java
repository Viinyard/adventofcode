package dev.vinyard.adventofcode.soluce.year2024.day3;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day3Part2SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day3Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2024/day3/part2/test.txt", 48, null, null),
            Arguments.of("soluce/year2024/day3/input.txt", 82045421, null, null)
        );
    }
}