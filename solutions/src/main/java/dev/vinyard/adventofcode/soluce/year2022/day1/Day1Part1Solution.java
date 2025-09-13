package dev.vinyard.adventofcode.soluce.year2022.day1;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2022, day = 1, part = 1, description = "Calorie Counting", link = "https://adventofcode.com/2022/day/1", tags = "unsolved")
public class Day1Part1Solution implements Solution<Long> {

    /**
    * <h2>--- Day 1: Calorie Counting ---</h2>
    * <p>Santa's reindeer typically eat regular reindeer food, but they need a lot of <a href="/2018/day/25">magical energy</a> to deliver presents on Christmas. For that, their favorite snack is a special type of <b>star</b> fruit that only grows deep in the jungle. The Elves have brought you on their annual expedition to the grove where the fruit grows.</p>
    * <p>To supply enough magical energy, the expedition needs to retrieve a minimum of <b>fifty stars</b> by December 25th. Although the Elves assure you that the grove has plenty of fruit, you decide to grab any fruit you see along the way, just in case.</p>
    * <p>Collect stars by solving puzzles. Two puzzles will be made available on each day in the Advent calendar; the second puzzle is unlocked when you complete the first. Each puzzle grants <b>one star</b>. Good luck!</p>
    * <p>The jungle must be too overgrown and difficult to navigate in vehicles or access from the air; the Elves' expedition traditionally goes on foot. As your boats approach land, the Elves begin taking inventory of their supplies. One important consideration is food - in particular, the number of <b>Calories</b> each Elf is carrying (your puzzle input).</p>
    * <p>The Elves take turns writing down the number of Calories contained by the various meals, snacks, rations, etc. that they've brought with them, one item per line. Each Elf separates their own inventory from the previous Elf's inventory (if any) by a blank line.</p>
    * <p>For example, suppose the Elves finish writing their items' Calories and end up with the following list:</p>
    * <pre>
    * 1000
    * 2000
    * 3000
    * 
    * 4000
    * 
    * 5000
    * 6000
    * 
    * 7000
    * 8000
    * 9000
    * 
    * 10000
    * </pre>
    * <p>This list represents the Calories of the food carried by five Elves:</p>
    * <ul>
    *  <li>The first Elf is carrying food with 1000, 2000, and 3000 Calories, a total of <b>6000</b> Calories.</li>
    *  <li>The second Elf is carrying one food item with <b>4000</b> Calories.</li>
    *  <li>The third Elf is carrying food with 5000 and 6000 Calories, a total of <b>11000</b> Calories.</li>
    *  <li>The fourth Elf is carrying food with 7000, 8000, and 9000 Calories, a total of <b>24000</b> Calories.</li>
    *  <li>The fifth Elf is carrying one food item with <b>10000</b> Calories.</li>
    * </ul>
    * <p>In case the Elves get hungry and need extra snacks, they need to know which Elf to ask: they'd like to know how many Calories are being carried by the Elf carrying the <b>most</b> Calories. In the example above, this is <b>24000</b> (carried by the fourth Elf).</p>
    * <p>Find the Elf carrying the most Calories. <b>How many total Calories is that Elf carrying?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.part1();
    }
}
