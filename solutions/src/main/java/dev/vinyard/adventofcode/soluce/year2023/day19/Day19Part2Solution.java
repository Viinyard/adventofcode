package dev.vinyard.adventofcode.soluce.year2023.day19;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2023, day = 19, part = 2, description = "Aplenty", link = "https://adventofcode.com/2023/day/19", tags = "unsolved")
public class Day19Part2Solution implements Solution<Long> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>Even with your help, the sorting process <b>still</b> isn't fast enough.</p>
    * <p>One of the Elves comes up with a new plan: rather than sort parts individually through all of these workflows, maybe you can figure out in advance which combinations of ratings will be accepted or rejected.</p>
    * <p>Each of the four ratings (x, m, a, s) can have an integer value ranging from a minimum of 1 to a maximum of 4000. Of <b>all possible distinct combinations</b> of ratings, your job is to figure out which ones will be <b>accepted</b>.</p>
    * <p>In the above example, there are <b>167409079868000</b> distinct combinations of ratings that will be accepted.</p>
    * <p>Consider only your list of workflows; the list of part ratings that the Elves wanted you to sort is no longer relevant. <b>How many distinct combinations of ratings will be accepted by the Elves' workflows?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.getAllCombinations();
    }
}
