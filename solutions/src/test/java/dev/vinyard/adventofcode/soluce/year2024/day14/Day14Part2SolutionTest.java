package dev.vinyard.adventofcode.soluce.year2024.day14;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day14Part2SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day14Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
           // Arguments.of("soluce/year2024/day14/part2/test.txt", null, null, null),
            Arguments.of("soluce/year2024/day14/input.txt", 8006, null, null)
        );
    }
}