package dev.vinyard.adventofcode.soluce.year2024.day12;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2024, day = 12, part = 1, description = "Garden Groups", link = "https://adventofcode.com/2024/day/12", tags = "unsolved")
public class Day12Part1Solution implements Solution<Object> {

    /**
    * <h2>--- Day 12: Garden Groups ---</h2>
    * <p>Why not search for the Chief Historian near the <a href="/2023/day/5">gardener</a> and his <a href="/2023/day/21">massive farm</a>? There's plenty of food, so The Historians grab something to eat while they search.</p>
    * <p>You're about to settle near a complex arrangement of garden plots when some Elves ask if you can lend a hand. They'd like to set up fences around each region of garden plots, but they can't figure out how much fence they need to order or how much it will cost. They hand you a map (your puzzle input) of the garden plots.</p>
    * <p>Each garden plot grows only a single type of plant and is indicated by a single letter on your map. When multiple garden plots are growing the same type of plant and are touching (horizontally or vertically), they form a <b>region</b>. For example:</p>
    * <pre>
    * AAAA
    * BBCD
    * BBCC
    * EEEC
    * </pre>
    * <p>This 4x4 arrangement includes garden plots growing five different types of plants (labeled A, B, C, D, and E), each grouped into their own region.</p>
    * <p>In order to accurately calculate the cost of the fence around a single region, you need to know that region's <b>area</b> and <b>perimeter</b>.</p>
    * <p>The <b>area</b> of a region is simply the number of garden plots the region contains. The above map's type A, B, and C plants are each in a region of area 4. The type E plants are in a region of area 3; the type D plants are in a region of area 1.</p>
    * <p>Each garden plot is a square and so has <b>four sides</b>. The <b>perimeter</b> of a region is the number of sides of garden plots in the region that do not touch another garden plot in the same region. The type A and C plants are each in a region with perimeter 10. The type B and E plants are each in a region with perimeter 8. The lone D plot forms its own region with perimeter 4.</p>
    * <p>Visually indicating the sides of plots in each region that contribute to the perimeter using - and |, the above map's regions' perimeters are measured as follows:</p>
    * <pre>
    * +-+-+-+-+
    * |A A A A|
    * +-+-+-+-+     +-+
    *               |D|
    * +-+-+   +-+   +-+
    * |B B|   |C|
    * +   +   + +-+
    * |B B|   |C C|
    * +-+-+   +-+ +
    *           |C|
    * +-+-+-+   +-+
    * |E E E|
    * +-+-+-+
    * </pre>
    * <p>Plants of the same type can appear in multiple separate regions, and regions can even appear within other regions. For example:</p>
    * <pre>
    * OOOOO
    * OXOXO
    * OOOOO
    * OXOXO
    * OOOOO
    * </pre>
    * <p>The above map contains <b>five</b> regions, one containing all of the O garden plots, and the other four each containing a single X plot.</p>
    * <p>The four X regions each have area 1 and perimeter 4. The region containing 21 type O plants is more complicated; in addition to its outer edge contributing a perimeter of 20, its boundary with each X region contributes an additional 4 to its perimeter, for a total perimeter of 36.</p>
    * <p>Due to "modern" business practices, the <b>price</b> of fence required for a region is found by <b>multiplying</b> that region's area by its perimeter. The <b>total price</b> of fencing all regions on a map is found by adding together the price of fence for every region on the map.</p>
    * <p>In the first example, region A has price 4 * 10 = 40, region B has price 4 * 8 = 32, region C has price 4 * 10 = 40, region D has price 1 * 4 = 4, and region E has price 3 * 8 = 24. So, the total price for the first example is <b>140</b>.</p>
    * <p>In the second example, the region with all of the O plants has price 21 * 36 = 756, and each of the four smaller X regions has price 1 * 4 = 4, for a total price of <b>772</b> (756 + 4 + 4 + 4 + 4).</p>
    * <p>Here's a larger example:</p>
    * <pre>
    * RRRRIICCFF
    * RRRRIICCCF
    * VVRRRCCFFF
    * VVRCCCJFFF
    * VVVVCJJCFE
    * VVIVCCJJEE
    * VVIIICJJEE
    * MIIIIIJJEE
    * MIIISIJEEE
    * MMMISSJEEE
    * </pre>
    * <p>It contains:</p>
    * <ul>
    *  <li>A region of R plants with price 12 * 18 = 216.</li>
    *  <li>A region of I plants with price 4 * 8 = 32.</li>
    *  <li>A region of C plants with price 14 * 28 = 392.</li>
    *  <li>A region of F plants with price 10 * 18 = 180.</li>
    *  <li>A region of V plants with price 13 * 20 = 260.</li>
    *  <li>A region of J plants with price 11 * 20 = 220.</li>
    *  <li>A region of C plants with price 1 * 4 = 4.</li>
    *  <li>A region of E plants with price 13 * 18 = 234.</li>
    *  <li>A region of I plants with price 14 * 22 = 308.</li>
    *  <li>A region of M plants with price 5 * 12 = 60.</li>
    *  <li>A region of S plants with price 3 * 8 = 24.</li>
    * </ul>
    * <p>So, it has a total price of <b>1930</b>.</p>
    * <p><b>What is the total price of fencing all regions on your map?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Garden garden = parser.root().out;

        return garden.getFences();
    }
}
