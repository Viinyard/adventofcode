package dev.vinyard.adventofcode.soluce.year2023.day21;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2023, day = 21, part = 1, description = "Step Counter", link = "https://adventofcode.com/2023/day/21", tags = "unsolved")
public class Day21Part1Solution implements Solution<Long> {

    /**
    * <h2>--- Day 21: Step Counter ---</h2>
    * <p>You manage to catch the <a href="7">airship</a> right as it's dropping someone else off on their all-expenses-paid trip to Desert Island! It even helpfully drops you off near the <a href="5">gardener</a> and his massive farm.</p>
    * <p>"You got the sand flowing again! Great work! Now we just need to wait until we have enough sand to filter the water for Snow Island and we'll have snow again in no time."</p>
    * <p>While you wait, one of the Elves that works with the gardener heard how good you are at solving problems and would like your help. He needs to get his <a href="https://en.wikipedia.org/wiki/Pedometer">steps</a> in for the day, and so he'd like to know <b>which garden plots he can reach with exactly his remaining 64 steps</b>.</p>
    * <p>He gives you an up-to-date map (your puzzle input) of his starting position (S), garden plots (.), and rocks (#). For example:</p>
    * <pre>
    * ...........
    * .....###.#.
    * .###.##..#.
    * ..#.#...#..
    * ....#.#....
    * .##..S####.
    * .##..#...#.
    * .......##..
    * .##.#.####.
    * .##..##.##.
    * ...........
    * </pre>
    * <p>The Elf starts at the starting position (S) which also counts as a garden plot. Then, he can take one step north, south, east, or west, but only onto tiles that are garden plots. This would allow him to reach any of the tiles marked O:</p>
    * <pre>
    * ...........
    * .....###.#.
    * .###.##..#.
    * ..#.#...#..
    * ....#O#....
    * .##.OS####.
    * .##..#...#.
    * .......##..
    * .##.#.####.
    * .##..##.##.
    * ...........
    * </pre>
    * <p>Then, he takes a second step. Since at this point he could be at <b>either</b> tile marked O, his second step would allow him to reach any garden plot that is one step north, south, east, or west of <b>any</b> tile that he could have reached after the first step:</p>
    * <pre>
    * ...........
    * .....###.#.
    * .###.##..#.
    * ..#.#O..#..
    * ....#.#....
    * .##O.O####.
    * .##.O#...#.
    * .......##..
    * .##.#.####.
    * .##..##.##.
    * ...........
    * </pre>
    * <p>After two steps, he could be at any of the tiles marked O above, including the starting position (either by going north-then-south or by going west-then-east).</p>
    * <p>A single third step leads to even more possibilities:</p>
    * <pre>
    * ...........
    * .....###.#.
    * .###.##..#.
    * ..#.#.O.#..
    * ...O#O#....
    * .##.OS####.
    * .##O.#...#.
    * ....O..##..
    * .##.#.####.
    * .##..##.##.
    * ...........
    * </pre>
    * <p>He will continue like this until his steps for the day have been exhausted. After a total of 6 steps, he could reach any of the garden plots marked O:</p>
    * <pre>
    * ...........
    * .....###.#.
    * .###.##.O#.
    * .O#O#O.O#..
    * O.O.#.#.O..
    * .##O.O####.
    * .##.O#O..#.
    * .O.O.O.##..
    * .##.#.####.
    * .##O.##.##.
    * ...........
    * </pre>
    * <p>In this example, if the Elf's goal was to get exactly 6 more steps today, he could use them to reach any of <b>16</b> garden plots.</p>
    * <p>However, the Elf <b>actually needs to get 64 steps today</b>, and the map he's handed you is much larger than the example map.</p>
    * <p>Starting from the garden plot marked S on your map, <b>how many garden plots could the Elf reach in exactly 64 steps?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.countReachablePlotsFromElve();
    }
}
