package dev.vinyard.adventofcode.soluce.year2024.day24;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2024, day = 24, part = 1, description = "Crossed Wires", link = "https://adventofcode.com/2024/day/24", tags = "unsolved")
public class Day24Part1Solution implements Solution<Object> {

    /**
    * <h2>--- Day 24: Crossed Wires ---</h2>
    * <p>You and The Historians arrive at the edge of a <a href="/2022/day/23">large grove</a> somewhere in the jungle. After the last incident, the Elves installed a small device that monitors the fruit. While The Historians search the grove, one of them asks if you can take a look at the monitoring device; apparently, it's been malfunctioning recently.</p>
    * <p>The device seems to be trying to produce a number through some boolean logic gates. Each gate has two inputs and one output. The gates all operate on values that are either <b>true</b> (1) or <b>false</b> (0).</p>
    * <ul>
    *  <li>AND gates output 1 if <b>both</b> inputs are 1; if either input is 0, these gates output 0.</li>
    *  <li>OR gates output 1 if <b>one or both</b> inputs is 1; if both inputs are 0, these gates output 0.</li>
    *  <li>XOR gates output 1 if the inputs are <b>different</b>; if the inputs are the same, these gates output 0.</li>
    * </ul>
    * <p>Gates wait until both inputs are received before producing output; wires can carry 0, 1 or no value at all. There are no loops; once a gate has determined its output, the output will not change until the whole system is reset. Each wire is connected to at most one gate output, but can be connected to many gate inputs.</p>
    * <p>Rather than risk getting shocked while tinkering with the live system, you write down all of the gate connections and initial wire values (your puzzle input) so you can consider them in relative safety. For example:</p>
    * <pre>
    * x00: 1
    * x01: 1
    * x02: 1
    * y00: 0
    * y01: 1
    * y02: 0
    * 
    * x00 AND y00 -&gt; z00
    * x01 XOR y01 -&gt; z01
    * x02 OR y02 -&gt; z02
    * </pre>
    * <p>Because gates wait for input, some wires need to start with a value (as inputs to the entire system). The first section specifies these values. For example, x00: 1 means that the wire named x00 starts with the value 1 (as if a gate is already outputting that value onto that wire).</p>
    * <p>The second section lists all of the gates and the wires connected to them. For example, x00 AND y00 -&gt; z00 describes an instance of an AND gate which has wires x00 and y00 connected to its inputs and which will write its output to wire z00.</p>
    * <p>In this example, simulating these gates eventually causes 0 to appear on wire z00, 0 to appear on wire z01, and 1 to appear on wire z02.</p>
    * <p>Ultimately, the system is trying to produce a <b>number</b> by combining the bits on all wires starting with z. z00 is the least significant bit, then z01, then z02, and so on.</p>
    * <p>In this example, the three output bits form the binary number 100 which is equal to the decimal number <b>4</b>.</p>
    * <p>Here's a larger example:</p>
    * <pre>
    * x00: 1
    * x01: 0
    * x02: 1
    * x03: 1
    * x04: 0
    * y00: 1
    * y01: 1
    * y02: 1
    * y03: 1
    * y04: 1
    * 
    * ntg XOR fgs -&gt; mjb
    * y02 OR x01 -&gt; tnw
    * kwq OR kpj -&gt; z05
    * x00 OR x03 -&gt; fst
    * tgd XOR rvg -&gt; z01
    * vdt OR tnw -&gt; bfw
    * bfw AND frj -&gt; z10
    * ffh OR nrd -&gt; bqk
    * y00 AND y03 -&gt; djm
    * y03 OR y00 -&gt; psh
    * bqk OR frj -&gt; z08
    * tnw OR fst -&gt; frj
    * gnj AND tgd -&gt; z11
    * bfw XOR mjb -&gt; z00
    * x03 OR x00 -&gt; vdt
    * gnj AND wpb -&gt; z02
    * x04 AND y00 -&gt; kjc
    * djm OR pbm -&gt; qhw
    * nrd AND vdt -&gt; hwm
    * kjc AND fst -&gt; rvg
    * y04 OR y02 -&gt; fgs
    * y01 AND x02 -&gt; pbm
    * ntg OR kjc -&gt; kwq
    * psh XOR fgs -&gt; tgd
    * qhw XOR tgd -&gt; z09
    * pbm OR djm -&gt; kpj
    * x03 XOR y03 -&gt; ffh
    * x00 XOR y04 -&gt; ntg
    * bfw OR bqk -&gt; z06
    * nrd XOR fgs -&gt; wpb
    * frj XOR qhw -&gt; z04
    * bqk OR frj -&gt; z07
    * y03 OR x01 -&gt; nrd
    * hwm AND bqk -&gt; z03
    * tgd XOR rvg -&gt; z12
    * tnw OR pbm -&gt; gnj
    * </pre>
    * <p>After waiting for values on all wires starting with z, the wires in this system have the following values:</p>
    * <pre>
    * bfw: 1
    * bqk: 1
    * djm: 1
    * ffh: 0
    * fgs: 1
    * frj: 1
    * fst: 1
    * gnj: 1
    * hwm: 1
    * kjc: 0
    * kpj: 1
    * kwq: 0
    * mjb: 1
    * nrd: 1
    * ntg: 0
    * pbm: 1
    * psh: 1
    * qhw: 1
    * rvg: 0
    * tgd: 0
    * tnw: 1
    * vdt: 1
    * wpb: 0
    * z00: 0
    * z01: 0
    * z02: 0
    * z03: 1
    * z04: 0
    * z05: 1
    * z06: 1
    * z07: 1
    * z08: 1
    * z09: 1
    * z10: 1
    * z11: 0
    * z12: 0
    * </pre>
    * <p>Combining the bits from all wires starting with z produces the binary number 0011111101000. Converting this number to decimal produces <b>2024</b>.</p>
    * <p>Simulate the system of gates and wires. <b>What decimal number does it output on the wires starting with z?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.evaluate();
    }
}
