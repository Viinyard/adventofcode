package dev.vinyard.adventofcode.soluce.year2025.day1;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import dev.vinyard.adventofcode.utils.FileReader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.net.URISyntaxException;

@AdventOfCodeSolution(year = 2025, day = 1, part = 1, description = "Secret Entrance", link = "https://adventofcode.com/2025/day/1", tags = "unsolved")
public class Day1Part1Solution implements Solution<Long> {

    /**
    * <h2>--- Day 1: Secret Entrance ---</h2>
    * <p>The Elves have good news and bad news.</p>
    * <p>The good news is that they've discovered <a href="https://en.wikipedia.org/wiki/Project_management">project management</a>! This has given them the tools they need to prevent their usual Christmas emergency. For example, they now know that the North Pole decorations need to be finished soon so that other critical tasks can start on time.</p>
    * <p>The bad news is that they've realized they have a <b>different</b> emergency: according to their resource planning, none of them have any time left to decorate the North Pole!</p>
    * <p>To save Christmas, the Elves need <b>you</b> to <b>finish decorating the North Pole by December 12th</b>.</p>
    * <p>Collect stars by solving puzzles. Two puzzles will be made available on each day; the second puzzle is unlocked when you complete the first. Each puzzle grants <b>one star</b>. Good luck!</p>
    * <p>You arrive at the secret entrance to the North Pole base ready to start decorating. Unfortunately, the <b>password</b> seems to have been changed, so you can't get in. A document taped to the wall helpfully explains:</p>
    * <p>"Due to new security protocols, the password is locked in the safe below. Please see the attached document for the new combination."</p>
    * <p>The safe has a dial with only an arrow on it; around the dial are the numbers 0 through 99 in order. As you turn the dial, it makes a small <b>click</b> noise as it reaches each number.</p>
    * <p>The attached document (your puzzle input) contains a sequence of <b>rotations</b>, one per line, which tell you how to open the safe. A rotation starts with an L or R which indicates whether the rotation should be to the <b>left</b> (toward lower numbers) or to the <b>right</b> (toward higher numbers). Then, the rotation has a <b>distance</b> value which indicates how many clicks the dial should be rotated in that direction.</p>
    * <p>So, if the dial were pointing at 11, a rotation of R8 would cause the dial to point at 19. After that, a rotation of L19 would cause it to point at 0.</p>
    * <p>Because the dial is a circle, turning the dial <b>left from 0</b> one click makes it point at 99. Similarly, turning the dial <b>right from 99</b> one click makes it point at 0.</p>
    * <p>So, if the dial were pointing at 5, a rotation of L10 would cause it to point at 95. After that, a rotation of R5 could cause it to point at 0.</p>
    * <p>The dial starts by pointing at 50.</p>
    * <p>You could follow the instructions, but your recent required official North Pole secret entrance security training seminar taught you that the safe is actually a decoy. The actual password is <b>the number of times the dial is left pointing at 0 after any rotation in the sequence</b>.</p>
    * <p>For example, suppose the attached document contained the following rotations:</p>
    * <pre>
    * L68
    * L30
    * R48
    * L5
    * R60
    * L55
    * L1
    * L99
    * R14
    * L82
    * </pre>
    * <p>Following these rotations would cause the dial to move as follows:</p>
    * <ul>
    *  <li>The dial starts by pointing at 50.</li>
    *  <li>The dial is rotated L68 to point at 82.</li>
    *  <li>The dial is rotated L30 to point at 52.</li>
    *  <li>The dial is rotated R48 to point at <b>0</b>.</li>
    *  <li>The dial is rotated L5 to point at 95.</li>
    *  <li>The dial is rotated R60 to point at 55.</li>
    *  <li>The dial is rotated L55 to point at <b>0</b>.</li>
    *  <li>The dial is rotated L1 to point at 99.</li>
    *  <li>The dial is rotated L99 to point at <b>0</b>.</li>
    *  <li>The dial is rotated R14 to point at 14.</li>
    *  <li>The dial is rotated L82 to point at 32.</li>
    * </ul>
    * <p>Because the dial points at 0 a total of three times during this process, the password in this example is <b>3</b>.</p>
    * <p>Analyze the rotations in your attached document. <b>What's the actual password to open the door?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);
        // TODO get the ASD from the parser

        return null;
    }
}
