package dev.vinyard.adventofcode.soluce.year2024.day7;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import dev.vinyard.adventofcode.utils.FileReader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.net.URISyntaxException;

@AdventOfCodeSolution(year = 2024, day = 7, part = 1, description = "Bridge Repair", link = "https://adventofcode.com/2024/day/7", tags = "unsolved")
public class Day7Part1Solution implements Solution<Object> {

    /**
    * <h2>--- Day 7: Bridge Repair ---</h2>
    * <p>The Historians take you to a familiar <a href="/2022/day/9">rope bridge</a> over a river in the middle of a jungle. The Chief isn't on this side of the bridge, though; maybe he's on the other side?</p>
    * <p>When you go to cross the bridge, you notice a group of engineers trying to repair it. (Apparently, it breaks pretty frequently.) You won't be able to cross until it's fixed.</p>
    * <p>You ask how long it'll take; the engineers tell you that it only needs final calibrations, but some young elephants were playing nearby and <b>stole all the operators</b> from their calibration equations! They could finish the calibrations if only someone could determine which test values could possibly be produced by placing any combination of operators into their calibration equations (your puzzle input).</p>
    * <p>For example:</p>
    * <pre>
    * 190: 10 19
    * 3267: 81 40 27
    * 83: 17 5
    * 156: 15 6
    * 7290: 6 8 6 15
    * 161011: 16 10 13
    * 192: 17 8 14
    * 21037: 9 7 18 13
    * 292: 11 6 16 20
    * </pre>
    * <p>Each line represents a single equation. The test value appears before the colon on each line; it is your job to determine whether the remaining numbers can be combined with operators to produce the test value.</p>
    * <p>Operators are <b>always evaluated left-to-right</b>, <b>not</b> according to precedence rules. Furthermore, numbers in the equations cannot be rearranged. Glancing into the jungle, you can see elephants holding two different types of operators: <b>add</b> (+) and <b>multiply</b> (*).</p>
    * <p>Only three of the above equations can be made true by inserting operators:</p>
    * <ul>
    *  <li>190: 10 19 has only one position that accepts an operator: between 10 and 19. Choosing + would give 29, but choosing * would give the test value (10 * 19 = 190).</li>
    *  <li>3267: 81 40 27 has two positions for operators. Of the four possible configurations of the operators, <b>two</b> cause the right side to match the test value: 81 + 40 * 27 and 81 * 40 + 27 both equal 3267 (when evaluated left-to-right)!</li>
    *  <li>292: 11 6 16 20 can be solved in exactly one way: 11 + 6 * 16 + 20.</li>
    * </ul>
    * <p>The engineers just need the <b>total calibration result</b>, which is the sum of the test values from just the equations that could possibly be true. In the above example, the sum of the test values for the three equations listed above is <b>3749</b>.</p>
    * <p>Determine which equations could possibly be true. <b>What is their total calibration result?</b></p>
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
