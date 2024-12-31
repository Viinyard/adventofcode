package dev.vinyard.adventofcode.soluce.year2024.day23;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day23Part2SolutionTest extends BaseTest<Object> {

    @Override
    public Solution<Object> getSolution() {
        return new Day23Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2024/day23/part2/test.txt", "co,de,ka,ta", null, null),
            Arguments.of("soluce/year2024/day23/input.txt", "bw,dr,du,ha,mm,ov,pj,qh,tz,uv,vq,wq,xw", null, null)
        );
    }
}