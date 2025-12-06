package dev.vinyard.adventofcode.soluce.year2025.day6;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2025, day = 6, part = 1, description = "Trash Compactor", link = "https://adventofcode.com/2025/day/6", tags = "unsolved")
public class Day6Part1Solution implements Solution<Long> {

    /**
    * <h2>--- Day 6: Trash Compactor ---</h2>
    * <p>After helping the Elves in the kitchen, you were taking a break and helping them re-enact a movie scene when you over-enthusiastically jumped into the garbage chute!</p>
    * <p>A brief fall later, you find yourself in a garbage smasher. Unfortunately, the door's been magnetically sealed.</p>
    * <p>As you try to find a way out, you are approached by a family of cephalopods! They're pretty sure they can get the door open, but it will take some time. While you wait, they're curious if you can help the youngest cephalopod with her <a href="/2021/day/18">math homework</a>.</p>
    * <p>Cephalopod math doesn't look that different from normal math. The math worksheet (your puzzle input) consists of a list of <b>problems</b>; each problem has a group of numbers that need to be either <b>added</b> (+) or <b>multiplied</b> (*) together.</p>
    * <p>However, the problems are arranged a little strangely; they seem to be presented next to each other in a very long horizontal list. For example:</p>
    * <pre>
    * 123 328  51 64 
    *  45 64  387 23 
    *   6 98  215 314
    * *   +   *   +  
    * </pre>
    * <p>Each problem's numbers are arranged vertically; at the bottom of the problem is the symbol for the operation that needs to be performed. Problems are separated by a full column of only spaces. The left/right alignment of numbers within each problem can be ignored.</p>
    * <p>So, this worksheet contains four problems:</p>
    * <ul>
    *  <li>123 * 45 * 6 = <b>33210</b></li>
    *  <li>328 + 64 + 98 = <b>490</b></li>
    *  <li>51 * 387 * 215 = <b>4243455</b></li>
    *  <li>64 + 23 + 314 = <b>401</b></li>
    * </ul>
    * <p>To check their work, cephalopod students are given the <b>grand total</b> of adding together all of the answers to the individual problems. In this worksheet, the grand total is 33210 + 490 + 4243455 + 401 = <b>4277556</b>.</p>
    * <p>Of course, the actual worksheet is <b>much</b> wider. You'll need to make sure to unroll it completely so that you can read the problems clearly.</p>
    * <p>Solve the problems on the math worksheet. <b>What is the grand total found by adding together all of the answers to the individual problems?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.solution1();
    }
}
