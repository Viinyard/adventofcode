package dev.vinyard.adventofcode.soluce.year2023.day18;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day18Part2SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day18Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2023/day18/test.txt", 952408144115L, null, null),
            Arguments.of("soluce/year2023/day18/input.txt", 201398068194715L, null, null)
        );
    }
}