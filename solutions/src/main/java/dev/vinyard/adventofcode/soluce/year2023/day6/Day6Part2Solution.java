package dev.vinyard.adventofcode.soluce.year2023.day6;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;


@AdventOfCodeSolution(year = 2023, day = 6, part = 2, description = "Wait For It", link = "https://adventofcode.com/2023/day/6", tags = "unsolved")
public class Day6Part2Solution implements Solution<Long> {

    /**
     * <h2>--- Part Two ---</h2>
     * <p>As the race is about to start, you realize the piece of paper with race times and record distances you got earlier actually just has very bad <a href="https://en.wikipedia.org/wiki/Kerning">kerning</a>. There's really <b>only one race</b> - ignore the spaces between the numbers on each line.</p>
     * <p>So, the example from before:</p>
     * <pre>
     * Time:      7  15   30
     * Distance:  9  40  200
     * </pre>
     * <p>...now instead means this:</p>
     * <pre>
     * Time:      71530
     * Distance:  940200
     * </pre>
     * <p>Now, you have to figure out how many ways there are to win this single race. In this example, the race lasts for <b>71530 milliseconds</b> and the record distance you need to beat is <b>940200 millimeters</b>. You could hold the button anywhere from 14 to 71516 milliseconds and beat the record, a total of <b>71503</b> ways!</p>
     * <p><b>How many ways can you beat the record in this one much longer race?</b></p>
     */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);
        ASD.Race race = parser.race().out;

        return race.getNumberOfWaysToBeatTheDistance();
    }
}
