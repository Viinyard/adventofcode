package dev.vinyard.adventofcode.soluce.year2023.day20;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2023, day = 20, part = 2, description = "Pulse Propagation", link = "https://adventofcode.com/2023/day/20", tags = "unsolved")
public class Day20Part2Solution implements Solution<Long> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>The final machine responsible for moving the sand down to Island Island has a module attached named rx. The machine turns on when a <b>single low pulse</b> is sent to rx.</p>
    * <p>Reset all modules to their default states. Waiting for all pulses to be fully handled after each button press, <b>what is the fewest number of button presses required to deliver a single low pulse to the module named rx?</b></p>
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
