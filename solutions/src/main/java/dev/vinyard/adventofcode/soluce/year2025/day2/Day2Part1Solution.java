package dev.vinyard.adventofcode.soluce.year2025.day2;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import dev.vinyard.adventofcode.utils.FileReader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.net.URISyntaxException;

@AdventOfCodeSolution(year = 2025, day = 2, part = 1, description = "Gift Shop", link = "https://adventofcode.com/2025/day/2", tags = "unsolved")
public class Day2Part1Solution implements Solution<Long> {

    /**
    * <h2>--- Day 2: Gift Shop ---</h2>
    * <p>You get inside and take the elevator to its only other stop: the gift shop. "Thank you for visiting the North Pole!" gleefully exclaims a nearby sign. You aren't sure who is even allowed to visit the North Pole, but you know you can access the lobby through here, and from there you can access the rest of the North Pole base.</p>
    * <p>As you make your way through the surprisingly extensive selection, one of the clerks recognizes you and asks for your help.</p>
    * <p>As it turns out, one of the younger Elves was playing on a gift shop computer and managed to add a whole bunch of invalid product IDs to their gift shop database! Surely, it would be no trouble for you to identify the invalid product IDs for them, right?</p>
    * <p>They've even checked most of the product ID ranges already; they only have a few product ID ranges (your puzzle input) that you'll need to check. For example:</p>
    * <pre>
    * 11-22,95-115,998-1012,1188511880-1188511890,222220-222224,
    * 1698522-1698528,446443-446449,38593856-38593862,565653-565659,
    * 824824821-824824827,2121212118-2121212124</pre>
    * <p>(The ID ranges are wrapped here for legibility; in your input, they appear on a single long line.)</p>
    * <p>The ranges are separated by commas (,); each range gives its <b>first ID</b> and <b>last ID</b> separated by a dash (-).</p>
    * <p>Since the young Elf was just doing silly patterns, you can find the <b>invalid IDs</b> by looking for any ID which is made only of some sequence of digits repeated twice. So, 55 (5 twice), 6464 (64 twice), and 123123 (123 twice) would all be invalid IDs.</p>
    * <p>None of the numbers have leading zeroes; 0101 isn't an ID at all. (101 is a <b>valid</b> ID that you would ignore.)</p>
    * <p>Your job is to find all of the invalid IDs that appear in the given ranges. In the above example:</p>
    * <ul>
    *  <li>11-22 has two invalid IDs, <b>11</b> and <b>22</b>.</li>
    *  <li>95-115 has one invalid ID, <b>99</b>.</li>
    *  <li>998-1012 has one invalid ID, <b>1010</b>.</li>
    *  <li>1188511880-1188511890 has one invalid ID, <b>1188511885</b>.</li>
    *  <li>222220-222224 has one invalid ID, <b>222222</b>.</li>
    *  <li>1698522-1698528 contains no invalid IDs.</li>
    *  <li>446443-446449 has one invalid ID, <b>446446</b>.</li>
    *  <li>38593856-38593862 has one invalid ID, <b>38593859</b>.</li>
    *  <li>The rest of the ranges contain no invalid IDs.</li>
    * </ul>
    * <p>Adding up all the invalid IDs in this example produces <b>1227775554</b>.</p>
    * <p><b>What do you get if you add up all of the invalid IDs?</b></p>
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
