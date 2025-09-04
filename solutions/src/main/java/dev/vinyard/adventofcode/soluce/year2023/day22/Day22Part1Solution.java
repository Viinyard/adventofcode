package dev.vinyard.adventofcode.soluce.year2023.day22;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2023, day = 22, part = 1, description = "Sand Slabs", link = "https://adventofcode.com/2023/day/22", tags = "unsolved")
public class Day22Part1Solution implements Solution<Long> {

    /**
    * <h2>--- Day 22: Sand Slabs ---</h2>
    * <p>Enough sand has fallen; it can finally filter water for Snow Island.</p>
    * <p>Well, <b>almost</b>.</p>
    * <p>The sand has been falling as large compacted <b>bricks</b> of sand, piling up to form an impressive stack here near the edge of Island Island. In order to make use of the sand to filter water, some of the bricks will need to be broken apart - nay, <b>disintegrated</b> - back into freely flowing sand.</p>
    * <p>The stack is tall enough that you'll have to be careful about choosing which bricks to disintegrate; if you disintegrate the wrong brick, large portions of the stack could topple, which sounds pretty dangerous.</p>
    * <p>The Elves responsible for water filtering operations took a <b>snapshot of the bricks while they were still falling</b> (your puzzle input) which should let you work out which bricks are safe to disintegrate. For example:</p>
    * <pre>
    * 1,0,1~1,2,1
    * 0,0,2~2,0,2
    * 0,2,3~2,2,3
    * 0,0,4~0,2,4
    * 2,0,5~2,2,5
    * 0,1,6~2,1,6
    * 1,1,8~1,1,9
    * </pre>
    * <p>Each line of text in the snapshot represents the position of a single brick at the time the snapshot was taken. The position is given as two x,y,z coordinates - one for each end of the brick - separated by a tilde (~). Each brick is made up of a single straight line of cubes, and the Elves were even careful to choose a time for the snapshot that had all of the free-falling bricks at <b>integer positions above the ground</b>, so the whole snapshot is aligned to a three-dimensional cube grid.</p>
    * <p>A line like 2,2,2~2,2,2 means that both ends of the brick are at the same coordinate - in other words, that the brick is a single cube.</p>
    * <p>Lines like 0,0,10~1,0,10 or 0,0,10~0,1,10 both represent bricks that are <b>two cubes</b> in volume, both oriented horizontally. The first brick extends in the x direction, while the second brick extends in the y direction.</p>
    * <p>A line like 0,0,1~0,0,10 represents a <b>ten-cube brick</b> which is oriented <b>vertically</b>. One end of the brick is the cube located at 0,0,1, while the other end of the brick is located directly above it at 0,0,10.</p>
    * <p>The ground is at z=0 and is perfectly flat; the lowest z value a brick can have is therefore 1. So, 5,5,1~5,6,1 and 0,2,1~0,2,5 are both resting on the ground, but 3,3,2~3,3,3 was above the ground at the time of the snapshot.</p>
    * <p>Because the snapshot was taken while the bricks were still falling, some bricks will <b>still be in the air</b>; you'll need to start by figuring out where they will end up. Bricks are magically stabilized, so they <b>never rotate</b>, even in weird situations like where a long horizontal brick is only supported on one end. Two bricks cannot occupy the same position, so a falling brick will come to rest upon the first other brick it encounters.</p>
    * <p>Here is the same example again, this time with each brick given a letter so it can be marked in diagrams:</p>
    * <pre>
    * 1,0,1~1,2,1   &lt;- A
    * 0,0,2~2,0,2   &lt;- B
    * 0,2,3~2,2,3   &lt;- C
    * 0,0,4~0,2,4   &lt;- D
    * 2,0,5~2,2,5   &lt;- E
    * 0,1,6~2,1,6   &lt;- F
    * 1,1,8~1,1,9   &lt;- G
    * </pre>
    * <p>At the time of the snapshot, from the side so the x axis goes left to right, these bricks are arranged like this:</p>
    * <pre>
    *  x
    * 012
    * .G. 9
    * .G. 8
    * ... 7
    * FFF 6
    * ..E 5 z
    * D.. 4
    * CCC 3
    * BBB 2
    * .A. 1
    * --- 0
    * </pre>
    * <p>Rotating the perspective 90 degrees so the y axis now goes left to right, the same bricks are arranged like this:</p>
    * <pre>
    *  y
    * 012
    * .G. 9
    * .G. 8
    * ... 7
    * .F. 6
    * EEE 5 z
    * DDD 4
    * ..C 3
    * B.. 2
    * AAA 1
    * --- 0
    * </pre>
    * <p>Once all of the bricks fall downward as far as they can go, the stack looks like this, where ? means bricks are hidden behind other bricks at that location:</p>
    * <pre>
    *  x
    * 012
    * .G. 6
    * .G. 5
    * FFF 4
    * D.E 3 z
    * ??? 2
    * .A. 1
    * --- 0
    * </pre>
    * <p>Again from the side:</p>
    * <pre>
    *  y
    * 012
    * .G. 6
    * .G. 5
    * .F. 4
    * ??? 3 z
    * B.C 2
    * AAA 1
    * --- 0
    * </pre>
    * <p>Now that all of the bricks have settled, it becomes easier to tell which bricks are supporting which other bricks:</p>
    * <ul>
    *  <li>Brick A is the only brick supporting bricks B and C.</li>
    *  <li>Brick B is one of two bricks supporting brick D and brick E.</li>
    *  <li>Brick C is the other brick supporting brick D and brick E.</li>
    *  <li>Brick D supports brick F.</li>
    *  <li>Brick E also supports brick F.</li>
    *  <li>Brick F supports brick G.</li>
    *  <li>Brick G isn't supporting any bricks.</li>
    * </ul>
    * <p>Your first task is to figure out <b>which bricks are safe to disintegrate</b>. A brick can be safely disintegrated if, after removing it, <b>no other bricks</b> would fall further directly downward. Don't actually disintegrate any bricks - just determine what would happen if, for each brick, only that brick were disintegrated. Bricks can be disintegrated even if they're completely surrounded by other bricks; you can squeeze between bricks if you need to.</p>
    * <p>In this example, the bricks can be disintegrated as follows:</p>
    * <ul>
    *  <li>Brick A cannot be disintegrated safely; if it were disintegrated, bricks B and C would both fall.</li>
    *  <li>Brick B <b>can</b> be disintegrated; the bricks above it (D and E) would still be supported by brick C.</li>
    *  <li>Brick C <b>can</b> be disintegrated; the bricks above it (D and E) would still be supported by brick B.</li>
    *  <li>Brick D <b>can</b> be disintegrated; the brick above it (F) would still be supported by brick E.</li>
    *  <li>Brick E <b>can</b> be disintegrated; the brick above it (F) would still be supported by brick D.</li>
    *  <li>Brick F cannot be disintegrated; the brick above it (G) would fall.</li>
    *  <li>Brick G <b>can</b> be disintegrated; it does not support any other bricks.</li>
    * </ul>
    * <p>So, in this example, <b>5</b> bricks can be safely disintegrated.</p>
    * <p>Figure how the blocks will settle based on the snapshot. Once they've settled, consider disintegrating a single brick; <b>how many bricks could be safely chosen as the one to get disintegrated?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.fall();
    }
}
