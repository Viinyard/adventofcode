package dev.vinyard.adventofcode.soluce.year2024.day11;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import dev.vinyard.adventofcode.utils.FileReader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.net.URISyntaxException;

@AdventOfCodeSolution(year = 2024, day = 11, part = 1, description = "Plutonian Pebbles", link = "https://adventofcode.com/2024/day/11", tags = "unsolved")
public class Day11Part1Solution implements Solution<Object> {

    /**
    * <h2>--- Day 11: Plutonian Pebbles ---</h2>
    * <p>The ancient civilization on <a href="/2019/day/20">Pluto</a> was known for its ability to manipulate spacetime, and while The Historians explore their infinite corridors, you've noticed a strange set of physics-defying stones.</p>
    * <p>At first glance, they seem like normal stones: they're arranged in a perfectly <b>straight line</b>, and each stone has a <b>number</b> engraved on it.</p>
    * <p>The strange part is that every time you blink, the stones <b>change</b>.</p>
    * <p>Sometimes, the number engraved on a stone changes. Other times, a stone might <b>split in two</b>, causing all the other stones to shift over a bit to make room in their perfectly straight line.</p>
    * <p>As you observe them for a while, you find that the stones have a consistent behavior. Every time you blink, the stones each <b>simultaneously</b> change according to the <b>first applicable rule</b> in this list:</p>
    * <ul>
    *  <li>If the stone is engraved with the number 0, it is replaced by a stone engraved with the number 1.</li>
    *  <li>If the stone is engraved with a number that has an <b>even</b> number of digits, it is replaced by <b>two stones</b>. The left half of the digits are engraved on the new left stone, and the right half of the digits are engraved on the new right stone. (The new numbers don't keep extra leading zeroes: 1000 would become stones 10 and 0.)</li>
    *  <li>If none of the other rules apply, the stone is replaced by a new stone; the old stone's number <b>multiplied by 2024</b> is engraved on the new stone.</li>
    * </ul>
    * <p>No matter how the stones change, their <b>order is preserved</b>, and they stay on their perfectly straight line.</p>
    * <p>How will the stones evolve if you keep blinking at them? You take a note of the number engraved on each stone in the line (your puzzle input).</p>
    * <p>If you have an arrangement of five stones engraved with the numbers 0 1 10 99 999 and you blink once, the stones transform as follows:</p>
    * <ul>
    *  <li>The first stone, 0, becomes a stone marked 1.</li>
    *  <li>The second stone, 1, is multiplied by 2024 to become 2024.</li>
    *  <li>The third stone, 10, is split into a stone marked 1 followed by a stone marked 0.</li>
    *  <li>The fourth stone, 99, is split into two stones marked 9.</li>
    *  <li>The fifth stone, 999, is replaced by a stone marked 2021976.</li>
    * </ul>
    * <p>So, after blinking once, your five stones would become an arrangement of seven stones engraved with the numbers 1 2024 1 0 9 9 2021976.</p>
    * <p>Here is a longer example:</p>
    * <pre>
    * Initial arrangement:
    * 125 17
    * 
    * After 1 blink:
    * 253000 1 7
    * 
    * After 2 blinks:
    * 253 0 2024 14168
    * 
    * After 3 blinks:
    * 512072 1 20 24 28676032
    * 
    * After 4 blinks:
    * 512 72 2024 2 0 2 4 2867 6032
    * 
    * After 5 blinks:
    * 1036288 7 2 20 24 4048 1 4048 8096 28 67 60 32
    * 
    * After 6 blinks:
    * 2097446912 14168 4048 2 0 2 4 40 48 2024 40 48 80 96 2 8 6 7 6 0 3 2
    * </pre>
    * <p>In this example, after blinking six times, you would have 22 stones. After blinking 25 times, you would have <b>55312</b> stones!</p>
    * <p>Consider the arrangement of stones in front of you. <b>How many stones will you have after blinking 25 times?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);
        // TODO get the ASD from the parser

        return null;
    }
}
