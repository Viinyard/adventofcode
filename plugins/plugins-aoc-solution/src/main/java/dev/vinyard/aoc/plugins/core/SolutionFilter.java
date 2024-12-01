package dev.vinyard.aoc.plugins.core;

import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;

import java.util.*;
import java.util.function.BiPredicate;

public class SolutionFilter {

    private final Map<SolverArgumentType, List<String>> arguments;
    private final BiPredicate<SolverArgumentType, String> filterPredicate = (type, value) -> !this.contains(type) || this.contains(type, value);

    public SolutionFilter() {
        this.arguments = new HashMap<>();
    }

    public void put(SolverArgumentType solverArgumentType, List<String> values) {
        arguments.put(solverArgumentType, values);
    }

    public void add(SolverArgumentType solverArgumentType, String value) {
        arguments.computeIfAbsent(solverArgumentType, k -> new ArrayList<>()).add(value);
    }

    public Optional<List<String>> get(SolverArgumentType solverArgumentType) {
        return Optional.ofNullable(arguments.get(solverArgumentType));
    }

    public boolean contains(SolverArgumentType solverArgumentType) {
        return arguments.containsKey(solverArgumentType);
    }

    public boolean contains(SolverArgumentType solverArgumentType, String value) {
        return Optional.ofNullable(arguments.get(solverArgumentType)).filter(l -> l.contains(value)).isPresent();
    }

    public boolean filter(AdventOfCodeSolution annotation) {
        return filterPredicate.test(SolverArgumentType.YEAR, String.valueOf(annotation.year()))
                && filterPredicate.test(SolverArgumentType.DAY, String.valueOf(annotation.day()))
                && filterPredicate.test(SolverArgumentType.PART, String.valueOf(annotation.part()))
                && Arrays.stream(annotation.tags()).anyMatch(tag -> filterPredicate.test(SolverArgumentType.TAGS, tag))
                && filterPredicate.test(SolverArgumentType.DESCRIPTION, String.valueOf(annotation.description()))
                && filterPredicate.test(SolverArgumentType.LINK, String.valueOf(annotation.link()));
    }

    public enum SolverArgumentType {
        YEAR,
        DAY,
        PART,
        TAGS,
        DESCRIPTION,
        LINK,
        CLASSLOADER;

        public static SolverArgumentType from(String value) {
            return SolverArgumentType.valueOf(value.toUpperCase());
        }
    }
}
