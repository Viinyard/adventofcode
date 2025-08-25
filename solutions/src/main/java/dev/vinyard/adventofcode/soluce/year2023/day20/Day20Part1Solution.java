package dev.vinyard.adventofcode.soluce.year2023.day20;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2023, day = 20, part = 1, description = "Pulse Propagation", link = "https://adventofcode.com/2023/day/20", tags = "unsolved")
public class Day20Part1Solution implements Solution<Long> {

    /**
    * <h2>--- Day 20: Pulse Propagation ---</h2>
    * <p>With your help, the Elves manage to find the right parts and fix all of the machines. Now, they just need to send the command to boot up the machines and get the sand flowing again.</p>
    * <p>The machines are far apart and wired together with long <b>cables</b>. The cables don't connect to the machines directly, but rather to communication <b>modules</b> attached to the machines that perform various initialization tasks and also act as communication relays.</p>
    * <p>Modules communicate using <b>pulses</b>. Each pulse is either a <b>high pulse</b> or a <b>low pulse</b>. When a module sends a pulse, it sends that type of pulse to each module in its list of <b>destination modules</b>.</p>
    * <p>There are several different types of modules:</p>
    * <p><b>Flip-flop</b> modules (prefix %) are either <b>on</b> or <b>off</b>; they are initially <b>off</b>. If a flip-flop module receives a high pulse, it is ignored and nothing happens. However, if a flip-flop module receives a low pulse, it <b>flips between on and off</b>. If it was off, it turns on and sends a high pulse. If it was on, it turns off and sends a low pulse.</p>
    * <p><b>Conjunction</b> modules (prefix &amp;) <b>remember</b> the type of the most recent pulse received from <b>each</b> of their connected input modules; they initially default to remembering a <b>low pulse</b> for each input. When a pulse is received, the conjunction module first updates its memory for that input. Then, if it remembers <b>high pulses</b> for all inputs, it sends a <b>low pulse</b>; otherwise, it sends a <b>high pulse</b>.</p>
    * <p>There is a single <b>broadcast module</b> (named broadcaster). When it receives a pulse, it sends the same pulse to all of its destination modules.</p>
    * <p>Here at Desert Machine Headquarters, there is a module with a single button on it called, aptly, the <b>button module</b>. When you push the button, a single <b>low pulse</b> is sent directly to the broadcaster module.</p>
    * <p>After pushing the button, you must wait until all pulses have been delivered and fully handled before pushing it again. Never push the button if modules are still processing pulses.</p>
    * <p>Pulses are always processed <b>in the order they are sent</b>. So, if a pulse is sent to modules a, b, and c, and then module a processes its pulse and sends more pulses, the pulses sent to modules b and c would have to be handled first.</p>
    * <p>The module configuration (your puzzle input) lists each module. The name of the module is preceded by a symbol identifying its type, if any. The name is then followed by an arrow and a list of its destination modules. For example:</p>
    * <pre>
    * broadcaster -&gt; a, b, c
    * %a -&gt; b
    * %b -&gt; c
    * %c -&gt; inv
    * &amp;inv -&gt; a
    * </pre>
    * <p>In this module configuration, the broadcaster has three destination modules named a, b, and c. Each of these modules is a flip-flop module (as indicated by the % prefix). a outputs to b which outputs to c which outputs to another module named inv. inv is a conjunction module (as indicated by the &amp; prefix) which, because it has only one input, acts like an inverter (it sends the opposite of the pulse type it receives); it outputs to a.</p>
    * <p>By pushing the button once, the following pulses are sent:</p>
    * <pre>
    * button -low-&gt; broadcaster
    * broadcaster -low-&gt; a
    * broadcaster -low-&gt; b
    * broadcaster -low-&gt; c
    * a -high-&gt; b
    * b -high-&gt; c
    * c -high-&gt; inv
    * inv -low-&gt; a
    * a -low-&gt; b
    * b -low-&gt; c
    * c -low-&gt; inv
    * inv -high-&gt; a
    * </pre>
    * <p>After this sequence, the flip-flop modules all end up <b>off</b>, so pushing the button again repeats the same sequence.</p>
    * <p>Here's a more interesting example:</p>
    * <pre>
    * broadcaster -&gt; a
    * %a -&gt; inv, con
    * &amp;inv -&gt; b
    * %b -&gt; con
    * &amp;con -&gt; output
    * </pre>
    * <p>This module configuration includes the broadcaster, two flip-flops (named a and b), a single-input conjunction module (inv), a multi-input conjunction module (con), and an untyped module named output (for testing purposes). The multi-input conjunction module con watches the two flip-flop modules and, if they're both on, sends a <b>low pulse</b> to the output module.</p>
    * <p>Here's what happens if you push the button once:</p>
    * <pre>
    * button -low-&gt; broadcaster
    * broadcaster -low-&gt; a
    * a -high-&gt; inv
    * a -high-&gt; con
    * inv -low-&gt; b
    * con -high-&gt; output
    * b -high-&gt; con
    * con -low-&gt; output
    * </pre>
    * <p>Both flip-flops turn on and a low pulse is sent to output! However, now that both flip-flops are on and con remembers a high pulse from each of its two inputs, pushing the button a second time does something different:</p>
    * <pre>
    * button -low-&gt; broadcaster
    * broadcaster -low-&gt; a
    * a -low-&gt; inv
    * a -low-&gt; con
    * inv -high-&gt; b
    * con -high-&gt; output
    * </pre>
    * <p>Flip-flop a turns off! Now, con remembers a low pulse from module a, and so it sends only a high pulse to output.</p>
    * <p>Push the button a third time:</p>
    * <pre>
    * button -low-&gt; broadcaster
    * broadcaster -low-&gt; a
    * a -high-&gt; inv
    * a -high-&gt; con
    * inv -low-&gt; b
    * con -low-&gt; output
    * b -low-&gt; con
    * con -high-&gt; output
    * </pre>
    * <p>This time, flip-flop a turns on, then flip-flop b turns off. However, before b can turn off, the pulse sent to con is handled first, so it <b>briefly remembers all high pulses</b> for its inputs and sends a low pulse to output. After that, flip-flop b turns off, which causes con to update its state and send a high pulse to output.</p>
    * <p>Finally, with a on and b off, push the button a fourth time:</p>
    * <pre>
    * button -low-&gt; broadcaster
    * broadcaster -low-&gt; a
    * a -low-&gt; inv
    * a -low-&gt; con
    * inv -high-&gt; b
    * con -high-&gt; output
    * </pre>
    * <p>This completes the cycle: a turns off, causing con to remember only low pulses and restoring all modules to their original states.</p>
    * <p>To get the cables warmed up, the Elves have pushed the button 1000 times. How many pulses got sent as a result (including the pulses sent by the button itself)?</p>
    * <p>In the first example, the same thing happens every time the button is pushed: 8 low pulses and 4 high pulses are sent. So, after pushing the button 1000 times, 8000 low pulses and 4000 high pulses are sent. Multiplying these together gives <b>32000000</b>.</p>
    * <p>In the second example, after pushing the button 1000 times, 4250 low pulses and 2750 high pulses are sent. Multiplying these together gives <b>11687500</b>.</p>
    * <p>Consult your module configuration; determine the number of low pulses and high pulses that would be sent after pushing the button 1000 times, waiting for all pulses to be fully handled after each push of the button. <b>What do you get if you multiply the total number of low pulses sent by the total number of high pulses sent?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Registry registry = parser.register().out;
        parser.reset();
        ASD.Root root = parser.root(registry).out;

        return root.pressButton(1000);
    }
}
