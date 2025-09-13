package dev.vinyard.adventofcode.soluce.year2022.day4;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2022, day = 4, part = 2, description = "Camp Cleanup", link = "https://adventofcode.com/2022/day/4", tags = "unsolved")
public class Day4Part2Solution implements Solution<Long> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>It seems like there is still quite a bit of duplicate work planned. Instead, the Elves would like to know the number of pairs that <b>overlap at all</b>.</p>
    * <p>In the above example, the first two pairs (2-4,6-8 and 2-3,4-5) don't overlap, while the remaining four pairs (5-7,7-9, 2-8,3-7, 6-6,4-6, and 2-6,4-8) do overlap:</p>
    * <ul>
    *  <li>5-7,7-9 overlaps in a single section, 7.</li>
    *  <li>2-8,3-7 overlaps all of the sections 3 through 7.</li>
    *  <li>6-6,4-6 overlaps in a single section, 6.</li>
    *  <li>2-6,4-8 overlaps in sections 4, 5, and 6.</li>
    * </ul>
    * <p>So, in this example, the number of overlapping assignment pairs is <b>4</b>.</p>
    * <p><b>In how many assignment pairs do the ranges overlap?</b></p>
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
