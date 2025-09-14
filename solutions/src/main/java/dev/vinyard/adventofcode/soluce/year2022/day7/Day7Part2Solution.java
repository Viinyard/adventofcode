package dev.vinyard.adventofcode.soluce.year2022.day7;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2022, day = 7, part = 2, description = "No Space Left On Device", link = "https://adventofcode.com/2022/day/7", tags = "unsolved")
public class Day7Part2Solution implements Solution<Long> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>Now, you're ready to choose a directory to delete.</p>
    * <p>The total disk space available to the filesystem is <b>70000000</b>. To run the update, you need unused space of at least <b>30000000</b>. You need to find a directory you can delete that will <b>free up enough space</b> to run the update.</p>
    * <p>In the example above, the total size of the outermost directory (and thus the total amount of used space) is 48381165; this means that the size of the <b>unused</b> space must currently be 21618835, which isn't quite the 30000000 required by the update. Therefore, the update still requires a directory with total size of at least 8381165 to be deleted before it can run.</p>
    * <p>To achieve this, you have the following options:</p>
    * <ul>
    *  <li>Delete directory e, which would increase unused space by 584.</li>
    *  <li>Delete directory a, which would increase unused space by 94853.</li>
    *  <li>Delete directory d, which would increase unused space by 24933642.</li>
    *  <li>Delete directory /, which would increase unused space by 48381165.</li>
    * </ul>
    * <p>Directories e and a are both too small; deleting them would not free up enough space. However, directories d and / are both big enough! Between these, choose the <b>smallest</b>: d, increasing unused space by <b>24933642</b>.</p>
    * <p>Find the smallest directory that, if deleted, would free up enough space on the filesystem to run the update. <b>What is the total size of that directory?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.part2();
    }
}
