package dev.vinyard.adventofcode.soluce.year2023.day23;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2023, day = 23, part = 1, description = "A Long Walk", link = "https://adventofcode.com/2023/day/23", tags = "unsolved")
public class Day23Part1Solution implements Solution<Long> {

    /**
    * <h2>--- Day 23: A Long Walk ---</h2>
    * <p>The Elves resume water filtering operations! Clean water starts flowing over the edge of Island Island.</p>
    * <p>They offer to help <b>you</b> go over the edge of Island Island, too! Just hold on tight to one end of this impossibly long rope and they'll lower you down a safe distance from the massive waterfall you just created.</p>
    * <p>As you finally reach Snow Island, you see that the water isn't really reaching the ground: it's being <b>absorbed by the air</b> itself. It looks like you'll finally have a little downtime while the moisture builds up to snow-producing levels. Snow Island is pretty scenic, even without any snow; why not take a walk?</p>
    * <p>There's a map of nearby hiking trails (your puzzle input) that indicates <b>paths</b> (.), <b>forest</b> (#), and steep <b>slopes</b> (^, &gt;, v, and &lt;).</p>
    * <p>For example:</p>
    * <pre>
    * #.#####################
    * #.......#########...###
    * #######.#########.#.###
    * ###.....#.&gt;.&gt;.###.#.###
    * ###v#####.#v#.###.#.###
    * ###.&gt;...#.#.#.....#...#
    * ###v###.#.#.#########.#
    * ###...#.#.#.......#...#
    * #####.#.#.#######.#.###
    * #.....#.#.#.......#...#
    * #.#####.#.#.#########v#
    * #.#...#...#...###...&gt;.#
    * #.#.#v#######v###.###v#
    * #...#.&gt;.#...&gt;.&gt;.#.###.#
    * #####v#.#.###v#.#.###.#
    * #.....#...#...#.#.#...#
    * #.#########.###.#.#.###
    * #...###...#...#...#.###
    * ###.###.#.###v#####v###
    * #...#...#.#.&gt;.&gt;.#.&gt;.###
    * #.###.###.#.###.#.#v###
    * #.....###...###...#...#
    * #####################.#
    * </pre>
    * <p>You're currently on the single path tile in the top row; your goal is to reach the single path tile in the bottom row. Because of all the mist from the waterfall, the slopes are probably quite <b>icy</b>; if you step onto a slope tile, your next step must be <b>downhill</b> (in the direction the arrow is pointing). To make sure you have the most scenic hike possible, <b>never step onto the same tile twice</b>. What is the longest hike you can take?</p>
    * <p>In the example above, the longest hike you can take is marked with O, and your starting position is marked S:</p>
    * <pre>
    * #S#####################
    * #OOOOOOO#########...###
    * #######O#########.#.###
    * ###OOOOO#OOO&gt;.###.#.###
    * ###O#####O#O#.###.#.###
    * ###OOOOO#O#O#.....#...#
    * ###v###O#O#O#########.#
    * ###...#O#O#OOOOOOO#...#
    * #####.#O#O#######O#.###
    * #.....#O#O#OOOOOOO#...#
    * #.#####O#O#O#########v#
    * #.#...#OOO#OOO###OOOOO#
    * #.#.#v#######O###O###O#
    * #...#.&gt;.#...&gt;OOO#O###O#
    * #####v#.#.###v#O#O###O#
    * #.....#...#...#O#O#OOO#
    * #.#########.###O#O#O###
    * #...###...#...#OOO#O###
    * ###.###.#.###v#####O###
    * #...#...#.#.&gt;.&gt;.#.&gt;O###
    * #.###.###.#.###.#.#O###
    * #.....###...###...#OOO#
    * #####################O#
    * </pre>
    * <p>This hike contains <b>94</b> steps. (The other possible hikes you could have taken were 90, 86, 82, 82, and 74 steps long.)</p>
    * <p>Find the longest hike you can take through the hiking trails listed on your map. <b>How many steps long is the longest hike?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.findLongestPath();
    }
}
