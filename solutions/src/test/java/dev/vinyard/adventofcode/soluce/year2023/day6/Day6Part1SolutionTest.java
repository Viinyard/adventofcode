package dev.vinyard.adventofcode.soluce.year2023.day6;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day6Part1SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day6Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
                Arguments.of("soluce/year2023/day6/part1/line1.txt", 4L, null, null),
                Arguments.of("soluce/year2023/day6/part1/line2.txt", 8L, null, null),
                Arguments.of("soluce/year2023/day6/part1/line3.txt", 9L, null, null),
                Arguments.of("soluce/year2023/day6/part1/test.txt", 288L, null, null),
                Arguments.of("soluce/year2023/day6/input.txt", 840336L, null, null)
        );
    }
}
