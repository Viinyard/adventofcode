package dev.vinyard.adventofcode.soluce.year2024.day17;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day17Part1SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day17Part1Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2024/day17/part1/test.txt", "4,6,3,5,6,3,5,2,1,0", null, null),
            Arguments.of("soluce/year2024/day17/part1/test1.txt", "0,1,2", null, null),
            Arguments.of("soluce/year2024/day17/part1/test2.txt", "4,2,5,6,7,7,7,7,3,1,0", null, null),
            Arguments.of("soluce/year2024/day17/input.txt", "2,0,7,3,0,3,1,3,7", null, null)
        );
    }
}