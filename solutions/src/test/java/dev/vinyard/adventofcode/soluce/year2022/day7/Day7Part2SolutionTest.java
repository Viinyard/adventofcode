package dev.vinyard.adventofcode.soluce.year2022.day7;

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
            Arguments.of("soluce/year2022/day7/test.txt", 24933642L, null, null),
            Arguments.of("soluce/year2022/day7/input.txt", 4370655L, null, null)
        );
    }
}