package dev.vinyard.adventofcode.soluce.year2023.day8;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day8Part2SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day8Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
                Arguments.of("soluce/year2023/day8/part2/test.txt", 6L, null, null),
                Arguments.of("soluce/year2023/day8/input.txt", 15746133679061L, null, null)
        );
    }
}
