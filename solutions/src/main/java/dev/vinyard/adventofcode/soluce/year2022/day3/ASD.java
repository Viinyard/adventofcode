package dev.vinyard.adventofcode.soluce.year2022.day3;

import lombok.Getter;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class ASD {

    public static class Part1 {

        private final List<Rucksack> rucksacks;

        public Part1(List<Rucksack> rucksacks) {
            this.rucksacks = rucksacks;
        }

        public long result() {
            return rucksacks.stream().map(Rucksack::findCommonItem).mapToLong(Item::getPriority).sum();
        }
    }

    public static class Part2 {
        private final List<Group> groups;

        public Part2(List<Group> groups) {
            this.groups = groups;
        }

        public long result() {
            return groups.stream().map(Group::findCommonItem).mapToLong(Item::getPriority).sum();
        }
    }

    public static class Group {
        private final List<Rucksack> rucksacks;

        public Group(List<Rucksack> rucksacks) {
            this.rucksacks = rucksacks;
        }

        public Item findCommonItem() {
            List<Item> commonItems = rucksacks.stream()
                    .map(Rucksack::getItems)
                    .reduce((l1, l2) -> l1.stream().filter(l2::contains).distinct().toList())
                    .orElseThrow();

            if (commonItems.size() != 1)
                throw new IllegalStateException("There should be exactly one common item in the group");

            return commonItems.getFirst();
        }
    }

    @Getter
    public static class Rucksack {

        private final List<Item> items;
        private final List<Item> compartment1;
        private final List<Item> compartment2;

        public Rucksack(List<Item> items) {
            int mid = items.size() / 2;
            this.items = items;
            this.compartment1 = items.subList(0, mid);
            this.compartment2 = items.subList(mid, items.size());
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
