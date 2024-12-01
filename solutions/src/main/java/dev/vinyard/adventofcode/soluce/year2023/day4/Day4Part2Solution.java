package dev.vinyard.adventofcode.soluce.year2023.day4;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.Objects;
import java.util.stream.Stream;


@AdventOfCodeSolution(year = 2023, day = 4, part = 2, description = "Scratchcards", link = "https://adventofcode.com/2023/day/4", tags = "unsolved")
public class Day4Part2Solution implements Solution<Long> {

    /**
     * <h2>--- Part Two ---</h2>
     * <p>Just as you're about to report your findings to the Elf, one of you realizes that the rules have actually been printed on the back of every card this whole time.</p>
     * <p>There's no such thing as "points". Instead, scratchcards only cause you to <b>win more scratchcards</b> equal to the number of winning numbers you have.</p>
     * <p>Specifically, you win <b>copies</b> of the scratchcards below the winning card equal to the number of matches. So, if card 10 were to have 5 matching numbers, you would win one copy each of cards 11, 12, 13, 14, and 15.</p>
     * <p>Copies of scratchcards are scored like normal scratchcards and have the <b>same card number</b> as the card they copied. So, if you win a copy of card 10 and it has 5 matching numbers, it would then win a copy of the same cards that the original card 10 won: cards 11, 12, 13, 14, and 15. This process repeats until none of the copies cause you to win any more cards. (Cards will never make you copy a card past the end of the table.)</p>
     * <p>This time, the above example goes differently:</p>
     * <pre>
     * Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
     * Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
     * Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
     * Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
     * Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
     * Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
     * </pre>
     * <ul>
     *  <li>Card 1 has four matching numbers, so you win one copy each of the next four cards: cards 2, 3, 4, and 5.</li>
     *  <li>Your original card 2 has two matching numbers, so you win one copy each of cards 3 and 4.</li>
     *  <li>Your copy of card 2 also wins one copy each of cards 3 and 4.</li>
     *  <li>Your four instances of card 3 (one original and three copies) have two matching numbers, so you win <b>four</b> copies each of cards 4 and 5.</li>
     *  <li>Your eight instances of card 4 (one original and seven copies) have one matching number, so you win <b>eight</b> copies of card 5.</li>
     *  <li>Your fourteen instances of card 5 (one original and thirteen copies) have no matching numbers and win no more cards.</li>
     *  <li>Your one instance of card 6 (one original) has no matching numbers and wins no more cards.</li>
     * </ul>
     * <p>Once all of the originals and copies have been processed, you end up with <b>1</b> instance of card 1, <b>2</b> instances of card 2, <b>4</b> instances of card 3, <b>8</b> instances of card 4, <b>14</b> instances of card 5, and <b>1</b> instance of card 6. In total, this example pile of scratchcards causes you to ultimately have <b>30</b> scratchcards!</p>
     * <p>Process all of the original and copied scratchcards until no more scratchcards are won. Including the original set of scratchcards, <b>how many total scratchcards do you end up with?</b></p>
     */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);
        ASD.Scratchcard scratchcard = parser.scratchcards().out;

        return Stream.iterate(scratchcard.getFirst(), Objects::nonNull, ASD.Scratchcard::getNext).peek(ASD.Scratchcard::scratch).mapToLong(ASD.Scratchcard::getNumberOfCopies).sum();
    }
}
