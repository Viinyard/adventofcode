package dev.vinyard.adventofcode.soluce.year2022.day10;

import dev.vinyard.adventofcode.common.BaseTest;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class Day10Part2SolutionTest extends BaseTest<String> {

    @Override
    public Solution<String> getSolution() {
        return new Day10Part2Solution();
    }

    @Override
    public Stream<Arguments> testSolution() {
        return Stream.of(
            Arguments.of("soluce/year2022/day10/test.txt",
                    """
                    ##..##..##..##..##..##..##..##..##..##..
                    ###...###...###...###...###...###...###.
                    ####....####....####....####....####....
                    #####.....#####.....#####.....#####.....
                    ######......######......######......####
                    #######.......#######.......#######.....
                    """, null, null),
            Arguments.of("soluce/year2022/day10/input.txt", """
                    ###..#.....##..####.#..#..##..####..##..
                    #..#.#....#..#.#....#.#..#..#....#.#..#.
                    #..#.#....#....###..##...#..#...#..#....
                    ###..#....#.##.#....#.#..####..#...#.##.
                    #....#....#..#.#....#.#..#..#.#....#..#.
                    #....####..###.#....#..#.#..#.####..###.
                    """, null, null)
        );
    }
}