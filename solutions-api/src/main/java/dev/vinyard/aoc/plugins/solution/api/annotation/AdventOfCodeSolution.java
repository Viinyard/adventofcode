package dev.vinyard.aoc.plugins.solution.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Advent of code solution annotation.
 * This annotation is used to describe a solution.
 * It can be used by the plugin to generate the documentation.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AdventOfCodeSolution {

    /**
     * The year of the solution.
     * @return The year of the solution.
     */
    int year();

    /**
     * The day of the solution.
     * @return The day of the solution.
     */
    int day();

    /**
     * The part of the solution.
     * @return The part of the solution.
     */
    int part();

    /**
     * The description of the solution.
     * @return The description of the solution.
     */
    String description();

    /**
     * The link to the solution.
     * @return The link to the solution.
     */
    String link();

    /**
     * The tags of the solution.
     * For exemple : solved or unsolved.
     * @return The tags of the solution.
     */
    String[] tags() default "";

}