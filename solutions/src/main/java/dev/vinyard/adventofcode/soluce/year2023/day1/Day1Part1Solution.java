package dev.vinyard.adventofcode.soluce.year2023.day1;


import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;

import java.util.regex.Pattern;
import java.util.stream.Collectors;


@AdventOfCodeSolution(year = 2023, day = 1, part = 1, description = "Trebuchet?!", link = "https://adventofcode.com/2023/day/1", tags = "unsolved")
public class Day1Part1Solution implements Solution<Long> {

    /**
     * <h2>--- Day 1: Trebuchet?! ---</h2>
     * <p>Something is wrong with global snow production, and you've been selected to take a look. The Elves have even given you a map; on it, they've used stars to mark the top fifty locations that are likely to be having problems.</p>
     * <p>You've been doing this long enough to know that to restore snow operations, you need to check all <b>fifty stars</b> by December 25th.</p>
     * <p>Collect stars by solving puzzles. Two puzzles will be made available on each day in the Advent calendar; the second puzzle is unlocked when you complete the first. Each puzzle grants <b>one star</b>. Good luck!</p>
     * <p>You try to ask why they can't just use a <a href="/2015/day/1">weather machine</a> ("not powerful enough") and where they're even sending you ("the sky") and why your map looks mostly blank ("you sure ask a lot of questions") and hang on did you just say the sky ("of course, where do you think snow comes from") when you realize that the Elves are already loading you into a <a href="https://en.wikipedia.org/wiki/Trebuchet">trebuchet</a> ("please hold still, we need to strap you in").</p>
     * <p>As they're making the final adjustments, they discover that their calibration document (your puzzle input) has been <b>amended</b> by a very young Elf who was apparently just excited to show off her art skills. Consequently, the Elves are having trouble reading the values on the document.</p>
     * <p>The newly-improved calibration document consists of lines of text; each line originally contained a specific <b>calibration value</b> that the Elves now need to recover. On each line, the calibration value can be found by combining the <b>first digit</b> and the <b>last digit</b> (in that order) to form a single <b>two-digit number</b>.</p>
     * <p>For example:</p>
     * <pre>
     * 1abc2
     * pqr3stu8vwx
     * a1b2c3d4e5f
     * treb7uchet
     * </pre>
     * <p>In this example, the calibration values of these four lines are 12, 38, 15, and 77. Adding these together produces <b>142</b>.</p>
     * <p>Consider your entire calibration document. <b>What is the sum of all of the calibration values?</b></p>
     */
    @Override
    public Long solve(String input) {
        Pattern pattern = Pattern.compile("\\d");

        return input.lines().map(s -> pattern.matcher(s).results().collect(Collectors.toList()))
                .map(l -> l.getFirst().group() + l.getLast().group())
                .mapToLong(Long::parseLong)
                .sum();
    }
}
