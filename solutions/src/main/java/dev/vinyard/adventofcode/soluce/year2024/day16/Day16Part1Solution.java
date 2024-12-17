package dev.vinyard.adventofcode.soluce.year2024.day16;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2024, day = 16, part = 1, description = "Reindeer Maze", link = "https://adventofcode.com/2024/day/16", tags = "unsolved")
public class Day16Part1Solution implements Solution<Object> {

    /**
    * <h2>--- Day 16: Reindeer Maze ---</h2>
    * <p>It's time again for the <a href="/2015/day/14">Reindeer Olympics</a>! This year, the big event is the <b>Reindeer Maze</b>, where the Reindeer compete for the <b>lowest score</b>.</p>
    * <p>You and The Historians arrive to search for the Chief right as the event is about to start. It wouldn't hurt to watch a little, right?</p>
    * <p>The Reindeer start on the Start Tile (marked S) facing <b>East</b> and need to reach the End Tile (marked E). They can move forward one tile at a time (increasing their score by 1 point), but never into a wall (#). They can also rotate clockwise or counterclockwise 90 degrees at a time (increasing their score by 1000 points).</p>
    * <p>To figure out the best place to sit, you start by grabbing a map (your puzzle input) from a nearby kiosk. For example:</p>
    * <pre>
    * ###############
    * #.......#....E#
    * #.#.###.#.###.#
    * #.....#.#...#.#
    * #.###.#####.#.#
    * #.#.#.......#.#
    * #.#.#####.###.#
    * #...........#.#
    * ###.#.#####.#.#
    * #...#.....#.#.#
    * #.#.#.###.#.#.#
    * #.....#...#.#.#
    * #.###.#.#.#.#.#
    * #S..#.....#...#
    * ###############
    * </pre>
    * <p>There are many paths through this maze, but taking any of the best paths would incur a score of only <b>7036</b>. This can be achieved by taking a total of 36 steps forward and turning 90 degrees a total of 7 times:</p>
    * <pre>
    * 
    * ###############
    * #.......#....<b>E</b>#
    * #.#.###.#.###<b>^</b>#
    * #.....#.#...#<b>^</b>#
    * #.###.#####.#<b>^</b>#
    * #.#.#.......#<b>^</b>#
    * #.#.#####.###<b>^</b>#
    * #..<b>&gt;</b><b>&gt;</b><b>&gt;</b><b>&gt;</b><b>&gt;</b><b>&gt;</b><b>&gt;</b><b>&gt;</b><b>v</b>#<b>^</b>#
    * ###<b>^</b>#.#####<b>v</b>#<b>^</b>#
    * #<b>&gt;</b><b>&gt;</b><b>^</b>#.....#<b>v</b>#<b>^</b>#
    * #<b>^</b>#.#.###.#<b>v</b>#<b>^</b>#
    * #<b>^</b>....#...#<b>v</b>#<b>^</b>#
    * #<b>^</b>###.#.#.#<b>v</b>#<b>^</b>#
    * #S..#.....#<b>&gt;</b><b>&gt;</b><b>^</b>#
    * ###############
    * </pre>
    * <p>Here's a second example:</p>
    * <pre>
    * #################
    * #...#...#...#..E#
    * #.#.#.#.#.#.#.#.#
    * #.#.#.#...#...#.#
    * #.#.#.#.###.#.#.#
    * #...#.#.#.....#.#
    * #.#.#.#.#.#####.#
    * #.#...#.#.#.....#
    * #.#.#####.#.###.#
    * #.#.#.......#...#
    * #.#.###.#####.###
    * #.#.#...#.....#.#
    * #.#.#.#####.###.#
    * #.#.#.........#.#
    * #.#.#.#########.#
    * #S#.............#
    * #################
    * </pre>
    * <p>In this maze, the best paths cost <b>11048</b> points; following one such path would look like this:</p>
    * <pre>
    * #################
    * #...#...#...#..<b>E</b>#
    * #.#.#.#.#.#.#.#<b>^</b>#
    * #.#.#.#...#...#<b>^</b>#
    * #.#.#.#.###.#.#<b>^</b>#
    * #<b>&gt;</b><b>&gt;</b><b>v</b>#.#.#.....#<b>^</b>#
    * #<b>^</b>#<b>v</b>#.#.#.#####<b>^</b>#
    * #<b>^</b>#<b>v</b>..#.#.#<b>&gt;</b><b>&gt;</b><b>&gt;</b><b>&gt;</b><b>^</b>#
    * #<b>^</b>#<b>v</b>#####.#<b>^</b>###.#
    * #<b>^</b>#<b>v</b>#..<b>&gt;</b><b>&gt;</b><b>&gt;</b><b>&gt;</b><b>^</b>#...#
    * #<b>^</b>#<b>v</b>###<b>^</b>#####.###
    * #<b>^</b>#<b>v</b>#<b>&gt;</b><b>&gt;</b><b>^</b>#.....#.#
    * #<b>^</b>#<b>v</b>#<b>^</b>#####.###.#
    * #<b>^</b>#<b>v</b>#<b>^</b>........#.#
    * #<b>^</b>#<b>v</b>#<b>^</b>#########.#
    * #S#<b>&gt;</b><b>&gt;</b><b>^</b>..........#
    * #################
    * </pre>
    * <p>Note that the path shown above includes one 90 degree turn as the very first move, rotating the Reindeer from facing East to facing North.</p>
    * <p>Analyze your map carefully. <b>What is the lowest score a Reindeer could possibly get?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.getShortestPath(root.findStart().position, root.findEnd().position);
    }
}
