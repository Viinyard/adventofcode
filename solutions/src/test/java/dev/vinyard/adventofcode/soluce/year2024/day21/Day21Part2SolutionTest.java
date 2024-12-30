package dev.vinyard.adventofcode.soluce.year2024.day21;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day21Part2SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day21Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2024/day21/part2/test.txt", 154115708116294L, null, null),
            Arguments.of("soluce/year2024/day21/part2/test1.txt", 2379451789590L, null, null),
            Arguments.of("soluce/year2024/day21/part2/test2.txt", 70797185862200L, null, null),
            Arguments.of("soluce/year2024/day21/part2/test3.txt", 14543936021812L, null, null),
            Arguments.of("soluce/year2024/day21/part2/test4.txt", 36838581189648L, null, null),
            Arguments.of("soluce/year2024/day21/part2/test5.txt", 29556553253044L, null, null),
            Arguments.of("soluce/year2024/day21/input.txt", 230049027535970L, null, null)
        );
    }
}