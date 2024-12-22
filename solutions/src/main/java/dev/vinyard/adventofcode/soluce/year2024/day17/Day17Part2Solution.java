package dev.vinyard.adventofcode.soluce.year2024.day17;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2024, day = 17, part = 2, description = "Chronospatial Computer", link = "https://adventofcode.com/2024/day/17", tags = "unsolved")
public class Day17Part2Solution implements Solution<Object> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>Digging deeper in the device's manual, you discover the problem: this program is supposed to <b>output another copy of the program</b>! Unfortunately, the value in register A seems to have been corrupted. You'll need to find a new value to which you can initialize register A so that the program's output instructions produce an exact copy of the program itself.</p>
    * <p>For example:</p>
    * <pre>
    * Register A: 2024
    * Register B: 0
    * Register C: 0
    * 
    * Program: 0,3,5,4,3,0
    * </pre>
    * <p>This program outputs a copy of itself if register A is instead initialized to <b>117440</b>. (The original initial value of register A, 2024, is ignored.)</p>
    * <p><b>What is the lowest positive initial value for register A that causes the program to output a copy of itself?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Program program = parser.root().out;

        return program.findA();
    }
}
