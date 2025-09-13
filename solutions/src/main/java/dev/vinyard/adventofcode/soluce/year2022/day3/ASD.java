package dev.vinyard.adventofcode.soluce.year2022.day3;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class ASD {

    public static class Root {

        private final List<Rucksack> rucksacks;

        public Root(List<Rucksack> rucksacks) {
            this.rucksacks = rucksacks;
        }

        public long part1() {
            return rucksacks.stream().map(Rucksack::findCommonItem).mapToLong(Item::getPriority).sum();
        }
    }

    public static class Rucksack {

        private final List<Item> compartment1;
        private final List<Item> compartment2;

        public Rucksack(List<Item> allItems) {
            int mid = allItems.size() / 2;
            this.compartment1 = allItems.subList(0, mid);
            this.compartment2 = allItems.subList(mid, allItems.size());
        }

        public Item findCommonItem() {
            return Stream.of(
                    compartment1.stream().filter(compartment2::contains),
                    compartment2.stream().filter(compartment1::contains)
            ).flatMap(Function.identity()).findAny().orElseThrow();
        }
    }

    public record Item(String name) {
        public int getPriority() {
            char c = name.charAt(0);
            if (Character.isLowerCase(c)) {
                return c - 'a' + 1;
            } else {
                return c - 'A' + 27;
            }
        }
    }
}
