package dev.vinyard.adventofcode.soluce.year2022.day10;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2022, day = 10, part = 1, description = "Cathode-Ray Tube", link = "https://adventofcode.com/2022/day/10", tags = "unsolved")
public class Day10Part1Solution implements Solution<Long> {

    /**
    * <h2>--- Day 10: Cathode-Ray Tube ---</h2>
    * <p>You avoid the ropes, plunge into the river, and swim to shore.</p>
    * <p>The Elves yell something about meeting back up with them upriver, but the river is too loud to tell exactly what they're saying. They finish crossing the bridge and disappear from view.</p>
    * <p>Situations like this must be why the Elves prioritized getting the communication system on your handheld device working. You pull it out of your pack, but the amount of water slowly draining from a big crack in its screen tells you it probably won't be of much immediate use.</p>
    * <p><b>Unless</b>, that is, you can design a replacement for the device's video system! It seems to be some kind of <a href="https://en.wikipedia.org/wiki/Cathode-ray_tube">cathode-ray tube</a> screen and simple CPU that are both driven by a precise <b>clock circuit</b>. The clock circuit ticks at a constant rate; each tick is called a <b>cycle</b>.</p>
    * <p>Start by figuring out the signal being sent by the CPU. The CPU has a single register, X, which starts with the value 1. It supports only two instructions:</p>
    * <ul>
    *  <li>addx V takes <b>two cycles</b> to complete. <b>After</b> two cycles, the X register is increased by the value V. (V can be negative.)</li>
    *  <li>noop takes <b>one cycle</b> to complete. It has no other effect.</li>
    * </ul>
    * <p>The CPU uses these instructions in a program (your puzzle input) to, somehow, tell the screen what to draw.</p>
    * <p>Consider the following small program:</p>
    * <pre>
    * noop
    * addx 3
    * addx -5
    * </pre>
    * <p>Execution of this program proceeds as follows:</p>
    * <ul>
    *  <li>At the start of the first cycle, the noop instruction begins execution. During the first cycle, X is 1. After the first cycle, the noop instruction finishes execution, doing nothing.</li>
    *  <li>At the start of the second cycle, the addx 3 instruction begins execution. During the second cycle, X is still 1.</li>
    *  <li>During the third cycle, X is still 1. After the third cycle, the addx 3 instruction finishes execution, setting X to 4.</li>
    *  <li>At the start of the fourth cycle, the addx -5 instruction begins execution. During the fourth cycle, X is still 4.</li>
    *  <li>During the fifth cycle, X is still 4. After the fifth cycle, the addx -5 instruction finishes execution, setting X to -1.</li>
    * </ul>
    * <p>Maybe you can learn something by looking at the value of the X register throughout execution. For now, consider the <b>signal strength</b> (the cycle number multiplied by the value of the X register) <b>during</b> the 20th cycle and every 40 cycles after that (that is, during the 20th, 60th, 100th, 140th, 180th, and 220th cycles).</p>
    * <p>For example, consider this larger program:</p>
    * <pre>
    * addx 15
    * addx -11
    * addx 6
    * addx -3
    * addx 5
    * addx -1
    * addx -8
    * addx 13
    * addx 4
    * noop
    * addx -1
    * addx 5
    * addx -1
    * addx 5
    * addx -1
    * addx 5
    * addx -1
    * addx 5
    * addx -1
    * addx -35
    * addx 1
    * addx 24
    * addx -19
    * addx 1
    * addx 16
    * addx -11
    * noop
    * noop
    * addx 21
    * addx -15
    * noop
    * noop
    * addx -3
    * addx 9
    * addx 1
    * addx -3
    * addx 8
    * addx 1
    * addx 5
    * noop
    * noop
    * noop
    * noop
    * noop
    * addx -36
    * noop
    * addx 1
    * addx 7
    * noop
    * noop
    * noop
    * addx 2
    * addx 6
    * noop
    * noop
    * noop
    * noop
    * noop
    * addx 1
    * noop
    * noop
    * addx 7
    * addx 1
    * noop
    * addx -13
    * addx 13
    * addx 7
    * noop
    * addx 1
    * addx -33
    * noop
    * noop
    * noop
    * addx 2
    * noop
    * noop
    * noop
    * addx 8
    * noop
    * addx -1
    * addx 2
    * addx 1
    * noop
    * addx 17
    * addx -9
    * addx 1
    * addx 1
    * addx -3
    * addx 11
    * noop
    * noop
    * addx 1
    * noop
    * addx 1
    * noop
    * noop
    * addx -13
    * addx -19
    * addx 1
    * addx 3
    * addx 26
    * addx -30
    * addx 12
    * addx -1
    * addx 3
    * addx 1
    * noop
    * noop
    * noop
    * addx -9
    * addx 18
    * addx 1
    * addx 2
    * noop
    * noop
    * addx 9
    * noop
    * noop
    * noop
    * addx -1
    * addx 2
    * addx -37
    * addx 1
    * addx 3
    * noop
    * addx 15
    * addx -21
    * addx 22
    * addx -6
    * addx 1
    * noop
    * addx 2
    * addx 1
    * noop
    * addx -10
    * noop
    * noop
    * addx 20
    * addx 1
    * addx 2
    * addx 2
    * addx -6
    * addx -11
    * noop
    * noop
    * noop
    * </pre>
    * <p>The interesting signal strengths can be determined as follows:</p>
    * <ul>
    *  <li>During the 20th cycle, register X has the value 21, so the signal strength is 20 * 21 = <b>420</b>. (The 20th cycle occurs in the middle of the second addx -1, so the value of register X is the starting value, 1, plus all of the other addx values up to that point: 1 + 15 - 11 + 6 - 3 + 5 - 1 - 8 + 13 + 4 = 21.)</li>
    *  <li>During the 60th cycle, register X has the value 19, so the signal strength is 60 * 19 = <b>1140</b>.</li>
    *  <li>During the 100th cycle, register X has the value 18, so the signal strength is 100 * 18 = <b>1800</b>.</li>
    *  <li>During the 140th cycle, register X has the value 21, so the signal strength is 140 * 21 = <b>2940</b>.</li>
    *  <li>During the 180th cycle, register X has the value 16, so the signal strength is 180 * 16 = <b>2880</b>.</li>
    *  <li>During the 220th cycle, register X has the value 18, so the signal strength is 220 * 18 = <b>3960</b>.</li>
    * </ul>
    * <p>The sum of these signal strengths is <b>13140</b>.</p>
    * <p>Find the signal strength during the 20th, 60th, 100th, 140th, 180th, and 220th cycles. <b>What is the sum of these six signal strengths?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.part1();
    }
}
