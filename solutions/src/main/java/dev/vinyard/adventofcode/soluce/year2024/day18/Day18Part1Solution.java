package dev.vinyard.adventofcode.soluce.year2024.day18;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2024, day = 18, part = 1, description = "RAM Run", link = "https://adventofcode.com/2024/day/18", tags = "unsolved")
public class Day18Part1Solution implements Solution<Object> {

    /**
    * <h2>--- Day 18: RAM Run ---</h2>
    * <p>You and The Historians look a lot more pixelated than you remember. You're <a href="/2017/day/2">inside a computer</a> at the North Pole!</p>
    * <p>Just as you're about to check out your surroundings, a program runs up to you. "This region of memory isn't safe! The User misunderstood what a <a href="https://en.wikipedia.org/wiki/Pushdown_automaton">pushdown automaton</a> is and their algorithm is pushing whole <b>bytes</b> down on top of us! Run!"</p>
    * <p>The algorithm is fast - it's going to cause a byte to fall into your memory space once every <a href="https://www.youtube.com/watch?v=9eyFDBPk4Yw">nanosecond</a>! Fortunately, you're <b>faster</b>, and by quickly scanning the algorithm, you create a <b>list of which bytes will fall</b> (your puzzle input) in the order they'll land in your memory space.</p>
    * <p>Your memory space is a two-dimensional grid with coordinates that range from 0 to 70 both horizontally and vertically. However, for the sake of example, suppose you're on a smaller grid with coordinates that range from 0 to 6 and the following list of incoming byte positions:</p>
    * <pre>
    * 5,4
    * 4,2
    * 4,5
    * 3,0
    * 2,1
    * 6,3
    * 2,4
    * 1,5
    * 0,6
    * 3,3
    * 2,6
    * 5,1
    * 1,2
    * 5,5
    * 2,5
    * 6,5
    * 1,4
    * 0,4
    * 6,4
    * 1,1
    * 6,1
    * 1,0
    * 0,5
    * 1,6
    * 2,0
    * </pre>
    * <p>Each byte position is given as an X,Y coordinate, where X is the distance from the left edge of your memory space and Y is the distance from the top edge of your memory space.</p>
    * <p>You and The Historians are currently in the top left corner of the memory space (at 0,0) and need to reach the exit in the bottom right corner (at 70,70 in your memory space, but at 6,6 in this example). You'll need to simulate the falling bytes to plan out where it will be safe to run; for now, simulate just the first few bytes falling into your memory space.</p>
    * <p>As bytes fall into your memory space, they make that coordinate <b>corrupted</b>. Corrupted memory coordinates cannot be entered by you or The Historians, so you'll need to plan your route carefully. You also cannot leave the boundaries of the memory space; your only hope is to reach the exit.</p>
    * <p>In the above example, if you were to draw the memory space after the first 12 bytes have fallen (using . for safe and # for corrupted), it would look like this:</p>
    * <pre>
    * ...#...
    * ..#..#.
    * ....#..
    * ...#..#
    * ..#..#.
    * .#..#..
    * #.#....
    * </pre>
    * <p>You can take steps up, down, left, or right. After just 12 bytes have corrupted locations in your memory space, the shortest path from the top left corner to the exit would take <b>22</b> steps. Here (marked with O) is one such path:</p>
    * <pre>
    * <b>O</b><b>O</b>.#<b>O</b><b>O</b><b>O</b>
    * .<b>O</b>#<b>O</b><b>O</b>#<b>O</b>
    * .<b>O</b><b>O</b><b>O</b>#<b>O</b><b>O</b>
    * ...#<b>O</b><b>O</b>#
    * ..#<b>O</b><b>O</b>#.
    * .#.<b>O</b>#..
    * #.#<b>O</b><b>O</b><b>O</b><b>O</b>
    * </pre>
    * <p>Simulate the first kilobyte (1024 bytes) falling onto your memory space. Afterward, <b>what is the minimum number of steps needed to reach the exit?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.calculateShortestPath();
    }
}
