package dev.vinyard.adventofcode.soluce.year2023.day20;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day20Part1SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day20Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2023/day20/part1/test1.txt", 32000000L, null, null),
            Arguments.of("soluce/year2023/day20/part1/test2.txt", 11687500L, null, null),
            Arguments.of("soluce/year2023/day20/input.txt", 819397964L, null, null)
        );
    }
}