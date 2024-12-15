package dev.vinyard.adventofcode.soluce.year2023.day11;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2023, day = 11, part = 2, description = "Cosmic Expansion", link = "https://adventofcode.com/2023/day/11", tags = "unsolved")
public class Day11Part2Solution implements Solution<Object> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>The galaxies are much <b>older</b> (and thus much <b>farther apart</b>) than the researcher initially estimated.</p>
    * <p>Now, instead of the expansion you did before, make each empty row or column <b>one million times</b> larger. That is, each empty row should be replaced with 1000000 empty rows, and each empty column should be replaced with 1000000 empty columns.</p>
    * <p>(In the example above, if each empty row or column were merely 10 times larger, the sum of the shortest paths between every pair of galaxies would be <b>1030</b>. If each empty row or column were merely 100 times larger, the sum of the shortest paths between every pair of galaxies would be <b>8410</b>. However, your universe will need to expand far beyond these values.)</p>
    * <p>Starting with the same initial image, expand the universe according to these new rules, then find the length of the shortest path between every pair of galaxies. <b>What is the sum of these lengths?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.calculateSumOfShortestPathBetweenEveryPairOfGalaxies(1_000_000);
    }
}
