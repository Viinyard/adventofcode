package dev.vinyard.adventofcode.soluce.year2024.day6;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import dev.vinyard.adventofcode.utils.FileReader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.net.URISyntaxException;

@AdventOfCodeSolution(year = 2024, day = 6, part = 1, description = "Guard Gallivant", link = "https://adventofcode.com/2024/day/6", tags = "unsolved")
public class Day6Part1Solution implements Solution<Object> {

    /**
    * <h2>--- Day 6: Guard Gallivant ---</h2>
    * <p>The Historians use their fancy <a href="4">device</a> again, this time to whisk you all away to the North Pole prototype suit manufacturing lab... in the year <a href="/2018/day/5">1518</a>! It turns out that having direct access to history is very convenient for a group of historians.</p>
    * <p>You still have to be careful of time paradoxes, and so it will be important to avoid anyone from 1518 while The Historians search for the Chief. Unfortunately, a single <b>guard</b> is patrolling this part of the lab.</p>
    * <p>Maybe you can work out where the guard will go ahead of time so that The Historians can search safely?</p>
    * <p>You start by making a map (your puzzle input) of the situation. For example:</p>
    * <pre>
    * ....#.....
    * .........#
    * ..........
    * ..#.......
    * .......#..
    * ..........
    * .#..^.....
    * ........#.
    * #.........
    * ......#...
    * </pre>
    * <p>The map shows the current position of the guard with ^ (to indicate the guard is currently facing <b>up</b> from the perspective of the map). Any <b>obstructions</b> - crates, desks, alchemical reactors, etc. - are shown as #.</p>
    * <p>Lab guards in 1518 follow a very strict patrol protocol which involves repeatedly following these steps:</p>
    * <ul>
    *  <li>If there is something directly in front of you, turn right 90 degrees.</li>
    *  <li>Otherwise, take a step forward.</li>
    * </ul>
    * <p>Following the above protocol, the guard moves up several times until she reaches an obstacle (in this case, a pile of failed suit prototypes):</p>
    * <pre>
    * ....#.....
    * ....^....#
    * ..........
    * ..#.......
    * .......#..
    * ..........
    * .#........
    * ........#.
    * #.........
    * ......#...
    * </pre>
    * <p>Because there is now an obstacle in front of the guard, she turns right before continuing straight in her new facing direction:</p>
    * <pre>
    * ....#.....
    * ........&gt;#
    * ..........
    * ..#.......
    * .......#..
    * ..........
    * .#........
    * ........#.
    * #.........
    * ......#...
    * </pre>
    * <p>Reaching another obstacle (a spool of several <b>very</b> long polymers), she turns right again and continues downward:</p>
    * <pre>
    * ....#.....
    * .........#
    * ..........
    * ..#.......
    * .......#..
    * ..........
    * .#......v.
    * ........#.
    * #.........
    * ......#...
    * </pre>
    * <p>This process continues for a while, but the guard eventually leaves the mapped area (after walking past a tank of universal solvent):</p>
    * <pre>
    * ....#.....
    * .........#
    * ..........
    * ..#.......
    * .......#..
    * ..........
    * .#........
    * ........#.
    * #.........
    * ......#v..
    * </pre>
    * <p>By predicting the guard's route, you can determine which specific positions in the lab will be in the patrol path. <b>Including the guard's starting position</b>, the positions visited by the guard before leaving the area are marked with an X:</p>
    * <pre>
    * ....#.....
    * ....XXXXX#
    * ....X...X.
    * ..#.X...X.
    * ..XXXXX#X.
    * ..X.X.X.X.
    * .#XXXXXXX.
    * .XXXXXXX#.
    * #XXXXXXX..
    * ......#X..
    * </pre>
    * <p>In this example, the guard will visit <b>41</b> distinct positions on your map.</p>
    * <p>Predict the path of the guard. <b>How many distinct positions will the guard visit before leaving the mapped area?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);
        // TODO get the ASD from the parser

        return null;
    }
}
