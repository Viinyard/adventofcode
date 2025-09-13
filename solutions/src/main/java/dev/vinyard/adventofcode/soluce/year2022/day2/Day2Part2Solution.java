package dev.vinyard.adventofcode.soluce.year2022.day2;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2022, day = 2, part = 2, description = "Rock Paper Scissors", link = "https://adventofcode.com/2022/day/2", tags = "unsolved")
public class Day2Part2Solution implements Solution<Long> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>The Elf finishes helping with the tent and sneaks back over to you. "Anyway, the second column says how the round needs to end: X means you need to lose, Y means you need to end the round in a draw, and Z means you need to win. Good luck!"</p>
    * <p>The total score is still calculated in the same way, but now you need to figure out what shape to choose so the round ends as indicated. The example above now goes like this:</p>
    * <ul>
    *  <li>In the first round, your opponent will choose Rock (A), and you need the round to end in a draw (Y), so you also choose Rock. This gives you a score of 1 + 3 = <b>4</b>.</li>
    *  <li>In the second round, your opponent will choose Paper (B), and you choose Rock so you lose (X) with a score of 1 + 0 = <b>1</b>.</li>
    *  <li>In the third round, you will defeat your opponent's Scissors with Rock for a score of 1 + 6 = <b>7</b>.</li>
    * </ul>
    * <p>Now that you're correctly decrypting the ultra top secret strategy guide, you would get a total score of <b>12</b>.</p>
    * <p>Following the Elf's instructions for the second column, <b>what would your total score be if everything goes exactly according to your strategy guide?</b></p>
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
