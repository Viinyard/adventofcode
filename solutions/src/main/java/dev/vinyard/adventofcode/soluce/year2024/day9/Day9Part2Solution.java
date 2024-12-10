package dev.vinyard.adventofcode.soluce.year2024.day9;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2024, day = 9, part = 2, description = "Disk Fragmenter", link = "https://adventofcode.com/2024/day/9", tags = "unsolved")
public class Day9Part2Solution implements Solution<Object> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>Upon completion, two things immediately become clear. First, the disk definitely has a lot more contiguous free space, just like the amphipod hoped. Second, the computer is running much more slowly! Maybe introducing all of that <a href="https://en.wikipedia.org/wiki/File_system_fragmentation">file system fragmentation</a> was a bad idea?</p>
    * <p>The eager amphipod already has a new plan: rather than move individual blocks, he'd like to try compacting the files on his disk by moving <b>whole files</b> instead.</p>
    * <p>This time, attempt to move whole files to the leftmost span of free space blocks that could fit the file. Attempt to move each file exactly once in order of <b>decreasing file ID number</b> starting with the file with the highest file ID number. If there is no span of free space to the left of a file that is large enough to fit the file, the file does not move.</p>
    * <p>The first example from above now proceeds differently:</p>
    * <pre>
    * 00...111...2...333.44.5555.6666.777.888899
    * 0099.111...2...333.44.5555.6666.777.8888..
    * 0099.1117772...333.44.5555.6666.....8888..
    * 0099.111777244.333....5555.6666.....8888..
    * 00992111777.44.333....5555.6666.....8888..
    * </pre>
    * <p>The process of updating the filesystem checksum is the same; now, this example's checksum would be <b>2858</b>.</p>
    * <p>Start over, now compacting the amphipod's hard drive using this new method instead. <b>What is the resulting filesystem checksum?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.DiskMap diskMap = parser.root().out;

        System.out.println(diskMap);

        diskMap.compressWithoutFragmentation();

        System.out.println(diskMap);

        return diskMap.checksum();
    }
}
