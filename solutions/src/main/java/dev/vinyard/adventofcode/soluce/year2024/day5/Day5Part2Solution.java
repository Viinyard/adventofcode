package dev.vinyard.adventofcode.soluce.year2024.day5;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import dev.vinyard.adventofcode.utils.FileReader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.net.URISyntaxException;

@AdventOfCodeSolution(year = 2024, day = 5, part = 2, description = "Print Queue", link = "https://adventofcode.com/2024/day/5", tags = "unsolved")
public class Day5Part2Solution implements Solution<Object> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>While the Elves get to work printing the correctly-ordered updates, you have a little time to fix the rest of them.</p>
    * <p>For each of the <b>incorrectly-ordered updates</b>, use the page ordering rules to put the page numbers in the right order. For the above example, here are the three incorrectly-ordered updates and their correct orderings:</p>
    * <ul>
    *  <li>75,97,47,61,53 becomes 97,75,<b>47</b>,61,53.</li>
    *  <li>61,13,29 becomes 61,<b>29</b>,13.</li>
    *  <li>97,13,75,29,47 becomes 97,75,<b>47</b>,29,13.</li>
    * </ul>
    * <p>After taking <b>only the incorrectly-ordered updates</b> and ordering them correctly, their middle page numbers are 47, 29, and 47. Adding these together produces <b>123</b>.</p>
    * <p>Find the updates which are not in the correct order. <b>What do you get if you add up the middle page numbers after correctly ordering just those updates?</b></p>
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
