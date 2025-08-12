package dev.vinyard.adventofcode.soluce.year2023.day13;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day13Part2SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day13Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2023/day13/test1.txt", 300L, null, null),
            Arguments.of("soluce/year2023/day13/test2.txt", 100L, null, null),
            Arguments.of("soluce/year2023/day13/test.txt", 400L, null, null),
            Arguments.of("soluce/year2023/day13/input.txt", 33054L, null, null)
        );
    }
}