package dev.vinyard.adventofcode.soluce.year2023.day8;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.stream.Stream;


@AdventOfCodeSolution(year = 2023, day = 8, part = 1, description = "Haunted Wasteland", link = "https://adventofcode.com/2023/day/8", tags = "unsolved")
public class Day8Part1Solution implements Solution<Long> {

    /**
     * <h2>--- Day 8: Haunted Wasteland ---</h2>
     * <p>You're still riding a camel across Desert Island when you spot a sandstorm quickly approaching. When you turn to warn the Elf, she disappears before your eyes! To be fair, she had just finished warning you about <b>ghosts</b> a few minutes ago.</p>
     * <p>One of the camel's pouches is labeled "maps" - sure enough, it's full of documents (your puzzle input) about how to navigate the desert. At least, you're pretty sure that's what they are; one of the documents contains a list of left/right instructions, and the rest of the documents seem to describe some kind of <b>network</b> of labeled nodes.</p>
     * <p>It seems like you're meant to use the <b>left/right</b> instructions to <b>navigate the network</b>. Perhaps if you have the camel follow the same instructions, you can escape the haunted wasteland!</p>
     * <p>After examining the maps for a bit, two nodes stick out: AAA and ZZZ. You feel like AAA is where you are now, and you have to follow the left/right instructions until you reach ZZZ.</p>
     * <p>This format defines each <b>node</b> of the network individually. For example:</p>
     * <pre>
     * RL
     *
     * AAA = (BBB, CCC)
     * BBB = (DDD, EEE)
     * CCC = (ZZZ, GGG)
     * DDD = (DDD, DDD)
     * EEE = (EEE, EEE)
     * GGG = (GGG, GGG)
     * ZZZ = (ZZZ, ZZZ)
     * </pre>
     * <p>Starting with AAA, you need to <b>look up the next element</b> based on the next left/right instruction in your input. In this example, start with AAA and go <b>right</b> (R) by choosing the right element of AAA, <b>CCC</b>. Then, L means to choose the <b>left</b> element of CCC, <b>ZZZ</b>. By following the left/right instructions, you reach ZZZ in <b>2</b> steps.</p>
     * <p>Of course, you might not find ZZZ right away. If you run out of left/right instructions, repeat the whole sequence of instructions as necessary: RL really means RLRLRLRLRLRLRLRL... and so on. For example, here is a situation that takes <b>6</b> steps to reach ZZZ:</p>
     * <pre>
     * LLR
     *
     * AAA = (BBB, BBB)
     * BBB = (AAA, ZZZ)
     * ZZZ = (ZZZ, ZZZ)
     * </pre>
     * <p>Starting at AAA, follow the left/right instructions. <b>How many steps are required to reach ZZZ?</b></p>
     */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);
        ASD.Maps maps = parser.maps().out;

        ASD.Node start = maps.getNodes().get("AAA");
        ASD.Node end = maps.getNodes().get("ZZZ");

        return Stream.iterate(start, n -> !end.equals(n), n -> n.move(maps.getInstructions())).count() * maps.getInstructions().size();
    }
}
