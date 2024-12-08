package dev.vinyard.adventofcode.soluce.year2024.day7;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day7Part2SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day7Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2024/day7/part2/test.txt", 11387L, null, null),
            Arguments.of("soluce/year2024/day7/input.txt", 169122112716571L, null, null)
        );
    }
}