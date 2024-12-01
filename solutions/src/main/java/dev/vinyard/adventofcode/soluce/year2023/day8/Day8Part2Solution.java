package dev.vinyard.adventofcode.soluce.year2023.day8;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.math.BigInteger;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;


@AdventOfCodeSolution(year = 2023, day = 8, part = 2, description = "Haunted Wasteland", link = "https://adventofcode.com/2023/day/8", tags = "unsolved")
public class Day8Part2Solution implements Solution<Long> {

    /**
     * <h2>--- Part Two ---</h2>
     * <p>The sandstorm is upon you and you aren't any closer to escaping the wasteland. You had the camel follow the instructions, but you've barely left your starting position. It's going to take <b>significantly more steps</b> to escape!</p>
     * <p>What if the map isn't for people - what if the map is for <b>ghosts</b>? Are ghosts even bound by the laws of spacetime? Only one way to find out.</p>
     * <p>After examining the maps a bit longer, your attention is drawn to a curious fact: the number of nodes with names ending in A is equal to the number ending in Z! If you were a ghost, you'd probably just <b>start at every node that ends with A</b> and follow all of the paths at the same time until they all simultaneously end up at nodes that end with Z.</p>
     * <p>For example:</p>
     * <pre>
     * LR
     *
     * 11A = (11B, XXX)
     * 11B = (XXX, 11Z)
     * 11Z = (11B, XXX)
     * 22A = (22B, XXX)
     * 22B = (22C, 22C)
     * 22C = (22Z, 22Z)
     * 22Z = (22B, 22B)
     * XXX = (XXX, XXX)
     * </pre>
     * <p>Here, there are two starting nodes, 11A and 22A (because they both end with A). As you follow each left/right instruction, use that instruction to <b>simultaneously</b> navigate away from both nodes you're currently on. Repeat this process until <b>all</b> of the nodes you're currently on end with Z. (If only some of the nodes you're on end with Z, they act like any other node and you continue as normal.) In this example, you would proceed as follows:</p>
     * <ul>
     *  <li>Step 0: You are at 11A and 22A.</li>
     *  <li>Step 1: You choose all of the <b>left</b> paths, leading you to 11B and 22B.</li>
     *  <li>Step 2: You choose all of the <b>right</b> paths, leading you to <b>11Z</b> and 22C.</li>
     *  <li>Step 3: You choose all of the <b>left</b> paths, leading you to 11B and <b>22Z</b>.</li>
     *  <li>Step 4: You choose all of the <b>right</b> paths, leading you to <b>11Z</b> and 22B.</li>
     *  <li>Step 5: You choose all of the <b>left</b> paths, leading you to 11B and 22C.</li>
     *  <li>Step 6: You choose all of the <b>right</b> paths, leading you to <b>11Z</b> and <b>22Z</b>.</li>
     * </ul>
     * <p>So, in this example, you end up entirely on nodes that end in Z after <b>6</b> steps.</p>
     * <p>Simultaneously start on every node that ends with A. <b>How many steps does it take before you're only on nodes that end with Z?</b></p>
     */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);
        ASD.Maps maps = parser.maps().out;

        List<ASD.Node> starts = maps.getNodes().values().stream().filter(n -> n.getKey().endsWith("A")).toList();
        List<ASD.Node> ends = maps.getNodes().values().stream().filter(n -> n.getKey().endsWith("Z")).toList();

        BinaryOperator<BigInteger> lcm = (a, b) -> a.multiply(b).divide(a.gcd(b));

        return starts.stream().parallel().map(start -> Stream.iterate(start, n -> !ends.contains(n), n -> n.move(maps.getInstructions())).count()).map(BigInteger::valueOf).reduce(lcm).map(BigInteger::longValue).orElseThrow(() -> new RuntimeException("No solution found")) * maps.getInstructions().size();
    }
}
