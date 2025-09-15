package dev.vinyard.adventofcode.soluce.year2022.day11;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class ASD {

    public static class Root {

        public final List<Monkey> monkeys;

        public Root(List<Monkey> monkeys) {
            this.monkeys = monkeys;
        }

        public long part1() {
            IntStream.range(0, 20).forEach(i -> {;
                System.out.printf("Round %d:%n", i + 1);
                monkeys.forEach(Monkey::round);
                System.out.println();
            });

            return monkeys.stream().mapToLong(Monkey::getInspectedItems).boxed().sorted((a, b) -> Long.compare(b, a)).limit(2).peek(System.out::println).reduce((a, b) -> a * b).orElseThrow();
        }
    }

    @Setter
    public static class Monkey {

        private final int id;

        private LinkedList<Item> items;
        private Function<Integer, Integer> operation;
        private Predicate<Integer> test;
        private Monkey ifTrue;
        private Monkey ifFalse;

        @Getter
        private long inspectedItems = 0;

        public Monkey(int id) {
            this.id = id;
        }

        public void round() {
            System.out.printf("Monkey %d:%n", id);

            while (!items.isEmpty()) {
                Item item = items.pollFirst();
                inspect(item);
            }
        }

        public void inspect(Item item) {

            inspectedItems++;

            System.out.printf("\tMonkey inspects an item with a worry level of %d.%n", item.worryLevel);

            item.applyOperation(operation);

            item.bored();

            if (test.test(item.worryLevel)) {
                System.out.printf("\t\tItem with worry level %d is thrown to monkey %d.%n", item.worryLevel, ifTrue.id);
                ifTrue.throwItem(item);
            } else {
                System.out.printf("\t\tItem with worry level %d is thrown to monkey %d.%n", item.worryLevel, ifFalse.id);
                ifFalse.throwItem(item);
            }
        }

        public void throwItem(Item item) {
            items.addLast(item);
        }
    }

    public static class Item {
        private int worryLevel;

        public Item(int worryLevel) {
            this.worryLevel = worryLevel;
        }

        public void bored() {
            worryLevel /= 3;
            System.out.printf("\t\tMonkey gets bored with item. Worry level is divided by 3 to %d.%n", worryLevel);
        }

        public void applyOperation(Function<Integer, Integer> operation) {
            worryLevel = operation.apply(worryLevel);

            System.out.printf("\t\tWorry level is changed to %d.%n", worryLevel);
        }
    }


}
