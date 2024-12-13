package dev.vinyard.adventofcode.soluce.year2024.day13;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day13Part1SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day13Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2024/day13/part1/test.txt", 480L, null, null),
            Arguments.of("soluce/year2024/day13/part1/test1.txt", 280L, null, null),
            Arguments.of("soluce/year2024/day13/part1/test2.txt", 200L, null, null),
            Arguments.of("soluce/year2024/day13/input.txt", 39748L, null, null)
        );
    }
}