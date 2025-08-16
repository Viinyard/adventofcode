package dev.vinyard.adventofcode.soluce.year2023.day16;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2023, day = 16, part = 2, description = "The Floor Will Be Lava", link = "https://adventofcode.com/2023/day/16", tags = "unsolved")
public class Day16Part2Solution implements Solution<Long> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>As you try to work out what might be wrong, the reindeer tugs on your shirt and leads you to a nearby control panel. There, a collection of buttons lets you align the contraption so that the beam enters from <b>any edge tile</b> and heading away from that edge. (You can choose either of two directions for the beam if it starts on a corner; for instance, if the beam starts in the bottom-right corner, it can start heading either left or upward.)</p>
    * <p>So, the beam could start on any tile in the top row (heading downward), any tile in the bottom row (heading upward), any tile in the leftmost column (heading right), or any tile in the rightmost column (heading left). To produce lava, you need to find the configuration that <b>energizes as many tiles as possible</b>.</p>
    * <p>In the above example, this can be achieved by starting the beam in the fourth tile from the left in the top row:</p>
    * <pre>
    * .|&lt;2&lt;\....
    * |v-v\^....
    * .v.v.|-&gt;&gt;&gt;
    * .v.v.v^.|.
    * .v.v.v^...
    * .v.v.v^..\
    * .v.v/2\\..
    * &lt;-2-/vv|..
    * .|&lt;&lt;&lt;2-|.\
    * .v//.|.v..
    * </pre>
    * <p>Using this configuration, <b>51</b> tiles are energized:</p>
    * <pre>
    * .#####....
    * .#.#.#....
    * .#.#.#####
    * .#.#.##...
    * .#.#.##...
    * .#.#.##...
    * .#.#####..
    * ########..
    * .#######..
    * .#...#.#..
    * </pre>
    * <p>Find the initial beam configuration that energizes the largest number of tiles; <b>how many tiles are energized in that configuration?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        System.out.println(root);

        return root.getMaxEnergizedCount();
    }
}
