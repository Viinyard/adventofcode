package dev.vinyard.adventofcode.soluce.year2023.day13;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2023, day = 13, part = 2, description = "Point of Incidence", link = "https://adventofcode.com/2023/day/13", tags = "unsolved")
public class Day13Part2Solution implements Solution<Long> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>You resume walking through the valley of mirrors and - <b>SMACK!</b> - run directly into one. Hopefully nobody was watching, because that must have been pretty embarrassing.</p>
    * <p>Upon closer inspection, you discover that every mirror has exactly one <b>smudge</b>: exactly one . or # should be the opposite type.</p>
    * <p>In each pattern, you'll need to locate and fix the smudge that causes a <b>different reflection line</b> to be valid. (The old reflection line won't necessarily continue being valid after the smudge is fixed.)</p>
    * <p>Here's the above example again:</p>
    * <pre>
    * #.##..##.
    * ..#.##.#.
    * ##......#
    * ##......#
    * ..#.##.#.
    * ..##..##.
    * #.#.##.#.
    * 
    * #...##..#
    * #....#..#
    * ..##..###
    * #####.##.
    * #####.##.
    * ..##..###
    * #....#..#
    * </pre>
    * <p>The first pattern's smudge is in the top-left corner. If the top-left # were instead ., it would have a different, horizontal line of reflection:</p>
    * <pre>
    * 1 ..##..##. 1
    * 2 ..#.##.#. 2
    * 3v##......#v3
    * 4^##......#^4
    * 5 ..#.##.#. 5
    * 6 ..##..##. 6
    * 7 #.#.##.#. 7
    * </pre>
    * <p>With the smudge in the top-left corner repaired, a new horizontal line of reflection between rows 3 and 4 now exists. Row 7 has no corresponding reflected row and can be ignored, but every other row matches exactly: row 1 matches row 6, row 2 matches row 5, and row 3 matches row 4.</p>
    * <p>In the second pattern, the smudge can be fixed by changing the fifth symbol on row 2 from . to #:</p>
    * <pre>
    * 1v#...##..#v1
    * 2^#...##..#^2
    * 3 ..##..### 3
    * 4 #####.##. 4
    * 5 #####.##. 5
    * 6 ..##..### 6
    * 7 #....#..# 7
    * </pre>
    * <p>Now, the pattern has a different horizontal line of reflection between rows 1 and 2.</p>
    * <p>Summarize your notes as before, but instead use the new different reflection lines. In this example, the first pattern's new horizontal line has 3 rows above it and the second pattern's new horizontal line has 1 row above it, summarizing to the value <b>400</b>.</p>
    * <p>In each pattern, fix the smudge and find the different line of reflection. <b>What number do you get after summarizing the new reflection line in each pattern in your notes?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);
        // TODO get the ASD from the parser

        return null;
    }
}
