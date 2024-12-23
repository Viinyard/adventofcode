package dev.vinyard.adventofcode.soluce.year2024.day19;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2024, day = 19, part = 2, description = "Linen Layout", link = "https://adventofcode.com/2024/day/19", tags = "unsolved")
public class Day19Part2Solution implements Solution<Object> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>The staff don't really like some of the towel arrangements you came up with. To avoid an endless cycle of towel rearrangement, maybe you should just give them every possible option.</p>
    * <p>Here are all of the different ways the above example's designs can be made:</p>
    * <p>brwrr can be made in two different ways: b, r, wr, r <b>or</b> br, wr, r.</p>
    * <p>bggr can only be made with b, g, g, and r.</p>
    * <p>gbbr can be made 4 different ways:</p>
    * <ul>
    *  <li>g, b, b, r</li>
    *  <li>g, b, br</li>
    *  <li>gb, b, r</li>
    *  <li>gb, br</li>
    * </ul>
    * <p>rrbgbr can be made 6 different ways:</p>
    * <ul>
    *  <li>r, r, b, g, b, r</li>
    *  <li>r, r, b, g, br</li>
    *  <li>r, r, b, gb, r</li>
    *  <li>r, rb, g, b, r</li>
    *  <li>r, rb, g, br</li>
    *  <li>r, rb, gb, r</li>
    * </ul>
    * <p>bwurrg can only be made with bwu, r, r, and g.</p>
    * <p>brgr can be made in two different ways: b, r, g, r <b>or</b> br, g, r.</p>
    * <p>ubwu and bbrgwb are still impossible.</p>
    * <p>Adding up all of the ways the towels in this example could be arranged into the desired designs yields <b>16</b> (2 + 1 + 4 + 6 + 1 + 2).</p>
    * <p>They'll let you into the onsen as soon as you have the list. <b>What do you get if you add up the number of different ways you could make each design?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.countAllValidPatterns();
    }
}
