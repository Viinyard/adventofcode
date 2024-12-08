package dev.vinyard.adventofcode.soluce.year2024.day8;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.awt.*;
import java.awt.geom.AffineTransform;

@AdventOfCodeSolution(year = 2024, day = 8, part = 2, description = "Resonant Collinearity", link = "https://adventofcode.com/2024/day/8", tags = "unsolved")
public class Day8Part2Solution implements Solution<Object> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>Watching over your shoulder as you work, one of The Historians asks if you took the effects of resonant harmonics into your calculations.</p>
    * <p>Whoops!</p>
    * <p>After updating your model, it turns out that an antinode occurs at <b>any grid position</b> exactly in line with at least two antennas of the same frequency, regardless of distance. This means that some of the new antinodes will occur at the position of each antenna (unless that antenna is the only one of its frequency).</p>
    * <p>So, these three T-frequency antennas now create many antinodes:</p>
    * <pre>
    * T....#....
    * ...T......
    * .T....#...
    * .........#
    * ..#.......
    * ..........
    * ...#......
    * ..........
    * ....#.....
    * ..........
    * </pre>
    * <p>In fact, the three T-frequency antennas are all exactly in line with two antennas, so they are all also antinodes! This brings the total number of antinodes in the above example to <b>9</b>.</p>
    * <p>The original example now has <b>34</b> antinodes, including the antinodes that appear on every antenna:</p>
    * <pre>
    * ##....#....#
    * .#.#....0...
    * ..#.#0....#.
    * ..##...0....
    * ....0....#..
    * .#...#A....#
    * ...#..#.....
    * #....#.#....
    * ..#.....A...
    * ....#....A..
    * .#........#.
    * ...#......##
    * </pre>
    * <p>Calculate the impact of the signal using this updated model. <b>How many unique locations within the bounds of the map contain an antinode?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        root.findAllAntennas().forEach(a -> root.getAllAntennasByFrequencies().get(a.getFrequency())
                .stream().map(ASD.Antenna::getPosition)
                .forEach(b -> {
                    Point antinode = new Point(b);
                    AffineTransform transform = a.getTransform(antinode);
                    do {
                        transform.transform(antinode, antinode);
                        root.getEntityAt(antinode).ifPresent(ASD.Entity::setAntinode);
                    } while (root.getEntityAt(antinode).isPresent() && !transform.isIdentity());
                }));

        return root.countAntinodes();
    }
}
