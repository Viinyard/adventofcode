package dev.vinyard.adventofcode.soluce.year2022.day4;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2022, day = 4, part = 1, description = "Camp Cleanup", link = "https://adventofcode.com/2022/day/4", tags = "unsolved")
public class Day4Part1Solution implements Solution<Long> {

    /**
    * <h2>--- Day 4: Camp Cleanup ---</h2>
    * <p>Space needs to be cleared before the last supplies can be unloaded from the ships, and so several Elves have been assigned the job of cleaning up sections of the camp. Every section has a unique <b>ID number</b>, and each Elf is assigned a range of section IDs.</p>
    * <p>However, as some of the Elves compare their section assignments with each other, they've noticed that many of the assignments <b>overlap</b>. To try to quickly find overlaps and reduce duplicated effort, the Elves pair up and make a <b>big list of the section assignments for each pair</b> (your puzzle input).</p>
    * <p>For example, consider the following list of section assignment pairs:</p>
    * <pre>
    * 2-4,6-8
    * 2-3,4-5
    * 5-7,7-9
    * 2-8,3-7
    * 6-6,4-6
    * 2-6,4-8
    * </pre>
    * <p>For the first few pairs, this list means:</p>
    * <ul>
    *  <li>Within the first pair of Elves, the first Elf was assigned sections 2-4 (sections 2, 3, and 4), while the second Elf was assigned sections 6-8 (sections 6, 7, 8).</li>
    *  <li>The Elves in the second pair were each assigned two sections.</li>
    *  <li>The Elves in the third pair were each assigned three sections: one got sections 5, 6, and 7, while the other also got 7, plus 8 and 9.</li>
    * </ul>
    * <p>This example list uses single-digit section IDs to make it easier to draw; your actual list might contain larger numbers. Visually, these pairs of section assignments look like this:</p>
    * <pre>
    * .234.....  2-4
    * .....678.  6-8
    * 
    * .23......  2-3
    * ...45....  4-5
    * 
    * ....567..  5-7
    * ......789  7-9
    * 
    * .2345678.  2-8
    * ..34567..  3-7
    * 
    * .....6...  6-6
    * ...456...  4-6
    * 
    * .23456...  2-6
    * ...45678.  4-8
    * </pre>
    * <p>Some of the pairs have noticed that one of their assignments <b>fully contains</b> the other. For example, 2-8 fully contains 3-7, and 6-6 is fully contained by 4-6. In pairs where one assignment fully contains the other, one Elf in the pair would be exclusively cleaning sections their partner will already be cleaning, so these seem like the most in need of reconsideration. In this example, there are <b>2</b> such pairs.</p>
    * <p><b>In how many assignment pairs does one range fully contain the other?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.part1();
    }
}
