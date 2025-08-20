package dev.vinyard.adventofcode.soluce.year2023.day18;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2023, day = 18, part = 1, description = "Lavaduct Lagoon", link = "https://adventofcode.com/2023/day/18", tags = "unsolved")
public class Day18Part1Solution implements Solution<Long> {

    /**
    * <h2>--- Day 18: Lavaduct Lagoon ---</h2>
    * <p>Thanks to your efforts, the machine parts factory is one of the first factories up and running since the lavafall came back. However, to catch up with the large backlog of parts requests, the factory will also need a <b>large supply of lava</b> for a while; the Elves have already started creating a large lagoon nearby for this purpose.</p>
    * <p>However, they aren't sure the lagoon will be big enough; they've asked you to take a look at the <b>dig plan</b> (your puzzle input). For example:</p>
    * <pre>
    * R 6 (#70c710)
    * D 5 (#0dc571)
    * L 2 (#5713f0)
    * D 2 (#d2c081)
    * R 2 (#59c680)
    * D 2 (#411b91)
    * L 5 (#8ceee2)
    * U 2 (#caa173)
    * L 1 (#1b58a2)
    * U 2 (#caa171)
    * R 2 (#7807d2)
    * U 3 (#a77fa3)
    * L 2 (#015232)
    * U 2 (#7a21e3)
    * </pre>
    * <p>The digger starts in a 1 meter cube hole in the ground. They then dig the specified number of meters <b>up</b> (U), <b>down</b> (D), <b>left</b> (L), or <b>right</b> (R), clearing full 1 meter cubes as they go. The directions are given as seen from above, so if "up" were north, then "right" would be east, and so on. Each trench is also listed with <b>the color that the edge of the trench should be painted</b> as an <a href="https://en.wikipedia.org/wiki/RGB_color_model#Numeric_representations">RGB hexadecimal color code</a>.</p>
    * <p>When viewed from above, the above example dig plan would result in the following loop of <b>trench</b> (#) having been dug out from otherwise <b>ground-level terrain</b> (.):</p>
    * <pre>
    * #######
    * #.....#
    * ###...#
    * ..#...#
    * ..#...#
    * ###.###
    * #...#..
    * ##..###
    * .#....#
    * .######
    * </pre>
    * <p>At this point, the trench could contain 38 cubic meters of lava. However, this is just the edge of the lagoon; the next step is to <b>dig out the interior</b> so that it is one meter deep as well:</p>
    * <pre>
    * #######
    * #######
    * #######
    * ..#####
    * ..#####
    * #######
    * #####..
    * #######
    * .######
    * .######
    * </pre>
    * <p>Now, the lagoon can contain a much more respectable <b>62</b> cubic meters of lava. While the interior is dug out, the edges are also painted according to the color codes in the dig plan.</p>
    * <p>The Elves are concerned the lagoon won't be large enough; if they follow their dig plan, <b>how many cubic meters of lava could it hold?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.calculateArea();
    }
}
