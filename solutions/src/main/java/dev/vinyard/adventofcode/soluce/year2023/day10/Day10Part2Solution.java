package dev.vinyard.adventofcode.soluce.year2023.day10;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2023, day = 10, part = 2, description = "Pipe Maze", link = "https://adventofcode.com/2023/day/10", tags = "unsolved")
public class Day10Part2Solution implements Solution<Object> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>You quickly reach the farthest point of the loop, but the animal never emerges. Maybe its nest is <b>within the area enclosed by the loop</b>?</p>
    * <p>To determine whether it's even worth taking the time to search for such a nest, you should calculate how many tiles are contained within the loop. For example:</p>
    * <pre>
    * ...........
    * .S-------7.
    * .|F-----7|.
    * .||.....||.
    * .||.....||.
    * .|L-7.F-J|.
    * .|..|.|..|.
    * .L--J.L--J.
    * ...........
    * </pre>
    * <p>The above loop encloses merely <b>four tiles</b> - the two pairs of . in the southwest and southeast (marked I below). The middle . tiles (marked O below) are <b>not</b> in the loop. Here is the same loop again with those regions marked:</p>
    * <pre>
    * ...........
    * .S-------7.
    * .|F-----7|.
    * .||<b>OOOOO</b>||.
    * .||<b>OOOOO</b>||.
    * .|L-7<b>O</b>F-J|.
    * .|<b>II</b>|<b>O</b>|<b>II</b>|.
    * .L--J<b>O</b>L--J.
    * .....<b>O</b>.....
    * </pre>
    * <p>In fact, there doesn't even need to be a full tile path to the outside for tiles to count as outside the loop - squeezing between pipes is also allowed! Here, I is still within the loop and O is still outside the loop:</p>
    * <pre>
    * ..........
    * .S------7.
    * .|F----7|.
    * .||<b>OOOO</b>||.
    * .||<b>OOOO</b>||.
    * .|L-7F-J|.
    * .|<b>II</b>||<b>II</b>|.
    * .L--JL--J.
    * ..........
    * </pre>
    * <p>In both of the above examples, <b>4</b> tiles are enclosed by the loop.</p>
    * <p>Here's a larger example:</p>
    * <pre>
    * .F----7F7F7F7F-7....
    * .|F--7||||||||FJ....
    * .||.FJ||||||||L7....
    * FJL7L7LJLJ||LJ.L-7..
    * L--J.L7...LJS7F-7L7.
    * ....F-J..F7FJ|L7L7L7
    * ....L7.F7||L7|.L7L7|
    * .....|FJLJ|FJ|F7|.LJ
    * ....FJL-7.||.||||...
    * ....L---J.LJ.LJLJ...
    * </pre>
    * <p>The above sketch has many random bits of ground, some of which are in the loop (I) and some of which are outside it (O):</p>
    * <pre>
    * <b>O</b>F----7F7F7F7F-7<b>OOOO</b>
    * <b>O</b>|F--7||||||||FJ<b>OOOO</b>
    * <b>O</b>||<b>O</b>FJ||||||||L7<b>OOOO</b>
    * FJL7L7LJLJ||LJ<b>I</b>L-7<b>OO</b>
    * L--J<b>O</b>L7<b>III</b>LJS7F-7L7<b>O</b>
    * <b>OOOO</b>F-J<b>II</b>F7FJ|L7L7L7
    * <b>OOOO</b>L7<b>I</b>F7||L7|<b>I</b>L7L7|
    * <b>OOOOO</b>|FJLJ|FJ|F7|<b>O</b>LJ
    * <b>OOOO</b>FJL-7<b>O</b>||<b>O</b>||||<b>OOO</b>
    * <b>OOOO</b>L---J<b>O</b>LJ<b>O</b>LJLJ<b>OOO</b>
    * </pre>
    * <p>In this larger example, <b>8</b> tiles are enclosed by the loop.</p>
    * <p>Any tile that isn't part of the main loop can count as being enclosed by the loop. Here's another example with many bits of junk pipe lying around that aren't connected to the main loop at all:</p>
    * <pre>
    * FF7FSF7F7F7F7F7F---7
    * L|LJ||||||||||||F--J
    * FL-7LJLJ||||||LJL-77
    * F--JF--7||LJLJ7F7FJ-
    * L---JF-JLJ.||-FJLJJ7
    * |F|F-JF---7F7-L7L|7|
    * |FFJF7L7F-JF7|JL---7
    * 7-L-JL7||F7|L7F-7F7|
    * L.L7LFJ|||||FJL7||LJ
    * L7JLJL-JLJLJL--JLJ.L
    * </pre>
    * <p>Here are just the tiles that are <b>enclosed by the loop</b> marked with I:</p>
    * <pre>
    * FF7FSF7F7F7F7F7F---7
    * L|LJ||||||||||||F--J
    * FL-7LJLJ||||||LJL-77
    * F--JF--7||LJLJ<b>I</b>F7FJ-
    * L---JF-JLJ<b>IIII</b>FJLJJ7
    * |F|F-JF---7<b>III</b>L7L|7|
    * |FFJF7L7F-JF7<b>II</b>L---7
    * 7-L-JL7||F7|L7F-7F7|
    * L.L7LFJ|||||FJL7||LJ
    * L7JLJL-JLJLJL--JLJ.L
    * </pre>
    * <p>In this last example, <b>10</b> tiles are enclosed by the loop.</p>
    * <p>Figure out whether you have time to search for the nest by calculating the area within the loop. <b>How many tiles are enclosed by the loop?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.countUnvisitedInPath(root.findAnimal().getPath(root));
    }
}
