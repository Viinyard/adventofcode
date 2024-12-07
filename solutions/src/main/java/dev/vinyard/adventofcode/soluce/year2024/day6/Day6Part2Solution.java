package dev.vinyard.adventofcode.soluce.year2024.day6;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2024, day = 6, part = 2, description = "Guard Gallivant", link = "https://adventofcode.com/2024/day/6", tags = "unsolved")
public class Day6Part2Solution implements Solution<Object> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>While The Historians begin working around the guard's patrol route, you borrow their fancy device and step outside the lab. From the safety of a supply closet, you time travel through the last few months and <a href="/2018/day/4">record</a> the nightly status of the lab's guard post on the walls of the closet.</p>
    * <p>Returning after what seems like only a few seconds to The Historians, they explain that the guard's patrol area is simply too large for them to safely search the lab without getting caught.</p>
    * <p>Fortunately, they are <b>pretty sure</b> that adding a single new obstruction <b>won't</b> cause a time paradox. They'd like to place the new obstruction in such a way that the guard will get <b>stuck in a loop</b>, making the rest of the lab safe to search.</p>
    * <p>To have the lowest chance of creating a time paradox, The Historians would like to know <b>all</b> of the possible positions for such an obstruction. The new obstruction can't be placed at the guard's starting position - the guard is there right now and would notice.</p>
    * <p>In the above example, there are only <b>6</b> different positions where a new obstruction would cause the guard to get stuck in a loop. The diagrams of these six situations use O to mark the new obstruction, | to show a position where the guard moves up/down, - to show a position where the guard moves left/right, and + to show a position where the guard moves both up/down and left/right.</p>
    * <p>Option one, put a printing press next to the guard's starting position:</p>
    * <pre>
    * ....#.....
    * ....+---+#
    * ....|...|.
    * ..#.|...|.
    * ....|..#|.
    * ....|...|.
    * .#.<b>O</b>^---+.
    * ........#.
    * #.........
    * ......#...
    * </pre>
    * <p>Option two, put a stack of failed suit prototypes in the bottom right quadrant of the mapped area:</p>
    * <p></p>
    * <pre>
    * ....#.....
    * ....+---+#
    * ....|...|.
    * ..#.|...|.
    * ..+-+-+#|.
    * ..|.|.|.|.
    * .#+-^-+-+.
    * ......<b>O</b>.#.
    * #.........
    * ......#...
    * </pre>
    * <p>Option three, put a crate of chimney-squeeze prototype fabric next to the standing desk in the bottom right quadrant:</p>
    * <pre>
    * ....#.....
    * ....+---+#
    * ....|...|.
    * ..#.|...|.
    * ..+-+-+#|.
    * ..|.|.|.|.
    * .#+-^-+-+.
    * .+----+<b>O</b>#.
    * #+----+...
    * ......#...
    * </pre>
    * <p>Option four, put an alchemical retroencabulator near the bottom left corner:</p>
    * <pre>
    * ....#.....
    * ....+---+#
    * ....|...|.
    * ..#.|...|.
    * ..+-+-+#|.
    * ..|.|.|.|.
    * .#+-^-+-+.
    * ..|...|.#.
    * #<b>O</b>+---+...
    * ......#...
    * </pre>
    * <p>Option five, put the alchemical retroencabulator a bit to the right instead:</p>
    * <pre>
    * ....#.....
    * ....+---+#
    * ....|...|.
    * ..#.|...|.
    * ..+-+-+#|.
    * ..|.|.|.|.
    * .#+-^-+-+.
    * ....|.|.#.
    * #..<b>O</b>+-+...
    * ......#...
    * </pre>
    * <p>Option six, put a tank of sovereign glue right next to the tank of universal solvent:</p>
    * <pre>
    * ....#.....
    * ....+---+#
    * ....|...|.
    * ..#.|...|.
    * ..+-+-+#|.
    * ..|.|.|.|.
    * .#+-^-+-+.
    * .+----++#.
    * #+----++..
    * ......#<b>O</b>..
    * </pre>
    * <p>It doesn't really matter what you choose to use as an obstacle so long as you and The Historians can put it into position without the guard noticing. The important thing is having enough options that you can find one that minimizes time paradoxes, and in this example, there are <b>6</b> different positions you could choose.</p>
    * <p>You need to get the guard stuck in a loop by adding a single new obstruction. <b>How many different positions could you choose for this obstruction?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Map map = parser.root().out;

        ASD.Guardian guardian = map.findGuardian();

        ASD.Vector vector = guardian.getVector();

        return guardian.getVisitedPositions(map).stream().map(ASD.Vector::position).distinct().parallel()
                .map(position -> new ASD.Guardian(vector).obstruction(new ASD.Obstruction(position))
                ).filter(g -> g.isStuck(map)).count();
    }
}
