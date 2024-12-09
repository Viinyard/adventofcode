package dev.vinyard.adventofcode.soluce.year2024.day9;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import dev.vinyard.adventofcode.utils.FileReader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.net.URISyntaxException;

@AdventOfCodeSolution(year = 2024, day = 9, part = 1, description = "Disk Fragmenter", link = "https://adventofcode.com/2024/day/9", tags = "unsolved")
public class Day9Part1Solution implements Solution<Object> {

    /**
    * <h2>--- Day 9: Disk Fragmenter ---</h2>
    * <p>Another push of the button leaves you in the familiar hallways of some friendly <a href="/2021/day/23">amphipods</a>! Good thing you each somehow got your own personal mini submarine. The Historians jet away in search of the Chief, mostly by driving directly into walls.</p>
    * <p>While The Historians quickly figure out how to pilot these things, you notice an amphipod in the corner struggling with his computer. He's trying to make more contiguous free space by compacting all of the files, but his program isn't working; you offer to help.</p>
    * <p>He shows you the <b>disk map</b> (your puzzle input) he's already generated. For example:</p>
    * <pre>
    * 2333133121414131402</pre>
    * <p>The disk map uses a dense format to represent the layout of <b>files</b> and <b>free space</b> on the disk. The digits alternate between indicating the length of a file and the length of free space.</p>
    * <p>So, a disk map like 12345 would represent a one-block file, two blocks of free space, a three-block file, four blocks of free space, and then a five-block file. A disk map like 90909 would represent three nine-block files in a row (with no free space between them).</p>
    * <p>Each file on disk also has an <b>ID number</b> based on the order of the files as they appear <b>before</b> they are rearranged, starting with ID 0. So, the disk map 12345 has three files: a one-block file with ID 0, a three-block file with ID 1, and a five-block file with ID 2. Using one character for each block where digits are the file ID and . is free space, the disk map 12345 represents these individual blocks:</p>
    * <pre>
    * 0..111....22222</pre>
    * <p>The first example above, 2333133121414131402, represents these individual blocks:</p>
    * <pre>
    * 00...111...2...333.44.5555.6666.777.888899</pre>
    * <p>The amphipod would like to <b>move file blocks one at a time</b> from the end of the disk to the leftmost free space block (until there are no gaps remaining between file blocks). For the disk map 12345, the process looks like this:</p>
    * <pre>
    * 0..111....22222
    * 02.111....2222.
    * 022111....222..
    * 0221112...22...
    * 02211122..2....
    * 022111222......
    * </pre>
    * <p>The first example requires a few more steps:</p>
    * <pre>
    * 00...111...2...333.44.5555.6666.777.888899
    * 009..111...2...333.44.5555.6666.777.88889.
    * 0099.111...2...333.44.5555.6666.777.8888..
    * 00998111...2...333.44.5555.6666.777.888...
    * 009981118..2...333.44.5555.6666.777.88....
    * 0099811188.2...333.44.5555.6666.777.8.....
    * 009981118882...333.44.5555.6666.777.......
    * 0099811188827..333.44.5555.6666.77........
    * 00998111888277.333.44.5555.6666.7.........
    * 009981118882777333.44.5555.6666...........
    * 009981118882777333644.5555.666............
    * 00998111888277733364465555.66.............
    * 0099811188827773336446555566..............
    * </pre>
    * <p>The final step of this file-compacting process is to update the <b>filesystem checksum</b>. To calculate the checksum, add up the result of multiplying each of these blocks' position with the file ID number it contains. The leftmost block is in position 0. If a block contains free space, skip it instead.</p>
    * <p>Continuing the first example, the first few blocks' position multiplied by its file ID number are 0 * 0 = 0, 1 * 0 = 0, 2 * 9 = 18, 3 * 9 = 27, 4 * 8 = 32, and so on. In this example, the checksum is the sum of these, <b>1928</b>.</p>
    * <p>Compact the amphipod's hard drive using the process he requested. <b>What is the resulting filesystem checksum?</b> (Be careful copy/pasting the input for this puzzle; it is a single, very long line.)</p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);
        // TODO get the ASD from the parser

        return null;
    }
}