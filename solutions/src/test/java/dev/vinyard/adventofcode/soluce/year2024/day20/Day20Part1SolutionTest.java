package dev.vinyard.adventofcode.soluce.year2024.day20;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day20Part1SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day20Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2024/day20/part1/test2.txt", 14L, null, null),
            Arguments.of("soluce/year2024/day20/part1/test4.txt", 14L, null, null),
            Arguments.of("soluce/year2024/day20/part1/test6.txt", 2L, null, null),
            Arguments.of("soluce/year2024/day20/part1/test8.txt", 4L, null, null),
            Arguments.of("soluce/year2024/day20/part1/test10.txt", 2L, null, null),
            Arguments.of("soluce/year2024/day20/part1/test12.txt", 3L, null, null),
            Arguments.of("soluce/year2024/day20/part1/test20.txt", 1L, null, null),
            Arguments.of("soluce/year2024/day20/part1/test36.txt", 1L, null, null),
            Arguments.of("soluce/year2024/day20/part1/test38.txt", 1L, null, null),
            Arguments.of("soluce/year2024/day20/part1/test40.txt", 1L, null, null),
            Arguments.of("soluce/year2024/day20/part1/test64.txt", 1L, null, null),
            Arguments.of("soluce/year2024/day20/input.txt", 1499L, null, null)
        );
    }
}