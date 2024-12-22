package dev.vinyard.adventofcode.soluce.year2024.day19;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import dev.vinyard.adventofcode.utils.FileReader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.net.URISyntaxException;

@AdventOfCodeSolution(year = 2024, day = 19, part = 1, description = "Linen Layout", link = "https://adventofcode.com/2024/day/19", tags = "unsolved")
public class Day19Part1Solution implements Solution<Object> {

    /**
    * <h2>--- Day 19: Linen Layout ---</h2>
    * <p>Today, The Historians take you up to the <a href="/2023/day/12">hot springs</a> on Gear Island! Very <a href="https://www.youtube.com/watch?v=ekL881PJMjI">suspiciously</a>, absolutely nothing goes wrong as they begin their careful search of the vast field of helixes.</p>
    * <p>Could this <b>finally</b> be your chance to visit the <a href="https://en.wikipedia.org/wiki/Onsen">onsen</a> next door? Only one way to find out.</p>
    * <p>After a brief conversation with the reception staff at the onsen front desk, you discover that you don't have the right kind of money to pay the admission fee. However, before you can leave, the staff get your attention. Apparently, they've heard about how you helped at the hot springs, and they're willing to make a deal: if you can simply help them <b>arrange their towels</b>, they'll let you in for <b>free</b>!</p>
    * <p>Every towel at this onsen is marked with a <b>pattern of colored stripes</b>. There are only a few patterns, but for any particular pattern, the staff can get you as many towels with that pattern as you need. Each stripe can be <b>white</b> (w), <b>blue</b> (u), <b>black</b> (b), <b>red</b> (r), or <b>green</b> (g). So, a towel with the pattern ggr would have a green stripe, a green stripe, and then a red stripe, in that order. (You can't reverse a pattern by flipping a towel upside-down, as that would cause the onsen logo to face the wrong way.)</p>
    * <p>The Official Onsen Branding Expert has produced a list of <b>designs</b> - each a long sequence of stripe colors - that they would like to be able to display. You can use any towels you want, but all of the towels' stripes must exactly match the desired design. So, to display the design rgrgr, you could use two rg towels and then an r towel, an rgr towel and then a gr towel, or even a single massive rgrgr towel (assuming such towel patterns were actually available).</p>
    * <p>To start, collect together all of the available towel patterns and the list of desired designs (your puzzle input). For example:</p>
    * <pre>
    * r, wr, b, g, bwu, rb, gb, br
    * 
    * brwrr
    * bggr
    * gbbr
    * rrbgbr
    * ubwu
    * bwurrg
    * brgr
    * bbrgwb
    * </pre>
    * <p>The first line indicates the available towel patterns; in this example, the onsen has unlimited towels with a single red stripe (r), unlimited towels with a white stripe and then a red stripe (wr), and so on.</p>
    * <p>After the blank line, the remaining lines each describe a design the onsen would like to be able to display. In this example, the first design (brwrr) indicates that the onsen would like to be able to display a black stripe, a red stripe, a white stripe, and then two red stripes, in that order.</p>
    * <p>Not all designs will be possible with the available towels. In the above example, the designs are possible or impossible as follows:</p>
    * <ul>
    *  <li>brwrr can be made with a br towel, then a wr towel, and then finally an r towel.</li>
    *  <li>bggr can be made with a b towel, two g towels, and then an r towel.</li>
    *  <li>gbbr can be made with a gb towel and then a br towel.</li>
    *  <li>rrbgbr can be made with r, rb, g, and br.</li>
    *  <li>ubwu is <b>impossible</b>.</li>
    *  <li>bwurrg can be made with bwu, r, r, and g.</li>
    *  <li>brgr can be made with br, g, and r.</li>
    *  <li>bbrgwb is <b>impossible</b>.</li>
    * </ul>
    * <p>In this example, <b>6</b> of the eight designs are possible with the available towel patterns.</p>
    * <p>To get into the onsen as soon as possible, consult your list of towel patterns and desired designs carefully. <b>How many designs are possible?</b></p>
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
