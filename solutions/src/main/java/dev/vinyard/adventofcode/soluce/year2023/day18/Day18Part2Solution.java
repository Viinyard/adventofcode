package dev.vinyard.adventofcode.soluce.year2023.day18;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2023, day = 18, part = 2, description = "Lavaduct Lagoon", link = "https://adventofcode.com/2023/day/18", tags = "unsolved")
public class Day18Part2Solution implements Solution<Long> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>The Elves were right to be concerned; the planned lagoon would be <b>much too small</b>.</p>
    * <p>After a few minutes, someone realizes what happened; someone <b>swapped the color and instruction parameters</b> when producing the dig plan. They don't have time to fix the bug; one of them asks if you can <b>extract the correct instructions</b> from the hexadecimal codes.</p>
    * <p>Each hexadecimal code is <b>six hexadecimal digits</b> long. The first five hexadecimal digits encode the <b>distance in meters</b> as a five-digit hexadecimal number. The last hexadecimal digit encodes the <b>direction to dig</b>: 0 means R, 1 means D, 2 means L, and 3 means U.</p>
    * <p>So, in the above example, the hexadecimal codes can be converted into the true instructions:</p>
    * <ul>
    *  <li>#70c710 = R 461937</li>
    *  <li>#0dc571 = D 56407</li>
    *  <li>#5713f0 = R 356671</li>
    *  <li>#d2c081 = D 863240</li>
    *  <li>#59c680 = R 367720</li>
    *  <li>#411b91 = D 266681</li>
    *  <li>#8ceee2 = L 577262</li>
    *  <li>#caa173 = U 829975</li>
    *  <li>#1b58a2 = L 112010</li>
    *  <li>#caa171 = D 829975</li>
    *  <li>#7807d2 = L 491645</li>
    *  <li>#a77fa3 = U 686074</li>
    *  <li>#015232 = L 5411</li>
    *  <li>#7a21e3 = U 500254</li>
    * </ul>
    * <p>Digging out this loop and its interior produces a lagoon that can hold an impressive <b>952408144115</b> cubic meters of lava.</p>
    * <p>Convert the hexadecimal color codes into the correct instructions; if the Elves follow this new dig plan, <b>how many cubic meters of lava could the lagoon hold?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.part2().out;

        return root.calculateArea();
    }
}
