package dev.vinyard.adventofcode.soluce.year2024.day16;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2024, day = 16, part = 2, description = "Reindeer Maze", link = "https://adventofcode.com/2024/day/16", tags = "unsolved")
public class Day16Part2Solution implements Solution<Object> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>Now that you know what the best paths look like, you can figure out the best spot to sit.</p>
    * <p>Every non-wall tile (S, ., or E) is equipped with places to sit along the edges of the tile. While determining which of these tiles would be the best spot to sit depends on a whole bunch of factors (how comfortable the seats are, how far away the bathrooms are, whether there's a pillar blocking your view, etc.), the most important factor is <b>whether the tile is on one of the best paths through the maze</b>. If you sit somewhere else, you'd miss all the action!</p>
    * <p>So, you'll need to determine which tiles are part of <b>any</b> best path through the maze, including the S and E tiles.</p>
    * <p>In the first example, there are <b>45</b> tiles (marked O) that are part of at least one of the various best paths through the maze:</p>
    * <pre>
    * ###############
    * #.......#....<b>O</b>#
    * #.#.###.#.###<b>O</b>#
    * #.....#.#...#<b>O</b>#
    * #.###.#####.#<b>O</b>#
    * #.#.#.......#<b>O</b>#
    * #.#.#####.###<b>O</b>#
    * #..<b>O</b><b>O</b><b>O</b><b>O</b><b>O</b><b>O</b><b>O</b><b>O</b><b>O</b>#<b>O</b>#
    * ###<b>O</b>#<b>O</b>#####<b>O</b>#<b>O</b>#
    * #<b>O</b><b>O</b><b>O</b>#<b>O</b>....#<b>O</b>#<b>O</b>#
    * #<b>O</b>#<b>O</b>#<b>O</b>###.#<b>O</b>#<b>O</b>#
    * #<b>O</b><b>O</b><b>O</b><b>O</b><b>O</b>#...#<b>O</b>#<b>O</b>#
    * #<b>O</b>###.#.#.#<b>O</b>#<b>O</b>#
    * #<b>O</b>..#.....#<b>O</b><b>O</b><b>O</b>#
    * ###############
    * </pre>
    * <p>In the second example, there are <b>64</b> tiles that are part of at least one of the best paths:</p>
    * <pre>
    * #################
    * #...#...#...#..<b>O</b>#
    * #.#.#.#.#.#.#.#<b>O</b>#
    * #.#.#.#...#...#<b>O</b>#
    * #.#.#.#.###.#.#<b>O</b>#
    * #<b>O</b><b>O</b><b>O</b>#.#.#.....#<b>O</b>#
    * #<b>O</b>#<b>O</b>#.#.#.#####<b>O</b>#
    * #<b>O</b>#<b>O</b>..#.#.#<b>O</b><b>O</b><b>O</b><b>O</b><b>O</b>#
    * #<b>O</b>#<b>O</b>#####.#<b>O</b>###<b>O</b>#
    * #<b>O</b>#<b>O</b>#..<b>O</b><b>O</b><b>O</b><b>O</b><b>O</b>#<b>O</b><b>O</b><b>O</b>#
    * #<b>O</b>#<b>O</b>###<b>O</b>#####<b>O</b>###
    * #<b>O</b>#<b>O</b>#<b>O</b><b>O</b><b>O</b>#..<b>O</b><b>O</b><b>O</b>#.#
    * #<b>O</b>#<b>O</b>#<b>O</b>#####<b>O</b>###.#
    * #<b>O</b>#<b>O</b>#<b>O</b><b>O</b><b>O</b><b>O</b><b>O</b><b>O</b><b>O</b>..#.#
    * #<b>O</b>#<b>O</b>#<b>O</b>#########.#
    * #<b>O</b>#<b>O</b><b>O</b><b>O</b>..........#
    * #################
    * </pre>
    * <p>Analyze your map further. <b>How many tiles are part of at least one of the best paths through the maze?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.countEntitiesOfShortestsPath(root.findStart(), root.findEnd());
    }
}
