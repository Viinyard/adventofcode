package dev.vinyard.adventofcode.soluce.year2024.day21;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2024, day = 21, part = 2, description = "Keypad Conundrum", link = "https://adventofcode.com/2024/day/21", tags = "unsolved")
public class Day21Part2Solution implements Solution<Object> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>Just as the missing Historian is released, The Historians realize that a <b>second</b> member of their search party has also been missing this entire time!</p>
    * <p>A quick life-form scan reveals the Historian is also trapped in a locked area of the ship. Due to a variety of hazards, robots are once again dispatched, forming another chain of remote control keypads managing robotic-arm-wielding robots.</p>
    * <p>This time, many more robots are involved. In summary, there are the following keypads:</p>
    * <ul>
    *  <li>One directional keypad that <b>you</b> are using.</li>
    *  <li><b>25</b> directional keypads that <b>robots</b> are using.</li>
    *  <li>One numeric keypad (on a door) that a <b>robot</b> is using.</li>
    * </ul>
    * <p>The keypads form a chain, just like before: your directional keypad controls a robot which is typing on a directional keypad which controls a robot which is typing on a directional keypad... and so on, ending with the robot which is typing on the numeric keypad.</p>
    * <p>The door codes are the same this time around; only the number of robots and directional keypads has changed.</p>
    * <p>Find the fewest number of button presses you'll need to perform in order to cause the robot in front of the door to type each code. <b>What is the sum of the complexities of the five codes on your list?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.calculateComplexity(25);
    }
}
