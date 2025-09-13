package dev.vinyard.adventofcode.soluce.year2022.day5;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day5Part2SolutionTest extends BaseTest<String> {

    @Override
    public Solution<String> getSolution() {
        return new Day5Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2022/day5/test.txt", "MCD", null, null),
            Arguments.of("soluce/year2022/day5/input.txt", "TPWCGNCCG", null, null)
        );
    }
}