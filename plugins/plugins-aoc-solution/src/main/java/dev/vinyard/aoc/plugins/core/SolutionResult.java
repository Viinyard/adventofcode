package dev.vinyard.aoc.plugins.core;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import dev.vinyard.aoc.plugins.utils.FileReader;
import lombok.Getter;

import java.io.IOException;
import java.util.Arrays;
import java.util.LongSummaryStatistics;
import java.util.stream.Stream;

@Getter
public class SolutionResult<T> {

    private final int year;
    private final int day;
    private final int part;
    private final String description;
    private final String link;
    private final String[] tags;
    private final String fileName;
    private final Solution<T> solution;

    public SolutionResult(Solution<T> solution) {
        AdventOfCodeSolution annotation = solution.getClass().getAnnotation(AdventOfCodeSolution.class);
        this.fileName = InputRetriever.retrieve(annotation);
        this.solution = solution;
        this.year = annotation.year();
        this.day = annotation.day();
        this.part = annotation.part();
        this.description = annotation.description();
        this.link = annotation.link();
        this.tags = annotation.tags();
    }

    public T getResult() throws IOException {
        String input = FileReader.readFileToString(this.fileName);
        return solution.solve(input);
    }

    public LongSummaryStatistics getStatistics(int times) throws IOException {
        String input = FileReader.readFileToString(this.fileName);
        return Stream.iterate(0, i -> i + 1)
                .limit(times)
                .mapToLong(i -> {
                    long start = System.nanoTime();
                    solution.solve(input);
                    long end = System.nanoTime();
                    return end - start;
                }).summaryStatistics();
    }

    @Override
    public String toString() {
        return "SolutionResult{" +
                "year=" + year +
                ", day=" + day +
                ", part=" + part +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", tags=" + Arrays.toString(tags) +
                '}';
    }
}
