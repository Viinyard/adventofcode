package dev.vinyard.adventofcode.soluce.year2023.day12;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day12Part1SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day12Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2023/day12/test1.txt", 1L, null, null),
            Arguments.of("soluce/year2023/day12/test2.txt", 4L, null, null),
            Arguments.of("soluce/year2023/day12/test3.txt", 1L, null, null),
            Arguments.of("soluce/year2023/day12/test4.txt", 1L, null, null),
            Arguments.of("soluce/year2023/day12/test5.txt", 4L, null, null),
            Arguments.of("soluce/year2023/day12/test6.txt", 10L, null, null),
            Arguments.of("soluce/year2023/day12/test.txt", 21L, null, null),
            Arguments.of("soluce/year2023/day12/input.txt", 7633L, null, null)
        );
    }
}