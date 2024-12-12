package dev.vinyard.adventofcode.soluce.year2024.day11;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2024, day = 11, part = 2, description = "Plutonian Pebbles", link = "https://adventofcode.com/2024/day/11", tags = "unsolved")
public class Day11Part2Solution implements Solution<Object> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>The Historians sure are taking a long time. To be fair, the infinite corridors <b>are</b> very large.</p>
    * <p><b>How many stones would you have after blinking a total of 75 times?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        root.blink(75);

        return root.countStones();
    }
}
