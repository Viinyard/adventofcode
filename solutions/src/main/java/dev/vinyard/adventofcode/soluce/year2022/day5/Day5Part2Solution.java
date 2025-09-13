package dev.vinyard.adventofcode.soluce.year2022.day5;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2022, day = 5, part = 2, description = "Supply Stacks", link = "https://adventofcode.com/2022/day/5", tags = "unsolved")
public class Day5Part2Solution implements Solution<String> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>As you watch the crane operator expertly rearrange the crates, you notice the process isn't following your prediction.</p>
    * <p>Some mud was covering the writing on the side of the crane, and you quickly wipe it away. The crane isn't a CrateMover 9000 - it's a <b>CrateMover 9001</b>.</p>
    * <p>The CrateMover 9001 is notable for many new and exciting features: air conditioning, leather seats, an extra cup holder, and <b>the ability to pick up and move multiple crates at once</b>.</p>
    * <p>Again considering the example above, the crates begin in the same configuration:</p>
    * <pre>
    *     [D]    
    * [N] [C]    
    * [Z] [M] [P]
    *  1   2   3 
    * </pre>
    * <p>Moving a single crate from stack 2 to stack 1 behaves the same as before:</p>
    * <pre>
    * [D]        
    * [N] [C]    
    * [Z] [M] [P]
    *  1   2   3 
    * </pre>
    * <p>However, the action of moving three crates from stack 1 to stack 3 means that those three moved crates <b>stay in the same order</b>, resulting in this new configuration:</p>
    * <pre>
    *         [D]
    *         [N]
    *     [C] [Z]
    *     [M] [P]
    *  1   2   3
    * </pre>
    * <p>Next, as both crates are moved from stack 2 to stack 1, they <b>retain their order</b> as well:</p>
    * <pre>
    *         [D]
    *         [N]
    * [C]     [Z]
    * [M]     [P]
    *  1   2   3
    * </pre>
    * <p>Finally, a single crate is still moved from stack 1 to stack 2, but now it's crate C that gets moved:</p>
    * <pre>
    *         [<b>D</b>]
    *         [N]
    *         [Z]
    * [<b>M</b>] [<b>C</b>] [P]
    *  1   2   3
    * </pre>
    * <p>In this example, the CrateMover 9001 has put the crates in a totally different order: <b>MCD</b>.</p>
    * <p>Before the rearrangement process finishes, update your simulation so that the Elves know where they should stand to be ready to unload the final supplies. <b>After the rearrangement procedure completes, what crate ends up on top of each stack?</b></p>
    */
    @Override
    public String solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.part2();
    }
}
