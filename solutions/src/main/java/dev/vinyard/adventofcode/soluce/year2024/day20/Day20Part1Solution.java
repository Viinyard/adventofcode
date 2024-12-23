package dev.vinyard.adventofcode.soluce.year2024.day20;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2024, day = 20, part = 1, description = "Race Condition", link = "https://adventofcode.com/2024/day/20", tags = "unsolved")
public class Day20Part1Solution implements Solution<Object> {

    /**
    * <h2>--- Day 20: Race Condition ---</h2>
    * <p>The Historians are quite pixelated again. This time, a massive, black building looms over you - you're <a href="/2017/day/24">right outside</a> the CPU!</p>
    * <p>While The Historians get to work, a nearby program sees that you're idle and challenges you to a <b>race</b>. Apparently, you've arrived just in time for the frequently-held <b>race condition</b> festival!</p>
    * <p>The race takes place on a particularly long and twisting code path; programs compete to see who can finish in the <b>fewest picoseconds</b>. The winner even gets their very own <a href="https://en.wikipedia.org/wiki/Lock_(computer_science)">mutex</a>!</p>
    * <p>They hand you a <b>map of the racetrack</b> (your puzzle input). For example:</p>
    * <pre>
    * ###############
    * #...#...#.....#
    * #.#.#.#.#.###.#
    * #<b>S</b>#...#.#.#...#
    * #######.#.#.###
    * #######.#.#...#
    * #######.#.###.#
    * ###..<b>E</b>#...#...#
    * ###.#######.###
    * #...###...#...#
    * #.#####.#.###.#
    * #.#...#.#.#...#
    * #.#.#.#.#.#.###
    * #...#...#...###
    * ###############
    * </pre>
    * <p>The map consists of track (.) - including the <b>start</b> (S) and <b>end</b> (E) positions (both of which also count as track) - and <b>walls</b> (#).</p>
    * <p>When a program runs through the racetrack, it starts at the start position. Then, it is allowed to move up, down, left, or right; each such move takes <b>1 picosecond</b>. The goal is to reach the end position as quickly as possible. In this example racetrack, the fastest time is 84 picoseconds.</p>
    * <p>Because there is only a single path from the start to the end and the programs all go the same speed, the races used to be pretty boring. To make things more interesting, they introduced a new rule to the races: programs are allowed to <b>cheat</b>.</p>
    * <p>The rules for cheating are very strict. <b>Exactly once</b> during a race, a program may <b>disable collision</b> for up to <b>2 picoseconds</b>. This allows the program to <b>pass through walls</b> as if they were regular track. At the end of the cheat, the program must be back on normal track again; otherwise, it will receive a <a href="https://en.wikipedia.org/wiki/Segmentation_fault">segmentation fault</a> and get disqualified.</p>
    * <p>So, a program could complete the course in 72 picoseconds (saving <b>12 picoseconds</b>) by cheating for the two moves marked 1 and 2:</p>
    * <pre>
    * ###############
    * #...#...12....#
    * #.#.#.#.#.###.#
    * #S#...#.#.#...#
    * #######.#.#.###
    * #######.#.#...#
    * #######.#.###.#
    * ###..E#...#...#
    * ###.#######.###
    * #...###...#...#
    * #.#####.#.###.#
    * #.#...#.#.#...#
    * #.#.#.#.#.#.###
    * #...#...#...###
    * ###############
    * </pre>
    * <p>Or, a program could complete the course in 64 picoseconds (saving <b>20 picoseconds</b>) by cheating for the two moves marked 1 and 2:</p>
    * <pre>
    * ###############
    * #...#...#.....#
    * #.#.#.#.#.###.#
    * #S#...#.#.#...#
    * #######.#.#.###
    * #######.#.#...#
    * #######.#.###.#
    * ###..E#...12..#
    * ###.#######.###
    * #...###...#...#
    * #.#####.#.###.#
    * #.#...#.#.#...#
    * #.#.#.#.#.#.###
    * #...#...#...###
    * ###############
    * </pre>
    * <p>This cheat saves <b>38 picoseconds</b>:</p>
    * <pre>
    * ###############
    * #...#...#.....#
    * #.#.#.#.#.###.#
    * #S#...#.#.#...#
    * #######.#.#.###
    * #######.#.#...#
    * #######.#.###.#
    * ###..E#...#...#
    * ###.####1##.###
    * #...###.2.#...#
    * #.#####.#.###.#
    * #.#...#.#.#...#
    * #.#.#.#.#.#.###
    * #...#...#...###
    * ###############
    * </pre>
    * <p>This cheat saves <b>64 picoseconds</b> and takes the program directly to the end:</p>
    * <pre>
    * ###############
    * #...#...#.....#
    * #.#.#.#.#.###.#
    * #S#...#.#.#...#
    * #######.#.#.###
    * #######.#.#...#
    * #######.#.###.#
    * ###..21...#...#
    * ###.#######.###
    * #...###...#...#
    * #.#####.#.###.#
    * #.#...#.#.#...#
    * #.#.#.#.#.#.###
    * #...#...#...###
    * ###############
    * </pre>
    * <p>Each cheat has a distinct <b>start position</b> (the position where the cheat is activated, just before the first move that is allowed to go through walls) and <b>end position</b>; cheats are uniquely identified by their start position and end position.</p>
    * <p>In this example, the total number of cheats (grouped by the amount of time they save) are as follows:</p>
    * <ul>
    *  <li>There are 14 cheats that save 2 picoseconds.</li>
    *  <li>There are 14 cheats that save 4 picoseconds.</li>
    *  <li>There are 2 cheats that save 6 picoseconds.</li>
    *  <li>There are 4 cheats that save 8 picoseconds.</li>
    *  <li>There are 2 cheats that save 10 picoseconds.</li>
    *  <li>There are 3 cheats that save 12 picoseconds.</li>
    *  <li>There is one cheat that saves 20 picoseconds.</li>
    *  <li>There is one cheat that saves 36 picoseconds.</li>
    *  <li>There is one cheat that saves 38 picoseconds.</li>
    *  <li>There is one cheat that saves 40 picoseconds.</li>
    *  <li>There is one cheat that saves 64 picoseconds.</li>
    * </ul>
    * <p>You aren't sure what the conditions of the racetrack will be like, so to give yourself as many options as possible, you'll need a list of the best cheats. <b>How many cheats would save you at least 100 picoseconds?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.countCheat(2);
    }
}
