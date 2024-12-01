package dev.vinyard.adventofcode.soluce.year2024.day1;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import dev.vinyard.adventofcode.utils.FileReader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.net.URISyntaxException;

@AdventOfCodeSolution(year = 2024, day = 1, part = 2, description = "Historian Hysteria", link = "https://adventofcode.com/2024/day/1", tags = "unsolved")
public class Day1Part2Solution implements Solution<Object> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>Your analysis only confirmed what everyone feared: the two lists of location IDs are indeed very different.</p>
    * <p>Or are they?</p>
    * <p>The Historians can't agree on which group made the mistakes <b>or</b> how to read most of the Chief's handwriting, but in the commotion you notice an interesting detail: a lot of location IDs appear in both lists! Maybe the other numbers aren't location IDs at all but rather misinterpreted handwriting.</p>
    * <p>This time, you'll need to figure out exactly how often each number from the left list appears in the right list. Calculate a total <b>similarity score</b> by adding up each number in the left list after multiplying it by the number of times that number appears in the right list.</p>
    * <p>Here are the same example lists again:</p>
    * <pre>
    * 3   4
    * 4   3
    * 2   5
    * 1   3
    * 3   9
    * 3   3
    * </pre>
    * <p>For these example lists, here is the process of finding the similarity score:</p>
    * <ul>
    *  <li>The first number in the left list is 3. It appears in the right list three times, so the similarity score increases by 3 * 3 = <b>9</b>.</li>
    *  <li>The second number in the left list is 4. It appears in the right list once, so the similarity score increases by 4 * 1 = <b>4</b>.</li>
    *  <li>The third number in the left list is 2. It does not appear in the right list, so the similarity score does not increase (2 * 0 = 0).</li>
    *  <li>The fourth number, 1, also does not appear in the right list.</li>
    *  <li>The fifth number, 3, appears in the right list three times; the similarity score increases by <b>9</b>.</li>
    *  <li>The last number, 3, appears in the right list three times; the similarity score again increases by <b>9</b>.</li>
    * </ul>
    * <p>So, for these example lists, the similarity score at the end of this process is <b>31</b> (9 + 4 + 0 + 0 + 9 + 9).</p>
    * <p>Once again consider your left and right lists. <b>What is their similarity score?</b></p>
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
