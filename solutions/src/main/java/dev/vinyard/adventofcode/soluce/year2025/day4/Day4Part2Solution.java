package dev.vinyard.adventofcode.soluce.year2025.day4;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2025, day = 4, part = 2, description = "Printing Department", link = "https://adventofcode.com/2025/day/4", tags = "unsolved")
public class Day4Part2Solution implements Solution<Object> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>Now, the Elves just need help accessing as much of the paper as they can.</p>
    * <p>Once a roll of paper can be accessed by a forklift, it can be <b>removed</b>. Once a roll of paper is removed, the forklifts might be able to access <b>more</b> rolls of paper, which they might also be able to remove. How many total rolls of paper could the Elves remove if they keep repeating this process?</p>
    * <p>Starting with the same example as above, here is one way you could remove as many rolls of paper as possible, using highlighted <b>@</b> to indicate that a roll of paper is about to be removed, and using x to indicate that a roll of paper was just removed:</p>
    * <pre>
    * Initial state:
    * ..<b>@</b><b>@</b>.<b>@</b><b>@</b>@<b>@</b>.
    * <b>@</b>@@.@.@.@@
    * @@@@@.<b>@</b>.@@
    * @.@@@@..@.
    * <b>@</b>@.@@@@.@<b>@</b>
    * .@@@@@@@.@
    * .@.@.@.@@@
    * <b>@</b>.@@@.@@@@
    * .@@@@@@@@.
    * <b>@</b>.<b>@</b>.@@@.<b>@</b>.
    * 
    * Remove 13 rolls of paper:
    * ..xx.xx<b>@</b>x.
    * x@@.<b>@</b>.<b>@</b>.@<b>@</b>
    * <b>@</b>@@@@.x.@@
    * <b>@</b>.@@@@..<b>@</b>.
    * x@.@@@@.<b>@</b>x
    * .<b>@</b>@@@@@@.<b>@</b>
    * .<b>@</b>.@.@.@@@
    * x.@@@.@@@@
    * .<b>@</b>@@@@@@@.
    * x.x.@@@.x.
    * 
    * Remove 12 rolls of paper:
    * .......x..
    * .<b>@</b>@.x.x.<b>@</b>x
    * x@@@@...<b>@</b><b>@</b>
    * x.@@@@..x.
    * .<b>@</b>.@@@@.x.
    * .x@@@@@@.x
    * .x.@.@.@@<b>@</b>
    * ..@@@.@@@@
    * .x<b>@</b>@@@@@@.
    * ....@@@...
    * 
    * Remove 7 rolls of paper:
    * ..........
    * .x<b>@</b>.....x.
    * .<b>@</b>@@@...xx
    * ..@@@@....
    * .x.@@@@...
    * ..<b>@</b>@@@@@..
    * ...@.@.@@x
    * ..<b>@</b>@@.@@@<b>@</b>
    * ..x@@@@@@.
    * ....@@@...
    * 
    * Remove 5 rolls of paper:
    * ..........
    * ..x.......
    * .x<b>@</b>@@.....
    * ..@@@@....
    * ...@@@@...
    * ..x@@@@@..
    * ...@.@.@@.
    * ..x@@.@@@x
    * ...@@@@@<b>@</b>.
    * ....@@@...
    * 
    * Remove 2 rolls of paper:
    * ..........
    * ..........
    * ..x@@.....
    * ..<b>@</b>@@@....
    * ...@@@@...
    * ...@@@@@..
    * ...@.@.@@.
    * ...@@.@@@.
    * ...@@@@@x.
    * ....@@@...
    * 
    * Remove 1 roll of paper:
    * ..........
    * ..........
    * ...<b>@</b>@.....
    * ..x@@@....
    * ...@@@@...
    * ...@@@@@..
    * ...@.@.@@.
    * ...@@.@@@.
    * ...@@@@@..
    * ....@@@...
    * 
    * Remove 1 roll of paper:
    * ..........
    * ..........
    * ...x<b>@</b>.....
    * ...@@@....
    * ...@@@@...
    * ...@@@@@..
    * ...@.@.@@.
    * ...@@.@@@.
    * ...@@@@@..
    * ....@@@...
    * 
    * Remove 1 roll of paper:
    * ..........
    * ..........
    * ....x.....
    * ...<b>@</b>@@....
    * ...@@@@...
    * ...@@@@@..
    * ...@.@.@@.
    * ...@@.@@@.
    * ...@@@@@..
    * ....@@@...
    * 
    * Remove 1 roll of paper:
    * ..........
    * ..........
    * ..........
    * ...x@@....
    * ...@@@@...
    * ...@@@@@..
    * ...@.@.@@.
    * ...@@.@@@.
    * ...@@@@@..
    * ....@@@...
    * </pre>
    * <p>Stop once no more rolls of paper are accessible by a forklift. In this example, a total of <b>43</b> rolls of paper can be removed.</p>
    * <p>Start with your original diagram. <b>How many rolls of paper in total can be removed by the Elves and their forklifts?</b></p>
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
