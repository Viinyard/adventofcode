package dev.vinyard.adventofcode.soluce.year2023.day13;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.List;

@AdventOfCodeSolution(year = 2023, day = 13, part = 1, description = "Point of Incidence", link = "https://adventofcode.com/2023/day/13", tags = "unsolved")
public class Day13Part1Solution implements Solution<Long> {

    /**
    * <h2>--- Day 13: Point of Incidence ---</h2>
    * <p>With your help, the hot springs team locates an appropriate spring which launches you neatly and precisely up to the edge of <b>Lava Island</b>.</p>
    * <p>There's just one problem: you don't see any <b>lava</b>.</p>
    * <p>You <b>do</b> see a lot of ash and igneous rock; there are even what look like gray mountains scattered around. After a while, you make your way to a nearby cluster of mountains only to discover that the valley between them is completely full of large <b>mirrors</b>. Most of the mirrors seem to be aligned in a consistent way; perhaps you should head in that direction?</p>
    * <p>As you move through the valley of mirrors, you find that several of them have fallen from the large metal frames keeping them in place. The mirrors are extremely flat and shiny, and many of the fallen mirrors have lodged into the ash at strange angles. Because the terrain is all one color, it's hard to tell where it's safe to walk or where you're about to run into a mirror.</p>
    * <p>You note down the patterns of ash (.) and rocks (#) that you see as you walk (your puzzle input); perhaps by carefully analyzing these patterns, you can figure out where the mirrors are!</p>
    * <p>For example:</p>
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
    * <p>To find the reflection in each pattern, you need to find a perfect reflection across either a horizontal line between two rows or across a vertical line between two columns.</p>
    * <p>In the first pattern, the reflection is across a vertical line between two columns; arrows on each of the two columns point at the line between the columns:</p>
    * <pre>
    * 123456789
    *     &gt;&lt;   
    * #.##..##.
    * ..#.##.#.
    * ##......#
    * ##......#
    * ..#.##.#.
    * ..##..##.
    * #.#.##.#.
    *     &gt;&lt;   
    * 123456789
    * </pre>
    * <p>In this pattern, the line of reflection is the vertical line between columns 5 and 6. Because the vertical line is not perfectly in the middle of the pattern, part of the pattern (column 1) has nowhere to reflect onto and can be ignored; every other column has a reflected column within the pattern and must match exactly: column 2 matches column 9, column 3 matches 8, 4 matches 7, and 5 matches 6.</p>
    * <p>The second pattern reflects across a horizontal line instead:</p>
    * <pre>
    * 1 #...##..# 1
    * 2 #....#..# 2
    * 3 ..##..### 3
    * 4v#####.##.v4
    * 5^#####.##.^5
    * 6 ..##..### 6
    * 7 #....#..# 7
    * </pre>
    * <p>This pattern reflects across the horizontal line between rows 4 and 5. Row 1 would reflect with a hypothetical row 8, but since that's not in the pattern, row 1 doesn't need to match anything. The remaining rows match: row 2 matches row 7, row 3 matches row 6, and row 4 matches row 5.</p>
    * <p>To <b>summarize</b> your pattern notes, add up <b>the number of columns</b> to the left of each vertical line of reflection; to that, also add <b>100 multiplied by the number of rows</b> above each horizontal line of reflection. In the above example, the first pattern's vertical line has 5 columns to its left and the second pattern's horizontal line has 4 rows above it, a total of <b>405</b>.</p>
    * <p>Find the line of reflection in each of the patterns in your notes. <b>What number do you get after summarizing all of your notes?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        List<ASD.Pattern> patterns = parser.root().out.getPatterns();

        return patterns.stream().map(ASD.Pattern::findVerticalReflectionColumns).flatMapToLong(l -> l.stream().mapToLong(Integer::longValue)).sum() + patterns.stream().map(ASD.Pattern::findHorizontalReflectionRows).flatMapToLong(l -> l.stream().mapToLong(Integer::longValue)).map(i -> i * 100).sum();
    }
}
