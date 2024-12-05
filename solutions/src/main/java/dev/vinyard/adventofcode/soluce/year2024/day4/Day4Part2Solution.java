package dev.vinyard.adventofcode.soluce.year2024.day4;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2024, day = 4, part = 2, description = "Ceres Search", link = "https://adventofcode.com/2024/day/4", tags = "unsolved")
public class Day4Part2Solution implements Solution<Long> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>The Elf looks quizzically at you. Did you misunderstand the assignment?</p>
    * <p>Looking for the instructions, you flip over the word search to find that this isn't actually an <b>XMAS</b> puzzle; it's an <b>X-MAS</b> puzzle in which you're supposed to find two MAS in the shape of an X. One way to achieve that is like this:</p>
    * <pre>
    * M.S
    * .A.
    * M.S
    * </pre>
    * <p>Irrelevant characters have again been replaced with . in the above diagram. Within the X, each MAS can be written forwards or backwards.</p>
    * <p>Here's the same example from before, but this time all of the X-MASes have been kept instead:</p>
    * <pre>
    * .M.S......
    * ..A..MSMS.
    * .M.S.MAA..
    * ..A.ASMSM.
    * .M.S.M....
    * ..........
    * S.S.S.S.S.
    * .A.A.A.A..
    * M.M.M.M.M.
    * ..........
    * </pre>
    * <p>In this example, an X-MAS appears <b>9</b> times.</p>
    * <p>Flip the word search from the instructions back over to the word search side and try again. <b>How many times does an X-MAS appear?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.countPattern("MAS", ASD.Direction.NORTH_EAST, ASD.Direction.NORTH_WEST, ASD.Direction.SOUTH_EAST, ASD.Direction.SOUTH_WEST);
    }
}
