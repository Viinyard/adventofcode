package dev.vinyard.adventofcode.soluce.year2023.day25;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2023, day = 25, part = 1, description = "Snowverload", link = "https://adventofcode.com/2023/day/25", tags = "unsolved")
public class Day25Part1Solution implements Solution<Long> {

    /**
    * <h2>--- Day 25: Snowverload ---</h2>
    * <p><b>Still</b> somehow without snow, you go to the last place you haven't checked: the center of Snow Island, directly below the waterfall.</p>
    * <p>Here, someone has clearly been trying to fix the problem. Scattered everywhere are hundreds of weather machines, almanacs, communication modules, hoof prints, machine parts, mirrors, lenses, and so on.</p>
    * <p>Somehow, everything has been <b>wired together</b> into a massive snow-producing apparatus, but nothing seems to be running. You check a tiny screen on one of the communication modules: Error 2023. It doesn't say what Error 2023 means, but it <b>does</b> have the phone number for a support line printed on it.</p>
    * <p>"Hi, you've reached Weather Machines And So On, Inc. How can I help you?" You explain the situation.</p>
    * <p>"Error 2023, you say? Why, that's a power overload error, of course! It means you have too many components plugged in. Try unplugging some components and--" You explain that there are hundreds of components here and you're in a bit of a hurry.</p>
    * <p>"Well, let's see how bad it is; do you see a <b>big red reset button</b> somewhere? It should be on its own module. If you push it, it probably won't fix anything, but it'll report how overloaded things are." After a minute or two, you find the reset button; it's so big that it takes two hands just to get enough leverage to push it. Its screen then displays:</p>
    * <pre>
    * SYSTEM OVERLOAD!
    * 
    * Connected components would require
    * power equal to at least <b>100 stars</b>!
    * </pre>
    * <p>"Wait, <b>how</b> many components did you say are plugged in? With that much equipment, you could produce snow for an <b>entire</b>--" You disconnect the call.</p>
    * <p>You have nowhere near that many stars - you need to find a way to disconnect at least half of the equipment here, but it's already Christmas! You only have time to disconnect <b>three wires</b>.</p>
    * <p>Fortunately, someone left a wiring diagram (your puzzle input) that shows <b>how the components are connected</b>. For example:</p>
    * <pre>
    * jqt: rhn xhk nvd
    * rsh: frs pzl lsr
    * xhk: hfx
    * cmg: qnr nvd lhk bvb
    * rhn: xhk bvb hfx
    * bvb: xhk hfx
    * pzl: lsr hfx nvd
    * qnr: nvd
    * ntq: jqt hfx bvb xhk
    * nvd: lhk
    * lsr: lhk
    * rzs: qnr cmg lsr rsh
    * frs: qnr lhk lsr
    * </pre>
    * <p>Each line shows the <b>name of a component</b>, a colon, and then <b>a list of other components</b> to which that component is connected. Connections aren't directional; abc: xyz and xyz: abc both represent the same configuration. Each connection between two components is represented only once, so some components might only ever appear on the left or right side of a colon.</p>
    * <p>In this example, if you disconnect the wire between hfx/pzl, the wire between bvb/cmg, and the wire between nvd/jqt, you will <b>divide the components into two separate, disconnected groups</b>:</p>
    * <ul>
    *  <li><b>9</b> components: cmg, frs, lhk, lsr, nvd, pzl, qnr, rsh, and rzs.</li>
    *  <li><b>6</b> components: bvb, hfx, jqt, ntq, rhn, and xhk.</li>
    * </ul>
    * <p>Multiplying the sizes of these groups together produces <b>54</b>.</p>
    * <p>Find the three wires you need to disconnect in order to divide the components into two separate groups. <b>What do you get if you multiply the sizes of these two groups together?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.getBlockProductOfGraph();
    }
}
