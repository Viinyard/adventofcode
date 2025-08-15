package dev.vinyard.adventofcode.soluce.year2023.day14;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2023, day = 14, part = 2, description = "Parabolic Reflector Dish", link = "https://adventofcode.com/2023/day/14", tags = "unsolved")
public class Day14Part2Solution implements Solution<Long> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>The parabolic reflector dish deforms, but not in a way that focuses the beam. To do that, you'll need to move the rocks to the edges of the platform. Fortunately, a button on the side of the control panel labeled "<b>spin cycle</b>" attempts to do just that!</p>
    * <p>Each <b>cycle</b> tilts the platform four times so that the rounded rocks roll <b>north</b>, then <b>west</b>, then <b>south</b>, then <b>east</b>. After each tilt, the rounded rocks roll as far as they can before the platform tilts in the next direction. After one cycle, the platform will have finished rolling the rounded rocks in those four directions in that order.</p>
    * <p>Here's what happens in the example above after each of the first few cycles:</p>
    * <pre>
    * After 1 cycle:
    * .....#....
    * ....#...O#
    * ...OO##...
    * .OO#......
    * .....OOO#.
    * .O#...O#.#
    * ....O#....
    * ......OOOO
    * #...O###..
    * #..OO#....
    * 
    * After 2 cycles:
    * .....#....
    * ....#...O#
    * .....##...
    * ..O#......
    * .....OOO#.
    * .O#...O#.#
    * ....O#...O
    * .......OOO
    * #..OO###..
    * #.OOO#...O
    * 
    * After 3 cycles:
    * .....#....
    * ....#...O#
    * .....##...
    * ..O#......
    * .....OOO#.
    * .O#...O#.#
    * ....O#...O
    * .......OOO
    * #...O###.O
    * #.OOO#...O
    * </pre>
    * <p>This process should work if you leave it running long enough, but you're still worried about the north support beams. To make sure they'll survive for a while, you need to calculate the <b>total load</b> on the north support beams after 1000000000 cycles.</p>
    * <p>In the above example, after 1000000000 cycles, the total load on the north support beams is <b>64</b>.</p>
    * <p>Run the spin cycle for 1000000000 cycles. Afterward, <b>what is the total load on the north support beams?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.getPlatform().tiltCycle(1_000_000_000L);
    }
}
