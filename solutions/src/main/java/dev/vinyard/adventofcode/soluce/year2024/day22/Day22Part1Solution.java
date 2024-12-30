package dev.vinyard.adventofcode.soluce.year2024.day22;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import dev.vinyard.adventofcode.utils.FileReader;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.net.URISyntaxException;

@AdventOfCodeSolution(year = 2024, day = 22, part = 1, description = "Monkey Market", link = "https://adventofcode.com/2024/day/22", tags = "unsolved")
public class Day22Part1Solution implements Solution<Object> {

    /**
    * <h2>--- Day 22: Monkey Market ---</h2>
    * <p>As you're all teleported deep into the jungle, a <a href="/2022/day/11">monkey</a> steals The Historians' device! You'll need to get it back while The Historians are looking for the Chief.</p>
    * <p>The monkey that stole the device seems willing to trade it, but only in exchange for an absurd number of bananas. Your only option is to buy bananas on the Monkey Exchange Market.</p>
    * <p>You aren't sure how the Monkey Exchange Market works, but one of The Historians senses trouble and comes over to help. Apparently, they've been studying these monkeys for a while and have deciphered their secrets.</p>
    * <p>Today, the Market is full of monkeys buying <b>good hiding spots</b>. Fortunately, because of the time you recently spent in this jungle, you know lots of good hiding spots you can sell! If you sell enough hiding spots, you should be able to get enough bananas to buy the device back.</p>
    * <p>On the Market, the buyers seem to use random prices, but their prices are actually only <a href="https://en.wikipedia.org/wiki/Pseudorandom_number_generator">pseudorandom</a>! If you know the secret of how they pick their prices, you can wait for the perfect time to sell.</p>
    * <p>The part about secrets is literal, the Historian explains. Each buyer produces a pseudorandom sequence of secret numbers where each secret is derived from the previous.</p>
    * <p>In particular, each buyer's <b>secret</b> number evolves into the next secret number in the sequence via the following process:</p>
    * <ul>
    *  <li>Calculate the result of <b>multiplying the secret number by 64</b>. Then, <b>mix</b> this result into the secret number. Finally, <b>prune</b> the secret number.</li>
    *  <li>Calculate the result of <b>dividing the secret number by 32</b>. Round the result down to the nearest integer. Then, <b>mix</b> this result into the secret number. Finally, <b>prune</b> the secret number.</li>
    *  <li>Calculate the result of <b>multiplying the secret number by 2048</b>. Then, <b>mix</b> this result into the secret number. Finally, <b>prune</b> the secret number.</li>
    * </ul>
    * <p>Each step of the above process involves <b>mixing</b> and <b>pruning</b>:</p>
    * <ul>
    *  <li>To <b>mix</b> a value into the secret number, calculate the <a href="https://en.wikipedia.org/wiki/Bitwise_operation#XOR">bitwise XOR</a> of the given value and the secret number. Then, the secret number becomes the result of that operation. (If the secret number is 42 and you were to <b>mix</b> 15 into the secret number, the secret number would become 37.)</li>
    *  <li>To <b>prune</b> the secret number, calculate the value of the secret number <a href="https://en.wikipedia.org/wiki/Modulo">modulo</a> 16777216. Then, the secret number becomes the result of that operation. (If the secret number is 100000000 and you were to <b>prune</b> the secret number, the secret number would become 16113920.)</li>
    * </ul>
    * <p>After this process completes, the buyer is left with the next secret number in the sequence. The buyer can repeat this process as many times as necessary to produce more secret numbers.</p>
    * <p>So, if a buyer had a secret number of 123, that buyer's next ten secret numbers would be:</p>
    * <pre>
    * 15887950
    * 16495136
    * 527345
    * 704524
    * 1553684
    * 12683156
    * 11100544
    * 12249484
    * 7753432
    * 5908254
    * </pre>
    * <p>Each buyer uses their own secret number when choosing their price, so it's important to be able to predict the sequence of secret numbers for each buyer. Fortunately, the Historian's research has uncovered the <b>initial secret number of each buyer</b> (your puzzle input). For example:</p>
    * <pre>
    * 1
    * 10
    * 100
    * 2024
    * </pre>
    * <p>This list describes the <b>initial secret number</b> of four different secret-hiding-spot-buyers on the Monkey Exchange Market. If you can simulate secret numbers from each buyer, you'll be able to predict all of their future prices.</p>
    * <p>In a single day, buyers each have time to generate 2000 <b>new</b> secret numbers. In this example, for each buyer, their initial secret number and the 2000th new secret number they would generate are:</p>
    * <pre>
    * 1: 8685429
    * 10: 4700978
    * 100: 15273692
    * 2024: 8667524
    * </pre>
    * <p>Adding up the 2000th new secret number for each buyer produces <b>37327623</b>.</p>
    * <p>For each buyer, simulate the creation of 2000 new secret numbers. <b>What is the sum of the 2000th secret number generated by each buyer?</b></p>
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
