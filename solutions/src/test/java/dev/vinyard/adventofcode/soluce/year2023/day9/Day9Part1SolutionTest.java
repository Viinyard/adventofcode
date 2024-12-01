package dev.vinyard.adventofcode.soluce.year2023.day9;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day9Part1SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day9Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
                Arguments.of("soluce/year2023/day9/part1/line1.txt", 18L, null, null),
                Arguments.of("soluce/year2023/day9/part1/line2.txt", 28L, null, null),
                Arguments.of("soluce/year2023/day9/part1/line3.txt", 68L, null, null),
                Arguments.of("soluce/year2023/day9/part1/test.txt", 114L, null, null),
                Arguments.of("soluce/year2023/day9/input.txt", 1904165718L, null, null)
        );
    }
}
