package dev.vinyard.adventofcode.soluce.year2023.day15;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day15Part1SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day15Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2023/day15/part1/test1.txt", 30L, null, null),
            Arguments.of("soluce/year2023/day15/part1/test2.txt", 253L, null, null),
            Arguments.of("soluce/year2023/day15/part1/test3.txt", 97L, null, null),
            Arguments.of("soluce/year2023/day15/part1/test4.txt", 47L, null, null),
            Arguments.of("soluce/year2023/day15/part1/test5.txt", 14L, null, null),
            Arguments.of("soluce/year2023/day15/part1/test6.txt", 180L, null, null),
            Arguments.of("soluce/year2023/day15/part1/test7.txt", 9L, null, null),
            Arguments.of("soluce/year2023/day15/part1/test8.txt", 197L, null, null),
            Arguments.of("soluce/year2023/day15/part1/test9.txt", 48L, null, null),
            Arguments.of("soluce/year2023/day15/part1/test10.txt", 214L, null, null),
            Arguments.of("soluce/year2023/day15/part1/test11.txt", 231L, null, null),
            Arguments.of("soluce/year2023/day15/part1/test.txt", 1320L, null, null),
            Arguments.of("soluce/year2023/day15/input.txt", 516469L, null, null)
        );
    }
}