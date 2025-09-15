package dev.vinyard.adventofcode.soluce.year2022.day11;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2022, day = 11, part = 2, description = "Monkey in the Middle", link = "https://adventofcode.com/2022/day/11", tags = "unsolved")
public class Day11Part2Solution implements Solution<Long> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>You're worried you might not ever get your items back. So worried, in fact, that your relief that a monkey's inspection didn't damage an item <b>no longer causes your worry level to be divided by three</b>.</p>
    * <p>Unfortunately, that relief was all that was keeping your worry levels from reaching <b>ridiculous levels</b>. You'll need to <b>find another way to keep your worry levels manageable</b>.</p>
    * <p>At this rate, you might be putting up with these monkeys for a <b>very long time</b> - possibly <b>10000 rounds</b>!</p>
    * <p>With these new rules, you can still figure out the monkey business after 10000 rounds. Using the same example above:</p>
    * <pre>
    * == After round 1 ==
    * Monkey 0 inspected items 2 times.
    * Monkey 1 inspected items 4 times.
    * Monkey 2 inspected items 3 times.
    * Monkey 3 inspected items 6 times.
    * 
    * == After round 20 ==
    * Monkey 0 inspected items 99 times.
    * Monkey 1 inspected items 97 times.
    * Monkey 2 inspected items 8 times.
    * Monkey 3 inspected items 103 times.
    * 
    * == After round 1000 ==
    * Monkey 0 inspected items 5204 times.
    * Monkey 1 inspected items 4792 times.
    * Monkey 2 inspected items 199 times.
    * Monkey 3 inspected items 5192 times.
    * 
    * == After round 2000 ==
    * Monkey 0 inspected items 10419 times.
    * Monkey 1 inspected items 9577 times.
    * Monkey 2 inspected items 392 times.
    * Monkey 3 inspected items 10391 times.
    * 
    * == After round 3000 ==
    * Monkey 0 inspected items 15638 times.
    * Monkey 1 inspected items 14358 times.
    * Monkey 2 inspected items 587 times.
    * Monkey 3 inspected items 15593 times.
    * 
    * == After round 4000 ==
    * Monkey 0 inspected items 20858 times.
    * Monkey 1 inspected items 19138 times.
    * Monkey 2 inspected items 780 times.
    * Monkey 3 inspected items 20797 times.
    * 
    * == After round 5000 ==
    * Monkey 0 inspected items 26075 times.
    * Monkey 1 inspected items 23921 times.
    * Monkey 2 inspected items 974 times.
    * Monkey 3 inspected items 26000 times.
    * 
    * == After round 6000 ==
    * Monkey 0 inspected items 31294 times.
    * Monkey 1 inspected items 28702 times.
    * Monkey 2 inspected items 1165 times.
    * Monkey 3 inspected items 31204 times.
    * 
    * == After round 7000 ==
    * Monkey 0 inspected items 36508 times.
    * Monkey 1 inspected items 33488 times.
    * Monkey 2 inspected items 1360 times.
    * Monkey 3 inspected items 36400 times.
    * 
    * == After round 8000 ==
    * Monkey 0 inspected items 41728 times.
    * Monkey 1 inspected items 38268 times.
    * Monkey 2 inspected items 1553 times.
    * Monkey 3 inspected items 41606 times.
    * 
    * == After round 9000 ==
    * Monkey 0 inspected items 46945 times.
    * Monkey 1 inspected items 43051 times.
    * Monkey 2 inspected items 1746 times.
    * Monkey 3 inspected items 46807 times.
    * 
    * == After round 10000 ==
    * <b>Monkey 0 inspected items 52166 times.</b>
    * Monkey 1 inspected items 47830 times.
    * Monkey 2 inspected items 1938 times.
    * <b>Monkey 3 inspected items 52013 times.</b>
    * </pre>
    * <p>After 10000 rounds, the two most active monkeys inspected items 52166 and 52013 times. Multiplying these together, the level of <b>monkey business</b> in this situation is now <b>2713310158</b>.</p>
    * <p>Worry levels are no longer divided by three after each item is inspected; you'll need to find another way to keep your worry levels manageable. Starting again from the initial state in your puzzle input, <b>what is the level of monkey business after 10000 rounds?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.part2();
    }
}
