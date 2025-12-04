package dev.vinyard.adventofcode.soluce.year2025.day4;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import dev.vinyard.adventofcode.utils.FileReader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.net.URISyntaxException;

@AdventOfCodeSolution(year = 2025, day = 4, part = 1, description = "Printing Department", link = "https://adventofcode.com/2025/day/4", tags = "unsolved")
public class Day4Part1Solution implements Solution<Long> {

    /**
    * <h2>--- Day 4: Printing Department ---</h2>
    * <p>You ride the escalator down to the printing department. They're clearly getting ready for Christmas; they have lots of large rolls of paper everywhere, and there's even a massive printer in the corner (to handle the really big print jobs).</p>
    * <p>Decorating here will be easy: they can make their own decorations. What you really need is a way to get further into the North Pole base while the elevators are offline.</p>
    * <p>"Actually, maybe we can help with that," one of the Elves replies when you ask for help. "We're pretty sure there's a cafeteria on the other side of the back wall. If we could break through the wall, you'd be able to keep moving. It's too bad all of our forklifts are so busy moving those big rolls of paper around."</p>
    * <p>If you can optimize the work the forklifts are doing, maybe they would have time to spare to break through the wall.</p>
    * <p>The rolls of paper (@) are arranged on a large grid; the Elves even have a helpful diagram (your puzzle input) indicating where everything is located.</p>
    * <p>For example:</p>
    * <pre>
    * ..@@.@@@@.
    * @@@.@.@.@@
    * @@@@@.@.@@
    * @.@@@@..@.
    * @@.@@@@.@@
    * .@@@@@@@.@
    * .@.@.@.@@@
    * @.@@@.@@@@
    * .@@@@@@@@.
    * @.@.@@@.@.
    * </pre>
    * <p>The forklifts can only access a roll of paper if there are <b>fewer than four rolls of paper</b> in the eight adjacent positions. If you can figure out which rolls of paper the forklifts can access, they'll spend less time looking and more time breaking down the wall to the cafeteria.</p>
    * <p>In this example, there are <b>13</b> rolls of paper that can be accessed by a forklift (marked with x):</p>
    * <pre>
    * ..xx.xx@x.
    * x@@.@.@.@@
    * @@@@@.x.@@
    * @.@@@@..@.
    * x@.@@@@.@x
    * .@@@@@@@.@
    * .@.@.@.@@@
    * x.@@@.@@@@
    * .@@@@@@@@.
    * x.x.@@@.x.
    * </pre>
    * <p>Consider your complete diagram of the paper roll locations. <b>How many rolls of paper can be accessed by a forklift?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);
        // TODO get the ASD from the parser

        return null;
    }
}
