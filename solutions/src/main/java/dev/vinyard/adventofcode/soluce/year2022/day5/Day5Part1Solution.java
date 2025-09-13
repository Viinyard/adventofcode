package dev.vinyard.adventofcode.soluce.year2022.day5;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2022, day = 5, part = 1, description = "Supply Stacks", link = "https://adventofcode.com/2022/day/5", tags = "unsolved")
public class Day5Part1Solution implements Solution<String> {

    /**
    * <h2>--- Day 5: Supply Stacks ---</h2>
    * <p>The expedition can depart as soon as the final supplies have been unloaded from the ships. Supplies are stored in stacks of marked <b>crates</b>, but because the needed supplies are buried under many other crates, the crates need to be rearranged.</p>
    * <p>The ship has a <b>giant cargo crane</b> capable of moving crates between stacks. To ensure none of the crates get crushed or fall over, the crane operator will rearrange them in a series of carefully-planned steps. After the crates are rearranged, the desired crates will be at the top of each stack.</p>
    * <p>The Elves don't want to interrupt the crane operator during this delicate procedure, but they forgot to ask her <b>which</b> crate will end up where, and they want to be ready to unload them as soon as possible so they can embark.</p>
    * <p>They do, however, have a drawing of the starting stacks of crates <b>and</b> the rearrangement procedure (your puzzle input). For example:</p>
    * <pre>
    *     [D]    
    * [N] [C]    
    * [Z] [M] [P]
    *  1   2   3 
    * 
    * move 1 from 2 to 1
    * move 3 from 1 to 3
    * move 2 from 2 to 1
    * move 1 from 1 to 2
    * </pre>
    * <p>In this example, there are three stacks of crates. Stack 1 contains two crates: crate Z is on the bottom, and crate N is on top. Stack 2 contains three crates; from bottom to top, they are crates M, C, and D. Finally, stack 3 contains a single crate, P.</p>
    * <p>Then, the rearrangement procedure is given. In each step of the procedure, a quantity of crates is moved from one stack to a different stack. In the first step of the above rearrangement procedure, one crate is moved from stack 2 to stack 1, resulting in this configuration:</p>
    * <pre>
    * [D]        
    * [N] [C]    
    * [Z] [M] [P]
    *  1   2   3 
    * </pre>
    * <p>In the second step, three crates are moved from stack 1 to stack 3. Crates are moved <b>one at a time</b>, so the first crate to be moved (D) ends up below the second and third crates:</p>
    * <pre>
    *         [Z]
    *         [N]
    *     [C] [D]
    *     [M] [P]
    *  1   2   3
    * </pre>
    * <p>Then, both crates are moved from stack 2 to stack 1. Again, because crates are moved <b>one at a time</b>, crate C ends up below crate M:</p>
    * <pre>
    *         [Z]
    *         [N]
    * [M]     [D]
    * [C]     [P]
    *  1   2   3
    * </pre>
    * <p>Finally, one crate is moved from stack 1 to stack 2:</p>
    * <pre>
    *         [<b>Z</b>]
    *         [N]
    *         [D]
    * [<b>C</b>] [<b>M</b>] [P]
    *  1   2   3
    * </pre>
    * <p>The Elves just need to know <b>which crate will end up on top of each stack</b>; in this example, the top crates are C in stack 1, M in stack 2, and Z in stack 3, so you should combine these together and give the Elves the message <b>CMZ</b>.</p>
    * <p><b>After the rearrangement procedure completes, what crate ends up on top of each stack?</b></p>
    */
    @Override
    public String solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.part1();
    }
}
