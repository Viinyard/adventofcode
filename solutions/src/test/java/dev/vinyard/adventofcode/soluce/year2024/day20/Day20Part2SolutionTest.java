package dev.vinyard.adventofcode.soluce.year2024.day20;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day20Part2SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day20Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2024/day20/part2/test50.txt", 32L, null, null),
            Arguments.of("soluce/year2024/day20/part2/test52.txt", 31L, null, null),
            Arguments.of("soluce/year2024/day20/part2/test54.txt", 29L, null, null),
            Arguments.of("soluce/year2024/day20/part2/test56.txt", 39L, null, null),
            Arguments.of("soluce/year2024/day20/part2/test58.txt", 25L, null, null),
            Arguments.of("soluce/year2024/day20/part2/test60.txt", 23L, null, null),
            Arguments.of("soluce/year2024/day20/part2/test62.txt", 20L, null, null),
            Arguments.of("soluce/year2024/day20/part2/test64.txt", 19L, null, null),
            Arguments.of("soluce/year2024/day20/part2/test66.txt", 12L, null, null),
            Arguments.of("soluce/year2024/day20/part2/test68.txt", 14L, null, null),
            Arguments.of("soluce/year2024/day20/part2/test70.txt", 12L, null, null),
            Arguments.of("soluce/year2024/day20/part2/test72.txt", 22L, null, null),
            Arguments.of("soluce/year2024/day20/part2/test74.txt", 4L, null, null),
            Arguments.of("soluce/year2024/day20/part2/test76.txt", 3L, null, null),
            Arguments.of("soluce/year2024/day20/input.txt", 1027164L, null, null)
        );
    }
}