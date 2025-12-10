package dev.vinyard.adventofcode.soluce.year2025.day10;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2025, day = 10, part = 1, description = "Factory", link = "https://adventofcode.com/2025/day/10", tags = "unsolved")
public class Day10Part1Solution implements Solution<Long> {

    /**
    * <h2>--- Day 10: Factory ---</h2>
    * <p>Just across the hall, you find a large factory. Fortunately, the Elves here have plenty of time to decorate. Unfortunately, it's because the factory machines are all offline, and none of the Elves can figure out the initialization procedure.</p>
    * <p>The Elves do have the manual for the machines, but the section detailing the initialization procedure was eaten by a <a href="https://en.wikipedia.org/wiki/Shiba_Inu">Shiba Inu</a>. All that remains of the manual are some indicator light diagrams, button wiring schematics, and <a href="3">joltage</a> requirements for each machine.</p>
    * <p>For example:</p>
    * <pre>
    * [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
    * [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
    * [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
    * </pre>
    * <p>The manual describes one machine per line. Each line contains a single indicator light diagram in [square brackets], one or more button wiring schematics in (parentheses), and joltage requirements in {curly braces}.</p>
    * <p>To start a machine, its <b>indicator lights</b> must match those shown in the diagram, where . means <b>off</b> and # means <b>on</b>. The machine has the number of indicator lights shown, but its indicator lights are all <b>initially off</b>.</p>
    * <p>So, an indicator light diagram like [.##.] means that the machine has four indicator lights which are initially off and that the goal is to simultaneously configure the first light to be off, the second light to be on, the third to be on, and the fourth to be off.</p>
    * <p>You can <b>toggle</b> the state of indicator lights by pushing any of the listed <b>buttons</b>. Each button lists which indicator lights it toggles, where 0 means the first light, 1 means the second light, and so on. When you push a button, each listed indicator light either turns on (if it was off) or turns off (if it was on). You have to push each button an integer number of times; there's no such thing as "0.5 presses" (nor can you push a button a negative number of times).</p>
    * <p>So, a button wiring schematic like (0,3,4) means that each time you push that button, the first, fourth, and fifth indicator lights would all toggle between on and off. If the indicator lights were [#.....], pushing the button would change them to be [...##.] instead.</p>
    * <p>Because none of the machines are running, the joltage requirements are irrelevant and can be safely ignored.</p>
    * <p>You can push each button as many times as you like. However, to save on time, you will need to determine the <b>fewest total presses</b> required to correctly configure all indicator lights for all machines in your list.</p>
    * <p>There are a few ways to correctly configure the first machine:</p>
    * <pre>
    * [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}</pre>
    * <ul>
    *  <li>You could press the first three buttons once each, a total of 3 button presses.</li>
    *  <li>You could press (1,3) once, (2,3) once, and (0,1) twice, a total of 4 button presses.</li>
    *  <li>You could press all of the buttons except (1,3) once each, a total of 5 button presses.</li>
    * </ul>
    * <p>However, the fewest button presses required is <b>2</b>. One way to do this is by pressing the last two buttons ((0,2) and (0,1)) once each.</p>
    * <p>The second machine can be configured with as few as <b>3</b> button presses:</p>
    * <pre>
    * [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}</pre>
    * <p>One way to achieve this is by pressing the last three buttons ((0,4), (0,1,2), and (1,2,3,4)) once each.</p>
    * <p>The third machine has a total of six indicator lights that need to be configured correctly:</p>
    * <pre>
    * [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}</pre>
    * <p>The fewest presses required to correctly configure it is <b>2</b>; one way to do this is by pressing buttons (0,3,4) and (0,1,2,4,5) once each.</p>
    * <p>So, the fewest button presses required to correctly configure the indicator lights on all of the machines is 2 + 3 + 2 = <b>7</b>.</p>
    * <p>Analyze each machine's indicator light diagram and button wiring schematics. <b>What is the fewest button presses required to correctly configure the indicator lights on all of the machines?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.solution1();
    }
}
