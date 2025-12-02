package dev.vinyard.adventofcode.soluce.year2025.day2;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import dev.vinyard.adventofcode.utils.FileReader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.net.URISyntaxException;

@AdventOfCodeSolution(year = 2025, day = 2, part = 2, description = "Gift Shop", link = "https://adventofcode.com/2025/day/2", tags = "unsolved")
public class Day2Part2Solution implements Solution<Object> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>The clerk quickly discovers that there are still invalid IDs in the ranges in your list. Maybe the young Elf was doing other silly patterns as well?</p>
    * <p>Now, an ID is invalid if it is made only of some sequence of digits repeated <b>at least</b> twice. So, 12341234 (1234 two times), 123123123 (123 three times), 1212121212 (12 five times), and 1111111 (1 seven times) are all invalid IDs.</p>
    * <p>From the same example as before:</p>
    * <ul>
    *  <li>11-22 still has two invalid IDs, <b>11</b> and <b>22</b>.</li>
    *  <li>95-115 now has two invalid IDs, <b>99</b> and <b>111</b>.</li>
    *  <li>998-1012 now has two invalid IDs, <b>999</b> and <b>1010</b>.</li>
    *  <li>1188511880-1188511890 still has one invalid ID, <b>1188511885</b>.</li>
    *  <li>222220-222224 still has one invalid ID, <b>222222</b>.</li>
    *  <li>1698522-1698528 still contains no invalid IDs.</li>
    *  <li>446443-446449 still has one invalid ID, <b>446446</b>.</li>
    *  <li>38593856-38593862 still has one invalid ID, <b>38593859</b>.</li>
    *  <li>565653-565659 now has one invalid ID, <b>565656</b>.</li>
    *  <li>824824821-824824827 now has one invalid ID, <b>824824824</b>.</li>
    *  <li>2121212118-2121212124 now has one invalid ID, <b>2121212121</b>.</li>
    * </ul>
    * <p>Adding up all the invalid IDs in this example produces <b>4174379265</b>.</p>
    * <p><b>What do you get if you add up all of the invalid IDs using these new rules?</b></p>
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
