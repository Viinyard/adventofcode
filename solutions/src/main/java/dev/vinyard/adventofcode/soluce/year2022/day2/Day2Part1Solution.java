package dev.vinyard.adventofcode.soluce.year2022.day2;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2022, day = 2, part = 1, description = "Rock Paper Scissors", link = "https://adventofcode.com/2022/day/2", tags = "unsolved")
public class Day2Part1Solution implements Solution<Long> {

    /**
    * <h2>--- Day 2: Rock Paper Scissors ---</h2>
    * <p>The Elves begin to set up camp on the beach. To decide whose tent gets to be closest to the snack storage, a giant <a href="https://en.wikipedia.org/wiki/Rock_paper_scissors">Rock Paper Scissors</a> tournament is already in progress.</p>
    * <p>Rock Paper Scissors is a game between two players. Each game contains many rounds; in each round, the players each simultaneously choose one of Rock, Paper, or Scissors using a hand shape. Then, a winner for that round is selected: Rock defeats Scissors, Scissors defeats Paper, and Paper defeats Rock. If both players choose the same shape, the round instead ends in a draw.</p>
    * <p>Appreciative of your help yesterday, one Elf gives you an <b>encrypted strategy guide</b> (your puzzle input) that they say will be sure to help you win. "The first column is what your opponent is going to play: A for Rock, B for Paper, and C for Scissors. The second column--" Suddenly, the Elf is called away to help with someone's tent.</p>
    * <p>The second column, you reason, must be what you should play in response: X for Rock, Y for Paper, and Z for Scissors. Winning every time would be suspicious, so the responses must have been carefully chosen.</p>
    * <p>The winner of the whole tournament is the player with the highest score. Your <b>total score</b> is the sum of your scores for each round. The score for a single round is the score for the <b>shape you selected</b> (1 for Rock, 2 for Paper, and 3 for Scissors) plus the score for the <b>outcome of the round</b> (0 if you lost, 3 if the round was a draw, and 6 if you won).</p>
    * <p>Since you can't be sure if the Elf is trying to help you or trick you, you should calculate the score you would get if you were to follow the strategy guide.</p>
    * <p>For example, suppose you were given the following strategy guide:</p>
    * <pre>
    * A Y
    * B X
    * C Z
    * </pre>
    * <p>This strategy guide predicts and recommends the following:</p>
    * <ul>
    *  <li>In the first round, your opponent will choose Rock (A), and you should choose Paper (Y). This ends in a win for you with a score of <b>8</b> (2 because you chose Paper + 6 because you won).</li>
    *  <li>In the second round, your opponent will choose Paper (B), and you should choose Rock (X). This ends in a loss for you with a score of <b>1</b> (1 + 0).</li>
    *  <li>The third round is a draw with both players choosing Scissors, giving you a score of 3 + 3 = <b>6</b>.</li>
    * </ul>
    * <p>In this example, if you were to follow the strategy guide, you would get a total score of <b>15</b> (8 + 1 + 6).</p>
    * <p><b>What would your total score be if everything goes exactly according to your strategy guide?</b></p>
    */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Part1 root = parser.part1().out;

        return root.result();
    }
}
