package dev.vinyard.adventofcode.soluce.year2023.day19;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day19Part2SolutionTest extends BaseTest<Long> {

    @Override
    public Solution<Long> getSolution() {
        return new Day19Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2023/day19/test.txt", 167409079868000L, null, null),
            Arguments.of("soluce/year2023/day19/input.txt", 131796824371749L, null, null)
        );
    }
}