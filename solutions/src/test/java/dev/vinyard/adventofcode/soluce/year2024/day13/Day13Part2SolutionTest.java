package dev.vinyard.adventofcode.soluce.year2024.day13;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day13Part2SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day13Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2024/day13/part2/test.txt", 875318608908L, null, null),
            Arguments.of("soluce/year2024/day13/input.txt", 74478585072604L, null, null)
        );
    }
}