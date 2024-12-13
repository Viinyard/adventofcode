package dev.vinyard.adventofcode.soluce.year2024.day13;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import dev.vinyard.adventofcode.utils.FileReader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.net.URISyntaxException;

@AdventOfCodeSolution(year = 2024, day = 13, part = 1, description = "Claw Contraption", link = "https://adventofcode.com/2024/day/13", tags = "unsolved")
public class Day13Part1Solution implements Solution<Object> {

    /**
    * <h2>--- Day 13: Claw Contraption ---</h2>
    * <p>Next up: the <a href="/2020/day/24">lobby</a> of a resort on a tropical island. The Historians take a moment to admire the hexagonal floor tiles before spreading out.</p>
    * <p>Fortunately, it looks like the resort has a new <a href="https://en.wikipedia.org/wiki/Amusement_arcade">arcade</a>! Maybe you can win some prizes from the <a href="https://en.wikipedia.org/wiki/Claw_machine">claw machines</a>?</p>
    * <p>The claw machines here are a little unusual. Instead of a joystick or directional buttons to control the claw, these machines have two buttons labeled A and B. Worse, you can't just put in a token and play; it costs <b>3 tokens</b> to push the A button and <b>1 token</b> to push the B button.</p>
    * <p>With a little experimentation, you figure out that each machine's buttons are configured to move the claw a specific amount to the <b>right</b> (along the X axis) and a specific amount <b>forward</b> (along the Y axis) each time that button is pressed.</p>
    * <p>Each machine contains one <b>prize</b>; to win the prize, the claw must be positioned <b>exactly</b> above the prize on both the X and Y axes.</p>
    * <p>You wonder: what is the smallest number of tokens you would have to spend to win as many prizes as possible? You assemble a list of every machine's button behavior and prize location (your puzzle input). For example:</p>
    * <pre>
    * Button A: X+94, Y+34
    * Button B: X+22, Y+67
    * Prize: X=8400, Y=5400
    * 
    * Button A: X+26, Y+66
    * Button B: X+67, Y+21
    * Prize: X=12748, Y=12176
    * 
    * Button A: X+17, Y+86
    * Button B: X+84, Y+37
    * Prize: X=7870, Y=6450
    * 
    * Button A: X+69, Y+23
    * Button B: X+27, Y+71
    * Prize: X=18641, Y=10279
    * </pre>
    * <p>This list describes the button configuration and prize location of four different claw machines.</p>
    * <p>For now, consider just the first claw machine in the list:</p>
    * <ul>
    *  <li>Pushing the machine's A button would move the claw 94 units along the X axis and 34 units along the Y axis.</li>
    *  <li>Pushing the B button would move the claw 22 units along the X axis and 67 units along the Y axis.</li>
    *  <li>The prize is located at X=8400, Y=5400; this means that from the claw's initial position, it would need to move exactly 8400 units along the X axis and exactly 5400 units along the Y axis to be perfectly aligned with the prize in this machine.</li>
    * </ul>
    * <p>The cheapest way to win the prize is by pushing the A button 80 times and the B button 40 times. This would line up the claw along the X axis (because 80*94 + 40*22 = 8400) and along the Y axis (because 80*34 + 40*67 = 5400). Doing this would cost 80*3 tokens for the A presses and 40*1 for the B presses, a total of <b>280</b> tokens.</p>
    * <p>For the second and fourth claw machines, there is no combination of A and B presses that will ever win a prize.</p>
    * <p>For the third claw machine, the cheapest way to win the prize is by pushing the A button 38 times and the B button 86 times. Doing this would cost a total of <b>200</b> tokens.</p>
    * <p>So, the most prizes you could possibly win is two; the minimum tokens you would have to spend to win all (two) prizes is <b>480</b>.</p>
    * <p>You estimate that each button would need to be pressed <b>no more than 100 times</b> to win a prize. How else would someone be expected to play?</p>
    * <p>Figure out how to win as many prizes as possible. <b>What is the fewest tokens you would have to spend to win all possible prizes?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);
        // TODO get the ASD from the parser

        return null;
    }
}
