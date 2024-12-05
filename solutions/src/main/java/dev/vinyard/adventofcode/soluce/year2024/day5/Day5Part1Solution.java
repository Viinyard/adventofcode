package dev.vinyard.adventofcode.soluce.year2024.day5;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2024, day = 5, part = 1, description = "Print Queue", link = "https://adventofcode.com/2024/day/5", tags = "unsolved")
public class Day5Part1Solution implements Solution<Object> {

    /**
    * <h2>--- Day 5: Print Queue ---</h2>
    * <p>Satisfied with their search on Ceres, the squadron of scholars suggests subsequently scanning the stationery stacks of sub-basement 17.</p>
    * <p>The North Pole printing department is busier than ever this close to Christmas, and while The Historians continue their search of this historically significant facility, an Elf operating a <a href="/2017/day/1">very familiar printer</a> beckons you over.</p>
    * <p>The Elf must recognize you, because they waste no time explaining that the new <b>sleigh launch safety manual</b> updates won't print correctly. Failure to update the safety manuals would be dire indeed, so you offer your services.</p>
    * <p>Safety protocols clearly indicate that new pages for the safety manuals must be printed in a <b>very specific order</b>. The notation X|Y means that if both page number X and page number Y are to be produced as part of an update, page number X <b>must</b> be printed at some point before page number Y.</p>
    * <p>The Elf has for you both the <b>page ordering rules</b> and the <b>pages to produce in each update</b> (your puzzle input), but can't figure out whether each update has the pages in the right order.</p>
    * <p>For example:</p>
    * <pre>
    * 47|53
    * 97|13
    * 97|61
    * 97|47
    * 75|29
    * 61|13
    * 75|53
    * 29|13
    * 97|29
    * 53|29
    * 61|53
    * 97|53
    * 61|29
    * 47|13
    * 75|47
    * 97|75
    * 47|61
    * 75|61
    * 47|29
    * 75|13
    * 53|13
    * 
    * 75,47,61,53,29
    * 97,61,53,29,13
    * 75,29,13
    * 75,97,47,61,53
    * 61,13,29
    * 97,13,75,29,47
    * </pre>
    * <p>The first section specifies the <b>page ordering rules</b>, one per line. The first rule, 47|53, means that if an update includes both page number 47 and page number 53, then page number 47 <b>must</b> be printed at some point before page number 53. (47 doesn't necessarily need to be <b>immediately</b> before 53; other pages are allowed to be between them.)</p>
    * <p>The second section specifies the page numbers of each <b>update</b>. Because most safety manuals are different, the pages needed in the updates are different too. The first update, 75,47,61,53,29, means that the update consists of page numbers 75, 47, 61, 53, and 29.</p>
    * <p>To get the printers going as soon as possible, start by identifying <b>which updates are already in the right order</b>.</p>
    * <p>In the above example, the first update (75,47,61,53,29) is in the right order:</p>
    * <ul>
    *  <li>75 is correctly first because there are rules that put each other page after it: 75|47, 75|61, 75|53, and 75|29.</li>
    *  <li>47 is correctly second because 75 must be before it (75|47) and every other page must be after it according to 47|61, 47|53, and 47|29.</li>
    *  <li>61 is correctly in the middle because 75 and 47 are before it (75|61 and 47|61) and 53 and 29 are after it (61|53 and 61|29).</li>
    *  <li>53 is correctly fourth because it is before page number 29 (53|29).</li>
    *  <li>29 is the only page left and so is correctly last.</li>
    * </ul>
    * <p>Because the first update does not include some page numbers, the ordering rules involving those missing page numbers are ignored.</p>
    * <p>The second and third updates are also in the correct order according to the rules. Like the first update, they also do not include every page number, and so only some of the ordering rules apply - within each update, the ordering rules that involve missing page numbers are not used.</p>
    * <p>The fourth update, 75,97,47,61,53, is <b>not</b> in the correct order: it would print 75 before 97, which violates the rule 97|75.</p>
    * <p>The fifth update, 61,13,29, is also <b>not</b> in the correct order, since it breaks the rule 29|13.</p>
    * <p>The last update, 97,13,75,29,47, is <b>not</b> in the correct order due to breaking several rules.</p>
    * <p>For some reason, the Elves also need to know the <b>middle page number</b> of each update being printed. Because you are currently only printing the correctly-ordered updates, you will need to find the middle page number of each correctly-ordered update. In the above example, the correctly-ordered updates are:</p>
    * <pre>
    * 75,47,<b>61</b>,53,29
    * 97,61,<b>53</b>,29,13
    * 75,<b>29</b>,13
    * </pre>
    * <p>These have middle page numbers of 61, 53, and 29 respectively. Adding these page numbers together gives <b>143</b>.</p>
    * <p>Of course, you'll need to be careful: the actual list of <b>page ordering rules</b> is bigger and more complicated than the above example.</p>
    * <p>Determine which updates are already in the correct order. <b>What do you get if you add up the middle page number from those correctly-ordered updates?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.getAllValidUpdate().stream().mapToInt(ASD.Update::getMiddlePage).sum();
    }
}
