package dev.vinyard.adventofcode.soluce.year2024.day4;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2024, day = 4, part = 1, description = "Ceres Search", link = "https://adventofcode.com/2024/day/4", tags = "unsolved")
public class Day4Part1Solution implements Solution<Object> {

    /**
    * <h2>--- Day 4: Ceres Search ---</h2>
    * <p>"Looks like the Chief's not here. Next!" One of The Historians pulls out a device and pushes the only button on it. After a brief flash, you recognize the interior of the <a href="/2019/day/10">Ceres monitoring station</a>!</p>
    * <p>As the search for the Chief continues, a small Elf who lives on the station tugs on your shirt; she'd like to know if you could help her with her <b>word search</b> (your puzzle input). She only has to find one word: XMAS.</p>
    * <p>This word search allows words to be horizontal, vertical, diagonal, written backwards, or even overlapping other words. It's a little unusual, though, as you don't merely need to find one instance of XMAS - you need to find <b>all of them</b>. Here are a few ways XMAS might appear, where irrelevant characters have been replaced with .:</p>
    * <p></p>
    * <pre>
    * ..X...
    * .SAMX.
    * .A..A.
    * XMAS.S
    * .X....
    * </pre>
    * <p>The actual word search will be full of letters instead. For example:</p>
    * <pre>
    * MMMSXXMASM
    * MSAMXMSMSA
    * AMXSXMAAMM
    * MSAMASMSMX
    * XMASAMXAMM
    * XXAMMXXAMA
    * SMSMSASXSS
    * SAXAMASAAA
    * MAMMMXMMMM
    * MXMXAXMASX
    * </pre>
    * <p>In this word search, XMAS occurs a total of <b>18</b> times; here's the same word search again, but where letters not involved in any XMAS have been replaced with .:</p>
    * <pre>
    * ....XXMAS.
    * .SAMXMS...
    * ...S..A...
    * ..A.A.MS.X
    * XMASAMX.MM
    * X.....XA.A
    * S.S.S.S.SS
    * .A.A.A.A.A
    * ..M.M.M.MM
    * .X.X.XMASX
    * </pre>
    * <p>Take a look at the little Elf's word search. <b>How many times does XMAS appear?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.countWord("XMAS");
    }
}
