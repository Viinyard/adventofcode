package dev.vinyard.adventofcode.soluce.year2025.day8;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2025, day = 8, part = 2, description = "Playground", link = "https://adventofcode.com/2025/day/8", tags = "unsolved")
public class Day8Part2Solution implements Solution<Object> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>The Elves were right; they <b>definitely</b> don't have enough extension cables. You'll need to keep connecting junction boxes together until they're all in <b>one large circuit</b>.</p>
    * <p>Continuing the above example, the first connection which causes all of the junction boxes to form a single circuit is between the junction boxes at 216,146,977 and 117,168,530. The Elves need to know how far those junction boxes are from the wall so they can pick the right extension cable; multiplying the X coordinates of those two junction boxes (216 and 117) produces <b>25272</b>.</p>
    * <p>Continue connecting the closest unconnected pairs of junction boxes together until they're all in the same circuit. <b>What do you get if you multiply together the X coordinates of the last two junction boxes you need to connect?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.solution2();
    }
}
