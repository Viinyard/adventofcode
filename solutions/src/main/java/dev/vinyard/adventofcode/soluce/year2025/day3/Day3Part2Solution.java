package dev.vinyard.adventofcode.soluce.year2025.day3;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import dev.vinyard.adventofcode.utils.FileReader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.net.URISyntaxException;

@AdventOfCodeSolution(year = 2025, day = 3, part = 2, description = "Lobby", link = "https://adventofcode.com/2025/day/3", tags = "unsolved")
public class Day3Part2Solution implements Solution<Object> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>The escalator doesn't move. The Elf explains that it probably needs more joltage to overcome the <a href="https://en.wikipedia.org/wiki/Static_friction">static friction</a> of the system and hits the big red "joltage limit safety override" button. You lose count of the number of times she needs to confirm "yes, I'm sure" and decorate the lobby a bit while you wait.</p>
    * <p>Now, you need to make the largest joltage by turning on <b>exactly twelve</b> batteries within each bank.</p>
    * <p>The joltage output for the bank is still the number formed by the digits of the batteries you've turned on; the only difference is that now there will be <b>12</b> digits in each bank's joltage output instead of two.</p>
    * <p>Consider again the example from before:</p>
    * <pre>
    * 987654321111111
    * 811111111111119
    * 234234234234278
    * 818181911112111
    * </pre>
    * <p>Now, the joltages are much larger:</p>
    * <ul>
    *  <li>In <b>987654321111</b>111, the largest joltage can be found by turning on everything except some 1s at the end to produce <b>987654321111</b>.</li>
    *  <li>In the digit sequence <b>81111111111</b>111<b>9</b>, the largest joltage can be found by turning on everything except some 1s, producing <b>811111111119</b>.</li>
    *  <li>In 23<b>4</b>2<b>34234234278</b>, the largest joltage can be found by turning on everything except a 2 battery, a 3 battery, and another 2 battery near the start to produce <b>434234234278</b>.</li>
    *  <li>In <b>8</b>1<b>8</b>1<b>8</b>1<b>911112111</b>, the joltage <b>888911112111</b> is produced by turning on everything except some 1s near the front.</li>
    * </ul>
    * <p>The total output joltage is now much larger: 987654321111 + 811111111119 + 434234234278 + 888911112111 = <b>3121910778619</b>.</p>
    * <p><b>What is the new total output joltage?</b></p>
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
