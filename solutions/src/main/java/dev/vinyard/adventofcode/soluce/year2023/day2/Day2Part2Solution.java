package dev.vinyard.adventofcode.soluce.year2023.day2;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@AdventOfCodeSolution(year = 2023, day = 2, part = 2, description = "Cube Conundrum", link = "https://adventofcode.com/2023/day/2", tags = "unsolved")
public class Day2Part2Solution implements Solution<Long> {

    /**
     * <h2>--- Part Two ---</h2>
     * <p>The Elf says they've stopped producing snow because they aren't getting any <b>water</b>! He isn't sure why the water stopped; however, he can show you how to get to the water source to check it out for yourself. It's just up ahead!</p>
     * <p>As you continue your walk, the Elf poses a second question: in each game you played, what is the <b>fewest number of cubes of each color</b> that could have been in the bag to make the game possible?</p>
     * <p>Again consider the example games from earlier:</p>
     * <pre>
     * Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
     * Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
     * Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
     * Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
     * Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
     * </pre>
     * <ul>
     *  <li>In game 1, the game could have been played with as few as 4 red, 2 green, and 6 blue cubes. If any color had even one fewer cube, the game would have been impossible.</li>
     *  <li>Game 2 could have been played with a minimum of 1 red, 3 green, and 4 blue cubes.</li>
     *  <li>Game 3 must have been played with at least 20 red, 13 green, and 6 blue cubes.</li>
     *  <li>Game 4 required at least 14 red, 3 green, and 15 blue cubes.</li>
     *  <li>Game 5 needed no fewer than 6 red, 3 green, and 2 blue cubes in the bag.</li>
     * </ul>
     * <p>The <b>power</b> of a set of cubes is equal to the numbers of red, green, and blue cubes multiplied together. The power of the minimum set of cubes in game 1 is 48. In games 2-5 it was 12, 1560, 630, and 36, respectively. Adding up these five powers produces the sum <b>2286</b>.</p>
     * <p>For each game, find the minimum set of cubes that must have been present. <b>What is the sum of the power of these sets?</b></p>
     */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);
        ASD.Games games = parser.games().out;

        return games.getGames().stream().map(g ->
                        g.getHides().stream().map(ASD.Hide::getCubes).flatMap(List::stream)
                                .collect(Collectors.groupingBy(ASD.Cube::getColor,
                                        Collectors.mapping(ASD.Cube::getValue,
                                                Collectors.reducing(Integer::max)
                                        )
                                ))
                ).map(Map::values)
                .mapToLong(v -> v.stream().mapToLong(o -> o.orElse(0)).reduce((a, b) -> a * b).orElse(0))
                .sum();
    }

}
