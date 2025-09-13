package dev.vinyard.adventofcode.soluce.year2022.day6;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day6Part2SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day6Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2022/day6/part2/test1.txt", 19L, null, null),
            Arguments.of("soluce/year2022/day6/part2/test2.txt", 23L, null, null),
            Arguments.of("soluce/year2022/day6/part2/test3.txt", 23L, null, null),
            Arguments.of("soluce/year2022/day6/part2/test4.txt", 29L, null, null),
            Arguments.of("soluce/year2022/day6/part2/test5.txt", 26L, null, null),
            Arguments.of("soluce/year2022/day6/input.txt", 2564L, null, null)
        );
    }
}