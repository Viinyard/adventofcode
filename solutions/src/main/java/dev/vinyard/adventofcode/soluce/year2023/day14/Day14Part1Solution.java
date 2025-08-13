package dev.vinyard.adventofcode.soluce.year2023.day14;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2023, day = 14, part = 1, description = "Parabolic Reflector Dish", link = "https://adventofcode.com/2023/day/14", tags = "unsolved")
public class Day14Part1Solution implements Solution<Long> {

    /**
    * <h2>--- Day 14: Parabolic Reflector Dish ---</h2>
    * <p>You reach the place where all of the mirrors were pointing: a massive <a href="https://en.wikipedia.org/wiki/Parabolic_reflector">parabolic reflector dish</a> attached to the side of another large mountain.</p>
    * <p>The dish is made up of many small mirrors, but while the mirrors themselves are roughly in the shape of a parabolic reflector dish, each individual mirror seems to be pointing in slightly the wrong direction. If the dish is meant to focus light, all it's doing right now is sending it in a vague direction.</p>
    * <p>This system must be what provides the energy for the lava! If you focus the reflector dish, maybe you can go where it's pointing and use the light to fix the lava production.</p>
    * <p>Upon closer inspection, the individual mirrors each appear to be connected via an elaborate system of ropes and pulleys to a large metal platform below the dish. The platform is covered in large rocks of various shapes. Depending on their position, the weight of the rocks deforms the platform, and the shape of the platform controls which ropes move and ultimately the focus of the dish.</p>
    * <p>In short: if you move the rocks, you can focus the dish. The platform even has a control panel on the side that lets you <b>tilt</b> it in one of four directions! The rounded rocks (O) will roll when the platform is tilted, while the cube-shaped rocks (#) will stay in place. You note the positions of all of the empty spaces (.) and rocks (your puzzle input). For example:</p>
    * <pre>
    * O....#....
    * O.OO#....#
    * .....##...
    * OO.#O....O
    * .O.....O#.
    * O.#..O.#.#
    * ..O..#O..O
    * .......O..
    * #....###..
    * #OO..#....
    * </pre>
    * <p>Start by tilting the lever so all of the rocks will slide <b>north</b> as far as they will go:</p>
    * <pre>
    * OOOO.#.O..
    * OO..#....#
    * OO..O##..O
    * O..#.OO...
    * ........#.
    * ..#....#.#
    * ..O..#.O.O
    * ..O.......
    * #....###..
    * #....#....
    * </pre>
    * <p>You notice that the support beams along the north side of the platform are <b>damaged</b>; to ensure the platform doesn't collapse, you should calculate the <b>total load</b> on the north support beams.</p>
    * <p>The amount of load caused by a single rounded rock (O) is equal to the number of rows from the rock to the south edge of the platform, including the row the rock is on. (Cube-shaped rocks (#) don't contribute to load.) So, the amount of load caused by each rock in each row is as follows:</p>
    * <pre>
    * OOOO.#.O.. 10
    * OO..#....#  9
    * OO..O##..O  8
    * O..#.OO...  7
    * ........#.  6
    * ..#....#.#  5
    * ..O..#.O.O  4
    * ..O.......  3
    * #....###..  2
    * #....#....  1
    * </pre>
    * <p>The total load is the sum of the load caused by all of the <b>rounded rocks</b>. In this example, the total load is <b>136</b>.</p>
    * <p>Tilt the platform so that the rounded rocks all roll north. Afterward, <b>what is the total load on the north support beams?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.getPlatform().tilt();
    }
}
