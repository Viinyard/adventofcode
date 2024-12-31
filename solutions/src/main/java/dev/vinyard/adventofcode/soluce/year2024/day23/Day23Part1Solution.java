package dev.vinyard.adventofcode.soluce.year2024.day23;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2024, day = 23, part = 1, description = "LAN Party", link = "https://adventofcode.com/2024/day/23", tags = "unsolved")
public class Day23Part1Solution implements Solution<Object> {

    /**
    * <h2>--- Day 23: LAN Party ---</h2>
    * <p>As The Historians wander around a secure area at Easter Bunny HQ, you come across posters for a <a href="https://en.wikipedia.org/wiki/LAN_party">LAN party</a> scheduled for today! Maybe you can find it; you connect to a nearby <a href="/2016/day/9">datalink port</a> and download a map of the local network (your puzzle input).</p>
    * <p>The network map provides a list of every <b>connection between two computers</b>. For example:</p>
    * <pre>
    * kh-tc
    * qp-kh
    * de-cg
    * ka-co
    * yn-aq
    * qp-ub
    * cg-tb
    * vc-aq
    * tb-ka
    * wh-tc
    * yn-cg
    * kh-ub
    * ta-co
    * de-co
    * tc-td
    * tb-wq
    * wh-td
    * ta-ka
    * td-qp
    * aq-cg
    * wq-ub
    * ub-vc
    * de-ta
    * wq-aq
    * wq-vc
    * wh-yn
    * ka-de
    * kh-ta
    * co-tc
    * wh-qp
    * tb-vc
    * td-yn
    * </pre>
    * <p>Each line of text in the network map represents a single connection; the line kh-tc represents a connection between the computer named kh and the computer named tc. Connections aren't directional; tc-kh would mean exactly the same thing.</p>
    * <p>LAN parties typically involve multiplayer games, so maybe you can locate it by finding groups of connected computers. Start by looking for <b>sets of three computers</b> where each computer in the set is connected to the other two computers.</p>
    * <p>In this example, there are 12 such sets of three inter-connected computers:</p>
    * <pre>
    * aq,cg,yn
    * aq,vc,wq
    * co,de,ka
    * co,de,ta
    * co,ka,ta
    * de,ka,ta
    * kh,qp,ub
    * qp,td,wh
    * tb,vc,wq
    * tc,td,wh
    * td,wh,yn
    * ub,vc,wq
    * </pre>
    * <p>If the Chief Historian is here, <b>and</b> he's at the LAN party, it would be best to know that right away. You're pretty sure his computer's name starts with t, so consider only sets of three computers where at least one computer's name starts with t. That narrows the list down to <b>7</b> sets of three inter-connected computers:</p>
    * <pre>
    * co,de,<b>ta</b>
    * co,ka,<b>ta</b>
    * de,ka,<b>ta</b>
    * qp,<b>td</b>,wh
    * <b>tb</b>,vc,wq
    * <b>tc</b>,<b>td</b>,wh
    * <b>td</b>,wh,yn
    * </pre>
    * <p>Find all the sets of three inter-connected computers. <b>How many contain at least one computer with a name that starts with t?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.part1();
    }
}
