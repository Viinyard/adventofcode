package dev.vinyard.adventofcode.soluce.year2023.day6;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;


@AdventOfCodeSolution(year = 2023, day = 6, part = 1, description = "Wait For It", link = "https://adventofcode.com/2023/day/6", tags = "unsolved")
public class Day6Part1Solution implements Solution<Long> {

    /**
     * <h2>--- Day 6: Wait For It ---</h2>
     * <p>The ferry quickly brings you across Island Island. After asking around, you discover that there is indeed normally a large pile of sand somewhere near here, but you don't see anything besides lots of water and the small island where the ferry has docked.</p>
     * <p>As you try to figure out what to do next, you notice a poster on a wall near the ferry dock. "Boat races! Open to the public! Grand prize is an all-expenses-paid trip to <b>Desert Island</b>!" That must be where the sand comes from! Best of all, the boat races are starting in just a few minutes.</p>
     * <p>You manage to sign up as a competitor in the boat races just in time. The organizer explains that it's not really a traditional race - instead, you will get a fixed amount of time during which your boat has to travel as far as it can, and you win if your boat goes the farthest.</p>
     * <p>As part of signing up, you get a sheet of paper (your puzzle input) that lists the <b>time</b> allowed for each race and also the best <b>distance</b> ever recorded in that race. To guarantee you win the grand prize, you need to make sure you <b>go farther in each race</b> than the current record holder.</p>
     * <p>The organizer brings you over to the area where the boat races are held. The boats are much smaller than you expected - they're actually <b>toy boats</b>, each with a big button on top. Holding down the button <b>charges the boat</b>, and releasing the button <b>allows the boat to move</b>. Boats move faster if their button was held longer, but time spent holding the button counts against the total race time. You can only hold the button at the start of the race, and boats don't move until the button is released.</p>
     * <p>For example:</p>
     * <pre>
     * Time:      7  15   30
     * Distance:  9  40  200
     * </pre>
     * <p>This document describes three races:</p>
     * <ul>
     *  <li>The first race lasts 7 milliseconds. The record distance in this race is 9 millimeters.</li>
     *  <li>The second race lasts 15 milliseconds. The record distance in this race is 40 millimeters.</li>
     *  <li>The third race lasts 30 milliseconds. The record distance in this race is 200 millimeters.</li>
     * </ul>
     * <p>Your toy boat has a starting speed of <b>zero millimeters per millisecond</b>. For each whole millisecond you spend at the beginning of the race holding down the button, the boat's speed increases by <b>one millimeter per millisecond</b>.</p>
     * <p>So, because the first race lasts 7 milliseconds, you only have a few options:</p>
     * <ul>
     *  <li>Don't hold the button at all (that is, hold it for <b>0 milliseconds</b>) at the start of the race. The boat won't move; it will have traveled <b>0 millimeters</b> by the end of the race.</li>
     *  <li>Hold the button for <b>1 millisecond</b> at the start of the race. Then, the boat will travel at a speed of 1 millimeter per millisecond for 6 milliseconds, reaching a total distance traveled of <b>6 millimeters</b>.</li>
     *  <li>Hold the button for <b>2 milliseconds</b>, giving the boat a speed of 2 millimeters per millisecond. It will then get 5 milliseconds to move, reaching a total distance of <b>10 millimeters</b>.</li>
     *  <li>Hold the button for <b>3 milliseconds</b>. After its remaining 4 milliseconds of travel time, the boat will have gone <b>12 millimeters</b>.</li>
     *  <li>Hold the button for <b>4 milliseconds</b>. After its remaining 3 milliseconds of travel time, the boat will have gone <b>12 millimeters</b>.</li>
     *  <li>Hold the button for <b>5 milliseconds</b>, causing the boat to travel a total of <b>10 millimeters</b>.</li>
     *  <li>Hold the button for <b>6 milliseconds</b>, causing the boat to travel a total of <b>6 millimeters</b>.</li>
     *  <li>Hold the button for <b>7 milliseconds</b>. That's the entire duration of the race. You never let go of the button. The boat can't move until you let go of the button. Please make sure you let go of the button so the boat gets to move. <b>0 millimeters</b>.</li>
     * </ul>
     * <p>Since the current record for this race is 9 millimeters, there are actually <b>4</b> different ways you could win: you could hold the button for 2, 3, 4, or 5 milliseconds at the start of the race.</p>
     * <p>In the second race, you could hold the button for at least 4 milliseconds and at most 11 milliseconds and beat the record, a total of <b>8</b> different ways to win.</p>
     * <p>In the third race, you could hold the button for at least 11 milliseconds and no more than 19 milliseconds and still beat the record, a total of <b>9</b> ways you could win.</p>
     * <p>To see how much margin of error you have, determine the <b>number of ways you can beat the record</b> in each race; in this example, if you multiply these values together, you get <b>288</b> (4 * 8 * 9).</p>
     * <p>Determine the number of ways you could beat the record in each race. <b>What do you get if you multiply these numbers together?</b></p>
     */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);
        ASD.Races races = parser.races().out;

        return races.getRaces().stream().mapToLong(ASD.Race::getNumberOfWaysToBeatTheDistance).reduce(1, (a, b) -> a * b);
    }
}
