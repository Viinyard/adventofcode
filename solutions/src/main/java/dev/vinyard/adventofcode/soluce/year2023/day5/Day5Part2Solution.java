package dev.vinyard.adventofcode.soluce.year2023.day5;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.List;
import java.util.stream.Stream;


@AdventOfCodeSolution(year = 2023, day = 5, part = 2, description = "If You Give A Seed A Fertilizer", link = "https://adventofcode.com/2023/day/5", tags = "unsolved")
public class Day5Part2Solution implements Solution<Long> {

    /**
     * <h2>--- Part Two ---</h2>
     * <p>Everyone will starve if you only plant such a small number of seeds. Re-reading the almanac, it looks like the seeds: line actually describes <b>ranges of seed numbers</b>.</p>
     * <p>The values on the initial seeds: line come in pairs. Within each pair, the first value is the <b>start</b> of the range and the second value is the <b>length</b> of the range. So, in the first line of the example above:</p>
     * <pre>
     * seeds: 79 14 55 13</pre>
     * <p>This line describes two ranges of seed numbers to be planted in the garden. The first range starts with seed number 79 and contains 14 values: 79, 80, ..., 91, 92. The second range starts with seed number 55 and contains 13 values: 55, 56, ..., 66, 67.</p>
     * <p>Now, rather than considering four seed numbers, you need to consider a total of <b>27</b> seed numbers.</p>
     * <p>In the above example, the lowest location number can be obtained from seed number 82, which corresponds to soil 84, fertilizer 84, water 84, light 77, temperature 45, humidity 46, and <b>location 46</b>. So, the lowest location number is <b>46</b>.</p>
     * <p>Consider all of the initial seed numbers listed in the ranges on the first line of the almanac. <b>What is the lowest location number that corresponds to any of the initial seed numbers?</b></p>
     */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);
        ASD.Almanach almanach = parser.almanach2().out;

        return Stream.of(almanach.getSeedRanges()).map(almanach.getSeedToLocationFunction()).flatMap(List::stream).mapToLong(ASD.Range::getStart).min().orElseThrow(() -> new IllegalStateException("No seed found"));
    }
}
