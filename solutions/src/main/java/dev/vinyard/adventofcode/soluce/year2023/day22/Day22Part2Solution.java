package dev.vinyard.adventofcode.soluce.year2023.day22;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2023, day = 22, part = 2, description = "Sand Slabs", link = "https://adventofcode.com/2023/day/22", tags = "unsolved")
public class Day22Part2Solution implements Solution<Long> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>Disintegrating bricks one at a time isn't going to be fast enough. While it might sound dangerous, what you really need is a <b>chain reaction</b>.</p>
    * <p>You'll need to figure out the best brick to disintegrate. For each brick, determine how many <b>other bricks would fall</b> if that brick were disintegrated.</p>
    * <p>Using the same example as above:</p>
    * <ul>
    *  <li>Disintegrating brick A would cause all <b>6</b> other bricks to fall.</li>
    *  <li>Disintegrating brick F would cause only <b>1</b> other brick, G, to fall.</li>
    * </ul>
    * <p>Disintegrating any other brick would cause <b>no other bricks</b> to fall. So, in this example, the sum of <b>the number of other bricks that would fall</b> as a result of disintegrating each brick is <b>7</b>.</p>
    * <p>For each brick, determine how many <b>other bricks</b> would fall if that brick were disintegrated. <b>What is the sum of the number of other bricks that would fall?</b></p>
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
