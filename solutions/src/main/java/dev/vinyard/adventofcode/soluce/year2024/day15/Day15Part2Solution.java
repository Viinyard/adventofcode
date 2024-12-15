package dev.vinyard.adventofcode.soluce.year2024.day15;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.awt.*;

@AdventOfCodeSolution(year = 2024, day = 15, part = 2, description = "Warehouse Woes", link = "https://adventofcode.com/2024/day/15", tags = "unsolved")
public class Day15Part2Solution implements Solution<Object> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>The lanternfish use your information to find a safe moment to swim in and turn off the malfunctioning robot! Just as they start preparing a festival in your honor, reports start coming in that a <b>second</b> warehouse's robot is <b>also</b> malfunctioning.</p>
    * <p>This warehouse's layout is surprisingly similar to the one you just helped. There is one key difference: everything except the robot is <b>twice as wide</b>! The robot's list of movements doesn't change.</p>
    * <p>To get the wider warehouse's map, start with your original map and, for each tile, make the following changes:</p>
    * <ul>
    *  <li>If the tile is #, the new map contains ## instead.</li>
    *  <li>If the tile is O, the new map contains [] instead.</li>
    *  <li>If the tile is ., the new map contains .. instead.</li>
    *  <li>If the tile is @, the new map contains @. instead.</li>
    * </ul>
    * <p>This will produce a new warehouse map which is twice as wide and with wide boxes that are represented by []. (The robot does not change size.)</p>
    * <p>The larger example from before would now look like this:</p>
    * <pre>
    * ####################
    * ##....[]....[]..[]##
    * ##............[]..##
    * ##..[][]....[]..[]##
    * ##....[]@.....[]..##
    * ##[]##....[]......##
    * ##[]....[]....[]..##
    * ##..[][]..[]..[][]##
    * ##........[]......##
    * ####################
    * </pre>
    * <p>Because boxes are now twice as wide but the robot is still the same size and speed, boxes can be aligned such that they directly push two other boxes at once. For example, consider this situation:</p>
    * <pre>
    * #######
    * #...#.#
    * #.....#
    * #..OO@#
    * #..O..#
    * #.....#
    * #######
    * 
    * &lt;vv&lt;&lt;^^&lt;&lt;^^
    * </pre>
    * <p>After appropriately resizing this map, the robot would push around these boxes as follows:</p>
    * <pre>
    * Initial state:
    * ##############
    * ##......##..##
    * ##..........##
    * ##....[][]<b>@</b>.##
    * ##....[]....##
    * ##..........##
    * ##############
    * 
    * Move &lt;:
    * ##############
    * ##......##..##
    * ##..........##
    * ##...[][]<b>@</b>..##
    * ##....[]....##
    * ##..........##
    * ##############
    * 
    * Move v:
    * ##############
    * ##......##..##
    * ##..........##
    * ##...[][]...##
    * ##....[].<b>@</b>..##
    * ##..........##
    * ##############
    * 
    * Move v:
    * ##############
    * ##......##..##
    * ##..........##
    * ##...[][]...##
    * ##....[]....##
    * ##.......<b>@</b>..##
    * ##############
    * 
    * Move &lt;:
    * ##############
    * ##......##..##
    * ##..........##
    * ##...[][]...##
    * ##....[]....##
    * ##......<b>@</b>...##
    * ##############
    * 
    * Move &lt;:
    * ##############
    * ##......##..##
    * ##..........##
    * ##...[][]...##
    * ##....[]....##
    * ##.....<b>@</b>....##
    * ##############
    * 
    * Move ^:
    * ##############
    * ##......##..##
    * ##...[][]...##
    * ##....[]....##
    * ##.....<b>@</b>....##
    * ##..........##
    * ##############
    * 
    * Move ^:
    * ##############
    * ##......##..##
    * ##...[][]...##
    * ##....[]....##
    * ##.....<b>@</b>....##
    * ##..........##
    * ##############
    * 
    * Move &lt;:
    * ##############
    * ##......##..##
    * ##...[][]...##
    * ##....[]....##
    * ##....<b>@</b>.....##
    * ##..........##
    * ##############
    * 
    * Move &lt;:
    * ##############
    * ##......##..##
    * ##...[][]...##
    * ##....[]....##
    * ##...<b>@</b>......##
    * ##..........##
    * ##############
    * 
    * Move ^:
    * ##############
    * ##......##..##
    * ##...[][]...##
    * ##...<b>@</b>[]....##
    * ##..........##
    * ##..........##
    * ##############
    * 
    * Move ^:
    * ##############
    * ##...[].##..##
    * ##...<b>@</b>.[]...##
    * ##....[]....##
    * ##..........##
    * ##..........##
    * ##############
    * </pre>
    * <p>This warehouse also uses GPS to locate the boxes. For these larger boxes, distances are measured from the edge of the map to the closest edge of the box in question. So, the box shown below has a distance of 1 from the top edge of the map and 5 from the left edge of the map, resulting in a GPS coordinate of 100 * 1 + 5 = 105.</p>
    * <pre>
    * ##########
    * ##...[]...
    * ##........
    * </pre>
    * <p>In the scaled-up version of the larger example from above, after the robot has finished all of its moves, the warehouse would look like this:</p>
    * <pre>
    * ####################
    * ##[].......[].[][]##
    * ##[]...........[].##
    * ##[]........[][][]##
    * ##[]......[]....[]##
    * ##..##......[]....##
    * ##..[]............##
    * ##..<b>@</b>......[].[][]##
    * ##......[][]..[]..##
    * ####################
    * </pre>
    * <p>The sum of these boxes' GPS coordinates is <b>9021</b>.</p>
    * <p>Predict the motion of the robot and boxes in this new, scaled-up warehouse. <b>What is the sum of all boxes' final GPS coordinates?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root(new Dimension(2, 1)).out;

        ASD.Player player = root.warehouse().findPlayer().orElseThrow(() -> new IllegalStateException("Player not found"));

        root.moves().forEach(direction -> player.move(root.warehouse(), direction));

        return root.warehouse().getScore();
    }
}
