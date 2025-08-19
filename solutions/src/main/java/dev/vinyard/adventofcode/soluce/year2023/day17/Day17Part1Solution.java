package dev.vinyard.adventofcode.soluce.year2023.day17;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2023, day = 17, part = 1, description = "Clumsy Crucible", link = "https://adventofcode.com/2023/day/17", tags = "unsolved")
public class Day17Part1Solution implements Solution<Long> {

    /**
    * <h2>--- Day 17: Clumsy Crucible ---</h2>
    * <p>The lava starts flowing rapidly once the Lava Production Facility is operational. As you leave, the reindeer offers you a parachute, allowing you to quickly reach Gear Island.</p>
    * <p>As you descend, your bird's-eye view of Gear Island reveals why you had trouble finding anyone on your way up: half of Gear Island is empty, but the half below you is a giant factory city!</p>
    * <p>You land near the gradually-filling pool of lava at the base of your new <b>lavafall</b>. Lavaducts will eventually carry the lava throughout the city, but to make use of it immediately, Elves are loading it into large <a href="https://en.wikipedia.org/wiki/Crucible">crucibles</a> on wheels.</p>
    * <p>The crucibles are top-heavy and pushed by hand. Unfortunately, the crucibles become very difficult to steer at high speeds, and so it can be hard to go in a straight line for very long.</p>
    * <p>To get Desert Island the machine parts it needs as soon as possible, you'll need to find the best way to get the crucible <b>from the lava pool to the machine parts factory</b>. To do this, you need to minimize <b>heat loss</b> while choosing a route that doesn't require the crucible to go in a <b>straight line</b> for too long.</p>
    * <p>Fortunately, the Elves here have a map (your puzzle input) that uses traffic patterns, ambient temperature, and hundreds of other parameters to calculate exactly how much heat loss can be expected for a crucible entering any particular city block.</p>
    * <p>For example:</p>
    * <pre>
    * 2413432311323
    * 3215453535623
    * 3255245654254
    * 3446585845452
    * 4546657867536
    * 1438598798454
    * 4457876987766
    * 3637877979653
    * 4654967986887
    * 4564679986453
    * 1224686865563
    * 2546548887735
    * 4322674655533
    * </pre>
    * <p>Each city block is marked by a single digit that represents the <b>amount of heat loss if the crucible enters that block</b>. The starting point, the lava pool, is the top-left city block; the destination, the machine parts factory, is the bottom-right city block. (Because you already start in the top-left block, you don't incur that block's heat loss unless you leave that block and then return to it.)</p>
    * <p>Because it is difficult to keep the top-heavy crucible going in a straight line for very long, it can move <b>at most three blocks</b> in a single direction before it must turn 90 degrees left or right. The crucible also can't reverse direction; after entering each city block, it may only turn left, continue straight, or turn right.</p>
    * <p>One way to <b>minimize heat loss</b> is this path:</p>
    * <pre>
    * 2<b>&gt;</b><b>&gt;</b>34<b>^</b><b>&gt;</b><b>&gt;</b><b>&gt;</b>1323
    * 32<b>v</b><b>&gt;</b><b>&gt;</b><b>&gt;</b>35<b>v</b>5623
    * 32552456<b>v</b><b>&gt;</b><b>&gt;</b>54
    * 3446585845<b>v</b>52
    * 4546657867<b>v</b><b>&gt;</b>6
    * 14385987984<b>v</b>4
    * 44578769877<b>v</b>6
    * 36378779796<b>v</b><b>&gt;</b>
    * 465496798688<b>v</b>
    * 456467998645<b>v</b>
    * 12246868655<b>&lt;</b><b>v</b>
    * 25465488877<b>v</b>5
    * 43226746555<b>v</b><b>&gt;</b>
    * </pre>
    * <p>This path never moves more than three consecutive blocks in the same direction and incurs a heat loss of only <b>102</b>.</p>
    * <p>Directing the crucible from the lava pool to the machine parts factory, but not moving more than three consecutive blocks in the same direction, <b>what is the least heat loss it can incur?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.getMinHeatLostPart1();
    }
}
