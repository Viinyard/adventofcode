package dev.vinyard.adventofcode.soluce.year2023.day10;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day10Part1SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day10Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
                Arguments.of("soluce/year2023/day10/part1/test1.txt", 4L, null, null),
                Arguments.of("soluce/year2023/day10/part1/test2.txt", 4L, null, null),
                Arguments.of("soluce/year2023/day10/part1/test3.txt", 8L, null, null),
                Arguments.of("soluce/year2023/day10/part1/test4.txt", 8L, null, null),
                Arguments.of("soluce/year2023/day10/input.txt", 6942L, null, null)
        );
    }
}