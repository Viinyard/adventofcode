package dev.vinyard.adventofcode.soluce.year2024.day15;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2024, day = 15, part = 1, description = "Warehouse Woes", link = "https://adventofcode.com/2024/day/15", tags = "unsolved")
public class Day15Part1Solution implements Solution<Object> {

    /**
    * <h2>--- Day 15: Warehouse Woes ---</h2>
    * <p>You appear back inside your own mini submarine! Each Historian drives their mini submarine in a different direction; maybe the Chief has his own submarine down here somewhere as well?</p>
    * <p>You look up to see a vast school of <a href="/2021/day/6">lanternfish</a> swimming past you. On closer inspection, they seem quite anxious, so you drive your mini submarine over to see if you can help.</p>
    * <p>Because lanternfish populations grow rapidly, they need a lot of food, and that food needs to be stored somewhere. That's why these lanternfish have built elaborate warehouse complexes operated by robots!</p>
    * <p>These lanternfish seem so anxious because they have lost control of the robot that operates one of their most important warehouses! It is currently running amok, pushing around boxes in the warehouse with no regard for lanternfish logistics <b>or</b> lanternfish inventory management strategies.</p>
    * <p>Right now, none of the lanternfish are brave enough to swim up to an unpredictable robot so they could shut it off. However, if you could anticipate the robot's movements, maybe they could find a safe option.</p>
    * <p>The lanternfish already have a map of the warehouse and a list of movements the robot will <b>attempt</b> to make (your puzzle input). The problem is that the movements will sometimes fail as boxes are shifted around, making the actual movements of the robot difficult to predict.</p>
    * <p>For example:</p>
    * <pre>
    * ##########
    * #..O..O.O#
    * #......O.#
    * #.OO..O.O#
    * #..O@..O.#
    * #O#..O...#
    * #O..O..O.#
    * #.OO.O.OO#
    * #....O...#
    * ##########
    * 
    * &lt;vv&gt;^&lt;v^&gt;v&gt;^vv^v&gt;v&lt;&gt;v^v&lt;v&lt;^vv&lt;&lt;&lt;^&gt;&lt;&lt;&gt;&lt;&gt;&gt;v&lt;vvv&lt;&gt;^v^&gt;^&lt;&lt;&lt;&gt;&lt;&lt;v&lt;&lt;&lt;v^vv^v&gt;^
    * vvv&lt;&lt;^&gt;^v^^&gt;&lt;&lt;&gt;&gt;&gt;&lt;&gt;^&lt;&lt;&gt;&lt;^vv^^&lt;&gt;vvv&lt;&gt;&gt;&lt;^^v&gt;^&gt;vv&lt;&gt;v&lt;&lt;&lt;&lt;v&lt;^v&gt;^&lt;^^&gt;&gt;&gt;^&lt;v&lt;v
    * &gt;&lt;&gt;vv&gt;v^v^&lt;&gt;&gt;&lt;&gt;&gt;&gt;&gt;&lt;^^&gt;vv&gt;v&lt;^^^&gt;&gt;v^v^&lt;^^&gt;v^^&gt;v^&lt;^v&gt;v&lt;&gt;&gt;v^v^&lt;v&gt;v^^&lt;^^vv&lt;
    * &lt;&lt;v&lt;^&gt;&gt;^^^^&gt;&gt;&gt;v^&lt;&gt;vvv^&gt;&lt;v&lt;&lt;&lt;&gt;^^^vv^&lt;vvv&gt;^&gt;v&lt;^^^^v&lt;&gt;^&gt;vvvv&gt;&lt;&gt;&gt;v^&lt;&lt;^^^^^
    * ^&gt;&lt;^&gt;&lt;&gt;&gt;&gt;&lt;&gt;^^&lt;&lt;^^v&gt;&gt;&gt;&lt;^&lt;v&gt;^&lt;vv&gt;&gt;v&gt;&gt;&gt;^v&gt;&lt;&gt;^v&gt;&lt;&lt;&lt;&lt;v&gt;&gt;v&lt;v&lt;v&gt;vvv&gt;^&lt;&gt;&lt;&lt;&gt;^&gt;&lt;
    * ^&gt;&gt;&lt;&gt;^v&lt;&gt;&lt;^vvv&lt;^^&lt;&gt;&lt;v&lt;&lt;&lt;&lt;&lt;&gt;&lt;^v&lt;&lt;&lt;&gt;&lt;&lt;&lt;^^&lt;v&lt;^^^&gt;&lt;^&gt;&gt;^&lt;v^&gt;&lt;&lt;&lt;^&gt;&gt;^v&lt;v^v&lt;v^
    * &gt;^&gt;&gt;^v&gt;vv&gt;^&lt;&lt;^v&lt;&gt;&gt;&lt;&lt;&gt;&lt;&lt;v&lt;&lt;v&gt;&lt;&gt;v&lt;^vv&lt;&lt;&lt;&gt;^^v^&gt;^^&gt;&gt;&gt;&lt;&lt;^v&gt;&gt;v^v&gt;&lt;^^&gt;&gt;^&lt;&gt;vv^
    * &lt;&gt;&lt;^^&gt;^^^&lt;&gt;&lt;vvvvv^v&lt;v&lt;&lt;&gt;^v&lt;v&gt;v&lt;&lt;^&gt;&lt;&lt;&gt;&lt;&lt;&gt;&lt;&lt;&lt;^^&lt;&lt;&lt;^&lt;&lt;&gt;&gt;&lt;&lt;&gt;&lt;^^^&gt;^^&lt;&gt;^&gt;v&lt;&gt;
    * ^^&gt;vv&lt;^v^v&lt;vv&gt;^&lt;&gt;&lt;v&lt;^v&gt;^^^&gt;&gt;&gt;^^vvv^&gt;vvv&lt;&gt;&gt;&gt;^&lt;^&gt;&gt;&gt;&gt;&gt;^&lt;&lt;^v&gt;^vvv&lt;&gt;^&lt;&gt;&lt;&lt;v&gt;
    * v^^&gt;&gt;&gt;&lt;&lt;^^&lt;&gt;&gt;^v^&lt;v^vv&lt;&gt;v^&lt;&lt;&gt;^&lt;^v^v&gt;&lt;^&lt;&lt;&lt;&gt;&lt;&lt;^&lt;v&gt;&lt;v&lt;&gt;vv&gt;&gt;v&gt;&lt;v^&lt;vv&lt;&gt;v^&lt;&lt;^
    * </pre>
    * <p>As the robot (@) attempts to move, if there are any boxes (O) in the way, the robot will also attempt to push those boxes. However, if this action would cause the robot or a box to move into a wall (#), nothing moves instead, including the robot. The initial positions of these are shown on the map at the top of the document the lanternfish gave you.</p>
    * <p>The rest of the document describes the <b>moves</b> (^ for up, v for down, &lt; for left, &gt; for right) that the robot will attempt to make, in order. (The moves form a single giant sequence; they are broken into multiple lines just to make copy-pasting easier. Newlines within the move sequence should be ignored.)</p>
    * <p>Here is a smaller example to get started:</p>
    * <pre>
    * ########
    * #..O.O.#
    * ##@.O..#
    * #...O..#
    * #.#.O..#
    * #...O..#
    * #......#
    * ########
    * 
    * &lt;^^&gt;&gt;&gt;vv&lt;v&gt;&gt;v&lt;&lt;
    * </pre>
    * <p>Were the robot to attempt the given sequence of moves, it would push around the boxes as follows:</p>
    * <pre>
    * Initial state:
    * ########
    * #..O.O.#
    * ##<b>@</b>.O..#
    * #...O..#
    * #.#.O..#
    * #...O..#
    * #......#
    * ########
    * 
    * Move &lt;:
    * ########
    * #..O.O.#
    * ##<b>@</b>.O..#
    * #...O..#
    * #.#.O..#
    * #...O..#
    * #......#
    * ########
    * 
    * Move ^:
    * ########
    * #.<b>@</b>O.O.#
    * ##..O..#
    * #...O..#
    * #.#.O..#
    * #...O..#
    * #......#
    * ########
    * 
    * Move ^:
    * ########
    * #.<b>@</b>O.O.#
    * ##..O..#
    * #...O..#
    * #.#.O..#
    * #...O..#
    * #......#
    * ########
    * 
    * Move &gt;:
    * ########
    * #..<b>@</b>OO.#
    * ##..O..#
    * #...O..#
    * #.#.O..#
    * #...O..#
    * #......#
    * ########
    * 
    * Move &gt;:
    * ########
    * #...<b>@</b>OO#
    * ##..O..#
    * #...O..#
    * #.#.O..#
    * #...O..#
    * #......#
    * ########
    * 
    * Move &gt;:
    * ########
    * #...<b>@</b>OO#
    * ##..O..#
    * #...O..#
    * #.#.O..#
    * #...O..#
    * #......#
    * ########
    * 
    * Move v:
    * ########
    * #....OO#
    * ##..<b>@</b>..#
    * #...O..#
    * #.#.O..#
    * #...O..#
    * #...O..#
    * ########
    * 
    * Move v:
    * ########
    * #....OO#
    * ##..<b>@</b>..#
    * #...O..#
    * #.#.O..#
    * #...O..#
    * #...O..#
    * ########
    * 
    * Move &lt;:
    * ########
    * #....OO#
    * ##.<b>@</b>...#
    * #...O..#
    * #.#.O..#
    * #...O..#
    * #...O..#
    * ########
    * 
    * Move v:
    * ########
    * #....OO#
    * ##.....#
    * #..<b>@</b>O..#
    * #.#.O..#
    * #...O..#
    * #...O..#
    * ########
    * 
    * Move &gt;:
    * ########
    * #....OO#
    * ##.....#
    * #...<b>@</b>O.#
    * #.#.O..#
    * #...O..#
    * #...O..#
    * ########
    * 
    * Move &gt;:
    * ########
    * #....OO#
    * ##.....#
    * #....<b>@</b>O#
    * #.#.O..#
    * #...O..#
    * #...O..#
    * ########
    * 
    * Move v:
    * ########
    * #....OO#
    * ##.....#
    * #.....O#
    * #.#.O<b>@</b>.#
    * #...O..#
    * #...O..#
    * ########
    * 
    * Move &lt;:
    * ########
    * #....OO#
    * ##.....#
    * #.....O#
    * #.#O<b>@</b>..#
    * #...O..#
    * #...O..#
    * ########
    * 
    * Move &lt;:
    * ########
    * #....OO#
    * ##.....#
    * #.....O#
    * #.#O<b>@</b>..#
    * #...O..#
    * #...O..#
    * ########
    * </pre>
    * <p>The larger example has many more moves; after the robot has finished those moves, the warehouse would look like this:</p>
    * <pre>
    * ##########
    * #.O.O.OOO#
    * #........#
    * #OO......#
    * #OO<b>@</b>.....#
    * #O#.....O#
    * #O.....OO#
    * #O.....OO#
    * #OO....OO#
    * ##########
    * </pre>
    * <p>The lanternfish use their own custom Goods Positioning System (GPS for short) to track the locations of the boxes. The <b>GPS coordinate</b> of a box is equal to 100 times its distance from the top edge of the map plus its distance from the left edge of the map. (This process does not stop at wall tiles; measure all the way to the edges of the map.)</p>
    * <p>So, the box shown below has a distance of 1 from the top edge of the map and 4 from the left edge of the map, resulting in a GPS coordinate of 100 * 1 + 4 = 104.</p>
    * <pre>
    * #######
    * #...O..
    * #......
    * </pre>
    * <p>The lanternfish would like to know the <b>sum of all boxes' GPS coordinates</b> after the robot finishes moving. In the larger example, the sum of all boxes' GPS coordinates is <b>10092</b>. In the smaller example, the sum is <b>2028</b>.</p>
    * <p>Predict the motion of the robot and boxes in the warehouse. After the robot is finished moving, <b>what is the sum of all boxes' GPS coordinates?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        System.out.println(root.warehouse());

        ASD.Player player = root.warehouse().findPlayer().orElseThrow(() -> new IllegalStateException("Player not found"));

        root.moves().forEach(direction -> player.move(root.warehouse(), direction));

        System.out.println(root.warehouse());

        return root.warehouse().getScore();
    }
}
