package dev.vinyard.adventofcode.soluce.year2024.day1;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@AdventOfCodeSolution(year = 2024, day = 1, part = 1, description = "Historian Hysteria", link = "https://adventofcode.com/2024/day/1", tags = "unsolved")
public class Day1Part1Solution implements Solution<Long> {

    /**
    * <h2>--- Day 1: Historian Hysteria ---</h2>
    * <p>The <b>Chief Historian</b> is always present for the big Christmas sleigh launch, but nobody has seen him in months! Last anyone heard, he was visiting locations that are historically significant to the North Pole; a group of Senior Historians has asked you to accompany them as they check the places they think he was most likely to visit.</p>
    * <p>As each location is checked, they will mark it on their list with a <b>star</b>. They figure the Chief Historian <b>must</b> be in one of the first fifty places they'll look, so in order to save Christmas, you need to help them get <b>fifty stars</b> on their list before Santa takes off on December 25th.</p>
    * <p>Collect stars by solving puzzles. Two puzzles will be made available on each day in the Advent calendar; the second puzzle is unlocked when you complete the first. Each puzzle grants <b>one star</b>. Good luck!</p>
    * <p>You haven't even left yet and the group of Elvish Senior Historians has already hit a problem: their list of locations to check is currently <b>empty</b>. Eventually, someone decides that the best place to check first would be the Chief Historian's office.</p>
    * <p>Upon pouring into the office, everyone confirms that the Chief Historian is indeed nowhere to be found. Instead, the Elves discover an assortment of notes and lists of historically significant locations! This seems to be the planning the Chief Historian was doing before he left. Perhaps these notes can be used to determine which locations to search?</p>
    * <p>Throughout the Chief's office, the historically significant locations are listed not by name but by a unique number called the <b>location ID</b>. To make sure they don't miss anything, The Historians split into two groups, each searching the office and trying to create their own complete list of location IDs.</p>
    * <p>There's just one problem: by holding the two lists up <b>side by side</b> (your puzzle input), it quickly becomes clear that the lists aren't very similar. Maybe you can help The Historians reconcile their lists?</p>
    * <p>For example:</p>
    * <pre>
    * 3   4
    * 4   3
    * 2   5
    * 1   3
    * 3   9
    * 3   3
    * </pre>
    * <p>Maybe the lists are only off by a small amount! To find out, pair up the numbers and measure how far apart they are. Pair up the <b>smallest number in the left list</b> with the <b>smallest number in the right list</b>, then the <b>second-smallest left number</b> with the <b>second-smallest right number</b>, and so on.</p>
    * <p>Within each pair, figure out <b>how far apart</b> the two numbers are; you'll need to <b>add up all of those distances</b>. For example, if you pair up a 3 from the left list with a 7 from the right list, the distance apart is 4; if you pair up a 9 with a 3, the distance apart is 6.</p>
    * <p>In the example list above, the pairs and distances would be as follows:</p>
    * <ul>
    *  <li>The smallest number in the left list is 1, and the smallest number in the right list is 3. The distance between them is <b>2</b>.</li>
    *  <li>The second-smallest number in the left list is 2, and the second-smallest number in the right list is another 3. The distance between them is <b>1</b>.</li>
    *  <li>The third-smallest number in both lists is 3, so the distance between them is <b>0</b>.</li>
    *  <li>The next numbers to pair up are 3 and 4, a distance of <b>1</b>.</li>
    *  <li>The fifth-smallest numbers in each list are 3 and 5, a distance of <b>2</b>.</li>
    *  <li>Finally, the largest number in the left list is 4, while the largest number in the right list is 9; these are a distance <b>5</b> apart.</li>
    * </ul>
    * <p>To find the <b>total distance</b> between the left list and the right list, add up the distances between all of the pairs you found. In the example above, this is 2 + 1 + 0 + 1 + 2 + 5, a total distance of <b>11</b>!</p>
    * <p>Your actual left and right lists contain many location IDs. <b>What is the total distance between your lists?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        LinkedList<Long> locationIdLeftList = root.left().stream().sorted().collect(Collectors.toCollection(LinkedList::new));
        LinkedList<Long> locationIdRightList = root.right().stream().sorted().collect(Collectors.toCollection(LinkedList::new));

        return LongStream.generate(() -> Math.abs(locationIdLeftList.poll() - locationIdRightList.poll()))
                .limit(Math.min(locationIdLeftList.size(), locationIdRightList.size()))
                .sum();
    }
}
