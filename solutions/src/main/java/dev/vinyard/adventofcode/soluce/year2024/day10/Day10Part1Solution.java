package dev.vinyard.adventofcode.soluce.year2024.day10;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import dev.vinyard.adventofcode.utils.FileReader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.net.URISyntaxException;

@AdventOfCodeSolution(year = 2024, day = 10, part = 1, description = "Hoof It", link = "https://adventofcode.com/2024/day/10", tags = "unsolved")
public class Day10Part1Solution implements Solution<Object> {

    /**
    * <h2>--- Day 10: Hoof It ---</h2>
    * <p>You all arrive at a <a href="/2023/day/15">Lava Production Facility</a> on a floating island in the sky. As the others begin to search the massive industrial complex, you feel a small nose boop your leg and look down to discover a reindeer wearing a hard hat.</p>
    * <p>The reindeer is holding a book titled "Lava Island Hiking Guide". However, when you open the book, you discover that most of it seems to have been scorched by lava! As you're about to ask how you can help, the reindeer brings you a blank <a href="https://en.wikipedia.org/wiki/Topographic_map">topographic map</a> of the surrounding area (your puzzle input) and looks up at you excitedly.</p>
    * <p>Perhaps you can help fill in the missing hiking trails?</p>
    * <p>The topographic map indicates the <b>height</b> at each position using a scale from 0 (lowest) to 9 (highest). For example:</p>
    * <pre>
    * 0123
    * 1234
    * 8765
    * 9876
    * </pre>
    * <p>Based on un-scorched scraps of the book, you determine that a good hiking trail is <b>as long as possible</b> and has an <b>even, gradual, uphill slope</b>. For all practical purposes, this means that a <b>hiking trail</b> is any path that starts at height 0, ends at height 9, and always increases by a height of exactly 1 at each step. Hiking trails never include diagonal steps - only up, down, left, or right (from the perspective of the map).</p>
    * <p>You look up from the map and notice that the reindeer has helpfully begun to construct a small pile of pencils, markers, rulers, compasses, stickers, and other equipment you might need to update the map with hiking trails.</p>
    * <p>A <b>trailhead</b> is any position that starts one or more hiking trails - here, these positions will always have height 0. Assembling more fragments of pages, you establish that a trailhead's <b>score</b> is the number of 9-height positions reachable from that trailhead via a hiking trail. In the above example, the single trailhead in the top left corner has a score of 1 because it can reach a single 9 (the one in the bottom left).</p>
    * <p>This trailhead has a score of 2:</p>
    * <pre>
    * ...0...
    * ...1...
    * ...2...
    * 6543456
    * 7.....7
    * 8.....8
    * 9.....9
    * </pre>
    * <p>(The positions marked . are impassable tiles to simplify these examples; they do not appear on your actual topographic map.)</p>
    * <p>This trailhead has a score of 4 because every 9 is reachable via a hiking trail except the one immediately to the left of the trailhead:</p>
    * <pre>
    * ..90..9
    * ...1.98
    * ...2..7
    * 6543456
    * 765.987
    * 876....
    * 987....
    * </pre>
    * <p>This topographic map contains <b>two</b> trailheads; the trailhead at the top has a score of 1, while the trailhead at the bottom has a score of 2:</p>
    * <pre>
    * 10..9..
    * 2...8..
    * 3...7..
    * 4567654
    * ...8..3
    * ...9..2
    * .....01
    * </pre>
    * <p>Here's a larger example:</p>
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
    * <p>This larger example has 9 trailheads. Considering the trailheads in reading order, they have scores of 5, 6, 5, 3, 1, 3, 5, 3, and 5. Adding these scores together, the sum of the scores of all trailheads is <b>36</b>.</p>
    * <p>The reindeer gleefully carries over a protractor and adds it to the pile. <b>What is the sum of the scores of all trailheads on your topographic map?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);
        // TODO get the ASD from the parser

        return null;
    }
}
