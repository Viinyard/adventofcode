package dev.vinyard.adventofcode.soluce.year2024.day10;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.List;

@AdventOfCodeSolution(year = 2024, day = 10, part = 2, description = "Hoof It", link = "https://adventofcode.com/2024/day/10", tags = "unsolved")
public class Day10Part2Solution implements Solution<Object> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>The reindeer spends a few minutes reviewing your hiking trail map before realizing something, disappearing for a few minutes, and finally returning with yet another slightly-charred piece of paper.</p>
    * <p>The paper describes a second way to measure a trailhead called its <b>rating</b>. A trailhead's rating is the <b>number of distinct hiking trails</b> which begin at that trailhead. For example:</p>
    * <pre>
    * .....0.
    * ..4321.
    * ..5..2.
    * ..6543.
    * ..7..4.
    * ..8765.
    * ..9....
    * </pre>
    * <p>The above map has a single trailhead; its rating is 3 because there are exactly three distinct hiking trails which begin at that position:</p>
    * <pre>
    * .....0.   .....0.   .....0.
    * ..4321.   .....1.   .....1.
    * ..5....   .....2.   .....2.
    * ..6....   ..6543.   .....3.
    * ..7....   ..7....   .....4.
    * ..8....   ..8....   ..8765.
    * ..9....   ..9....   ..9....
    * </pre>
    * <p>Here is a map containing a single trailhead with rating 13:</p>
    * <pre>
    * ..90..9
    * ...1.98
    * ...2..7
    * 6543456
    * 765.987
    * 876....
    * 987....
    * </pre>
    * <p>This map contains a single trailhead with rating 227 (because there are 121 distinct hiking trails that lead to the 9 on the right edge and 106 that lead to the 9 on the bottom edge):</p>
    * <pre>
    * 012345
    * 123456
    * 234567
    * 345678
    * 4.6789
    * 56789.
    * </pre>
    * <p>Here's the larger example from before:</p>
    * <pre>
    * 89010123
    * 78121874
    * 87430965
    * 96549874
    * 45678903
    * 32019012
    * 01329801
    * 10456732
    * </pre>
    * <p>Considering its trailheads in reading order, they have ratings of 20, 24, 10, 4, 1, 4, 5, 8, and 5. The sum of all trailhead ratings in this larger example topographic map is <b>81</b>.</p>
    * <p>You're not sure how, but the reindeer seems to have crafted some tiny flags out of toothpicks and bits of paper and is using them to mark trailheads on your topographic map. <b>What is the sum of the ratings of all trailheads?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.TopographicMap topographicMap = parser.root().out;

        return topographicMap.getAllTrailHead().stream().map(e -> e.getAllHikingTrail(null, topographicMap)).flatMap(List::stream).count();
    }
}
