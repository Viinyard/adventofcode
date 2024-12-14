package dev.vinyard.adventofcode.soluce.year2024.day14;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.awt.*;

@AdventOfCodeSolution(year = 2024, day = 14, part = 2, description = "Restroom Redoubt", link = "https://adventofcode.com/2024/day/14", tags = "unsolved")
public class Day14Part2Solution implements Solution<Object> {

    /**
     * <h2>--- Part Two ---</h2>
     * <p>During the bathroom break, someone notices that these robots seem awfully similar to ones built and used at the North Pole. If they're the same type of robots, they should have a hard-coded Easter egg: very rarely, most of the robots should arrange themselves into <b>a picture of a Christmas tree</b>.</p>
     * <p><b>What is the fewest number of seconds that must elapse for the robots to display the Easter egg?</b></p>
     */
    @Override
    public Object solve(String input) throws InterruptedException {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        Rectangle bounds = root.getBounds();
        return root.findEasterEgg(bounds);
    }
}
