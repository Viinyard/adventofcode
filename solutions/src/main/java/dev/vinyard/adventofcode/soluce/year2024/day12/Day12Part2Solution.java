package dev.vinyard.adventofcode.soluce.year2024.day12;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2024, day = 12, part = 2, description = "Garden Groups", link = "https://adventofcode.com/2024/day/12", tags = "unsolved")
public class Day12Part2Solution implements Solution<Object> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>Fortunately, the Elves are trying to order so much fence that they qualify for a <b>bulk discount</b>!</p>
    * <p>Under the bulk discount, instead of using the perimeter to calculate the price, you need to use the <b>number of sides</b> each region has. Each straight section of fence counts as a side, regardless of how long it is.</p>
    * <p>Consider this example again:</p>
    * <pre>
    * AAAA
    * BBCD
    * BBCC
    * EEEC
    * </pre>
    * <p>The region containing type A plants has 4 sides, as does each of the regions containing plants of type B, D, and E. However, the more complex region containing the plants of type C has 8 sides!</p>
    * <p>Using the new method of calculating the per-region price by multiplying the region's area by its number of sides, regions A through E have prices 16, 16, 32, 4, and 12, respectively, for a total price of <b>80</b>.</p>
    * <p>The second example above (full of type X and O plants) would have a total price of <b>436</b>.</p>
    * <p>Here's a map that includes an E-shaped region full of type E plants:</p>
    * <pre>
    * EEEEE
    * EXXXX
    * EEEEE
    * EXXXX
    * EEEEE
    * </pre>
    * <p>The E-shaped region has an area of 17 and 12 sides for a price of 204. Including the two regions full of type X plants, this map has a total price of <b>236</b>.</p>
    * <p>This map has a total price of <b>368</b>:</p>
    * <pre>
    * AAAAAA
    * AAABBA
    * AAABBA
    * ABBAAA
    * ABBAAA
    * AAAAAA
    * </pre>
    * <p>It includes two regions full of type B plants (each with 4 sides) and a single region full of type A plants (with 4 sides on the outside and 8 more sides on the inside, a total of 12 sides). Be especially careful when counting the fence around regions like the one full of type A plants; in particular, each section of fence has an in-side and an out-side, so the fence does not connect across the middle of the region (where the two B regions touch diagonally). (The Elves would have used the Möbius Fencing Company instead, but their contract terms were too one-sided.)</p>
    * <p>The larger example from before now has the following updated prices:</p>
    * <ul>
    *  <li>A region of R plants with price 12 * 10 = 120.</li>
    *  <li>A region of I plants with price 4 * 4 = 16.</li>
    *  <li>A region of C plants with price 14 * 22 = 308.</li>
    *  <li>A region of F plants with price 10 * 12 = 120.</li>
    *  <li>A region of V plants with price 13 * 10 = 130.</li>
    *  <li>A region of J plants with price 11 * 12 = 132.</li>
    *  <li>A region of C plants with price 1 * 4 = 4.</li>
    *  <li>A region of E plants with price 13 * 8 = 104.</li>
    *  <li>A region of I plants with price 14 * 16 = 224.</li>
    *  <li>A region of M plants with price 5 * 6 = 30.</li>
    *  <li>A region of S plants with price 3 * 6 = 18.</li>
    * </ul>
    * <p>Adding these together produces its new total price of <b>1206</b>.</p>
    * <p><b>What is the new total price of fencing all regions on your map?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Garden garden = parser.root().out;

        return garden.getPlants().stream().map(ASD.Plant::getRegion).mapToLong(region -> region.stream().mapToLong(ASD.Plant::countEdges).sum() * region.size()).sum();
    }
}
