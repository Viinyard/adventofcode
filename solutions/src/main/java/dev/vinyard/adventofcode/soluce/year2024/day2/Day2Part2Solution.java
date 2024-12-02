package dev.vinyard.adventofcode.soluce.year2024.day2;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import dev.vinyard.adventofcode.utils.FileReader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.net.URISyntaxException;

@AdventOfCodeSolution(year = 2024, day = 2, part = 2, description = "Red-Nosed Reports", link = "https://adventofcode.com/2024/day/2", tags = "unsolved")
public class Day2Part2Solution implements Solution<Object> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>The engineers are surprised by the low number of safe reports until they realize they forgot to tell you about the Problem Dampener.</p>
    * <p>The Problem Dampener is a reactor-mounted module that lets the reactor safety systems <b>tolerate a single bad level</b> in what would otherwise be a safe report. It's like the bad level never happened!</p>
    * <p>Now, the same rules apply as before, except if removing a single level from an unsafe report would make it safe, the report instead counts as safe.</p>
    * <p>More of the above example's reports are now safe:</p>
    * <ul>
    *  <li>7 6 4 2 1: <b>Safe</b> without removing any level.</li>
    *  <li>1 2 7 8 9: <b>Unsafe</b> regardless of which level is removed.</li>
    *  <li>9 7 6 2 1: <b>Unsafe</b> regardless of which level is removed.</li>
    *  <li>1 <b>3</b> 2 4 5: <b>Safe</b> by removing the second level, 3.</li>
    *  <li>8 6 <b>4</b> 4 1: <b>Safe</b> by removing the third level, 4.</li>
    *  <li>1 3 6 7 9: <b>Safe</b> without removing any level.</li>
    * </ul>
    * <p>Thanks to the Problem Dampener, <b>4</b> reports are actually <b>safe</b>!</p>
    * <p>Update your analysis by handling situations where the Problem Dampener can remove a single level from unsafe reports. <b>How many reports are now safe?</b></p>
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
