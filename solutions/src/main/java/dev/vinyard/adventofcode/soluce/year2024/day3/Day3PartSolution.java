package dev.vinyard.adventofcode.soluce.year2024.day3;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import dev.vinyard.adventofcode.utils.FileReader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.net.URISyntaxException;

@AdventOfCodeSolution(year = 2024, day = 3, part = , description = "Mull It Over", link = "https://adventofcode.com/2024/day/3", tags = "unsolved")
public class Day3PartSolution implements Solution<Object> {

    /**
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
