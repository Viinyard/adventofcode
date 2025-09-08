package dev.vinyard.adventofcode.soluce.year2023.day23;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day23Part2SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day23Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2023/day23/test.txt", 154L, null, null),
            Arguments.of("soluce/year2023/day23/input.txt", 6322L, null, null)
        );
    }
}