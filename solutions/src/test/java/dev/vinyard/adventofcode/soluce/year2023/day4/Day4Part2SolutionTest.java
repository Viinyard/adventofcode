package dev.vinyard.adventofcode.soluce.year2023.day4;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day4Part2SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day4Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
                Arguments.of("soluce/year2023/day4/part2/line1.txt", 1L, null, null),
                Arguments.of("soluce/year2023/day4/part2/line2.txt", 1L, null, null),
                Arguments.of("soluce/year2023/day4/part2/line3.txt", 1L, null, null),
                Arguments.of("soluce/year2023/day4/part2/line4.txt", 1L, null, null),
                Arguments.of("soluce/year2023/day4/part2/line5.txt", 1L, null, null),
                Arguments.of("soluce/year2023/day4/part2/line6.txt", 1L, null, null),
                Arguments.of("soluce/year2023/day4/part2/test.txt", 30L, null, null),
                Arguments.of("soluce/year2023/day4/input.txt", 6420979L, null, null)
        );
    }
}
