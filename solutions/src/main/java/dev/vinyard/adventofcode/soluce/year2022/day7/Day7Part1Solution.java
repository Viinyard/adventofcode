package dev.vinyard.adventofcode.soluce.year2022.day7;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2022, day = 7, part = 1, description = "No Space Left On Device", link = "https://adventofcode.com/2022/day/7", tags = "unsolved")
public class Day7Part1Solution implements Solution<Long> {

    /**
    * <h2>--- Day 7: No Space Left On Device ---</h2>
    * <p>You can hear birds chirping and raindrops hitting leaves as the expedition proceeds. Occasionally, you can even hear much louder sounds in the distance; how big do the animals get out here, anyway?</p>
    * <p>The device the Elves gave you has problems with more than just its communication system. You try to run a system update:</p>
    * <pre>
    * $ system-update --please --pretty-please-with-sugar-on-top
    * Error: No space left on device
    * </pre>
    * <p>Perhaps you can delete some files to make space for the update?</p>
    * <p>You browse around the filesystem to assess the situation and save the resulting terminal output (your puzzle input). For example:</p>
    * <pre>
    * $ cd /
    * $ ls
    * dir a
    * 14848514 b.txt
    * 8504156 c.dat
    * dir d
    * $ cd a
    * $ ls
    * dir e
    * 29116 f
    * 2557 g
    * 62596 h.lst
    * $ cd e
    * $ ls
    * 584 i
    * $ cd ..
    * $ cd ..
    * $ cd d
    * $ ls
    * 4060174 j
    * 8033020 d.log
    * 5626152 d.ext
    * 7214296 k
    * </pre>
    * <p>The filesystem consists of a tree of files (plain data) and directories (which can contain other directories or files). The outermost directory is called /. You can navigate around the filesystem, moving into or out of directories and listing the contents of the directory you're currently in.</p>
    * <p>Within the terminal output, lines that begin with $ are <b>commands you executed</b>, very much like some modern computers:</p>
    * <ul>
    *  <li>cd means <b>change directory</b>. This changes which directory is the current directory, but the specific result depends on the argument: 
    *   <ul>
    *    <li>cd x moves <b>in</b> one level: it looks in the current directory for the directory named x and makes it the current directory.</li>
    *    <li>cd .. moves <b>out</b> one level: it finds the directory that contains the current directory, then makes that directory the current directory.</li>
    *    <li>cd / switches the current directory to the outermost directory, /.</li>
    *   </ul>
    * </li>
    *  <li>ls means <b>list</b>. It prints out all of the files and directories immediately contained by the current directory: 
    *   <ul>
    *    <li>123 abc means that the current directory contains a file named abc with size 123.</li>
    *    <li>dir xyz means that the current directory contains a directory named xyz.</li>
    *   </ul>
    * </li>
    * </ul>
    * <p>Given the commands and output in the example above, you can determine that the filesystem looks visually like this:</p>
    * <pre>
    * - / (dir)
    *   - a (dir)
    *     - e (dir)
    *       - i (file, size=584)
    *     - f (file, size=29116)
    *     - g (file, size=2557)
    *     - h.lst (file, size=62596)
    *   - b.txt (file, size=14848514)
    *   - c.dat (file, size=8504156)
    *   - d (dir)
    *     - j (file, size=4060174)
    *     - d.log (file, size=8033020)
    *     - d.ext (file, size=5626152)
    *     - k (file, size=7214296)
    * </pre>
    * <p>Here, there are four directories: / (the outermost directory), a and d (which are in /), and e (which is in a). These directories also contain files of various sizes.</p>
    * <p>Since the disk is full, your first step should probably be to find directories that are good candidates for deletion. To do this, you need to determine the <b>total size</b> of each directory. The total size of a directory is the sum of the sizes of the files it contains, directly or indirectly. (Directories themselves do not count as having any intrinsic size.)</p>
    * <p>The total sizes of the directories above can be found as follows:</p>
    * <ul>
    *  <li>The total size of directory e is <b>584</b> because it contains a single file i of size 584 and no other directories.</li>
    *  <li>The directory a has total size <b>94853</b> because it contains files f (size 29116), g (size 2557), and h.lst (size 62596), plus file i indirectly (a contains e which contains i).</li>
    *  <li>Directory d has total size <b>24933642</b>.</li>
    *  <li>As the outermost directory, / contains every file. Its total size is <b>48381165</b>, the sum of the size of every file.</li>
    * </ul>
    * <p>To begin, find all of the directories with a total size of <b>at most 100000</b>, then calculate the sum of their total sizes. In the example above, these directories are a and e; the sum of their total sizes is <b>95437</b> (94853 + 584). (As in this example, this process can count files more than once!)</p>
    * <p>Find all of the directories with a total size of at most 100000. <b>What is the sum of the total sizes of those directories?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.part1();
    }
}
