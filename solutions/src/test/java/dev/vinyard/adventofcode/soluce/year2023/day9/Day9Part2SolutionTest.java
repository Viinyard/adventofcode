package dev.vinyard.adventofcode.soluce.year2023.day9;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day9Part2SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day9Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
                Arguments.of("soluce/year2023/day9/part2/line1.txt", -3L, null, null),
                Arguments.of("soluce/year2023/day9/part2/line2.txt", 0L, null, null),
                Arguments.of("soluce/year2023/day9/part2/line3.txt", 5L, null, null),
                Arguments.of("soluce/year2023/day9/part2/test.txt", 2L, null, null),
                Arguments.of("soluce/year2023/day9/input.txt", 964L, null, null)
        );
    }
}
