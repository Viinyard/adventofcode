package dev.vinyard.adventofcode.soluce.year2025.day3;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import dev.vinyard.adventofcode.utils.FileReader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.net.URISyntaxException;

@AdventOfCodeSolution(year = 2025, day = 3, part = 1, description = "Lobby", link = "https://adventofcode.com/2025/day/3", tags = "unsolved")
public class Day3Part1Solution implements Solution<Long> {

    /**
    * <h2>--- Day 3: Lobby ---</h2>
    * <p>You descend a short staircase, enter the surprisingly vast lobby, and are quickly cleared by the security checkpoint. When you get to the main elevators, however, you discover that each one has a red light above it: they're all <b>offline</b>.</p>
    * <p>"Sorry about that," an Elf apologizes as she tinkers with a nearby control panel. "Some kind of electrical surge seems to have fried them. I'll try to get them online soon."</p>
    * <p>You explain your need to get further underground. "Well, you could at least take the escalator down to the printing department, not that you'd get much further than that without the elevators working. That is, you could if the escalator weren't also offline."</p>
    * <p>"But, don't worry! It's not fried; it just needs power. Maybe you can get it running while I keep working on the elevators."</p>
    * <p>There are batteries nearby that can supply emergency power to the escalator for just such an occasion. The batteries are each labeled with their <a href="/2020/day/10">joltage</a> rating, a value from 1 to 9. You make a note of their joltage ratings (your puzzle input). For example:</p>
    * <pre>
    * 987654321111111
    * 811111111111119
    * 234234234234278
    * 818181911112111
    * </pre>
    * <p>The batteries are arranged into <b>banks</b>; each line of digits in your input corresponds to a single bank of batteries. Within each bank, you need to turn on <b>exactly two</b> batteries; the joltage that the bank produces is equal to the number formed by the digits on the batteries you've turned on. For example, if you have a bank like 12345 and you turn on batteries 2 and 4, the bank would produce 24 jolts. (You cannot rearrange batteries.)</p>
    * <p>You'll need to find the largest possible joltage each bank can produce. In the above example:</p>
    * <ul>
    *  <li>In <b>98</b>7654321111111, you can make the largest joltage possible, <b>98</b>, by turning on the first two batteries.</li>
    *  <li>In <b>8</b>1111111111111<b>9</b>, you can make the largest joltage possible by turning on the batteries labeled 8 and 9, producing <b>89</b> jolts.</li>
    *  <li>In 2342342342342<b>78</b>, you can make <b>78</b> by turning on the last two batteries (marked 7 and 8).</li>
    *  <li>In 818181<b>9</b>1111<b>2</b>111, the largest joltage you can produce is <b>92</b>.</li>
    * </ul>
    * <p>The total output joltage is the sum of the maximum joltage from each bank, so in this example, the total output joltage is 98 + 89 + 78 + 92 = <b>357</b>.</p>
    * <p>There are many batteries in front of you. Find the maximum joltage possible from each bank; <b>what is the total output joltage?</b></p>
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
