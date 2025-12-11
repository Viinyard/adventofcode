package dev.vinyard.adventofcode.soluce.year2025.day10;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2025, day = 10, part = 2, description = "Factory", link = "https://adventofcode.com/2025/day/10", tags = "unsolved")
public class Day10Part2Solution implements Solution<Object> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>All of the machines are starting to come online! Now, it's time to worry about the joltage requirements.</p>
    * <p>Each machine needs to be configured to <b>exactly the specified joltage levels</b> to function properly. Below the buttons on each machine is a big lever that you can use to switch the buttons from configuring the indicator lights to increasing the joltage levels. (Ignore the indicator light diagrams.)</p>
    * <p>The machines each have a set of <b>numeric counters</b> tracking its joltage levels, one counter per joltage requirement. The counters are all <b>initially set to zero</b>.</p>
    * <p>So, joltage requirements like {3,5,4,7} mean that the machine has four counters which are initially 0 and that the goal is to simultaneously configure the first counter to be 3, the second counter to be 5, the third to be 4, and the fourth to be 7.</p>
    * <p>The button wiring schematics are still relevant: in this new joltage configuration mode, each button now indicates which counters it affects, where 0 means the first counter, 1 means the second counter, and so on. When you push a button, each listed counter is <b>increased by 1</b>.</p>
    * <p>So, a button wiring schematic like (1,3) means that each time you push that button, the second and fourth counters would each increase by 1. If the current joltage levels were {0,1,2,3}, pushing the button would change them to be {0,2,2,4}.</p>
    * <p>You can push each button as many times as you like. However, your finger is getting sore from all the button pushing, and so you will need to determine the <b>fewest total presses</b> required to correctly configure each machine's joltage level counters to match the specified joltage requirements.</p>
    * <p>Consider again the example from before:</p>
    * <pre>
    * [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
    * [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
    * [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
    * </pre>
    * <p>Configuring the first machine's counters requires a minimum of <b>10</b> button presses. One way to do this is by pressing (3) once, (1,3) three times, (2,3) three times, (0,2) once, and (0,1) twice.</p>
    * <p>Configuring the second machine's counters requires a minimum of <b>12</b> button presses. One way to do this is by pressing (0,2,3,4) twice, (2,3) five times, and (0,1,2) five times.</p>
    * <p>Configuring the third machine's counters requires a minimum of <b>11</b> button presses. One way to do this is by pressing (0,1,2,3,4) five times, (0,1,2,4,5) five times, and (1,2) once.</p>
    * <p>So, the fewest button presses required to correctly configure the joltage level counters on all of the machines is 10 + 12 + 11 = <b>33</b>.</p>
    * <p>Analyze each machine's joltage requirements and button wiring schematics. <b>What is the fewest button presses required to correctly configure the joltage level counters on all of the machines?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.solution2();
    }
}
