package dev.vinyard.adventofcode.soluce.year2023.day7;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day7Part2SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day7Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
                Arguments.of("soluce/year2023/day7/part2/line1.txt", 765L, null, null),
                Arguments.of("soluce/year2023/day7/part2/line2.txt", 684L, null, null),
                Arguments.of("soluce/year2023/day7/part2/line3.txt", 28L, null, null),
                Arguments.of("soluce/year2023/day7/part2/line4.txt", 220L, null, null),
                Arguments.of("soluce/year2023/day7/part2/line5.txt", 483L, null, null),
                Arguments.of("soluce/year2023/day7/part2/test.txt", 5905L, null, null),
                Arguments.of("soluce/year2023/day7/input.txt", 248781813L, null, null)
        );
    }
}
