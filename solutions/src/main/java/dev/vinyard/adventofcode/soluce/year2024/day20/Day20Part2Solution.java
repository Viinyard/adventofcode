package dev.vinyard.adventofcode.soluce.year2024.day20;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2024, day = 20, part = 2, description = "Race Condition", link = "https://adventofcode.com/2024/day/20", tags = "unsolved")
public class Day20Part2Solution implements Solution<Object> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>The programs seem perplexed by your list of cheats. Apparently, the two-picosecond cheating rule was deprecated several milliseconds ago! The latest version of the cheating rule permits a single cheat that instead lasts at most <b>20 picoseconds</b>.</p>
    * <p>Now, in addition to all the cheats that were possible in just two picoseconds, many more cheats are possible. This six-picosecond cheat saves <b>76 picoseconds</b>:</p>
    * <pre>
    * ###############
    * #...#...#.....#
    * #.#.#.#.#.###.#
    * #S#...#.#.#...#
    * #1#####.#.#.###
    * #2#####.#.#...#
    * #3#####.#.###.#
    * #456.E#...#...#
    * ###.#######.###
    * #...###...#...#
    * #.#####.#.###.#
    * #.#...#.#.#...#
    * #.#.#.#.#.#.###
    * #...#...#...###
    * ###############
    * </pre>
    * <p>Because this cheat has the same start and end positions as the one above, it's the <b>same cheat</b>, even though the path taken during the cheat is different:</p>
    * <pre>
    * ###############
    * #...#...#.....#
    * #.#.#.#.#.###.#
    * #S12..#.#.#...#
    * ###3###.#.#.###
    * ###4###.#.#...#
    * ###5###.#.###.#
    * ###6.E#...#...#
    * ###.#######.###
    * #...###...#...#
    * #.#####.#.###.#
    * #.#...#.#.#...#
    * #.#.#.#.#.#.###
    * #...#...#...###
    * ###############
    * </pre>
    * <p>Cheats don't need to use all 20 picoseconds; cheats can last any amount of time up to and including 20 picoseconds (but can still only end when the program is on normal track). Any cheat time not used is lost; it can't be saved for another cheat later.</p>
    * <p>You'll still need a list of the best cheats, but now there are even more to choose between. Here are the quantities of cheats in this example that save <b>50 picoseconds or more</b>:</p>
    * <ul>
    *  <li>There are 32 cheats that save 50 picoseconds.</li>
    *  <li>There are 31 cheats that save 52 picoseconds.</li>
    *  <li>There are 29 cheats that save 54 picoseconds.</li>
    *  <li>There are 39 cheats that save 56 picoseconds.</li>
    *  <li>There are 25 cheats that save 58 picoseconds.</li>
    *  <li>There are 23 cheats that save 60 picoseconds.</li>
    *  <li>There are 20 cheats that save 62 picoseconds.</li>
    *  <li>There are 19 cheats that save 64 picoseconds.</li>
    *  <li>There are 12 cheats that save 66 picoseconds.</li>
    *  <li>There are 14 cheats that save 68 picoseconds.</li>
    *  <li>There are 12 cheats that save 70 picoseconds.</li>
    *  <li>There are 22 cheats that save 72 picoseconds.</li>
    *  <li>There are 4 cheats that save 74 picoseconds.</li>
    *  <li>There are 3 cheats that save 76 picoseconds.</li>
    * </ul>
    * <p>Find the best cheats using the updated cheating rules. <b>How many cheats would save you at least 100 picoseconds?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.countCheat(20);
    }
}
