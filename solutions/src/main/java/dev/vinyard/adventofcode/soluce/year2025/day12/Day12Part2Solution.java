package dev.vinyard.adventofcode.soluce.year2025.day12;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import dev.vinyard.adventofcode.utils.FileReader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.net.URISyntaxException;

@AdventOfCodeSolution(year = 2025, day = 12, part = 2, description = "Christmas Tree Farm", link = "https://adventofcode.com/2025/day/12", tags = "unsolved")
public class Day12Part2Solution implements Solution<Object> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>The Elves thank you profusely for the help and start rearranging the oddly-shaped presents. As you look up, you notice that a lot more Elves have arrived here at the Christmas tree farm.</p>
    * <p>In fact, many of these new arrivals look <b>familiar</b>: they're the Elves you helped while decorating the North Pole base. Right on <a href="1">schedule</a>, each group seems to have brought a <b>star</b> to put atop one of the Christmas trees!</p>
    * <p>Before any of them can find a ladder, a particularly large Christmas tree suddenly flashes brightly when a large <b>star</b> magically appears above it! As your eyes readjust, you think you notice a portly man with a white beard disappear into the crowd.</p>
    * <p>You go look for a ladder; only <b>23 stars</b> to go.</p>
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
