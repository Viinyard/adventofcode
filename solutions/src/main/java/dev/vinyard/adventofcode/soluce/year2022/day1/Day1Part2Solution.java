package dev.vinyard.adventofcode.soluce.year2022.day1;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2022, day = 1, part = 2, description = "Calorie Counting", link = "https://adventofcode.com/2022/day/1", tags = "unsolved")
public class Day1Part2Solution implements Solution<Long> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>By the time you calculate the answer to the Elves' question, they've already realized that the Elf carrying the most Calories of food might eventually <b>run out of snacks</b>.</p>
    * <p>To avoid this unacceptable situation, the Elves would instead like to know the total Calories carried by the <b>top three</b> Elves carrying the most Calories. That way, even if one of those Elves runs out of snacks, they still have two backups.</p>
    * <p>In the example above, the top three Elves are the fourth Elf (with 24000 Calories), then the third Elf (with 11000 Calories), then the fifth Elf (with 10000 Calories). The sum of the Calories carried by these three elves is <b>45000</b>.</p>
    * <p>Find the top three Elves carrying the most Calories. <b>How many Calories are those Elves carrying in total?</b></p>
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
