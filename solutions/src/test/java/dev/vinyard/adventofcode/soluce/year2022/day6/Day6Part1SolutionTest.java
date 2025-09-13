package dev.vinyard.adventofcode.soluce.year2022.day6;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day6Part1SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day6Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2022/day6/part1/test.txt", 7L, null, null),
            Arguments.of("soluce/year2022/day6/part1/test1.txt", 5L, null, null),
            Arguments.of("soluce/year2022/day6/part1/test2.txt", 6L, null, null),
            Arguments.of("soluce/year2022/day6/part1/test3.txt", 10L, null, null),
            Arguments.of("soluce/year2022/day6/part1/test4.txt", 11L, null, null),
            Arguments.of("soluce/year2022/day6/input.txt", 1356L, null, null)
        );
    }
}