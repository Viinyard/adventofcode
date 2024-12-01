package dev.vinyard.adventofcode.soluce.year2023.day7;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;


@AdventOfCodeSolution(year = 2023, day = 7, part = 1, description = "Camel Cards", link = "https://adventofcode.com/2023/day/7", tags = "unsolved")
public class Day7Part1Solution implements Solution<Long> {

    /**
     * <h2>--- Day 7: Camel Cards ---</h2>
     * <p>Your all-expenses-paid trip turns out to be a one-way, five-minute ride in an <a href="https://en.wikipedia.org/wiki/Airship">airship</a>. (At least it's a <b>cool</b> airship!) It drops you off at the edge of a vast desert and descends back to Island Island.</p>
     * <p>"Did you bring the parts?"</p>
     * <p>You turn around to see an Elf completely covered in white clothing, wearing goggles, and riding a large <a href="https://en.wikipedia.org/wiki/Dromedary">camel</a>.</p>
     * <p>"Did you bring the parts?" she asks again, louder this time. You aren't sure what parts she's looking for; you're here to figure out why the sand stopped.</p>
     * <p>"The parts! For the sand, yes! Come with me; I will show you." She beckons you onto the camel.</p>
     * <p>After riding a bit across the sands of Desert Island, you can see what look like very large rocks covering half of the horizon. The Elf explains that the rocks are all along the part of Desert Island that is directly above Island Island, making it hard to even get there. Normally, they use big machines to move the rocks and filter the sand, but the machines have broken down because Desert Island recently stopped receiving the <b>parts</b> they need to fix the machines.</p>
     * <p>You've already assumed it'll be your job to figure out why the parts stopped when she asks if you can help. You agree automatically.</p>
     * <p>Because the journey will take a few days, she offers to teach you the game of <b>Camel Cards</b>. Camel Cards is sort of similar to <a href="https://en.wikipedia.org/wiki/List_of_poker_hands">poker</a> except it's designed to be easier to play while riding a camel.</p>
     * <p>In Camel Cards, you get a list of <b>hands</b>, and your goal is to order them based on the <b>strength</b> of each hand. A hand consists of <b>five cards</b> labeled one of A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, or 2. The relative strength of each card follows this order, where A is the highest and 2 is the lowest.</p>
     * <p>Every hand is exactly one <b>type</b>. From strongest to weakest, they are:</p>
     * <ul>
     *  <li><b>Five of a kind</b>, where all five cards have the same label: AAAAA</li>
     *  <li><b>Four of a kind</b>, where four cards have the same label and one card has a different label: AA8AA</li>
     *  <li><b>Full house</b>, where three cards have the same label, and the remaining two cards share a different label: 23332</li>
     *  <li><b>Three of a kind</b>, where three cards have the same label, and the remaining two cards are each different from any other card in the hand: TTT98</li>
     *  <li><b>Two pair</b>, where two cards share one label, two other cards share a second label, and the remaining card has a third label: 23432</li>
     *  <li><b>One pair</b>, where two cards share one label, and the other three cards have a different label from the pair and each other: A23A4</li>
     *  <li><b>High card</b>, where all cards' labels are distinct: 23456</li>
     * </ul>
     * <p>Hands are primarily ordered based on type; for example, every <b>full house</b> is stronger than any <b>three of a kind</b>.</p>
     * <p>If two hands have the same type, a second ordering rule takes effect. Start by comparing the <b>first card in each hand</b>. If these cards are different, the hand with the stronger first card is considered stronger. If the first card in each hand have the <b>same label</b>, however, then move on to considering the <b>second card in each hand</b>. If they differ, the hand with the higher second card wins; otherwise, continue with the third card in each hand, then the fourth, then the fifth.</p>
     * <p>So, 33332 and 2AAAA are both <b>four of a kind</b> hands, but 33332 is stronger because its first card is stronger. Similarly, 77888 and 77788 are both a <b>full house</b>, but 77888 is stronger because its third card is stronger (and both hands have the same first and second card).</p>
     * <p>To play Camel Cards, you are given a list of hands and their corresponding <b>bid</b> (your puzzle input). For example:</p>
     * <pre>
     * 32T3K 765
     * T55J5 684
     * KK677 28
     * KTJJT 220
     * QQQJA 483
     * </pre>
     * <p>This example shows five hands; each hand is followed by its <b>bid</b> amount. Each hand wins an amount equal to its bid multiplied by its <b>rank</b>, where the weakest hand gets rank 1, the second-weakest hand gets rank 2, and so on up to the strongest hand. Because there are five hands in this example, the strongest hand will have rank 5 and its bid will be multiplied by 5.</p>
     * <p>So, the first step is to put the hands in order of strength:</p>
     * <ul>
     *  <li>32T3K is the only <b>one pair</b> and the other hands are all a stronger type, so it gets rank <b>1</b>.</li>
     *  <li>KK677 and KTJJT are both <b>two pair</b>. Their first cards both have the same label, but the second card of KK677 is stronger (K vs T), so KTJJT gets rank <b>2</b> and KK677 gets rank <b>3</b>.</li>
     *  <li>T55J5 and QQQJA are both <b>three of a kind</b>. QQQJA has a stronger first card, so it gets rank <b>5</b> and T55J5 gets rank <b>4</b>.</li>
     * </ul>
     * <p>Now, you can determine the total winnings of this set of hands by adding up the result of multiplying each hand's bid with its rank (765 * 1 + 220 * 2 + 28 * 3 + 684 * 4 + 483 * 5). So the <b>total winnings</b> in this example are <b>6440</b>.</p>
     * <p>Find the rank of every hand in your set. <b>What are the total winnings?</b></p>
     */
    @Override
    public Long solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);
        List<ASD.Game> games = parser.games(ASD.Card::fromValue).out;

        Collections.sort(games);

        return Stream.iterate(0, i -> i + 1).limit(games.size()).mapToLong(i -> games.get(i).getBid() * (i + 1)).sum();
    }
}
