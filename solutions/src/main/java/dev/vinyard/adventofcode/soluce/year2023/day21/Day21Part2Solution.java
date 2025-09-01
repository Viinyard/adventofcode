package dev.vinyard.adventofcode.soluce.year2023.day21;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2023, day = 21, part = 2, description = "Step Counter", link = "https://adventofcode.com/2023/day/21", tags = "unsolved")
public class Day21Part2Solution implements Solution<Long> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>The Elf seems confused by your answer until he realizes his mistake: he was reading from a list of his favorite numbers that are both perfect squares and perfect cubes, not his step counter.</p>
    * <p>The <b>actual</b> number of steps he needs to get today is exactly <b>26501365</b>.</p>
    * <p>He also points out that the garden plots and rocks are set up so that the map <b>repeats infinitely</b> in every direction.</p>
    * <p>So, if you were to look one additional map-width or map-height out from the edge of the example map above, you would find that it keeps repeating:</p>
    * <pre>
    * .................................
    * .....###.#......###.#......###.#.
    * .###.##..#..###.##..#..###.##..#.
    * ..#.#...#....#.#...#....#.#...#..
    * ....#.#........#.#........#.#....
    * .##...####..##...####..##...####.
    * .##..#...#..##..#...#..##..#...#.
    * .......##.........##.........##..
    * .##.#.####..##.#.####..##.#.####.
    * .##..##.##..##..##.##..##..##.##.
    * .................................
    * .................................
    * .....###.#......###.#......###.#.
    * .###.##..#..###.##..#..###.##..#.
    * ..#.#...#....#.#...#....#.#...#..
    * ....#.#........#.#........#.#....
    * .##...####..##..S####..##...####.
    * .##..#...#..##..#...#..##..#...#.
    * .......##.........##.........##..
    * .##.#.####..##.#.####..##.#.####.
    * .##..##.##..##..##.##..##..##.##.
    * .................................
    * .................................
    * .....###.#......###.#......###.#.
    * .###.##..#..###.##..#..###.##..#.
    * ..#.#...#....#.#...#....#.#...#..
    * ....#.#........#.#........#.#....
    * .##...####..##...####..##...####.
    * .##..#...#..##..#...#..##..#...#.
    * .......##.........##.........##..
    * .##.#.####..##.#.####..##.#.####.
    * .##..##.##..##..##.##..##..##.##.
    * .................................
    * </pre>
    * <p>This is just a tiny three-map-by-three-map slice of the inexplicably-infinite farm layout; garden plots and rocks repeat as far as you can see. The Elf still starts on the one middle tile marked S, though - every other repeated S is replaced with a normal garden plot (.).</p>
    * <p>Here are the number of reachable garden plots in this new infinite version of the example map for different numbers of steps:</p>
    * <ul>
    *  <li>In exactly 6 steps, he can still reach <b>16</b> garden plots.</li>
    *  <li>In exactly 10 steps, he can reach any of <b>50</b> garden plots.</li>
    *  <li>In exactly 50 steps, he can reach <b>1594</b> garden plots.</li>
    *  <li>In exactly 100 steps, he can reach <b>6536</b> garden plots.</li>
    *  <li>In exactly 500 steps, he can reach <b>167004</b> garden plots.</li>
    *  <li>In exactly 1000 steps, he can reach <b>668697</b> garden plots.</li>
    *  <li>In exactly 5000 steps, he can reach <b>16733044</b> garden plots.</li>
    * </ul>
    * <p>However, the step count the Elf needs is much larger! Starting from the garden plot marked S on your infinite map, <b>how many garden plots could the Elf reach in exactly 26501365 steps?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.countReachablePlotsFromElvePart2();
    }
}
