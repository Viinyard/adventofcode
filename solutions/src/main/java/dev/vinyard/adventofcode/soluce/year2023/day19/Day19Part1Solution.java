package dev.vinyard.adventofcode.soluce.year2023.day19;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2023, day = 19, part = 1, description = "Aplenty", link = "https://adventofcode.com/2023/day/19", tags = "unsolved")
public class Day19Part1Solution implements Solution<Long> {

    /**
    * <h2>--- Day 19: Aplenty ---</h2>
    * <p>The Elves of Gear Island are thankful for your help and send you on your way. They even have a hang glider that someone <a href="9">stole</a> from Desert Island; since you're already going that direction, it would help them a lot if you would use it to get down there and return it to them.</p>
    * <p>As you reach the bottom of the <b>relentless avalanche of machine parts</b>, you discover that they're already forming a formidable heap. Don't worry, though - a group of Elves is already here organizing the parts, and they have a <b>system</b>.</p>
    * <p>To start, each part is rated in each of four categories:</p>
    * <ul>
    *  <li>x: E<b>x</b>tremely cool looking</li>
    *  <li>m: <b>M</b>usical (it makes a noise when you hit it)</li>
    *  <li>a: <b>A</b>erodynamic</li>
    *  <li>s: <b>S</b>hiny</li>
    * </ul>
    * <p>Then, each part is sent through a series of <b>workflows</b> that will ultimately <b>accept</b> or <b>reject</b> the part. Each workflow has a name and contains a list of <b>rules</b>; each rule specifies a condition and where to send the part if the condition is true. The first rule that matches the part being considered is applied immediately, and the part moves on to the destination described by the rule. (The last rule in each workflow has no condition and always applies if reached.)</p>
    * <p>Consider the workflow ex{x&gt;10:one,m&lt;20:two,a&gt;30:R,A}. This workflow is named ex and contains four rules. If workflow ex were considering a specific part, it would perform the following steps in order:</p>
    * <ul>
    *  <li>Rule "x&gt;10:one": If the part's x is more than 10, send the part to the workflow named one.</li>
    *  <li>Rule "m&lt;20:two": Otherwise, if the part's m is less than 20, send the part to the workflow named two.</li>
    *  <li>Rule "a&gt;30:R": Otherwise, if the part's a is more than 30, the part is immediately <b>rejected</b> (R).</li>
    *  <li>Rule "A": Otherwise, because no other rules matched the part, the part is immediately <b>accepted</b> (A).</li>
    * </ul>
    * <p>If a part is sent to another workflow, it immediately switches to the start of that workflow instead and never returns. If a part is <b>accepted</b> (sent to A) or <b>rejected</b> (sent to R), the part immediately stops any further processing.</p>
    * <p>The system works, but it's not keeping up with the torrent of weird metal shapes. The Elves ask if you can help sort a few parts and give you the list of workflows and some part ratings (your puzzle input). For example:</p>
    * <pre>
    * px{a&lt;2006:qkq,m&gt;2090:A,rfg}
    * pv{a&gt;1716:R,A}
    * lnx{m&gt;1548:A,A}
    * rfg{s&lt;537:gd,x&gt;2440:R,A}
    * qs{s&gt;3448:A,lnx}
    * qkq{x&lt;1416:A,crn}
    * crn{x&gt;2662:A,R}
    * in{s&lt;1351:px,qqz}
    * qqz{s&gt;2770:qs,m&lt;1801:hdj,R}
    * gd{a&gt;3333:R,R}
    * hdj{m&gt;838:A,pv}
    * 
    * {x=787,m=2655,a=1222,s=2876}
    * {x=1679,m=44,a=2067,s=496}
    * {x=2036,m=264,a=79,s=2244}
    * {x=2461,m=1339,a=466,s=291}
    * {x=2127,m=1623,a=2188,s=1013}
    * </pre>
    * <p>The workflows are listed first, followed by a blank line, then the ratings of the parts the Elves would like you to sort. All parts begin in the workflow named in. In this example, the five listed parts go through the following workflows:</p>
    * <ul>
    *  <li>{x=787,m=2655,a=1222,s=2876}: in -&gt; qqz -&gt; qs -&gt; lnx -&gt; <b>A</b></li>
    *  <li>{x=1679,m=44,a=2067,s=496}: in -&gt; px -&gt; rfg -&gt; gd -&gt; <b>R</b></li>
    *  <li>{x=2036,m=264,a=79,s=2244}: in -&gt; qqz -&gt; hdj -&gt; pv -&gt; <b>A</b></li>
    *  <li>{x=2461,m=1339,a=466,s=291}: in -&gt; px -&gt; qkq -&gt; crn -&gt; <b>R</b></li>
    *  <li>{x=2127,m=1623,a=2188,s=1013}: in -&gt; px -&gt; rfg -&gt; <b>A</b></li>
    * </ul>
    * <p>Ultimately, three parts are <b>accepted</b>. Adding up the x, m, a, and s rating for each of the accepted parts gives 7540 for the part with x=787, 4623 for the part with x=2036, and 6951 for the part with x=2127. Adding all of the ratings for <b>all</b> of the accepted parts gives the sum total of <b>19114</b>.</p>
    * <p>Sort through all of the parts you've been given; <b>what do you get if you add together all of the rating numbers for all of the parts that ultimately get accepted?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.evaluateAll();
    }
}
