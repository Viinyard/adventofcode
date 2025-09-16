package dev.vinyard.adventofcode.soluce.year2022.day12;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2022, day = 12, part = 1, description = "Hill Climbing Algorithm", link = "https://adventofcode.com/2022/day/12", tags = "unsolved")
public class Day12Part1Solution implements Solution<Long> {

    /**
    * <h2>--- Day 12: Hill Climbing Algorithm ---</h2>
    * <p>You try contacting the Elves using your handheld device, but the river you're following must be too low to get a decent signal.</p>
    * <p>You ask the device for a heightmap of the surrounding area (your puzzle input). The heightmap shows the local area from above broken into a grid; the elevation of each square of the grid is given by a single lowercase letter, where a is the lowest elevation, b is the next-lowest, and so on up to the highest elevation, z.</p>
    * <p>Also included on the heightmap are marks for your current position (S) and the location that should get the best signal (E). Your current position (S) has elevation a, and the location that should get the best signal (E) has elevation z.</p>
    * <p>You'd like to reach E, but to save energy, you should do it in <b>as few steps as possible</b>. During each step, you can move exactly one square up, down, left, or right. To avoid needing to get out your climbing gear, the elevation of the destination square can be <b>at most one higher</b> than the elevation of your current square; that is, if your current elevation is m, you could step to elevation n, but not to elevation o. (This also means that the elevation of the destination square can be much lower than the elevation of your current square.)</p>
    * <p>For example:</p>
    * <pre>
    * <b>S</b>abqponm
    * abcryxxl
    * accsz<b>E</b>xk
    * acctuvwj
    * abdefghi
    * </pre>
    * <p>Here, you start in the top-left corner; your goal is near the middle. You could start by moving down or right, but eventually you'll need to head toward the e at the bottom. From there, you can spiral around to the goal:</p>
    * <pre>
    * v..v&lt;&lt;&lt;&lt;
    * &gt;v.vv&lt;&lt;^
    * .&gt;vv&gt;E^^
    * ..v&gt;&gt;&gt;^^
    * ..&gt;&gt;&gt;&gt;&gt;^
    * </pre>
    * <p>In the above diagram, the symbols indicate whether the path exits each square moving up (^), down (v), left (&lt;), or right (&gt;). The location that should get the best signal is still E, and . marks unvisited squares.</p>
    * <p>This path reaches the goal in <b>31</b> steps, the fewest possible.</p>
    * <p><b>What is the fewest steps required to move from your current position to the location that should get the best signal?</b></p>
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
