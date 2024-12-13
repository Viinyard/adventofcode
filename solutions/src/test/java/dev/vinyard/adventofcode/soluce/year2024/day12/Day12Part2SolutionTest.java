package dev.vinyard.adventofcode.soluce.year2024.day12;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day12Part2SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day12Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2024/day12/part2/test.txt", 1206L, null, null),
            Arguments.of("soluce/year2024/day12/part2/test1.txt", 80L, null, null),
            Arguments.of("soluce/year2024/day12/part2/test2.txt", 436L, null, null),
            Arguments.of("soluce/year2024/day12/part2/test3.txt", 236L, null, null),
            Arguments.of("soluce/year2024/day12/part2/test4.txt", 368L, null, null),
            Arguments.of("soluce/year2024/day12/input.txt", 851994L, null, null)
        );
    }
}