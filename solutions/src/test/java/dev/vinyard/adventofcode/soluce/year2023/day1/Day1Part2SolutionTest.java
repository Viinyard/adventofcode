package dev.vinyard.adventofcode.soluce.year2023.day1;


import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day1Part2SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day1Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
                Arguments.of("soluce/year2023/day1/part2/line1.txt", 29L, null, null),
                Arguments.of("soluce/year2023/day1/part2/line2.txt", 83L, null, null),
                Arguments.of("soluce/year2023/day1/part2/line3.txt", 13L, null, null),
                Arguments.of("soluce/year2023/day1/part2/line4.txt", 24L, null, null),
                Arguments.of("soluce/year2023/day1/part2/line5.txt", 42L, null, null),
                Arguments.of("soluce/year2023/day1/part2/line6.txt", 14L, null, null),
                Arguments.of("soluce/year2023/day1/part2/line7.txt", 76L, null, null),
                Arguments.of("soluce/year2023/day1/part2/test.txt", 281L, null, null),
                Arguments.of("soluce/year2023/day1/input.txt", 53389L, null, null)
        );
    }
}
