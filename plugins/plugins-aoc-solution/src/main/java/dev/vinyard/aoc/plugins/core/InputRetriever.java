package dev.vinyard.aoc.plugins.core;

import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;

public class InputRetriever {

    public static String retrieve(AdventOfCodeSolution solution) {
        return String.format("soluce/year%d/day%d/input.txt", solution.year(), solution.day());
    }
}
