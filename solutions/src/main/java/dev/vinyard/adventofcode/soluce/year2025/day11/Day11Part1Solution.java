package dev.vinyard.adventofcode.soluce.year2025.day11;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2025, day = 11, part = 1, description = "Reactor", link = "https://adventofcode.com/2025/day/11", tags = "unsolved")
public class Day11Part1Solution implements Solution<Long> {

    /**
    * <h2>--- Day 11: Reactor ---</h2>
    * <p>You hear some loud beeping coming from a hatch in the floor of the factory, so you decide to check it out. Inside, you find several large electrical conduits and a ladder.</p>
    * <p>Climbing down the ladder, you discover the source of the beeping: a large, toroidal reactor which powers the factory above. Some Elves here are hurriedly running between the reactor and a nearby server rack, apparently trying to fix something.</p>
    * <p>One of the Elves notices you and rushes over. "It's a good thing you're here! We just installed a new <b>server rack</b>, but we aren't having any luck getting the reactor to communicate with it!" You glance around the room and see a tangle of cables and devices running from the server rack to the reactor. She rushes off, returning a moment later with a list of the devices and their outputs (your puzzle input).</p>
    * <p>For example:</p>
    * <pre>
    * aaa: you hhh
    * you: bbb ccc
    * bbb: ddd eee
    * ccc: ddd eee fff
    * ddd: ggg
    * eee: out
    * fff: out
    * ggg: out
    * hhh: ccc fff iii
    * iii: out
    * </pre>
    * <p>Each line gives the name of a device followed by a list of the devices to which its outputs are attached. So, bbb: ddd eee means that device bbb has two outputs, one leading to device ddd and the other leading to device eee.</p>
    * <p>The Elves are pretty sure that the issue isn't due to any specific device, but rather that the issue is triggered by data following some specific <b>path</b> through the devices. Data only ever flows from a device through its outputs; it can't flow backwards.</p>
    * <p>After dividing up the work, the Elves would like you to focus on the devices starting with the one next to you (an Elf hastily attaches a label which just says <b>you</b>) and ending with the main output to the reactor (which is the device with the label <b>out</b>).</p>
    * <p>To help the Elves figure out which path is causing the issue, they need you to find <b>every</b> path from you to out.</p>
    * <p>In this example, these are all of the paths from you to out:</p>
    * <ul>
    *  <li>Data could take the connection from you to bbb, then from bbb to ddd, then from ddd to ggg, then from ggg to out.</li>
    *  <li>Data could take the connection to bbb, then to eee, then to out.</li>
    *  <li>Data could go to ccc, then ddd, then ggg, then out.</li>
    *  <li>Data could go to ccc, then eee, then out.</li>
    *  <li>Data could go to ccc, then fff, then out.</li>
    * </ul>
    * <p>In total, there are <b>5</b> different paths leading from you to out.</p>
    * <p><b>How many different paths lead from you to out?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.solution1();
    }
}
