package dev.vinyard.adventofcode.soluce.year2022.day6;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2022, day = 6, part = 2, description = "Tuning Trouble", link = "https://adventofcode.com/2022/day/6", tags = "unsolved")
public class Day6Part2Solution implements Solution<Long> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>Your device's communication system is correctly detecting packets, but still isn't working. It looks like it also needs to look for <b>messages</b>.</p>
    * <p>A <b>start-of-message marker</b> is just like a start-of-packet marker, except it consists of <b>14 distinct characters</b> rather than 4.</p>
    * <p>Here are the first positions of start-of-message markers for all of the above examples:</p>
    * <ul>
    *  <li>mjqjpqmgbljsphdztnvjfqwrcgsmlb: first marker after character <b>19</b></li>
    *  <li>bvwbjplbgvbhsrlpgdmjqwftvncz: first marker after character <b>23</b></li>
    *  <li>nppdvjthqldpwncqszvftbrmjlhg: first marker after character <b>23</b></li>
    *  <li>nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg: first marker after character <b>29</b></li>
    *  <li>zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw: first marker after character <b>26</b></li>
    * </ul>
    * <p><b>How many characters need to be processed before the first start-of-message marker is detected?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.findStartOfMessage();
    }
}
