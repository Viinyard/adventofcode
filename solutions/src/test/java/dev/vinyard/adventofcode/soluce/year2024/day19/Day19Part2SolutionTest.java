package dev.vinyard.adventofcode.soluce.year2024.day19;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day19Part2SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day19Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2024/day19/part2/test.txt", 16L, null, null),
            Arguments.of("soluce/year2024/day19/input.txt", 717561822679428L, null, null)
        );
    }
}