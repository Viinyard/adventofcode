package dev.vinyard.adventofcode.soluce.year2025.day5;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import dev.vinyard.adventofcode.utils.FileReader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.net.URISyntaxException;

@AdventOfCodeSolution(year = 2025, day = 5, part = 2, description = "Cafeteria", link = "https://adventofcode.com/2025/day/5", tags = "unsolved")
public class Day5Part2Solution implements Solution<Object> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>The Elves start bringing their spoiled inventory to the trash chute at the back of the kitchen.</p>
    * <p>So that they can stop bugging you when they get new inventory, the Elves would like to know <b>all</b> of the IDs that the <b>fresh ingredient ID ranges</b> consider to be <b>fresh</b>. An ingredient ID is still considered fresh if it is in any range.</p>
    * <p>Now, the second section of the database (the available ingredient IDs) is irrelevant. Here are the fresh ingredient ID ranges from the above example:</p>
    * <pre>
    * 3-5
    * 10-14
    * 16-20
    * 12-18
    * </pre>
    * <p>The ingredient IDs that these ranges consider to be fresh are 3, 4, 5, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, and 20. So, in this example, the fresh ingredient ID ranges consider a total of <b>14</b> ingredient IDs to be fresh.</p>
    * <p>Process the database file again. <b>How many ingredient IDs are considered to be fresh according to the fresh ingredient ID ranges?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);
        // TODO get the ASD from the parser

        return null;
    }
}
