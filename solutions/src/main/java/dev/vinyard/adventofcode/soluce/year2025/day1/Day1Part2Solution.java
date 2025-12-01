package dev.vinyard.adventofcode.soluce.year2025.day1;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import dev.vinyard.adventofcode.utils.FileReader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.net.URISyntaxException;

@AdventOfCodeSolution(year = 2025, day = 1, part = 2, description = "Secret Entrance", link = "https://adventofcode.com/2025/day/1", tags = "unsolved")
public class Day1Part2Solution implements Solution<Object> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>You're sure that's the right password, but the door won't open. You knock, but nobody answers. You build a snowman while you think.</p>
    * <p>As you're rolling the snowballs for your snowman, you find another security document that must have fallen into the snow:</p>
    * <p>"Due to newer security protocols, please use <b>password method 0x434C49434B</b> until further notice."</p>
    * <p>You remember from the training seminar that "method 0x434C49434B" means you're actually supposed to count the number of times <b>any click</b> causes the dial to point at 0, regardless of whether it happens during a rotation or at the end of one.</p>
    * <p>Following the same rotations as in the above example, the dial points at zero a few extra times during its rotations:</p>
    * <ul>
    *  <li>The dial starts by pointing at 50.</li>
    *  <li>The dial is rotated L68 to point at 82; during this rotation, it points at 0 <b>once</b>.</li>
    *  <li>The dial is rotated L30 to point at 52.</li>
    *  <li>The dial is rotated R48 to point at <b>0</b>.</li>
    *  <li>The dial is rotated L5 to point at 95.</li>
    *  <li>The dial is rotated R60 to point at 55; during this rotation, it points at 0 <b>once</b>.</li>
    *  <li>The dial is rotated L55 to point at <b>0</b>.</li>
    *  <li>The dial is rotated L1 to point at 99.</li>
    *  <li>The dial is rotated L99 to point at <b>0</b>.</li>
    *  <li>The dial is rotated R14 to point at 14.</li>
    *  <li>The dial is rotated L82 to point at 32; during this rotation, it points at 0 <b>once</b>.</li>
    * </ul>
    * <p>In this example, the dial points at 0 three times at the end of a rotation, plus three more times during a rotation. So, in this example, the new password would be <b>6</b>.</p>
    * <p>Be careful: if the dial were pointing at 50, a single rotation like R1000 would cause the dial to point at 0 ten times before returning back to 50!</p>
    * <p>Using password method 0x434C49434B, <b>what is the password to open the door?</b></p>
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
