package dev.vinyard.adventofcode.soluce.year2024.day10;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day10Part2SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day10Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2024/day10/part2/test.txt", 81L, null, null),
            Arguments.of("soluce/year2024/day10/part2/test1.txt", 3L, null, null),
            Arguments.of("soluce/year2024/day10/part2/test2.txt", 1L, null, null),
            Arguments.of("soluce/year2024/day10/part2/test3.txt", 1L, null, null),
            Arguments.of("soluce/year2024/day10/part2/test4.txt", 1L, null, null),
            Arguments.of("soluce/year2024/day10/part2/test5.txt", 13L, null, null),
            Arguments.of("soluce/year2024/day10/part2/test6.txt", 227L, null, null),
            Arguments.of("soluce/year2024/day10/input.txt", 1494L, null, null)
        );
    }
}