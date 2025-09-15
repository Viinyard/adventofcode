package dev.vinyard.adventofcode.soluce.year2022.day11;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day11Part2SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day11Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2022/day11/test.txt", 2713310158L, null, null),
            Arguments.of("soluce/year2022/day11/input.txt", 17926061332L, null, null)
        );
    }
}