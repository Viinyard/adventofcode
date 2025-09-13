package dev.vinyard.adventofcode.soluce.year2022.day3;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2022, day = 3, part = 2, description = "Rucksack Reorganization", link = "https://adventofcode.com/2022/day/3", tags = "unsolved")
public class Day3Part2Solution implements Solution<Long> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>As you finish identifying the misplaced items, the Elves come to you with another issue.</p>
    * <p>For safety, the Elves are divided into groups of three. Every Elf carries a badge that identifies their group. For efficiency, within each group of three Elves, the badge is the <b>only item type carried by all three Elves</b>. That is, if a group's badge is item type B, then all three Elves will have item type B somewhere in their rucksack, and at most two of the Elves will be carrying any other item type.</p>
    * <p>The problem is that someone forgot to put this year's updated authenticity sticker on the badges. All of the badges need to be pulled out of the rucksacks so the new authenticity stickers can be attached.</p>
    * <p>Additionally, nobody wrote down which item type corresponds to each group's badges. The only way to tell which item type is the right one is by finding the one item type that is <b>common between all three Elves</b> in each group.</p>
    * <p>Every set of three lines in your list corresponds to a single group, but each group can have a different badge item type. So, in the above example, the first group's rucksacks are the first three lines:</p>
    * <pre>
    * vJrwpWtwJgWrhcsFMMfFFhFp
    * jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
    * PmmdzqPrVvPwwTWBwg
    * </pre>
    * <p>And the second group's rucksacks are the next three lines:</p>
    * <pre>
    * wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
    * ttgJtRGJQctTZtZT
    * CrZsJsPPZsGzwwsLwLmpwMDw
    * </pre>
    * <p>In the first group, the only item type that appears in all three rucksacks is lowercase r; this must be their badges. In the second group, their badge item type must be Z.</p>
    * <p>Priorities for these items must still be found to organize the sticker attachment efforts: here, they are 18 (r) for the first group and 52 (Z) for the second group. The sum of these is <b>70</b>.</p>
    * <p>Find the item type that corresponds to the badges of each three-Elf group. <b>What is the sum of the priorities of those item types?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Part2 root = parser.part2().out;

        return root.result();
    }
}
