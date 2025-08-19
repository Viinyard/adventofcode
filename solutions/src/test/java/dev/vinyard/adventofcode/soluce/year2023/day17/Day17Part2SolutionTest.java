package dev.vinyard.adventofcode.soluce.year2023.day17;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day17Part2SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day17Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2023/day17/part2/test1.txt", 71L, null, null),
            Arguments.of("soluce/year2023/day17/test.txt", 94L, null, null),
            Arguments.of("soluce/year2023/day17/input.txt", 1178L, null, null)
        );
    }
}