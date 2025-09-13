package dev.vinyard.adventofcode.soluce.year2022.day3;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2022, day = 3, part = 1, description = "Rucksack Reorganization", link = "https://adventofcode.com/2022/day/3", tags = "unsolved")
public class Day3Part1Solution implements Solution<Long> {

    /**
    * <h2>--- Day 3: Rucksack Reorganization ---</h2>
    * <p>One Elf has the important job of loading all of the <a href="https://en.wikipedia.org/wiki/Rucksack">rucksacks</a> with supplies for the jungle journey. Unfortunately, that Elf didn't quite follow the packing instructions, and so a few items now need to be rearranged.</p>
    * <p>Each rucksack has two large <b>compartments</b>. All items of a given type are meant to go into exactly one of the two compartments. The Elf that did the packing failed to follow this rule for exactly one item type per rucksack.</p>
    * <p>The Elves have made a list of all of the items currently in each rucksack (your puzzle input), but they need your help finding the errors. Every item type is identified by a single lowercase or uppercase letter (that is, a and A refer to different types of items).</p>
    * <p>The list of items for each rucksack is given as characters all on a single line. A given rucksack always has the same number of items in each of its two compartments, so the first half of the characters represent items in the first compartment, while the second half of the characters represent items in the second compartment.</p>
    * <p>For example, suppose you have the following list of contents from six rucksacks:</p>
    * <pre>
    * vJrwpWtwJgWrhcsFMMfFFhFp
    * jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
    * PmmdzqPrVvPwwTWBwg
    * wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
    * ttgJtRGJQctTZtZT
    * CrZsJsPPZsGzwwsLwLmpwMDw
    * </pre>
    * <ul>
    *  <li>The first rucksack contains the items vJrwpWtwJgWrhcsFMMfFFhFp, which means its first compartment contains the items vJrwpWtwJgWr, while the second compartment contains the items hcsFMMfFFhFp. The only item type that appears in both compartments is lowercase <b>p</b>.</li>
    *  <li>The second rucksack's compartments contain jqHRNqRjqzjGDLGL and rsFMfFZSrLrFZsSL. The only item type that appears in both compartments is uppercase <b>L</b>.</li>
    *  <li>The third rucksack's compartments contain PmmdzqPrV and vPwwTWBwg; the only common item type is uppercase <b>P</b>.</li>
    *  <li>The fourth rucksack's compartments only share item type <b>v</b>.</li>
    *  <li>The fifth rucksack's compartments only share item type <b>t</b>.</li>
    *  <li>The sixth rucksack's compartments only share item type <b>s</b>.</li>
    * </ul>
    * <p>To help prioritize item rearrangement, every item type can be converted to a <b>priority</b>:</p>
    * <ul>
    *  <li>Lowercase item types a through z have priorities 1 through 26.</li>
    *  <li>Uppercase item types A through Z have priorities 27 through 52.</li>
    * </ul>
    * <p>In the above example, the priority of the item type that appears in both compartments of each rucksack is 16 (p), 38 (L), 42 (P), 22 (v), 20 (t), and 19 (s); the sum of these is <b>157</b>.</p>
    * <p>Find the item type that appears in both compartments of each rucksack. <b>What is the sum of the priorities of those item types?</b></p>
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
