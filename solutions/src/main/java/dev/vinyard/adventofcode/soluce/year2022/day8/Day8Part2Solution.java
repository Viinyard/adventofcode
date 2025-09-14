package dev.vinyard.adventofcode.soluce.year2022.day8;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2022, day = 8, part = 2, description = "Treetop Tree House", link = "https://adventofcode.com/2022/day/8", tags = "unsolved")
public class Day8Part2Solution implements Solution<Long> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>Content with the amount of tree cover available, the Elves just need to know the best spot to build their tree house: they would like to be able to see a lot of <b>trees</b>.</p>
    * <p>To measure the viewing distance from a given tree, look up, down, left, and right from that tree; stop if you reach an edge or at the first tree that is the same height or taller than the tree under consideration. (If a tree is right on the edge, at least one of its viewing distances will be zero.)</p>
    * <p>The Elves don't care about distant trees taller than those found by the rules above; the proposed tree house has large <a href="https://en.wikipedia.org/wiki/Eaves">eaves</a> to keep it dry, so they wouldn't be able to see higher than the tree house anyway.</p>
    * <p>In the example above, consider the middle 5 in the second row:</p>
    * <pre>
    * 30373
    * 25<b>5</b>12
    * 65332
    * 33549
    * 35390
    * </pre>
    * <ul>
    *  <li>Looking up, its view is not blocked; it can see <b>1</b> tree (of height 3).</li>
    *  <li>Looking left, its view is blocked immediately; it can see only <b>1</b> tree (of height 5, right next to it).</li>
    *  <li>Looking right, its view is not blocked; it can see <b>2</b> trees.</li>
    *  <li>Looking down, its view is blocked eventually; it can see <b>2</b> trees (one of height 3, then the tree of height 5 that blocks its view).</li>
    * </ul>
    * <p>A tree's <b>scenic score</b> is found by <b>multiplying together</b> its viewing distance in each of the four directions. For this tree, this is <b>4</b> (found by multiplying 1 * 1 * 2 * 2).</p>
    * <p>However, you can do even better: consider the tree of height 5 in the middle of the fourth row:</p>
    * <pre>
    * 30373
    * 25512
    * 65332
    * 33<b>5</b>49
    * 35390
    * </pre>
    * <ul>
    *  <li>Looking up, its view is blocked at <b>2</b> trees (by another tree with a height of 5).</li>
    *  <li>Looking left, its view is not blocked; it can see <b>2</b> trees.</li>
    *  <li>Looking down, its view is also not blocked; it can see <b>1</b> tree.</li>
    *  <li>Looking right, its view is blocked at <b>2</b> trees (by a massive tree of height 9).</li>
    * </ul>
    * <p>This tree's scenic score is <b>8</b> (2 * 2 * 1 * 2); this is the ideal spot for the tree house.</p>
    * <p>Consider each tree on your map. <b>What is the highest scenic score possible for any tree?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.part2();
    }
}
