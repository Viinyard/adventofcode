package dev.vinyard.adventofcode.soluce.year2023.day12;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.List;

@AdventOfCodeSolution(year = 2023, day = 12, part = 2, description = "Hot Springs", link = "https://adventofcode.com/2023/day/12", tags = "unsolved")
public class Day12Part2Solution implements Solution<Long> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>As you look out at the field of springs, you feel like there are way more springs than the condition records list. When you examine the records, you discover that they were actually <b>folded up</b> this whole time!</p>
    * <p>To <b>unfold the records</b>, on each row, replace the list of spring conditions with five copies of itself (separated by ?) and replace the list of contiguous groups of damaged springs with five copies of itself (separated by ,).</p>
    * <p>So, this row:</p>
    * <pre>
    * .# 1</pre>
    * <p>Would become:</p>
    * <pre>
    * .#?.#?.#?.#?.# 1,1,1,1,1</pre>
    * <p>The first line of the above example would become:</p>
    * <pre>
    * ???.###????.###????.###????.###????.### 1,1,3,1,1,3,1,1,3,1,1,3,1,1,3</pre>
    * <p>In the above example, after unfolding, the number of possible arrangements for some rows is now much larger:</p>
    * <ul>
    *  <li>???.### 1,1,3 - <b>1</b> arrangement</li>
    *  <li>.??..??...?##. 1,1,3 - <b>16384</b> arrangements</li>
    *  <li>?#?#?#?#?#?#?#? 1,3,1,6 - <b>1</b> arrangement</li>
    *  <li>????.#...#... 4,1,1 - <b>16</b> arrangements</li>
    *  <li>????.######..#####. 1,6,5 - <b>2500</b> arrangements</li>
    *  <li>?###???????? 3,2,1 - <b>506250</b> arrangements</li>
    * </ul>
    * <p>After unfolding, adding all of the possible arrangement counts together produces <b>525152</b>.</p>
    * <p>Unfold your condition records; <b>what is the new sum of possible arrangement counts?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        List<ASD.Line> lines = parser.root().out.getLines();

        return lines.stream().parallel().mapToLong(ASD.Line::countArrangementsPart2).sum();
    }
}
