package dev.vinyard.adventofcode.soluce.year2023.day12;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.List;

@AdventOfCodeSolution(year = 2023, day = 12, part = 1, description = "Hot Springs", link = "https://adventofcode.com/2023/day/12", tags = "unsolved")
public class Day12Part1Solution implements Solution<Long> {

    /**
    * <h2>--- Day 12: Hot Springs ---</h2>
    * <p>You finally reach the hot springs! You can see steam rising from secluded areas attached to the primary, ornate building.</p>
    * <p>As you turn to enter, the <a href="11">researcher</a> stops you. "Wait - I thought you were looking for the hot springs, weren't you?" You indicate that this definitely looks like hot springs to you.</p>
    * <p>"Oh, sorry, common mistake! This is actually the <a href="https://en.wikipedia.org/wiki/Onsen">onsen</a>! The hot springs are next door."</p>
    * <p>You look in the direction the researcher is pointing and suddenly notice the massive metal helixes towering overhead. "This way!"</p>
    * <p>It only takes you a few more steps to reach the main gate of the massive fenced-off area containing the springs. You go through the gate and into a small administrative building.</p>
    * <p>"Hello! What brings you to the hot springs today? Sorry they're not very hot right now; we're having a <b>lava shortage</b> at the moment." You ask about the missing machine parts for Desert Island.</p>
    * <p>"Oh, all of Gear Island is currently offline! Nothing is being manufactured at the moment, not until we get more lava to heat our forges. And our springs. The springs aren't very springy unless they're hot!"</p>
    * <p>"Say, could you go up and see why the lava stopped flowing? The springs are too cold for normal operation, but we should be able to find one springy enough to launch <b>you</b> up there!"</p>
    * <p>There's just one problem - many of the springs have fallen into disrepair, so they're not actually sure which springs would even be <b>safe</b> to use! Worse yet, their <b>condition records of which springs are damaged</b> (your puzzle input) are also damaged! You'll need to help them repair the damaged records.</p>
    * <p>In the giant field just outside, the springs are arranged into <b>rows</b>. For each row, the condition records show every spring and whether it is <b>operational</b> (.) or <b>damaged</b> (#). This is the part of the condition records that is itself damaged; for some springs, it is simply <b>unknown</b> (?) whether the spring is operational or damaged.</p>
    * <p>However, the engineer that produced the condition records also duplicated some of this information in a different format! After the list of springs for a given row, the size of each <b>contiguous group of damaged springs</b> is listed in the order those groups appear in the row. This list always accounts for every damaged spring, and each number is the entire size of its contiguous group (that is, groups are always separated by at least one operational spring: #### would always be 4, never 2,2).</p>
    * <p>So, condition records with no unknown spring conditions might look like this:</p>
    * <pre>
    * #.#.### 1,1,3
    * .#...#....###. 1,1,3
    * .#.###.#.###### 1,3,1,6
    * ####.#...#... 4,1,1
    * #....######..#####. 1,6,5
    * .###.##....# 3,2,1
    * </pre>
    * <p>However, the condition records are partially damaged; some of the springs' conditions are actually <b>unknown</b> (?). For example:</p>
    * <pre>
    * ???.### 1,1,3
    * .??..??...?##. 1,1,3
    * ?#?#?#?#?#?#?#? 1,3,1,6
    * ????.#...#... 4,1,1
    * ????.######..#####. 1,6,5
    * ?###???????? 3,2,1
    * </pre>
    * <p>Equipped with this information, it is your job to figure out <b>how many different arrangements</b> of operational and broken springs fit the given criteria in each row.</p>
    * <p>In the first line (???.### 1,1,3), there is exactly <b>one</b> way separate groups of one, one, and three broken springs (in that order) can appear in that row: the first three unknown springs must be broken, then operational, then broken (#.#), making the whole row #.#.###.</p>
    * <p>The second line is more interesting: .??..??...?##. 1,1,3 could be a total of <b>four</b> different arrangements. The last ? must always be broken (to satisfy the final contiguous group of three broken springs), and each ?? must hide exactly one of the two broken springs. (Neither ?? could be both broken springs or they would form a single contiguous group of two; if that were true, the numbers afterward would have been 2,3 instead.) Since each ?? can either be #. or .#, there are four possible arrangements of springs.</p>
    * <p>The last line is actually consistent with <b>ten</b> different arrangements! Because the first number is 3, the first and second ? must both be . (if either were #, the first number would have to be 4 or higher). However, the remaining run of unknown spring conditions have many different ways they could hold groups of two and one broken springs:</p>
    * <pre>
    * ?###???????? 3,2,1
    * .###.##.#...
    * .###.##..#..
    * .###.##...#.
    * .###.##....#
    * .###..##.#..
    * .###..##..#.
    * .###..##...#
    * .###...##.#.
    * .###...##..#
    * .###....##.#
    * </pre>
    * <p>In this example, the number of possible arrangements for each row is:</p>
    * <ul>
    *  <li>???.### 1,1,3 - <b>1</b> arrangement</li>
    *  <li>.??..??...?##. 1,1,3 - <b>4</b> arrangements</li>
    *  <li>?#?#?#?#?#?#?#? 1,3,1,6 - <b>1</b> arrangement</li>
    *  <li>????.#...#... 4,1,1 - <b>1</b> arrangement</li>
    *  <li>????.######..#####. 1,6,5 - <b>4</b> arrangements</li>
    *  <li>?###???????? 3,2,1 - <b>10</b> arrangements</li>
    * </ul>
    * <p>Adding all of the possible arrangement counts together produces a total of <b>21</b> arrangements.</p>
    * <p>For each row, count all of the different arrangements of operational and broken springs that meet the given criteria. <b>What is the sum of those counts?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        List<ASD.Line> lines = parser.root().out.getLines();

        return lines.stream().mapToLong(ASD.Line::countArrangementsPart1).sum();
    }
}
