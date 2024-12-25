package dev.vinyard.adventofcode.soluce.year2024.day21;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2024, day = 21, part = 1, description = "Keypad Conundrum", link = "https://adventofcode.com/2024/day/21", tags = "unsolved")
public class Day21Part1Solution implements Solution<Object> {

    /**
    * <h2>--- Day 21: Keypad Conundrum ---</h2>
    * <p>As you teleport onto Santa's <a href="/2019/day/25">Reindeer-class starship</a>, The Historians begin to panic: someone from their search party is <b>missing</b>. A quick life-form scan by the ship's computer reveals that when the missing Historian teleported, he arrived in another part of the ship.</p>
    * <p>The door to that area is locked, but the computer can't open it; it can only be opened by <b>physically typing</b> the door codes (your puzzle input) on the numeric keypad on the door.</p>
    * <p>The numeric keypad has four rows of buttons: 789, 456, 123, and finally an empty gap followed by 0A. Visually, they are arranged like this:</p>
    * <pre>
    * +---+---+---+
    * | 7 | 8 | 9 |
    * +---+---+---+
    * | 4 | 5 | 6 |
    * +---+---+---+
    * | 1 | 2 | 3 |
    * +---+---+---+
    *     | 0 | A |
    *     +---+---+
    * </pre>
    * <p>Unfortunately, the area outside the door is currently <b>depressurized</b> and nobody can go near the door. A robot needs to be sent instead.</p>
    * <p>The robot has no problem navigating the ship and finding the numeric keypad, but it's not designed for button pushing: it can't be told to push a specific button directly. Instead, it has a robotic arm that can be controlled remotely via a <b>directional keypad</b>.</p>
    * <p>The directional keypad has two rows of buttons: a gap / ^ (up) / A (activate) on the first row and &lt; (left) / v (down) / &gt; (right) on the second row. Visually, they are arranged like this:</p>
    * <pre>
    *     +---+---+
    *     | ^ | A |
    * +---+---+---+
    * | &lt; | v | &gt; |
    * +---+---+---+
    * </pre>
    * <p>When the robot arrives at the numeric keypad, its robotic arm is pointed at the A button in the bottom right corner. After that, this directional keypad remote control must be used to maneuver the robotic arm: the up / down / left / right buttons cause it to move its arm one button in that direction, and the A button causes the robot to briefly move forward, pressing the button being aimed at by the robotic arm.</p>
    * <p>For example, to make the robot type 029A on the numeric keypad, one sequence of inputs on the directional keypad you could use is:</p>
    * <ul>
    *  <li>&lt; to move the arm from A (its initial position) to 0.</li>
    *  <li>A to push the 0 button.</li>
    *  <li>^A to move the arm to the 2 button and push it.</li>
    *  <li>&gt;^^A to move the arm to the 9 button and push it.</li>
    *  <li>vvvA to move the arm to the A button and push it.</li>
    * </ul>
    * <p>In total, there are three shortest possible sequences of button presses on this directional keypad that would cause the robot to type 029A: &lt;A^A&gt;^^AvvvA, &lt;A^A^&gt;^AvvvA, and &lt;A^A^^&gt;AvvvA.</p>
    * <p>Unfortunately, the area containing this directional keypad remote control is currently experiencing <b>high levels of radiation</b> and nobody can go near it. A robot needs to be sent instead.</p>
    * <p>When the robot arrives at the directional keypad, its robot arm is pointed at the A button in the upper right corner. After that, a <b>second, different</b> directional keypad remote control is used to control this robot (in the same way as the first robot, except that this one is typing on a directional keypad instead of a numeric keypad).</p>
    * <p>There are multiple shortest possible sequences of directional keypad button presses that would cause this robot to tell the first robot to type 029A on the door. One such sequence is v&lt;&lt;A&gt;&gt;^A&lt;A&gt;AvA&lt;^AA&gt;A&lt;vAAA&gt;^A.</p>
    * <p>Unfortunately, the area containing this second directional keypad remote control is currently <b>-40 degrees</b>! Another robot will need to be sent to type on that directional keypad, too.</p>
    * <p>There are many shortest possible sequences of directional keypad button presses that would cause this robot to tell the second robot to tell the first robot to eventually type 029A on the door. One such sequence is &lt;vA&lt;AA&gt;&gt;^AvAA&lt;^A&gt;A&lt;v&lt;A&gt;&gt;^AvA^A&lt;vA&gt;^A&lt;v&lt;A&gt;^A&gt;AAvA^A&lt;v&lt;A&gt;A&gt;^AAAvA&lt;^A&gt;A.</p>
    * <p>Unfortunately, the area containing this third directional keypad remote control is currently <b>full of Historians</b>, so no robots can find a clear path there. Instead, <b>you</b> will have to type this sequence yourself.</p>
    * <p>Were you to choose this sequence of button presses, here are all of the buttons that would be pressed on your directional keypad, the two robots' directional keypads, and the numeric keypad:</p>
    * <pre>
    * &lt;vA&lt;AA&gt;&gt;^AvAA&lt;^A&gt;A&lt;v&lt;A&gt;&gt;^AvA^A&lt;vA&gt;^A&lt;v&lt;A&gt;^A&gt;AAvA^A&lt;v&lt;A&gt;A&gt;^AAAvA&lt;^A&gt;A
    * v&lt;&lt;A&gt;&gt;^A&lt;A&gt;AvA&lt;^AA&gt;A&lt;vAAA&gt;^A
    * &lt;A^A&gt;^^AvvvA
    * 029A
    * </pre>
    * <p>In summary, there are the following keypads:</p>
    * <ul>
    *  <li>One directional keypad that <b>you</b> are using.</li>
    *  <li>Two directional keypads that <b>robots</b> are using.</li>
    *  <li>One numeric keypad (on a door) that a <b>robot</b> is using.</li>
    * </ul>
    * <p>It is important to remember that these robots are not designed for button pushing. In particular, if a robot arm is ever aimed at a <b>gap</b> where no button is present on the keypad, even for an instant, the robot will <b>panic</b> unrecoverably. So, don't do that. All robots will initially aim at the keypad's A key, wherever it is.</p>
    * <p>To unlock the door, <b>five</b> codes will need to be typed on its numeric keypad. For example:</p>
    * <pre>
    * 029A
    * 980A
    * 179A
    * 456A
    * 379A
    * </pre>
    * <p>For each of these, here is a shortest sequence of button presses you could type to cause the desired code to be typed on the numeric keypad:</p>
    * <pre>
    * 029A: &lt;vA&lt;AA&gt;&gt;^AvAA&lt;^A&gt;A&lt;v&lt;A&gt;&gt;^AvA^A&lt;vA&gt;^A&lt;v&lt;A&gt;^A&gt;AAvA^A&lt;v&lt;A&gt;A&gt;^AAAvA&lt;^A&gt;A
    * 980A: &lt;v&lt;A&gt;&gt;^AAAvA^A&lt;vA&lt;AA&gt;&gt;^AvAA&lt;^A&gt;A&lt;v&lt;A&gt;A&gt;^AAAvA&lt;^A&gt;A&lt;vA&gt;^A&lt;A&gt;A
    * 179A: &lt;v&lt;A&gt;&gt;^A&lt;vA&lt;A&gt;&gt;^AAvAA&lt;^A&gt;A&lt;v&lt;A&gt;&gt;^AAvA^A&lt;vA&gt;^AA&lt;A&gt;A&lt;v&lt;A&gt;A&gt;^AAAvA&lt;^A&gt;A
    * 456A: &lt;v&lt;A&gt;&gt;^AA&lt;vA&lt;A&gt;&gt;^AAvAA&lt;^A&gt;A&lt;vA&gt;^A&lt;A&gt;A&lt;vA&gt;^A&lt;A&gt;A&lt;v&lt;A&gt;A&gt;^AAvA&lt;^A&gt;A
    * 379A: &lt;v&lt;A&gt;&gt;^AvA^A&lt;vA&lt;AA&gt;&gt;^AAvA&lt;^A&gt;AAvA^A&lt;vA&gt;^AA&lt;A&gt;A&lt;v&lt;A&gt;A&gt;^AAAvA&lt;^A&gt;A
    * </pre>
    * <p>The Historians are getting nervous; the ship computer doesn't remember whether the missing Historian is trapped in the area containing a <b>giant electromagnet</b> or <b>molten lava</b>. You'll need to make sure that for each of the five codes, you find the <b>shortest sequence</b> of button presses necessary.</p>
    * <p>The <b>complexity</b> of a single code (like 029A) is equal to the result of multiplying these two values:</p>
    * <ul>
    *  <li>The <b>length of the shortest sequence</b> of button presses you need to type on your directional keypad in order to cause the code to be typed on the numeric keypad; for 029A, this would be 68.</li>
    *  <li>The <b>numeric part of the code</b> (ignoring leading zeroes); for 029A, this would be 29.</li>
    * </ul>
    * <p>In the above example, complexity of the five codes can be found by calculating 68 * 29, 60 * 980, 68 * 179, 64 * 456, and 64 * 379. Adding these together produces <b>126384</b>.</p>
    * <p>Find the fewest number of button presses you'll need to perform in order to cause the robot in front of the door to type each code. <b>What is the sum of the complexities of the five codes on your list?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.calculateComplexity();
    }
}
