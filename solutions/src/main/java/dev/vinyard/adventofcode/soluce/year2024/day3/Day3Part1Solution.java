package dev.vinyard.adventofcode.soluce.year2024.day3;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AdventOfCodeSolution(year = 2024, day = 3, part = 1, description = "Mull It Over", link = "https://adventofcode.com/2024/day/3", tags = "unsolved")
public class Day3Part1Solution implements Solution<Object> {

    /**
    * <h2>--- Day 3: Mull It Over ---</h2>
    * <p>"Our computers are having issues, so I have no idea if we have any Chief Historians in stock! You're welcome to check the warehouse, though," says the mildly flustered shopkeeper at the <a href="/2020/day/2">North Pole Toboggan Rental Shop</a>. The Historians head out to take a look.</p>
    * <p>The shopkeeper turns to you. "Any chance you can see why our computers are having issues again?"</p>
    * <p>The computer appears to be trying to run a program, but its memory (your puzzle input) is <b>corrupted</b>. All of the instructions have been jumbled up!</p>
    * <p>It seems like the goal of the program is just to <b>multiply some numbers</b>. It does that with instructions like mul(X,Y), where X and Y are each 1-3 digit numbers. For instance, mul(44,46) multiplies 44 by 46 to get a result of 2024. Similarly, mul(123,4) would multiply 123 by 4.</p>
    * <p>However, because the program's memory has been corrupted, there are also many invalid characters that should be <b>ignored</b>, even if they look like part of a mul instruction. Sequences like mul(4*, mul(6,9!, ?(12,34), or mul ( 2 , 4 ) do <b>nothing</b>.</p>
    * <p>For example, consider the following section of corrupted memory:</p>
    * <pre>
    * x<b>mul(2,4)</b>%&amp;mul[3,7]!@^do_not_<b>mul(5,5)</b>+mul(32,64]then(<b>mul(11,8)mul(8,5)</b>)</pre>
    * <p>Only the four highlighted sections are real mul instructions. Adding up the result of each instruction produces <b>161</b> (2*4 + 5*5 + 11*8 + 8*5).</p>
    * <p>Scan the corrupted memory for uncorrupted mul instructions. <b>What do you get if you add up all of the results of the multiplications?</b></p>
    */
    @Override
    public Object solve(String input) {
        Pattern pattern = Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)");
        Matcher matcher = pattern.matcher(input);

        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            sb.append(matcher.group());
        }

        CharStream charStream = CharStreams.fromString(sb.toString());

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        return parser.root().out;
    }
}
