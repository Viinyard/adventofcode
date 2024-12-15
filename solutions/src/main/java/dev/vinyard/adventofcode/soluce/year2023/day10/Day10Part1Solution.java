package dev.vinyard.adventofcode.soluce.year2023.day10;

import dev.vinyard.adventofcode.utils.PathUtils;
import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2023, day = 10, part = 1, description = "Pipe Maze", link = "https://adventofcode.com/2023/day/10", tags = "unsolved")
public class Day10Part1Solution implements Solution<Object> {

    /**
    * <h2>--- Day 10: Pipe Maze ---</h2>
    * <p>You use the hang glider to ride the hot air from Desert Island all the way up to the floating metal island. This island is surprisingly cold and there definitely aren't any thermals to glide on, so you leave your hang glider behind.</p>
    * <p>You wander around for a while, but you don't find any people or animals. However, you do occasionally find signposts labeled "<a href="https://en.wikipedia.org/wiki/Hot_spring">Hot Springs</a>" pointing in a seemingly consistent direction; maybe you can find someone at the hot springs and ask them where the desert-machine parts are made.</p>
    * <p>The landscape here is alien; even the flowers and trees are made of metal. As you stop to admire some metal grass, you notice something metallic scurry away in your peripheral vision and jump into a big pipe! It didn't look like any animal you've ever seen; if you want a better look, you'll need to get ahead of it.</p>
    * <p>Scanning the area, you discover that the entire field you're standing on is densely packed with pipes; it was hard to tell at first because they're the same metallic silver color as the "ground". You make a quick sketch of all of the surface pipes you can see (your puzzle input).</p>
    * <p>The pipes are arranged in a two-dimensional grid of <b>tiles</b>:</p>
    * <ul>
    *  <li>| is a <b>vertical pipe</b> connecting north and south.</li>
    *  <li>- is a <b>horizontal pipe</b> connecting east and west.</li>
    *  <li>L is a <b>90-degree bend</b> connecting north and east.</li>
    *  <li>J is a <b>90-degree bend</b> connecting north and west.</li>
    *  <li>7 is a <b>90-degree bend</b> connecting south and west.</li>
    *  <li>F is a <b>90-degree bend</b> connecting south and east.</li>
    *  <li>. is <b>ground</b>; there is no pipe in this tile.</li>
    *  <li>S is the <b>starting position</b> of the animal; there is a pipe on this tile, but your sketch doesn't show what shape the pipe has.</li>
    * </ul>
    * <p>Based on the acoustics of the animal's scurrying, you're confident the pipe that contains the animal is <b>one large, continuous loop</b>.</p>
    * <p>For example, here is a square loop of pipe:</p>
    * <pre>
    * .....
    * .F-7.
    * .|.|.
    * .L-J.
    * .....
    * </pre>
    * <p>If the animal had entered this loop in the northwest corner, the sketch would instead look like this:</p>
    * <pre>
    * .....
    * .<b>S</b>-7.
    * .|.|.
    * .L-J.
    * .....
    * </pre>
    * <p>In the above diagram, the S tile is still a 90-degree F bend: you can tell because of how the adjacent pipes connect to it.</p>
    * <p>Unfortunately, there are also many pipes that <b>aren't connected to the loop</b>! This sketch shows the same loop as above:</p>
    * <pre>
    * -L|F7
    * 7S-7|
    * L|7||
    * -L-J|
    * L|-JF
    * </pre>
    * <p>In the above diagram, you can still figure out which pipes form the main loop: they're the ones connected to S, pipes those pipes connect to, pipes <b>those</b> pipes connect to, and so on. Every pipe in the main loop connects to its two neighbors (including S, which will have exactly two pipes connecting to it, and which is assumed to connect back to those two pipes).</p>
    * <p>Here is a sketch that contains a slightly more complex main loop:</p>
    * <pre>
    * ..F7.
    * .FJ|.
    * SJ.L7
    * |F--J
    * LJ...
    * </pre>
    * <p>Here's the same example sketch with the extra, non-main-loop pipe tiles also shown:</p>
    * <pre>
    * 7-F7-
    * .FJ|7
    * SJLL7
    * |F--J
    * LJ.LJ
    * </pre>
    * <p>If you want to <b>get out ahead of the animal</b>, you should find the tile in the loop that is <b>farthest</b> from the starting position. Because the animal is in the pipe, it doesn't make sense to measure this by direct distance. Instead, you need to find the tile that would take the longest number of steps <b>along the loop</b> to reach from the starting point - regardless of which way around the loop the animal went.</p>
    * <p>In the first example with the square loop:</p>
    * <pre>
    * .....
    * .S-7.
    * .|.|.
    * .L-J.
    * .....
    * </pre>
    * <p>You can count the distance each tile in the loop is from the starting point like this:</p>
    * <pre>
    * .....
    * .012.
    * .1.3.
    * .23<b>4</b>.
    * .....
    * </pre>
    * <p>In this example, the farthest point from the start is <b>4</b> steps away.</p>
    * <p>Here's the more complex loop again:</p>
    * <pre>
    * ..F7.
    * .FJ|.
    * SJ.L7
    * |F--J
    * LJ...
    * </pre>
    * <p>Here are the distances for each tile on that loop:</p>
    * <pre>
    * ..45.
    * .236.
    * 01.7<b>8</b>
    * 14567
    * 23...
    * </pre>
    * <p>Find the single giant loop starting at S. <b>How many steps along the loop does it take to get from the starting position to the point farthest from the starting position?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return Math.ceilDiv((long) PathUtils.getLength(root.findAnimal().getPath(root)), 2);
    }
}
