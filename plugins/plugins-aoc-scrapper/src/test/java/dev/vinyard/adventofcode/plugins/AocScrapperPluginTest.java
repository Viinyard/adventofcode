package dev.vinyard.adventofcode.plugins;


import dev.vinyard.aoc.plugins.AoCScrapper;
import org.assertj.core.api.Assertions;
import org.jsoup.HttpStatusException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class AocScrapperPluginTest {

    private static Stream<Arguments> getTitle() {
        return Stream.of(
                Arguments.of("https://adventofcode.com/2023/day/1", 0, "Trebuchet?!", null, null),
                Arguments.of("https://adventofcode.com/2023/day/2", 0, "Cube Conundrum", null, null),
                Arguments.of("https://adventofcode.com/2023/day/3", 0, "Gear Ratios", null, null),
                Arguments.of("https://adventofcode.com/2023/day/4", 0, "Scratchcards", null, null),
                Arguments.of("https://adventofcode.com/2023/day/5", 0, "If You Give A Seed A Fertilizer", null, null),
                Arguments.of("https://adventofcode.com/2023/day/6", 0, "Wait For It", null, null),
                Arguments.of("https://adventofcode.com/2023/day/7", 0, "Camel Cards", null, null),
                Arguments.of("https://adventofcode.com/2023/day/8", 0, "Haunted Wasteland", null, null),
                Arguments.of("https://adventofcode.com/2023/day/9", 0, "Mirage Maintenance", null, null),
                Arguments.of("https://adventofcode.com/2023/day/10", 0, "Pipe Maze", null, null),
                Arguments.of("https://adventofcode.com/2023/day/11", 0, "Cosmic Expansion", null, null),
                Arguments.of("https://adventofcode.com/2023/day/12", 0, "Hot Springs", null, null),
                Arguments.of("https://adventofcode.com/2023/day/13", 0, "Point of Incidence", null, null),
                Arguments.of("https://adventofcode.com/2023/day/14", 0, "Parabolic Reflector Dish", null, null),
                Arguments.of("https://adventofcode.com/2023/day/15", 0, "Lens Library", null, null),
                Arguments.of("https://adventofcode.com/2023/day/16", 0, "The Floor Will Be Lava", null, null),
                Arguments.of("https://adventofcode.com/2023/day/17", 0, "Clumsy Crucible", null, null),
                Arguments.of("https://adventofcode.com/2023/day/18", 0, "Lavaduct Lagoon", null, null),
                Arguments.of("https://adventofcode.com/2023/day/19", 0, "Aplenty", null, null),
                Arguments.of("https://adventofcode.com/2023/day/20", 0, "Pulse Propagation", null, null),
                Arguments.of("https://adventofcode.com/2023/day/21", 0, "Step Counter", null, null),
                Arguments.of("https://adventofcode.com/2023/day/22", 0, "Sand Slabs", null, null),
                Arguments.of("https://adventofcode.com/2023/day/23", 0, "A Long Walk", null, null),
                Arguments.of("https://adventofcode.com/2023/day/24", 0, "Never Tell Me The Odds", null, null),
                Arguments.of("https://adventofcode.com/2023/day/25", 0, "Snowverload", null, null),
                Arguments.of("https://adventofcode.com/2023/day/26", 0, "", HttpStatusException.class, "HTTP error fetching URL. Status=404, URL=[https://adventofcode.com/2023/day/26]"),
                Arguments.of("https://google.com", 0, "", IndexOutOfBoundsException.class, "Index 0 out of bounds for length 0")
        );
    }

    private static Stream<Arguments> getDescription() {
        return Stream.of(
                Arguments.of("https://adventofcode.com/2023/day/1", 0,
                        """
                                <h2>--- Day 1: Trebuchet?! ---</h2>
                                <p>Something is wrong with global snow production, and you've been selected to take a look. The Elves have even given you a map; on it, they've used stars to mark the top fifty locations that are likely to be having problems.</p>
                                <p>You've been doing this long enough to know that to restore snow operations, you need to check all <b>fifty stars</b> by December 25th.</p>
                                <p>Collect stars by solving puzzles. Two puzzles will be made available on each day in the Advent calendar; the second puzzle is unlocked when you complete the first. Each puzzle grants <b>one star</b>. Good luck!</p>
                                <p>You try to ask why they can't just use a <a href="/2015/day/1">weather machine</a> ("not powerful enough") and where they're even sending you ("the sky") and why your map looks mostly blank ("you sure ask a lot of questions") and hang on did you just say the sky ("of course, where do you think snow comes from") when you realize that the Elves are already loading you into a <a href="https://en.wikipedia.org/wiki/Trebuchet">trebuchet</a> ("please hold still, we need to strap you in").</p>
                                <p>As they're making the final adjustments, they discover that their calibration document (your puzzle input) has been <b>amended</b> by a very young Elf who was apparently just excited to show off her art skills. Consequently, the Elves are having trouble reading the values on the document.</p>
                                <p>The newly-improved calibration document consists of lines of text; each line originally contained a specific <b>calibration value</b> that the Elves now need to recover. On each line, the calibration value can be found by combining the <b>first digit</b> and the <b>last digit</b> (in that order) to form a single <b>two-digit number</b>.</p>
                                <p>For example:</p>
                                <pre>
                                1abc2
                                pqr3stu8vwx
                                a1b2c3d4e5f
                                treb7uchet
                                </pre>
                                <p>In this example, the calibration values of these four lines are 12, 38, 15, and 77. Adding these together produces <b>142</b>.</p>
                                <p>Consider your entire calibration document. <b>What is the sum of all of the calibration values?</b></p>
                                """, null, null),
                Arguments.of("https://adventofcode.com/2023/day/4", 0,
                        """
                                <h2>--- Day 4: Scratchcards ---</h2>
                                <p>The gondola takes you up. Strangely, though, the ground doesn't seem to be coming with you; you're not climbing a mountain. As the circle of Snow Island recedes below you, an entire new landmass suddenly appears above you! The gondola carries you to the surface of the new island and lurches into the station.</p>
                                <p>As you exit the gondola, the first thing you notice is that the air here is much <b>warmer</b> than it was on Snow Island. It's also quite <b>humid</b>. Is this where the water source is?</p>
                                <p>The next thing you notice is an Elf sitting on the floor across the station in what seems to be a pile of colorful square cards.</p>
                                <p>"Oh! Hello!" The Elf excitedly runs over to you. "How may I be of service?" You ask about water sources.</p>
                                <p>"I'm not sure; I just operate the gondola lift. That does sound like something we'd have, though - this is <b>Island Island</b>, after all! I bet the <b>gardener</b> would know. He's on a different island, though - er, the small kind surrounded by water, not the floating kind. We really need to come up with a better naming scheme. Tell you what: if you can help me with something quick, I'll let you <b>borrow my boat</b> and you can go visit the gardener. I got all these <a href="https://en.wikipedia.org/wiki/Scratchcard">scratchcards</a> as a gift, but I can't figure out what I've won."</p>
                                <p>The Elf leads you over to the pile of colorful cards. There, you discover dozens of scratchcards, all with their opaque covering already scratched off. Picking one up, it looks like each card has two lists of numbers separated by a vertical bar (|): a list of <b>winning numbers</b> and then a list of <b>numbers you have</b>. You organize the information into a table (your puzzle input).</p>
                                <p>As far as the Elf has been able to figure out, you have to figure out which of the <b>numbers you have</b> appear in the list of <b>winning numbers</b>. The first match makes the card worth <b>one point</b> and each match after the first <b>doubles</b> the point value of that card.</p>
                                <p>For example:</p>
                                <pre>
                                Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
                                Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
                                Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
                                Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
                                Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
                                Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
                                </pre>
                                <p>In the above example, card 1 has five winning numbers (41, 48, 83, 86, and 17) and eight numbers you have (83, 86, 6, 31, 17, 9, 48, and 53). Of the numbers you have, four of them (48, 83, 17, and 86) are winning numbers! That means card 1 is worth <b>8</b> points (1 for the first match, then doubled three times for each of the three matches after the first).</p>
                                <ul>
                                 <li>Card 2 has two winning numbers (32 and 61), so it is worth <b>2</b> points.</li>
                                 <li>Card 3 has two winning numbers (1 and 21), so it is worth <b>2</b> points.</li>
                                 <li>Card 4 has one winning number (84), so it is worth <b>1</b> point.</li>
                                 <li>Card 5 has no winning numbers, so it is worth no points.</li>
                                 <li>Card 6 has no winning numbers, so it is worth no points.</li>
                                </ul>
                                <p>So, in this example, the Elf's pile of scratchcards is worth <b>13</b> points.</p>
                                <p>Take a seat in the large pile of colorful cards. <b>How many points are they worth in total?</b></p>
                                """, null, null)
        );
    }

    private static Stream<Arguments> getInputs() {
        return Stream.of(
                Arguments.of("https://adventofcode.com/2023/day/1", 0,
                        List.of(
                                """
                                        1abc2
                                        pqr3stu8vwx
                                        a1b2c3d4e5f
                                        treb7uchet"""
                        ), null, null)
        );
    }

    private static Stream<Arguments> getInput() {
        return Stream.of(
                Arguments.of("https://adventofcode.com/2023/day/1/input", "Puzzle inputs differ by user.  Please log in to get your puzzle input.\n", null, null)
        );
    }

    @ParameterizedTest
    @MethodSource
    void getTitle(String url, int index, String expected, Class<? extends Exception> expectedException, String expectedMessage) {

        try {
            String description = AoCScrapper.getTitle(url, "", index);

            Assertions.assertThat(description).isEqualTo(expected);

            Assertions.assertThat(expectedException).withFailMessage(String.format("Expected exception has not been thrown : %s", expectedException)).isNull();
        } catch (Exception e) {
            Assertions.assertThat(expectedException).withFailMessage(String.format("Unexpected exception has been thrown : %s", e)).isNotNull();
            Assertions.assertThat(e).isInstanceOf(expectedException).hasMessage(expectedMessage);
        }

    }

    @ParameterizedTest
    @MethodSource
    void getDescription(String url, int index, String expected, Class<? extends Exception> expectedException, String expectedMessage) {

        try {
            String description = AoCScrapper.getDescription(url, "", index);

            Assertions.assertThat(description).isEqualTo(expected);

            Assertions.assertThat(expectedException).withFailMessage(String.format("Expected exception has not been thrown : %s", expectedException)).isNull();
        } catch (Exception e) {
            Assertions.assertThat(expectedException).withFailMessage(String.format("Unexpected exception has been thrown : %s", e)).isNotNull();
            Assertions.assertThat(e).isInstanceOf(expectedException).hasMessage(expectedMessage);
        }

    }

    @ParameterizedTest
    @MethodSource
    void getInputs(String url, int index, List<String> expected, Class<? extends Exception> expectedException, String expectedMessage) {
        try {
            List<String> description = AoCScrapper.getInputs(url, "", index);

            Assertions.assertThat(description).isEqualTo(expected);

            Assertions.assertThat(expectedException).withFailMessage(String.format("Expected exception has not been thrown : %s", expectedException)).isNull();
        } catch (Exception e) {
            Assertions.assertThat(expectedException).withFailMessage(String.format("Unexpected exception has been thrown : %s", e)).isNotNull();
            Assertions.assertThat(e).isInstanceOf(expectedException).hasMessage(expectedMessage);
        }
    }

    @ParameterizedTest
    @MethodSource
    void getInput(String url, String expected, Class<? extends Exception> expectedException, String expectedMessage) {
        try {
            String input = AoCScrapper.getInput(url, "");

            Assertions.assertThat(input).isEqualTo(expected);

            Assertions.assertThat(expectedException).withFailMessage(String.format("Expected exception has not been thrown : %s", expectedException)).isNull();
        } catch (Exception e) {
            Assertions.assertThat(expectedException).withFailMessage(String.format("Unexpected exception has been thrown : %s", e)).isNotNull();
            Assertions.assertThat(e).isInstanceOf(expectedException).hasMessage(expectedMessage);
        }
    }
}
