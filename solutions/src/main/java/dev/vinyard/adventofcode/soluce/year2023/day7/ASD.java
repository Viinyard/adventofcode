package dev.vinyard.adventofcode.soluce.year2023.day7;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ASD {

    public enum Card {
        A("A"), K("K"), Q("Q"), J("J"), T("T"), N9("9"), N8("8"), N7("7"), N6("6"), N5("5"), N4("4"), N3("3"), N2("2"), JOKER("*");

        private final String value;

        Card(String value) {
            this.value = value;
        }

        public static Card fromValue(String value) {
            return Stream.of(Card.values()).filter(card -> card.value.equals(value)).findAny().orElseThrow(() -> new IllegalArgumentException("Unknown card value : " + value));
        }

    }

    public enum HandType {
        FIVE_OF_A_KIND,
        FOUR_OF_A_KIND,
        FULL_HOUSE,
        THREE_OF_A_KIND,
        TWO_PAIR,
        ONE_PAIR,
        HIGH_CARD
    }

    @Data
    public static class Game implements Comparable<Game> {
        private List<Card> hand;

        private Long bid;

        public Game(List<Card> hand, Long bid) {
            this.hand = hand;
            this.bid = bid;
        }

        public HandType getHandType() {
            Map<Card, Long> handNumber = hand.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            long numberOfJoker = handNumber.getOrDefault(Card.JOKER, 0L);
            handNumber.remove(Card.JOKER);

            List<Long> numbers = handNumber.values().stream().sorted((a, b) -> Long.compare(b, a)).collect(Collectors.toList());

            if (numbers.isEmpty()) {
                numbers.add(numberOfJoker);
            } else {
                numbers.set(0, numbers.getFirst() + numberOfJoker);
            }

            return switch (numbers.size()) {
                case 1 -> HandType.FIVE_OF_A_KIND;
                case 2 -> {
                    if (numbers.getFirst() == 4) {
                        yield HandType.FOUR_OF_A_KIND;
                    } else {
                        yield HandType.FULL_HOUSE;
                    }
                }
                case 3 -> {
                    if (numbers.getFirst() == 3) {
                        yield HandType.THREE_OF_A_KIND;
                    } else {
                        yield HandType.TWO_PAIR;
                    }
                }
                case 4 -> HandType.ONE_PAIR;
                case 5 -> HandType.HIGH_CARD;
                default -> throw new IllegalStateException("Unexpected value: " + numbers.size());
            };
        }

        @Override
        public int compareTo(Game o) {
            int compareHandType = o.getHandType().compareTo(this.getHandType());
            if (compareHandType == 0)
                return Stream.iterate(0, i -> i + 1).limit(hand.size()).map(i -> o.getHand().get(i).compareTo(hand.get(i))).filter(i -> i != 0).findFirst().orElse(0);
            return compareHandType;
        }

        @Override
        public String toString() {
            return "Game{" +
                    "hand=" + hand +
                    ", bid=" + bid +
                    ", handType=" + getHandType() +
                    '}';
        }
    }
}
