package dev.vinyard.adventofcode.soluce.year2024.day18;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2024, day = 18, part = 2, description = "RAM Run", link = "https://adventofcode.com/2024/day/18", tags = "unsolved")
public class Day18Part2Solution implements Solution<Object> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>The Historians aren't as used to moving around in this pixelated universe as you are. You're afraid they're not going to be fast enough to make it to the exit before the path is completely blocked.</p>
    * <p>To determine how fast everyone needs to go, you need to determine <b>the first byte that will cut off the path to the exit</b>.</p>
    * <p>In the above example, after the byte at 1,1 falls, there is still a path to the exit:</p>
    * <pre>
    * <b>O</b>..#<b>O</b><b>O</b><b>O</b>
    * <b>O</b>##<b>O</b><b>O</b>#<b>O</b>
    * <b>O</b>#<b>O</b><b>O</b>#<b>O</b><b>O</b>
    * <b>O</b><b>O</b><b>O</b>#<b>O</b><b>O</b>#
    * ###<b>O</b><b>O</b>##
    * .##<b>O</b>###
    * #.#<b>O</b><b>O</b><b>O</b><b>O</b>
    * </pre>
    * <p>However, after adding the very next byte (at 6,1), there is no longer a path to the exit:</p>
    * <pre>
    * ...#...
    * .##..#<b>#</b>
    * .#..#..
    * ...#..#
    * ###..##
    * .##.###
    * #.#....
    * </pre>
    * <p>So, in this example, the coordinates of the first byte that prevents the exit from being reachable are <b>6,1</b>.</p>
    * <p>Simulate more of the bytes that are about to corrupt your memory space. <b>What are the coordinates of the first byte that will prevent the exit from being reachable from your starting position?</b> (Provide the answer as two integers separated by a comma with no other characters.)</p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.getBlockingPoint().map(p -> p.x + "," + p.y).orElseThrow();
    }
}
