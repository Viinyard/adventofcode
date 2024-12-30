package dev.vinyard.adventofcode.soluce.year2024.day22;

import dev.vinyard.aoc.plugins.solution.api.Solution;
import dev.vinyard.aoc.plugins.solution.api.annotation.AdventOfCodeSolution;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

@AdventOfCodeSolution(year = 2024, day = 22, part = 2, description = "Monkey Market", link = "https://adventofcode.com/2024/day/22", tags = "unsolved")
public class Day22Part2Solution implements Solution<Object> {

    /**
    * <h2>--- Part Two ---</h2>
    * <p>Of course, the secret numbers aren't the prices each buyer is offering! That would be ridiculous. Instead, the <b>prices</b> the buyer offers are just the <b>ones digit</b> of each of their secret numbers.</p>
    * <p>So, if a buyer starts with a secret number of 123, that buyer's first ten <b>prices</b> would be:</p>
    * <pre>
    * 3 (from 123)
    * 0 (from 15887950)
    * 6 (from 16495136)
    * 5 (etc.)
    * 4
    * 4
    * 6
    * 4
    * 4
    * 2
    * </pre>
    * <p>This price is the number of <b>bananas</b> that buyer is offering in exchange for your information about a new hiding spot. However, you still don't speak <a href="/2022/day/21">monkey</a>, so you can't negotiate with the buyers directly. The Historian speaks a little, but not enough to negotiate; instead, he can ask another monkey to negotiate on your behalf.</p>
    * <p>Unfortunately, the monkey only knows how to decide when to sell by looking at the <b>changes</b> in price. Specifically, the monkey will only look for a specific sequence of <b>four consecutive changes</b> in price, then immediately sell when it sees that sequence.</p>
    * <p>So, if a buyer starts with a secret number of 123, that buyer's first ten secret numbers, prices, and the associated changes would be:</p>
    * <pre>
    *      123: 3 
    * 15887950: 0 (-3)
    * 16495136: 6 (6)
    *   527345: 5 (-1)
    *   704524: 4 (-1)
    *  1553684: 4 (0)
    * 12683156: 6 (2)
    * 11100544: 4 (-2)
    * 12249484: 4 (0)
    *  7753432: 2 (-2)
    * </pre>
    * <p>Note that the first price has no associated change because there was no previous price to compare it with.</p>
    * <p>In this short example, within just these first few prices, the highest price will be 6, so it would be nice to give the monkey instructions that would make it sell at that time. The first 6 occurs after only two changes, so there's no way to instruct the monkey to sell then, but the second 6 occurs after the changes -1,-1,0,2. So, if you gave the monkey that sequence of changes, it would wait until the first time it sees that sequence and then immediately sell your hiding spot information at the current price, winning you 6 bananas.</p>
    * <p>Each buyer only wants to buy one hiding spot, so after the hiding spot is sold, the monkey will move on to the next buyer. If the monkey <b>never</b> hears that sequence of price changes from a buyer, the monkey will never sell, and will instead just move on to the next buyer.</p>
    * <p>Worse, you can only give the monkey <b>a single sequence</b> of four price changes to look for. You can't change the sequence between buyers.</p>
    * <p>You're going to need as many bananas as possible, so you'll need to <b>determine which sequence</b> of four price changes will cause the monkey to get you the <b>most bananas overall</b>. Each buyer is going to generate 2000 secret numbers after their initial secret number, so, for each buyer, you'll have <b>2000 price changes</b> in which your sequence can occur.</p>
    * <p>Suppose the initial secret number of each buyer is:</p>
    * <pre>
    * 1
    * 2
    * 3
    * 2024
    * </pre>
    * <p>There are many sequences of four price changes you could tell the monkey, but for these four buyers, the sequence that will get you the most bananas is -2,1,-1,3. Using that sequence, the monkey will make the following sales:</p>
    * <ul>
    *  <li>For the buyer with an initial secret number of 1, changes -2,1,-1,3 first occur when the price is <b>7</b>.</li>
    *  <li>For the buyer with initial secret 2, changes -2,1,-1,3 first occur when the price is <b>7</b>.</li>
    *  <li>For the buyer with initial secret 3, the change sequence -2,1,-1,3 <b>does not occur</b> in the first 2000 changes.</li>
    *  <li>For the buyer starting with 2024, changes -2,1,-1,3 first occur when the price is <b>9</b>.</li>
    * </ul>
    * <p>So, by asking the monkey to sell the first time each buyer's prices go down 2, then up 1, then down 1, then up 3, you would get <b>23</b> (7 + 7 + 9) bananas!</p>
    * <p>Figure out the best sequence to tell the monkey so that by looking for that same sequence of changes in every buyer's future prices, you get the most bananas in total. <b>What is the most bananas you can get?</b></p>
    */
    @Override
    public Object solve(String input) {
        CharStream charStream = CharStreams.fromString(input);

        SolutionLexer lexer = new SolutionLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SolutionParser parser = new SolutionParser(tokens);

        ASD.Root root = parser.root().out;

        return root.getBestSequence(2000);
    }
}
