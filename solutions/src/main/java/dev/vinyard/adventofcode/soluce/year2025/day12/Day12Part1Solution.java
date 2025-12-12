package dev.vinyard.adventofcode.soluce.year2025.day12;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import dev.vinyard.adventofcode.utils.FileReader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.net.URISyntaxException;

@AdventOfCodeSolution(year = 2025, day = 12, part = 1, description = "Christmas Tree Farm", link = "https://adventofcode.com/2025/day/12", tags = "unsolved")
public class Day12Part1Solution implements Solution<Long> {

    /**
    * <h2>--- Day 12: Christmas Tree Farm ---</h2>
    * <p>You're almost out of time, but there can't be much left to decorate. Although there are no stairs, elevators, escalators, tunnels, chutes, teleporters, firepoles, or conduits here that would take you deeper into the North Pole base, there <b>is</b> a ventilation duct. You jump in.</p>
    * <p>After bumping around for a few minutes, you emerge into a large, well-lit cavern full of Christmas trees!</p>
    * <p>There are a few Elves here frantically decorating before the deadline. They think they'll be able to finish most of the work, but the one thing they're worried about is the <b>presents</b> for all the young Elves that live here at the North Pole. It's an ancient tradition to put the presents under the trees, but the Elves are worried they won't <b>fit</b>.</p>
    * <p>The presents come in a few standard but very weird shapes. The shapes and the regions into which they need to fit are all measured in standard <b>units</b>. To be aesthetically pleasing, the presents need to be placed into the regions in a way that follows a standardized two-dimensional unit grid; you also can't stack presents.</p>
    * <p>As always, the Elves have a summary of the situation (your puzzle input) for you. First, it contains a list of the presents' shapes. Second, it contains the size of the region under each tree and a list of the number of presents of each shape that need to fit into that region. For example:</p>
    * <pre>
    * 0:
    * ###
    * ##.
    * ##.
    * 
    * 1:
    * ###
    * ##.
    * .##
    * 
    * 2:
    * .##
    * ###
    * ##.
    * 
    * 3:
    * ##.
    * ###
    * ##.
    * 
    * 4:
    * ###
    * #..
    * ###
    * 
    * 5:
    * ###
    * .#.
    * ###
    * 
    * 4x4: 0 0 0 0 2 0
    * 12x5: 1 0 1 0 2 2
    * 12x5: 1 0 1 0 3 2
    * </pre>
    * <p>The first section lists the standard present <b>shapes</b>. For convenience, each shape starts with its <b>index</b> and a colon; then, the shape is displayed visually, where # is part of the shape and . is not.</p>
    * <p>The second section lists the <b>regions</b> under the trees. Each line starts with the width and length of the region; 12x5 means the region is 12 units wide and 5 units long. The rest of the line describes the presents that need to fit into that region by listing the <b>quantity of each shape</b> of present; 1 0 1 0 3 2 means you need to fit one present with shape index 0, no presents with shape index 1, one present with shape index 2, no presents with shape index 3, three presents with shape index 4, and two presents with shape index 5.</p>
    * <p>Presents can be <b>rotated and flipped</b> as necessary to make them fit in the available space, but they have to always be placed perfectly on the grid. Shapes can't overlap (that is, the # part from two different presents can't go in the same place on the grid), but they <b>can</b> fit together (that is, the . part in a present's shape's diagram does not block another present from occupying that space on the grid).</p>
    * <p>The Elves need to know <b>how many of the regions</b> can fit the presents listed. In the above example, there are six unique present shapes and three regions that need checking.</p>
    * <p>The first region is 4x4:</p>
    * <pre>
    * ....
    * ....
    * ....
    * ....
    * </pre>
    * <p>In it, you need to determine whether you could fit two presents that have shape index 4:</p>
    * <pre>
    * ###
    * #..
    * ###
    * </pre>
    * <p>After some experimentation, it turns out that you <b>can</b> fit both presents in this region. Here is one way to do it, using A to represent one present and B to represent the other:</p>
    * <pre>
    * AAA.
    * ABAB
    * ABAB
    * .BBB
    * </pre>
    * <p>The second region, 12x5: 1 0 1 0 2 2, is 12 units wide and 5 units long. In that region, you need to try to fit one present with shape index 0, one present with shape index 2, two presents with shape index 4, and two presents with shape index 5.</p>
    * <p>It turns out that these presents <b>can</b> all fit in this region. Here is one way to do it, again using different capital letters to represent all the required presents:</p>
    * <pre>
    * ....AAAFFE.E
    * .BBBAAFFFEEE
    * DDDBAAFFCECE
    * DBBB....CCC.
    * DDD.....C.C.
    * </pre>
    * <p>The third region, 12x5: 1 0 1 0 3 2, is the same size as the previous region; the only difference is that this region needs to fit one additional present with shape index 4. Unfortunately, no matter how hard you try, there is <b>no way to fit all of the presents</b> into this region.</p>
    * <p>So, in this example, <b>2</b> regions can fit all of their listed presents.</p>
    * <p>Consider the regions beneath each tree and the presents the Elves would like to fit into each of them. <b>How many of the regions can fit all of the presents listed?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);
        // TODO get the ASD from the parser

        return null;
    }
}
