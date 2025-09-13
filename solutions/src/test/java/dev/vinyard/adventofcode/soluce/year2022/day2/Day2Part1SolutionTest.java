package dev.vinyard.adventofcode.soluce.year2022.day2;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day2Part1SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day2Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2022/day2/test.txt", 15L, null, null),
            Arguments.of("soluce/year2022/day2/input.txt", 8890L, null, null)
        );
    }
}