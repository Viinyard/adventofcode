package dev.vinyard.adventofcode.soluce.year2023.day23;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2023, day = 23, part = 2, description = "A Long Walk", link = "https://adventofcode.com/2023/day/23", tags = "unsolved")
public class Day23Part2Solution implements Solution<Long> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>As you reach the trailhead, you realize that the ground isn't as slippery as you expected; you'll have <b>no problem</b> climbing up the steep slopes.</p>
    * <p>Now, treat all <b>slopes</b> as if they were normal <b>paths</b> (.). You still want to make sure you have the most scenic hike possible, so continue to ensure that you <b>never step onto the same tile twice</b>. What is the longest hike you can take?</p>
    * <p>In the example above, this increases the longest hike to <b>154</b> steps:</p>
    * <pre>
    * #S#####################
    * #OOOOOOO#########OOO###
    * #######O#########O#O###
    * ###OOOOO#.&gt;OOO###O#O###
    * ###O#####.#O#O###O#O###
    * ###O&gt;...#.#O#OOOOO#OOO#
    * ###O###.#.#O#########O#
    * ###OOO#.#.#OOOOOOO#OOO#
    * #####O#.#.#######O#O###
    * #OOOOO#.#.#OOOOOOO#OOO#
    * #O#####.#.#O#########O#
    * #O#OOO#...#OOO###...&gt;O#
    * #O#O#O#######O###.###O#
    * #OOO#O&gt;.#...&gt;O&gt;.#.###O#
    * #####O#.#.###O#.#.###O#
    * #OOOOO#...#OOO#.#.#OOO#
    * #O#########O###.#.#O###
    * #OOO###OOO#OOO#...#O###
    * ###O###O#O###O#####O###
    * #OOO#OOO#O#OOO&gt;.#.&gt;O###
    * #O###O###O#O###.#.#O###
    * #OOOOO###OOO###...#OOO#
    * #####################O#
    * </pre>
    * <p>Find the longest hike you can take through the surprisingly dry hiking trails listed on your map. <b>How many steps long is the longest hike?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.findLongestPathPart2();
    }
}
