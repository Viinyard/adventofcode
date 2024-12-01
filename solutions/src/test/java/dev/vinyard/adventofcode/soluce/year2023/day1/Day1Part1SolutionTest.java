package dev.vinyard.adventofcode.soluce.year2023.day1;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day1Part1SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day1Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
                Arguments.of("soluce/year2023/day1/part1/line1.txt", 12L, null, null),
                Arguments.of("soluce/year2023/day1/part1/line2.txt", 38L, null, null),
                Arguments.of("soluce/year2023/day1/part1/line3.txt", 15L, null, null),
                Arguments.of("soluce/year2023/day1/part1/line4.txt", 77L, null, null),
                Arguments.of("soluce/year2023/day1/part1/test.txt", 142L, null, null),
                Arguments.of("soluce/year2023/day1/input.txt", 54338L, null, null)
        );
    }
}