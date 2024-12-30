package dev.vinyard.adventofcode.soluce.year2024.day22;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day22Part2SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day22Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2024/day22/part2/test.txt", 23L, null, null),
            Arguments.of("soluce/year2024/day22/input.txt", 1863L, null, null)
        );
    }
}