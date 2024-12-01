package dev.vinyard.adventofcode.soluce.year2023.day4;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day4Part1SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day4Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
                Arguments.of("soluce/year2023/day4/part1/line1.txt", 8L, null, null),
                Arguments.of("soluce/year2023/day4/part1/line2.txt", 2L, null, null),
                Arguments.of("soluce/year2023/day4/part1/line3.txt", 2L, null, null),
                Arguments.of("soluce/year2023/day4/part1/line4.txt", 1L, null, null),
                Arguments.of("soluce/year2023/day4/part1/line5.txt", 0L, null, null),
                Arguments.of("soluce/year2023/day4/part1/line6.txt", 0L, null, null),
                Arguments.of("soluce/year2023/day4/part1/test.txt", 13L, null, null),
                Arguments.of("soluce/year2023/day4/input.txt", 25174L, null, null)
        );
    }
}
