package dev.vinyard.adventofcode.soluce.year2025.day9;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2025, day = 9, part = 1, description = "Movie Theater", link = "https://adventofcode.com/2025/day/9", tags = "unsolved")
public class Day9Part1Solution implements Solution<Long> {

    /**
    * <h2>--- Day 9: Movie Theater ---</h2>
    * <p>You slide down the <a href="https://en.wikipedia.org/wiki/Fireman%27s_pole">firepole</a> in the corner of the playground and land in the North Pole base movie theater!</p>
    * <p>The movie theater has a big tile floor with an interesting pattern. Elves here are redecorating the theater by switching out some of the square tiles in the big grid they form. Some of the tiles are <b>red</b>; the Elves would like to find the largest rectangle that uses red tiles for two of its opposite corners. They even have a list of where the red tiles are located in the grid (your puzzle input).</p>
    * <p>For example:</p>
    * <pre>
    * 7,1
    * 11,1
    * 11,7
    * 9,7
    * 9,5
    * 2,5
    * 2,3
    * 7,3
    * </pre>
    * <p>Showing red tiles as # and other tiles as ., the above arrangement of red tiles would look like this:</p>
    * <pre>
    * ..............
    * .......#...#..
    * ..............
    * ..#....#......
    * ..............
    * ..#......#....
    * ..............
    * .........#.#..
    * ..............
    * </pre>
    * <p>You can choose any two red tiles as the opposite corners of your rectangle; your goal is to find the largest rectangle possible.</p>
    * <p>For example, you could make a rectangle (shown as O) with an area of 24 between 2,5 and 9,7:</p>
    * <pre>
    * ..............
    * .......#...#..
    * ..............
    * ..#....#......
    * ..............
    * ..<b>O</b>OOOOOOO....
    * ..OOOOOOOO....
    * ..OOOOOOO<b>O</b>.#..
    * ..............
    * </pre>
    * <p>Or, you could make a rectangle with area 35 between 7,1 and 11,7:</p>
    * <pre>
    * ..............
    * .......<b>O</b>OOOO..
    * .......OOOOO..
    * ..#....OOOOO..
    * .......OOOOO..
    * ..#....OOOOO..
    * .......OOOOO..
    * .......OOOO<b>O</b>..
    * ..............
    * </pre>
    * <p>You could even make a thin rectangle with an area of only 6 between 7,3 and 2,3:</p>
    * <pre>
    * ..............
    * .......#...#..
    * ..............
    * ..<b>O</b>OOOO<b>O</b>......
    * ..............
    * ..#......#....
    * ..............
    * .........#.#..
    * ..............
    * </pre>
    * <p>Ultimately, the largest rectangle you can make in this example has area <b>50</b>. One way to do this is between 2,5 and 11,1:</p>
    * <pre>
    * ..............
    * ..OOOOOOOOO<b>O</b>..
    * ..OOOOOOOOOO..
    * ..OOOOOOOOOO..
    * ..OOOOOOOOOO..
    * ..<b>O</b>OOOOOOOOO..
    * ..............
    * .........#.#..
    * ..............
    * </pre>
    * <p>Using two red tiles as opposite corners, <b>what is the largest area of any rectangle you can make?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.solution1();
    }
}
