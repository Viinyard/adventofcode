package dev.vinyard.adventofcode.soluce.year2022.day8;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2022, day = 8, part = 1, description = "Treetop Tree House", link = "https://adventofcode.com/2022/day/8", tags = "unsolved")
public class Day8Part1Solution implements Solution<Long> {

    /**
    * <h2>--- Day 8: Treetop Tree House ---</h2>
    * <p>The expedition comes across a peculiar patch of tall trees all planted carefully in a grid. The Elves explain that a previous expedition planted these trees as a reforestation effort. Now, they're curious if this would be a good location for a <a href="https://en.wikipedia.org/wiki/Tree_house">tree house</a>.</p>
    * <p>First, determine whether there is enough tree cover here to keep a tree house <b>hidden</b>. To do this, you need to count the number of trees that are <b>visible from outside the grid</b> when looking directly along a row or column.</p>
    * <p>The Elves have already launched a <a href="https://en.wikipedia.org/wiki/Quadcopter">quadcopter</a> to generate a map with the height of each tree (your puzzle input). For example:</p>
    * <pre>
    * 30373
    * 25512
    * 65332
    * 33549
    * 35390
    * </pre>
    * <p>Each tree is represented as a single digit whose value is its height, where 0 is the shortest and 9 is the tallest.</p>
    * <p>A tree is <b>visible</b> if all of the other trees between it and an edge of the grid are <b>shorter</b> than it. Only consider trees in the same row or column; that is, only look up, down, left, or right from any given tree.</p>
    * <p>All of the trees around the edge of the grid are <b>visible</b> - since they are already on the edge, there are no trees to block the view. In this example, that only leaves the <b>interior nine trees</b> to consider:</p>
    * <ul>
    *  <li>The top-left 5 is <b>visible</b> from the left and top. (It isn't visible from the right or bottom since other trees of height 5 are in the way.)</li>
    *  <li>The top-middle 5 is <b>visible</b> from the top and right.</li>
    *  <li>The top-right 1 is not visible from any direction; for it to be visible, there would need to only be trees of height <b>0</b> between it and an edge.</li>
    *  <li>The left-middle 5 is <b>visible</b>, but only from the right.</li>
    *  <li>The center 3 is not visible from any direction; for it to be visible, there would need to be only trees of at most height 2 between it and an edge.</li>
    *  <li>The right-middle 3 is <b>visible</b> from the right.</li>
    *  <li>In the bottom row, the middle 5 is <b>visible</b>, but the 3 and 4 are not.</li>
    * </ul>
    * <p>With 16 trees visible on the edge and another 5 visible in the interior, a total of <b>21</b> trees are visible in this arrangement.</p>
    * <p>Consider your map; <b>how many trees are visible from outside the grid?</b></p>
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
