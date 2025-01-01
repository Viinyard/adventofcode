package dev.vinyard.adventofcode.soluce.year2024.day24;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2024, day = 24, part = 2, description = "Crossed Wires", link = "https://adventofcode.com/2024/day/24", tags = "unsolved")
public class Day24Part2Solution implements Solution<Object> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>After inspecting the monitoring device more closely, you determine that the system you're simulating is trying to <b>add two binary numbers</b>.</p>
    * <p>Specifically, it is treating the bits on wires starting with x as one binary number, treating the bits on wires starting with y as a second binary number, and then attempting to add those two numbers together. The output of this operation is produced as a binary number on the wires starting with z. (In all three cases, wire 00 is the least significant bit, then 01, then 02, and so on.)</p>
    * <p>The initial values for the wires in your puzzle input represent <b>just one instance</b> of a pair of numbers that sum to the wrong value. Ultimately, <b>any</b> two binary numbers provided as input should be handled correctly. That is, for any combination of bits on wires starting with x and wires starting with y, the sum of the two numbers those bits represent should be produced as a binary number on the wires starting with z.</p>
    * <p>For example, if you have an addition system with four x wires, four y wires, and five z wires, you should be able to supply any four-bit number on the x wires, any four-bit number on the y numbers, and eventually find the sum of those two numbers as a five-bit number on the z wires. One of the many ways you could provide numbers to such a system would be to pass 11 on the x wires (1011 in binary) and 13 on the y wires (1101 in binary):</p>
    * <pre>
    * x00: 1
    * x01: 1
    * x02: 0
    * x03: 1
    * y00: 1
    * y01: 0
    * y02: 1
    * y03: 1
    * </pre>
    * <p>If the system were working correctly, then after all gates are finished processing, you should find 24 (11+13) on the z wires as the five-bit binary number 11000:</p>
    * <pre>
    * z00: 0
    * z01: 0
    * z02: 0
    * z03: 1
    * z04: 1
    * </pre>
    * <p>Unfortunately, your actual system needs to add numbers with many more bits and therefore has many more wires.</p>
    * <p>Based on forensic analysis of scuff marks and scratches on the device, you can tell that there are exactly <b>four</b> pairs of gates whose output wires have been <b>swapped</b>. (A gate can only be in at most one such pair; no gate's output was swapped multiple times.)</p>
    * <p>For example, the system below is supposed to find the bitwise AND of the six-bit number on x00 through x05 and the six-bit number on y00 through y05 and then write the result as a six-bit number on z00 through z05:</p>
    * <pre>
    * x00: 0
    * x01: 1
    * x02: 0
    * x03: 1
    * x04: 0
    * x05: 1
    * y00: 0
    * y01: 0
    * y02: 1
    * y03: 1
    * y04: 0
    * y05: 1
    * 
    * x00 AND y00 -&gt; z05
    * x01 AND y01 -&gt; z02
    * x02 AND y02 -&gt; z01
    * x03 AND y03 -&gt; z03
    * x04 AND y04 -&gt; z04
    * x05 AND y05 -&gt; z00
    * </pre>
    * <p>However, in this example, two pairs of gates have had their output wires swapped, causing the system to produce wrong answers. The first pair of gates with swapped outputs is x00 AND y00 -&gt; z05 and x05 AND y05 -&gt; z00; the second pair of gates is x01 AND y01 -&gt; z02 and x02 AND y02 -&gt; z01. Correcting these two swaps results in this system that works as intended for any set of initial values on wires that start with x or y:</p>
    * <pre>
    * x00 AND y00 -&gt; z00
    * x01 AND y01 -&gt; z01
    * x02 AND y02 -&gt; z02
    * x03 AND y03 -&gt; z03
    * x04 AND y04 -&gt; z04
    * x05 AND y05 -&gt; z05
    * </pre>
    * <p>In this example, two pairs of gates have outputs that are involved in a swap. By sorting their output wires' names and joining them with commas, the list of wires involved in swaps is <b>z00,z01,z02,z05</b>.</p>
    * <p>Of course, your actual system is much more complex than this, and the gates that need their outputs swapped could be <b>anywhere</b>, not just attached to a wire starting with z. If you were to determine that you need to swap output wires aaa with eee, ooo with z99, bbb with ccc, and aoc with z24, your answer would be <b>aaa,aoc,bbb,ccc,eee,ooo,z24,z99</b>.</p>
    * <p>Your system of gates and wires has <b>four</b> pairs of gates which need their output wires swapped - <b>eight</b> wires in total. Determine which four pairs of gates need their outputs swapped so that your system correctly performs addition; <b>what do you get if you sort the names of the eight wires involved in a swap and then join those names with commas?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.part2();
    }
}
