package dev.vinyard.adventofcode.soluce.year2023.day15;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2023, day = 15, part = 1, description = "Lens Library", link = "https://adventofcode.com/2023/day/15", tags = "unsolved")
public class Day15Part1Solution implements Solution<Long> {

    /**
    * <h2>--- Day 15: Lens Library ---</h2>
    * <p>The newly-focused parabolic reflector dish is sending all of the collected light to a point on the side of yet another mountain - the largest mountain on Lava Island. As you approach the mountain, you find that the light is being collected by the wall of a large facility embedded in the mountainside.</p>
    * <p>You find a door under a large sign that says "Lava Production Facility" and next to a smaller sign that says "Danger - Personal Protective Equipment required beyond this point".</p>
    * <p>As you step inside, you are immediately greeted by a somewhat panicked reindeer wearing goggles and a loose-fitting <a href="https://en.wikipedia.org/wiki/Hard_hat">hard hat</a>. The reindeer leads you to a shelf of goggles and hard hats (you quickly find some that fit) and then further into the facility. At one point, you pass a button with a faint snout mark and the label "PUSH FOR HELP". No wonder you were loaded into that <a href="1">trebuchet</a> so quickly!</p>
    * <p>You pass through a final set of doors surrounded with even more warning signs and into what must be the room that collects all of the light from outside. As you admire the large assortment of lenses available to further focus the light, the reindeer brings you a book titled "Initialization Manual".</p>
    * <p>"Hello!", the book cheerfully begins, apparently unaware of the concerned reindeer reading over your shoulder. "This procedure will let you bring the Lava Production Facility online - all without burning or melting anything unintended!"</p>
    * <p>"Before you begin, please be prepared to use the Holiday ASCII String Helper algorithm (appendix 1A)." You turn to appendix 1A. The reindeer leans closer with interest.</p>
    * <p>The HASH algorithm is a way to turn any <a href="https://en.wikipedia.org/wiki/String_(computer_science)">string</a> of characters into a single <b>number</b> in the range 0 to 255. To run the HASH algorithm on a string, start with a <b>current value</b> of 0. Then, for each character in the string starting from the beginning:</p>
    * <ul>
    *  <li>Determine the <a href="https://en.wikipedia.org/wiki/ASCII#Printable_characters">ASCII code</a> for the current character of the string.</li>
    *  <li>Increase the <b>current value</b> by the ASCII code you just determined.</li>
    *  <li>Set the <b>current value</b> to itself multiplied by 17.</li>
    *  <li>Set the <b>current value</b> to the <a href="https://en.wikipedia.org/wiki/Modulo">remainder</a> of dividing itself by 256.</li>
    * </ul>
    * <p>After following these steps for each character in the string in order, the <b>current value</b> is the output of the HASH algorithm.</p>
    * <p>So, to find the result of running the HASH algorithm on the string HASH:</p>
    * <ul>
    *  <li>The <b>current value</b> starts at 0.</li>
    *  <li>The first character is H; its ASCII code is 72.</li>
    *  <li>The <b>current value</b> increases to 72.</li>
    *  <li>The <b>current value</b> is multiplied by 17 to become 1224.</li>
    *  <li>The <b>current value</b> becomes <b>200</b> (the remainder of 1224 divided by 256).</li>
    *  <li>The next character is A; its ASCII code is 65.</li>
    *  <li>The <b>current value</b> increases to 265.</li>
    *  <li>The <b>current value</b> is multiplied by 17 to become 4505.</li>
    *  <li>The <b>current value</b> becomes <b>153</b> (the remainder of 4505 divided by 256).</li>
    *  <li>The next character is S; its ASCII code is 83.</li>
    *  <li>The <b>current value</b> increases to 236.</li>
    *  <li>The <b>current value</b> is multiplied by 17 to become 4012.</li>
    *  <li>The <b>current value</b> becomes <b>172</b> (the remainder of 4012 divided by 256).</li>
    *  <li>The next character is H; its ASCII code is 72.</li>
    *  <li>The <b>current value</b> increases to 244.</li>
    *  <li>The <b>current value</b> is multiplied by 17 to become 4148.</li>
    *  <li>The <b>current value</b> becomes <b>52</b> (the remainder of 4148 divided by 256).</li>
    * </ul>
    * <p>So, the result of running the HASH algorithm on the string HASH is <b>52</b>.</p>
    * <p>The <b>initialization sequence</b> (your puzzle input) is a comma-separated list of steps to start the Lava Production Facility. <b>Ignore newline characters</b> when parsing the initialization sequence. To verify that your HASH algorithm is working, the book offers the sum of the result of running the HASH algorithm on each step in the initialization sequence.</p>
    * <p>For example:</p>
    * <pre>
    * rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7</pre>
    * <p>This initialization sequence specifies 11 individual steps; the result of running the HASH algorithm on each of the steps is as follows:</p>
    * <ul>
    *  <li>rn=1 becomes <b>30</b>.</li>
    *  <li>cm- becomes <b>253</b>.</li>
    *  <li>qp=3 becomes <b>97</b>.</li>
    *  <li>cm=2 becomes <b>47</b>.</li>
    *  <li>qp- becomes <b>14</b>.</li>
    *  <li>pc=4 becomes <b>180</b>.</li>
    *  <li>ot=9 becomes <b>9</b>.</li>
    *  <li>ab=5 becomes <b>197</b>.</li>
    *  <li>pc- becomes <b>48</b>.</li>
    *  <li>pc=6 becomes <b>214</b>.</li>
    *  <li>ot=7 becomes <b>231</b>.</li>
    * </ul>
    * <p>In this example, the sum of these results is <b>1320</b>. Unfortunately, the reindeer has stolen the page containing the expected verification number and is currently running around the facility with it excitedly.</p>
    * <p>Run the HASH algorithm on each step in the initialization sequence. <b>What is the sum of the results?</b> (The initialization sequence is one long line; be careful when copy-pasting it.)</p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.hash();
    }
}
