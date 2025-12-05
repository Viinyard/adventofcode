package dev.vinyard.adventofcode.soluce.year2025.day5;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2025, day = 5, part = 1, description = "Cafeteria", link = "https://adventofcode.com/2025/day/5", tags = "unsolved")
public class Day5Part1Solution implements Solution<Long> {

    /**
    * <h2>--- Day 5: Cafeteria ---</h2>
    * <p>As the forklifts break through the wall, the Elves are delighted to discover that there was a cafeteria on the other side after all.</p>
    * <p>You can hear a commotion coming from the kitchen. "At this rate, we won't have any time left to put the wreaths up in the dining hall!" Resolute in your quest, you investigate.</p>
    * <p>"If only we hadn't switched to the new inventory management system right before Christmas!" another Elf exclaims. You ask what's going on.</p>
    * <p>The Elves in the kitchen explain the situation: because of their complicated new inventory management system, they can't figure out which of their ingredients are <b>fresh</b> and which are <b>spoiled</b>. When you ask how it works, they give you a copy of their database (your puzzle input).</p>
    * <p>The database operates on <b>ingredient IDs</b>. It consists of a list of <b>fresh ingredient ID ranges</b>, a blank line, and a list of <b>available ingredient IDs</b>. For example:</p>
    * <pre>
    * 3-5
    * 10-14
    * 16-20
    * 12-18
    * 
    * 1
    * 5
    * 8
    * 11
    * 17
    * 32
    * </pre>
    * <p>The fresh ID ranges are <b>inclusive</b>: the range 3-5 means that ingredient IDs 3, 4, and 5 are all <b>fresh</b>. The ranges can also <b>overlap</b>; an ingredient ID is fresh if it is in <b>any</b> range.</p>
    * <p>The Elves are trying to determine which of the <b>available ingredient IDs</b> are <b>fresh</b>. In this example, this is done as follows:</p>
    * <ul>
    *  <li>Ingredient ID 1 is spoiled because it does not fall into any range.</li>
    *  <li>Ingredient ID 5 is <b>fresh</b> because it falls into range 3-5.</li>
    *  <li>Ingredient ID 8 is spoiled.</li>
    *  <li>Ingredient ID 11 is <b>fresh</b> because it falls into range 10-14.</li>
    *  <li>Ingredient ID 17 is <b>fresh</b> because it falls into range 16-20 as well as range 12-18.</li>
    *  <li>Ingredient ID 32 is spoiled.</li>
    * </ul>
    * <p>So, in this example, <b>3</b> of the available ingredient IDs are fresh.</p>
    * <p>Process the database file from the new inventory management system. <b>How many of the available ingredient IDs are fresh?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.solution1();
    }
}
