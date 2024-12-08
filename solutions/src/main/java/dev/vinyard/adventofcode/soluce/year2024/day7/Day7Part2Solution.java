package dev.vinyard.adventofcode.soluce.year2024.day7;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.List;

@AdventOfCodeSolution(year = 2024, day = 7, part = 2, description = "Bridge Repair", link = "https://adventofcode.com/2024/day/7", tags = "unsolved")
public class Day7Part2Solution implements Solution<Object> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>The engineers seem concerned; the total calibration result you gave them is nowhere close to being within safety tolerances. Just then, you spot your mistake: some well-hidden elephants are holding a <b>third type of operator</b>.</p>
    * <p>The <a href="https://en.wikipedia.org/wiki/Concatenation">concatenation</a> operator (||) combines the digits from its left and right inputs into a single number. For example, 12 || 345 would become 12345. All operators are still evaluated left-to-right.</p>
    * <p>Now, apart from the three equations that could be made true using only addition and multiplication, the above example has three more equations that can be made true by inserting operators:</p>
    * <ul>
    *  <li>156: 15 6 can be made true through a single concatenation: 15 || 6 = 156.</li>
    *  <li>7290: 6 8 6 15 can be made true using 6 * 8 || 6 * 15.</li>
    *  <li>192: 17 8 14 can be made true using 17 || 8 + 14.</li>
    * </ul>
    * <p>Adding up all six test values (the three that could be made before using only + and * plus the new three that can now be made by also using ||) produces the new <b>total calibration result</b> of <b>11387</b>.</p>
    * <p>Using your new knowledge of elephant hiding spots, determine which equations could possibly be true. <b>What is their total calibration result?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        List<ASD.Equation> equations = parser.root().out.equations();

        return equations.stream().mapToLong(e -> e.solve(
                Long::sum,
            (left, right) -> left * right,
            (left, right) -> Long.parseLong(left + "" + right)
        )).sum();
    }
}
