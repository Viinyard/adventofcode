package dev.vinyard.adventofcode.soluce.year2025.day9;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import dev.vinyard.adventofcode.utils.FileReader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.net.URISyntaxException;

@AdventOfCodeSolution(year = 2025, day = 9, part = 2, description = "Movie Theater", link = "https://adventofcode.com/2025/day/9", tags = "unsolved")
public class Day9Part2Solution implements Solution<Object> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>The Elves just remembered: they can only switch out tiles that are <b>red</b> or <b>green</b>. So, your rectangle can only include red or green tiles.</p>
    * <p>In your list, every red tile is connected to the red tile before and after it by a straight line of <b>green tiles</b>. The list wraps, so the first red tile is also connected to the last red tile. Tiles that are adjacent in your list will always be on either the same row or the same column.</p>
    * <p>Using the same example as before, the tiles marked X would be green:</p>
    * <pre>
    * ..............
    * .......#XXX#..
    * .......X...X..
    * ..#XXXX#...X..
    * ..X........X..
    * ..#XXXXXX#.X..
    * .........X.X..
    * .........#X#..
    * ..............
    * </pre>
    * <p>In addition, all of the tiles <b>inside</b> this loop of red and green tiles are <b>also</b> green. So, in this example, these are the green tiles:</p>
    * <pre>
    * ..............
    * .......#XXX#..
    * .......XXXXX..
    * ..#XXXX#XXXX..
    * ..XXXXXXXXXX..
    * ..#XXXXXX#XX..
    * .........XXX..
    * .........#X#..
    * ..............
    * </pre>
    * <p>The remaining tiles are never red nor green.</p>
    * <p>The rectangle you choose still must have red tiles in opposite corners, but any other tiles it includes must now be red or green. This significantly limits your options.</p>
    * <p>For example, you could make a rectangle out of red and green tiles with an area of 15 between 7,3 and 11,1:</p>
    * <pre>
    * ..............
    * .......OOOO<b>O</b>..
    * .......OOOOO..
    * ..#XXXX<b>O</b>OOOO..
    * ..XXXXXXXXXX..
    * ..#XXXXXX#XX..
    * .........XXX..
    * .........#X#..
    * ..............
    * </pre>
    * <p>Or, you could make a thin rectangle with an area of 3 between 9,7 and 9,5:</p>
    * <pre>
    * ..............
    * .......#XXX#..
    * .......XXXXX..
    * ..#XXXX#XXXX..
    * ..XXXXXXXXXX..
    * ..#XXXXXX<b>O</b>XX..
    * .........OXX..
    * .........<b>O</b>X#..
    * ..............
    * </pre>
    * <p>The largest rectangle you can make in this example using only red and green tiles has area <b>24</b>. One way to do this is between 9,5 and 2,3:</p>
    * <pre>
    * ..............
    * .......#XXX#..
    * .......XXXXX..
    * ..<b>O</b>OOOOOOOXX..
    * ..OOOOOOOOXX..
    * ..OOOOOOO<b>O</b>XX..
    * .........XXX..
    * .........#X#..
    * ..............
    * </pre>
    * <p>Using two red tiles as opposite corners, <b>what is the largest area of any rectangle you can make using only red and green tiles?</b></p>
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
