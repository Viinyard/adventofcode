package dev.vinyard.adventofcode.soluce.year2023.day9;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;


@AdventOfCodeSolution(year = 2023, day = 9, part = 1, description = "Mirage Maintenance", link = "https://adventofcode.com/2023/day/9", tags = "unsolved")
public class Day9Part1Solution implements Solution<Long> {

    /**
     * <h2>--- Day 9: Mirage Maintenance ---</h2>
     * <p>You ride the camel through the sandstorm and stop where the ghost's maps told you to stop. The sandstorm subsequently subsides, somehow seeing you standing at an <b>oasis</b>!</p>
     * <p>The camel goes to get some water and you stretch your neck. As you look up, you discover what must be yet another giant floating island, this one made of metal! That must be where the <b>parts to fix the sand machines</b> come from.</p>
     * <p>There's even a <a href="https://en.wikipedia.org/wiki/Hang_gliding">hang glider</a> partially buried in the sand here; once the sun rises and heats up the sand, you might be able to use the glider and the hot air to get all the way up to the metal island!</p>
     * <p>While you wait for the sun to rise, you admire the oasis hidden here in the middle of Desert Island. It must have a delicate ecosystem; you might as well take some ecological readings while you wait. Maybe you can report any environmental instabilities you find to someone so the oasis can be around for the next sandstorm-worn traveler.</p>
     * <p>You pull out your handy <b>Oasis And Sand Instability Sensor</b> and analyze your surroundings. The OASIS produces a report of many values and how they are changing over time (your puzzle input). Each line in the report contains the <b>history</b> of a single value. For example:</p>
     * <pre>
     * 0 3 6 9 12 15
     * 1 3 6 10 15 21
     * 10 13 16 21 30 45
     * </pre>
     * <p>To best protect the oasis, your environmental report should include a <b>prediction of the next value</b> in each history. To do this, start by making a new sequence from the <b>difference at each step</b> of your history. If that sequence is <b>not</b> all zeroes, repeat this process, using the sequence you just generated as the input sequence. Once all of the values in your latest sequence are zeroes, you can extrapolate what the next value of the original history should be.</p>
     * <p>In the above dataset, the first history is 0 3 6 9 12 15. Because the values increase by 3 each step, the first sequence of differences that you generate will be 3 3 3 3 3. Note that this sequence has one fewer value than the input sequence because at each step it considers two numbers from the input. Since these values aren't <b>all zero</b>, repeat the process: the values differ by 0 at each step, so the next sequence is 0 0 0 0. This means you have enough information to extrapolate the history! Visually, these sequences can be arranged like this:</p>
     * <pre>
     * 0   3   6   9  12  15
     *   3   3   3   3   3
     *     0   0   0   0
     * </pre>
     * <p>To extrapolate, start by adding a new zero to the end of your list of zeroes; because the zeroes represent differences between the two values above them, this also means there is now a placeholder in every sequence above it:</p>
     * <p></p>
     * <pre>
     * 0   3   6   9  12  15   <b>B</b>
     *   3   3   3   3   3   <b>A</b>
     *     0   0   0   0   <b>0</b>
     * </pre>
     * <p>You can then start filling in placeholders from the bottom up. A needs to be the result of increasing 3 (the value to its left) by 0 (the value below it); this means A must be <b>3</b>:</p>
     * <pre>
     * 0   3   6   9  12  15   B
     *   3   3   3   3   <b>3</b>   <b>3</b>
     *     0   0   0   0   <b>0</b>
     * </pre>
     * <p>Finally, you can fill in B, which needs to be the result of increasing 15 (the value to its left) by 3 (the value below it), or <b>18</b>:</p>
     * <pre>
     * 0   3   6   9  12  <b>15</b>  <b>18</b>
     *   3   3   3   3   3   <b>3</b>
     *     0   0   0   0   0
     * </pre>
     * <p>So, the next value of the first history is <b>18</b>.</p>
     * <p>Finding all-zero differences for the second history requires an additional sequence:</p>
     * <pre>
     * 1   3   6  10  15  21
     *   2   3   4   5   6
     *     1   1   1   1
     *       0   0   0
     * </pre>
     * <p>Then, following the same process as before, work out the next value in each sequence from the bottom up:</p>
     * <pre>
     * 1   3   6  10  15  21  <b>28</b>
     *   2   3   4   5   6   <b>7</b>
     *     1   1   1   1   <b>1</b>
     *       0   0   0   <b>0</b>
     * </pre>
     * <p>So, the next value of the second history is <b>28</b>.</p>
     * <p>The third history requires even more sequences, but its next value can be found the same way:</p>
     * <pre>
     * 10  13  16  21  30  45  <b>68</b>
     *    3   3   5   9  15  <b>23</b>
     *      0   2   4   6   <b>8</b>
     *        2   2   2   <b>2</b>
     *          0   0   <b>0</b>
     * </pre>
     * <p>So, the next value of the third history is <b>68</b>.</p>
     * <p>If you find the next value for each history in this example and add them together, you get <b>114</b>.</p>
     * <p>Analyze your OASIS report and extrapolate the next value for each history. <b>What is the sum of these extrapolated values?</b></p>
     */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);
        ASD.Report report = parser.report().out;

        return report.getSuites().stream().mapToLong(ASD.Suite::getNextValue).sum();
    }
}
