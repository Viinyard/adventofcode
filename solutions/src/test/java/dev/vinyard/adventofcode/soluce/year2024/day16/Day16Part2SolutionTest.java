package dev.vinyard.adventofcode.soluce.year2024.day16;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day16Part2SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day16Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2024/day16/part2/test.txt", 45L, null, null),
            Arguments.of("soluce/year2024/day16/part2/test1.txt", 64L, null, null),
            Arguments.of("soluce/year2024/day16/input.txt", 538L, null, null)
        );
    }
}