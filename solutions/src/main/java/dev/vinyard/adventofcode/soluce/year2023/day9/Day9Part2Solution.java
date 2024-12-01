package dev.vinyard.adventofcode.soluce.year2023.day9;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2023, day = 9, part = 2, description = "Mirage Maintenance", link = "https://adventofcode.com/2023/day/9", tags = "unsolved")
public class Day9Part2Solution implements Solution<Long> {

    /**
     * <h2>--- Part Two ---</h2>
     * <p>Of course, it would be nice to have <b>even more history</b> included in your report. Surely it's safe to just <b>extrapolate backwards</b> as well, right?</p>
     * <p>For each history, repeat the process of finding differences until the sequence of differences is entirely zero. Then, rather than adding a zero to the end and filling in the next values of each previous sequence, you should instead add a zero to the <b>beginning</b> of your sequence of zeroes, then fill in new <b>first</b> values for each previous sequence.</p>
     * <p>In particular, here is what the third example history looks like when extrapolating back in time:</p>
     * <pre>
     * <b>5</b>  10  13  16  21  30  45
     *   <b>5</b>   3   3   5   9  15
     *    <b>-2</b>   0   2   4   6
     *       <b>2</b>   2   2   2
     *         <b>0</b>   0   0
     * </pre>
     * <p>Adding the new values on the left side of each sequence from bottom to top eventually reveals the new left-most history value: <b>5</b>.</p>
     * <p>Doing this for the remaining example data above results in previous values of <b>-3</b> for the first history and <b>0</b> for the second history. Adding all three new values together produces <b>2</b>.</p>
     * <p>Analyze your OASIS report again, this time extrapolating the <b>previous</b> value for each history. <b>What is the sum of these extrapolated values?</b></p>
     */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);
        ASD.Report report = parser.report().out;

        return report.getSuites().stream().mapToLong(ASD.Suite::getPreviousValue).sum();
    }
}
