package dev.vinyard.adventofcode.soluce.year2024.day2;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import dev.vinyard.adventofcode.utils.FileReader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.net.URISyntaxException;

@AdventOfCodeSolution(year = 2024, day = 2, part = 1, description = "Red-Nosed Reports", link = "https://adventofcode.com/2024/day/2", tags = "unsolved")
public class Day2Part1Solution implements Solution<Long> {

    /**
    * <h2>--- Day 2: Red-Nosed Reports ---</h2>
    * <p>Fortunately, the first location The Historians want to search isn't a long walk from the Chief Historian's office.</p>
    * <p>While the <a href="/2015/day/19">Red-Nosed Reindeer nuclear fusion/fission plant</a> appears to contain no sign of the Chief Historian, the engineers there run up to you as soon as they see you. Apparently, they <b>still</b> talk about the time Rudolph was saved through molecular synthesis from a single electron.</p>
    * <p>They're quick to add that - since you're already here - they'd really appreciate your help analyzing some unusual data from the Red-Nosed reactor. You turn to check if The Historians are waiting for you, but they seem to have already divided into groups that are currently searching every corner of the facility. You offer to help with the unusual data.</p>
    * <p>The unusual data (your puzzle input) consists of many <b>reports</b>, one report per line. Each report is a list of numbers called <b>levels</b> that are separated by spaces. For example:</p>
    * <pre>
    * 7 6 4 2 1
    * 1 2 7 8 9
    * 9 7 6 2 1
    * 1 3 2 4 5
    * 8 6 4 4 1
    * 1 3 6 7 9
    * </pre>
    * <p>This example data contains six reports each containing five levels.</p>
    * <p>The engineers are trying to figure out which reports are <b>safe</b>. The Red-Nosed reactor safety systems can only tolerate levels that are either gradually increasing or gradually decreasing. So, a report only counts as safe if both of the following are true:</p>
    * <ul>
    *  <li>The levels are either <b>all increasing</b> or <b>all decreasing</b>.</li>
    *  <li>Any two adjacent levels differ by <b>at least one</b> and <b>at most three</b>.</li>
    * </ul>
    * <p>In the example above, the reports can be found safe or unsafe by checking those rules:</p>
    * <ul>
    *  <li>7 6 4 2 1: <b>Safe</b> because the levels are all decreasing by 1 or 2.</li>
    *  <li>1 2 7 8 9: <b>Unsafe</b> because 2 7 is an increase of 5.</li>
    *  <li>9 7 6 2 1: <b>Unsafe</b> because 6 2 is a decrease of 4.</li>
    *  <li>1 3 2 4 5: <b>Unsafe</b> because 1 3 is increasing but 3 2 is decreasing.</li>
    *  <li>8 6 4 4 1: <b>Unsafe</b> because 4 4 is neither an increase or a decrease.</li>
    *  <li>1 3 6 7 9: <b>Safe</b> because the levels are all increasing by 1, 2, or 3.</li>
    * </ul>
    * <p>So, in this example, <b>2</b> reports are <b>safe</b>.</p>
    * <p>Analyze the unusual data from the engineers. <b>How many reports are safe?</b></p>
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
