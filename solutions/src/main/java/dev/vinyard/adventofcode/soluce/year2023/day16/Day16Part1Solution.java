package dev.vinyard.adventofcode.soluce.year2023.day16;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2023, day = 16, part = 1, description = "The Floor Will Be Lava", link = "https://adventofcode.com/2023/day/16", tags = "unsolved")
public class Day16Part1Solution implements Solution<Long> {

    /**
    * <h2>--- Day 16: The Floor Will Be Lava ---</h2>
    * <p>With the beam of light completely focused <b>somewhere</b>, the reindeer leads you deeper still into the Lava Production Facility. At some point, you realize that the steel facility walls have been replaced with cave, and the doorways are just cave, and the floor is cave, and you're pretty sure this is actually just a giant cave.</p>
    * <p>Finally, as you approach what must be the heart of the mountain, you see a bright light in a cavern up ahead. There, you discover that the beam of light you so carefully focused is emerging from the cavern wall closest to the facility and pouring all of its energy into a contraption on the opposite side.</p>
    * <p>Upon closer inspection, the contraption appears to be a flat, two-dimensional square grid containing <b>empty space</b> (.), <b>mirrors</b> (/ and \), and <b>splitters</b> (| and -).</p>
    * <p>The contraption is aligned so that most of the beam bounces around the grid, but each tile on the grid converts some of the beam's light into <b>heat</b> to melt the rock in the cavern.</p>
    * <p>You note the layout of the contraption (your puzzle input). For example:</p>
    * <pre>
    * .|...\....
    * |.-.\.....
    * .....|-...
    * ........|.
    * ..........
    * .........\
    * ..../.\\..
    * .-.-/..|..
    * .|....-|.\
    * ..//.|....
    * </pre>
    * <p>The beam enters in the top-left corner from the left and heading to the <b>right</b>. Then, its behavior depends on what it encounters as it moves:</p>
    * <ul>
    *  <li>If the beam encounters <b>empty space</b> (.), it continues in the same direction.</li>
    *  <li>If the beam encounters a <b>mirror</b> (/ or \), the beam is <b>reflected</b> 90 degrees depending on the angle of the mirror. For instance, a rightward-moving beam that encounters a / mirror would continue <b>upward</b> in the mirror's column, while a rightward-moving beam that encounters a \ mirror would continue <b>downward</b> from the mirror's column.</li>
    *  <li>If the beam encounters the <b>pointy end of a splitter</b> (| or -), the beam passes through the splitter as if the splitter were <b>empty space</b>. For instance, a rightward-moving beam that encounters a - splitter would continue in the same direction.</li>
    *  <li>If the beam encounters the <b>flat side of a splitter</b> (| or -), the beam is <b>split into two beams</b> going in each of the two directions the splitter's pointy ends are pointing. For instance, a rightward-moving beam that encounters a | splitter would split into two beams: one that continues <b>upward</b> from the splitter's column and one that continues <b>downward</b> from the splitter's column.</li>
    * </ul>
    * <p>Beams do not interact with other beams; a tile can have many beams passing through it at the same time. A tile is <b>energized</b> if that tile has at least one beam pass through it, reflect in it, or split in it.</p>
    * <p>In the above example, here is how the beam of light bounces around the contraption:</p>
    * <pre>
    * &gt;|&lt;&lt;&lt;\....
    * |v-.\^....
    * .v...|-&gt;&gt;&gt;
    * .v...v^.|.
    * .v...v^...
    * .v...v^..\
    * .v../2\\..
    * &lt;-&gt;-/vv|..
    * .|&lt;&lt;&lt;2-|.\
    * .v//.|.v..
    * </pre>
    * <p>Beams are only shown on empty tiles; arrows indicate the direction of the beams. If a tile contains beams moving in multiple directions, the number of distinct directions is shown instead. Here is the same diagram but instead only showing whether a tile is <b>energized</b> (#) or not (.):</p>
    * <pre>
    * ######....
    * .#...#....
    * .#...#####
    * .#...##...
    * .#...##...
    * .#...##...
    * .#..####..
    * ########..
    * .#######..
    * .#...#.#..
    * </pre>
    * <p>Ultimately, in this example, <b>46</b> tiles become <b>energized</b>.</p>
    * <p>The light isn't energizing enough tiles to produce lava; to debug the contraption, you need to start by analyzing the current situation. With the beam starting in the top-left heading right, <b>how many tiles end up being energized?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        System.out.println(root);

        return root.getEngergizedCount();
    }
}
