package dev.vinyard.adventofcode.soluce.year2023.day17;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2023, day = 17, part = 2, description = "Clumsy Crucible", link = "https://adventofcode.com/2023/day/17", tags = "unsolved")
public class Day17Part2Solution implements Solution<Long> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>The crucibles of lava simply aren't large enough to provide an adequate supply of lava to the machine parts factory. Instead, the Elves are going to upgrade to <b>ultra crucibles</b>.</p>
    * <p>Ultra crucibles are even more difficult to steer than normal crucibles. Not only do they have trouble going in a straight line, but they also have trouble turning!</p>
    * <p>Once an ultra crucible starts moving in a direction, it needs to move <b>a minimum of four blocks</b> in that direction before it can turn (or even before it can stop at the end). However, it will eventually start to get wobbly: an ultra crucible can move a maximum of <b>ten consecutive blocks</b> without turning.</p>
    * <p>In the above example, an ultra crucible could follow this path to minimize heat loss:</p>
    * <pre>
    * 2<b>&gt;</b><b>&gt;</b><b>&gt;</b><b>&gt;</b><b>&gt;</b><b>&gt;</b><b>&gt;</b><b>&gt;</b>1323
    * 32154535<b>v</b>5623
    * 32552456<b>v</b>4254
    * 34465858<b>v</b>5452
    * 45466578<b>v</b><b>&gt;</b><b>&gt;</b><b>&gt;</b><b>&gt;</b>
    * 143859879845<b>v</b>
    * 445787698776<b>v</b>
    * 363787797965<b>v</b>
    * 465496798688<b>v</b>
    * 456467998645<b>v</b>
    * 122468686556<b>v</b>
    * 254654888773<b>v</b>
    * 432267465553<b>v</b>
    * </pre>
    * <p>In the above example, an ultra crucible would incur the minimum possible heat loss of <b>94</b>.</p>
    * <p>Here's another example:</p>
    * <pre>
    * 111111111111
    * 999999999991
    * 999999999991
    * 999999999991
    * 999999999991
    * </pre>
    * <p>Sadly, an ultra crucible would need to take an unfortunate path like this one:</p>
    * <pre>
    * 1<b>&gt;</b><b>&gt;</b><b>&gt;</b><b>&gt;</b><b>&gt;</b><b>&gt;</b><b>&gt;</b>1111
    * 9999999<b>v</b>9991
    * 9999999<b>v</b>9991
    * 9999999<b>v</b>9991
    * 9999999<b>v</b><b>&gt;</b><b>&gt;</b><b>&gt;</b><b>&gt;</b>
    * </pre>
    * <p>This route causes the ultra crucible to incur the minimum possible heat loss of <b>71</b>.</p>
    * <p>Directing the <b>ultra crucible</b> from the lava pool to the machine parts factory, <b>what is the least heat loss it can incur?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.getMinHeatLostPart2();
    }
}
