package dev.vinyard.adventofcode.soluce.year2023.day25;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2023, day = 25, part = 2, description = "Snowverload", link = "https://adventofcode.com/2023/day/25", tags = "unsolved")
public class Day25Part2Solution implements Solution<Long> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>You climb over weather machines, under giant springs, and narrowly avoid a pile of pipes as you find and disconnect the three wires.</p>
    * <p>A moment after you disconnect the last wire, the big red reset button module makes a small ding noise:</p>
    * <pre>
    * System overload resolved!
    * Power required is now <b>50 stars</b>.
    * </pre>
    * <p>Out of the corner of your eye, you notice goggles and a loose-fitting hard hat peeking at you from behind an ultra crucible. You think you see a faint glow, but before you can investigate, you hear another small ding:</p>
    * <pre>
    * Power required is now <b>49 stars</b>.
    * 
    * Please supply the necessary stars and
    * push the button to restart the system.
    * </pre>
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
