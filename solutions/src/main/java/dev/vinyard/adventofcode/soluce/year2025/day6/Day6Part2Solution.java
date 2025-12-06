package dev.vinyard.adventofcode.soluce.year2025.day6;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2025, day = 6, part = 2, description = "Trash Compactor", link = "https://adventofcode.com/2025/day/6", tags = "unsolved")
public class Day6Part2Solution implements Solution<Long> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>The big cephalopods come back to check on how things are going. When they see that your grand total doesn't match the one expected by the worksheet, they realize they forgot to explain how to read cephalopod math.</p>
    * <p>Cephalopod math is written <b>right-to-left in columns</b>. Each number is given in its own column, with the most significant digit at the top and the least significant digit at the bottom. (Problems are still separated with a column consisting only of spaces, and the symbol at the bottom of the problem is still the operator to use.)</p>
    * <p>Here's the example worksheet again:</p>
    * <pre>
    * 123 328  51 64 
    *  45 64  387 23 
    *   6 98  215 314
    * *   +   *   +  
    * </pre>
    * <p>Reading the problems right-to-left one column at a time, the problems are now quite different:</p>
    * <ul>
    *  <li>The rightmost problem is 4 + 431 + 623 = <b>1058</b></li>
    *  <li>The second problem from the right is 175 * 581 * 32 = <b>3253600</b></li>
    *  <li>The third problem from the right is 8 + 248 + 369 = <b>625</b></li>
    *  <li>Finally, the leftmost problem is 356 * 24 * 1 = <b>8544</b></li>
    * </ul>
    * <p>Now, the grand total is 1058 + 3253600 + 625 + 8544 = <b>3263827</b>.</p>
    * <p>Solve the problems on the math worksheet again. <b>What is the grand total found by adding together all of the answers to the individual problems?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.solution2();
    }
}
