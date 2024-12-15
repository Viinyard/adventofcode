package dev.vinyard.adventofcode.soluce.year2024.day15;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day15Part2SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day15Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
                Arguments.of("soluce/year2024/day15/part2/test.txt", 9021L, null, null),
                Arguments.of("soluce/year2024/day15/part2/test1.txt", 618L, null, null),
                Arguments.of("soluce/year2024/day15/input.txt", 1543338L, null, null)
        );
    }
}