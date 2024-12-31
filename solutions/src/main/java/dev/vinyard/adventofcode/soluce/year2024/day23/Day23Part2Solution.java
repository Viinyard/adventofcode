package dev.vinyard.adventofcode.soluce.year2024.day23;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2024, day = 23, part = 2, description = "LAN Party", link = "https://adventofcode.com/2024/day/23", tags = "unsolved")
public class Day23Part2Solution implements Solution<Object> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>There are still way too many results to go through them all. You'll have to find the LAN party another way and go there yourself.</p>
    * <p>Since it doesn't seem like any employees are around, you figure they must all be at the LAN party. If that's true, the LAN party will be the <b>largest set of computers that are all connected to each other</b>. That is, for each computer at the LAN party, that computer will have a connection to every other computer at the LAN party.</p>
    * <p>In the above example, the largest set of computers that are all connected to each other is made up of co, de, ka, and ta. Each computer in this set has a connection to every other computer in the set:</p>
    * <pre>
    * ka-co
    * ta-co
    * de-co
    * ta-ka
    * de-ta
    * ka-de
    * </pre>
    * <p>The LAN party posters say that the <b>password</b> to get into the LAN party is the name of every computer at the LAN party, sorted alphabetically, then joined together with commas. (The people running the LAN party are clearly a bunch of nerds.) In this example, the password would be <b>co,de,ka,ta</b>.</p>
    * <p><b>What is the password to get into the LAN party?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.part2();
    }
}
