package dev.vinyard.adventofcode.soluce.year2024.day13;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2024, day = 13, part = 2, description = "Claw Contraption", link = "https://adventofcode.com/2024/day/13", tags = "unsolved")
public class Day13Part2Solution implements Solution<Object> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>As you go to win the first prize, you discover that the claw is nowhere near where you expected it would be. Due to a unit conversion error in your measurements, the position of every prize is actually 10000000000000 higher on both the X and Y axis!</p>
    * <p>Add 10000000000000 to the X and Y position of every prize. After making this change, the example above would now look like this:</p>
    * <pre>
    * Button A: X+94, Y+34
    * Button B: X+22, Y+67
    * Prize: X=10000000008400, Y=10000000005400
    * 
    * Button A: X+26, Y+66
    * Button B: X+67, Y+21
    * Prize: X=10000000012748, Y=10000000012176
    * 
    * Button A: X+17, Y+86
    * Button B: X+84, Y+37
    * Prize: X=10000000007870, Y=10000000006450
    * 
    * Button A: X+69, Y+23
    * Button B: X+27, Y+71
    * Prize: X=10000000018641, Y=10000000010279
    * </pre>
    * <p>Now, it is only possible to win a prize on the second and fourth claw machines. Unfortunately, it will take <b>many more than 100 presses</b> to do so.</p>
    * <p>Using the corrected prize coordinates, figure out how to win as many prizes as possible. <b>What is the fewest tokens you would have to spend to win all possible prizes?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        root.machines().stream().map(ASD.Machine::p).forEach(p -> p.addToPosition(10_000_000_000_000L));

        return root.solve();
    }
}
