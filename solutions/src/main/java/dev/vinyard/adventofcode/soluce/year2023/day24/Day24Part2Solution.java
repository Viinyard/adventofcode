package dev.vinyard.adventofcode.soluce.year2023.day24;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2023, day = 24, part = 2, description = "Never Tell Me The Odds", link = "https://adventofcode.com/2023/day/24", tags = "unsolved")
public class Day24Part2Solution implements Solution<Long> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>Upon further analysis, it doesn't seem like <b>any</b> hailstones will naturally collide. It's up to you to fix that!</p>
    * <p>You find a rock on the ground nearby. While it seems extremely unlikely, if you throw it just right, you should be able to <b>hit every hailstone in a single throw</b>!</p>
    * <p>You can use the probably-magical winds to reach <b>any integer position</b> you like and to propel the rock at <b>any integer velocity</b>. Now <b>including the Z axis</b> in your calculations, if you throw the rock at time 0, where do you need to be so that the rock <b>perfectly collides with every hailstone</b>? Due to probably-magical inertia, the rock won't slow down or change direction when it collides with a hailstone.</p>
    * <p>In the example above, you can achieve this by moving to position 24, 13, 10 and throwing the rock at velocity -3, 1, 2. If you do this, you will hit every hailstone as follows:</p>
    * <pre>
    * Hailstone: 19, 13, 30 @ -2, 1, -2
    * Collision time: 5
    * Collision position: 9, 18, 20
    * 
    * Hailstone: 18, 19, 22 @ -1, -1, -2
    * Collision time: 3
    * Collision position: 15, 16, 16
    * 
    * Hailstone: 20, 25, 34 @ -2, -2, -4
    * Collision time: 4
    * Collision position: 12, 17, 18
    * 
    * Hailstone: 12, 31, 28 @ -1, -2, -1
    * Collision time: 6
    * Collision position: 6, 19, 22
    * 
    * Hailstone: 20, 19, 15 @ 1, -5, -3
    * Collision time: 1
    * Collision position: 21, 14, 12
    * </pre>
    * <p>Above, each hailstone is identified by its initial position and its velocity. Then, the time and position of that hailstone's collision with your rock are given.</p>
    * <p>After 1 nanosecond, the rock has <b>exactly the same position</b> as one of the hailstones, obliterating it into ice dust! Another hailstone is smashed to bits two nanoseconds after that. After a total of 6 nanoseconds, all of the hailstones have been destroyed.</p>
    * <p>So, at time 0, the rock needs to be at X position 24, Y position 13, and Z position 10. Adding these three coordinates together produces <b>47</b>. (Don't add any coordinates from the rock's velocity.)</p>
    * <p>Determine the exact position and velocity the rock needs to have at time 0 so that it perfectly collides with every hailstone. <b>What do you get if you add up the X, Y, and Z coordinates of that initial position?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.findRockPositionScore();
    }
}
