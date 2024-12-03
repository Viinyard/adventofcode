package dev.vinyard.adventofcode.soluce.year2024.day3;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import dev.vinyard.adventofcode.utils.FileReader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.net.URISyntaxException;

@AdventOfCodeSolution(year = 2024, day = 3, part = 2, description = "Mull It Over", link = "https://adventofcode.com/2024/day/3", tags = "unsolved")
public class Day3Part2Solution implements Solution<Object> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>As you scan through the corrupted memory, you notice that some of the conditional statements are also still intact. If you handle some of the uncorrupted conditional statements in the program, you might be able to get an even more accurate result.</p>
    * <p>There are two new instructions you'll need to handle:</p>
    * <ul>
    *  <li>The do() instruction <b>enables</b> future mul instructions.</li>
    *  <li>The don't() instruction <b>disables</b> future mul instructions.</li>
    * </ul>
    * <p>Only the <b>most recent</b> do() or don't() instruction applies. At the beginning of the program, mul instructions are <b>enabled</b>.</p>
    * <p>For example:</p>
    * <pre>
    * x<b>mul(2,4)</b>&amp;mul[3,7]!^<b>don't()</b>_mul(5,5)+mul(32,64](mul(11,8)un<b>do()</b>?<b>mul(8,5)</b>)</pre>
    * <p>This corrupted memory is similar to the example from before, but this time the mul(5,5) and mul(11,8) instructions are <b>disabled</b> because there is a don't() instruction before them. The other mul instructions function normally, including the one at the end that gets re-<b>enabled</b> by a do() instruction.</p>
    * <p>This time, the sum of the results is <b>48</b> (2*4 + 8*5).</p>
    * <p>Handle the new instructions; <b>what do you get if you add up all of the results of just the enabled multiplications?</b></p>
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
