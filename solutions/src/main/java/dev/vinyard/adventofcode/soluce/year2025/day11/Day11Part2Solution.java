package dev.vinyard.adventofcode.soluce.year2025.day11;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2025, day = 11, part = 2, description = "Reactor", link = "https://adventofcode.com/2025/day/11", tags = "unsolved")
public class Day11Part2Solution implements Solution<Object> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>Thanks in part to your analysis, the Elves have figured out a little bit about the issue. They now know that the problematic data path passes through both dac (a <a href="https://en.wikipedia.org/wiki/Digital-to-analog_converter">digital-to-analog converter</a>) and fft (a device which performs a <a href="https://en.wikipedia.org/wiki/Fast_Fourier_transform">fast Fourier transform</a>).</p>
    * <p>They're still not sure which specific path is the problem, and so they now need you to find every path from svr (the server rack) to out. However, the paths you find must all also visit both dac <b>and</b> fft (in any order).</p>
    * <p>For example:</p>
    * <pre>
    * svr: aaa bbb
    * aaa: fft
    * fft: ccc
    * bbb: tty
    * tty: ccc
    * ccc: ddd eee
    * ddd: hub
    * hub: fff
    * eee: dac
    * dac: fff
    * fff: ggg hhh
    * ggg: out
    * hhh: out
    * </pre>
    * <p>This new list of devices contains many paths from svr to out:</p>
    * <pre>
    * svr,aaa,<b>fft</b>,ccc,ddd,hub,fff,ggg,out
    * svr,aaa,<b>fft</b>,ccc,ddd,hub,fff,hhh,out
    * svr,aaa,<b>fft</b>,ccc,eee,<b>dac</b>,fff,ggg,out
    * svr,aaa,<b>fft</b>,ccc,eee,<b>dac</b>,fff,hhh,out
    * svr,bbb,tty,ccc,ddd,hub,fff,ggg,out
    * svr,bbb,tty,ccc,ddd,hub,fff,hhh,out
    * svr,bbb,tty,ccc,eee,<b>dac</b>,fff,ggg,out
    * svr,bbb,tty,ccc,eee,<b>dac</b>,fff,hhh,out
    * </pre>
    * <p>However, only <b>2</b> paths from svr to out visit both dac and fft.</p>
    * <p>Find all of the paths that lead from svr to out. <b>How many of those paths visit both dac and fft?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);
        
        ASD.Root root = parser.root().out;
        
        return root.solution2();
    }
}
