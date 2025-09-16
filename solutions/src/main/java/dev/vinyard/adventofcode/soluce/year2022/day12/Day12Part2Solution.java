package dev.vinyard.adventofcode.soluce.year2022.day12;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2022, day = 12, part = 2, description = "Hill Climbing Algorithm", link = "https://adventofcode.com/2022/day/12", tags = "unsolved")
public class Day12Part2Solution implements Solution<Long> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>As you walk up the hill, you suspect that the Elves will want to turn this into a hiking trail. The beginning isn't very scenic, though; perhaps you can find a better starting point.</p>
    * <p>To maximize exercise while hiking, the trail should start as low as possible: elevation a. The goal is still the square marked E. However, the trail should still be direct, taking the fewest steps to reach its goal. So, you'll need to find the shortest path from <b>any square at elevation a</b> to the square marked E.</p>
    * <p>Again consider the example from above:</p>
    * <pre>
    * <b>S</b>abqponm
    * abcryxxl
    * accsz<b>E</b>xk
    * acctuvwj
    * abdefghi
    * </pre>
    * <p>Now, there are six choices for starting position (five marked a, plus the square marked S that counts as being at elevation a). If you start at the bottom-left square, you can reach the goal most quickly:</p>
    * <pre>
    * ...v&lt;&lt;&lt;&lt;
    * ...vv&lt;&lt;^
    * ...v&gt;E^^
    * .&gt;v&gt;&gt;&gt;^^
    * &gt;^&gt;&gt;&gt;&gt;&gt;^
    * </pre>
    * <p>This path reaches the goal in only <b>29</b> steps, the fewest possible.</p>
    * <p><b>What is the fewest steps required to move starting from any square with elevation a to the location that should get the best signal?</b></p>
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
