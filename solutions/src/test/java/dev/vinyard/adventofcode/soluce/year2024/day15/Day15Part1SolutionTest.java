package dev.vinyard.adventofcode.soluce.year2024.day15;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day15Part1SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day15Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2024/day15/part1/test.txt", 10092L, null, null),
            Arguments.of("soluce/year2024/day15/part1/test1.txt", 2028L, null, null),
            Arguments.of("soluce/year2024/day15/input.txt", 1538871L, null, null)
        );
    }
}