package dev.vinyard.aoc.plugins.core;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SolutionRetriever {

    public static List<Solution<?>> retrieve(SolutionFilter args) {
        URL[] urls = args.get(SolutionFilter.SolverArgumentType.CLASSLOADER).orElseGet(Collections::emptyList).stream().map(File::new).map(File::toURI).map(uri -> {
            try {
                return uri.toURL();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }).toArray(URL[]::new);


        Set<Class<?>> solutionClazz = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forClassLoader(new URLClassLoader(urls)))).getTypesAnnotatedWith(AdventOfCodeSolution.class);

        Predicate<Class<?>> annotationFilter = clazz -> clazz.isAnnotationPresent(AdventOfCodeSolution.class);
        Predicate<Class<?>> typeFilter = Solution.class::isAssignableFrom;
        Predicate<Class<?>> argsFilter = clazz -> args.filter(clazz.getAnnotation(AdventOfCodeSolution.class));

        Comparator<AdventOfCodeSolution> comparator = Comparator.comparingInt(AdventOfCodeSolution::year)
                .thenComparingInt(AdventOfCodeSolution::day)
                .thenComparingInt(AdventOfCodeSolution::part);

        return solutionClazz.stream().filter(Solution.class::isAssignableFrom)
                .filter(typeFilter.and(annotationFilter).and(argsFilter))
                .sorted((a, b) -> comparator.compare(a.getAnnotation(AdventOfCodeSolution.class), b.getAnnotation(AdventOfCodeSolution.class)))
                .map(clazz -> {
                    try {
                        return (Solution<?>) clazz.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }
}
