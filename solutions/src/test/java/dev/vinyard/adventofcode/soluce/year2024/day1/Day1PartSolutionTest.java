package dev.vinyard.adventofcode.soluce.year2024.day1;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day1PartSolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day1PartSolution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2024/day1/part/test.txt", null, null, null),
            Arguments.of("soluce/year2024/day1/input.txt", null, null, null)
        );
    }
}