package dev.vinyard.adventofcode.soluce.year2024.day17;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day17Part2SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day17Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2024/day17/part2/test.txt", 117440L, null, null),
            Arguments.of("soluce/year2024/day17/input.txt", 247839539763386L, null, null)
        );
    }
}