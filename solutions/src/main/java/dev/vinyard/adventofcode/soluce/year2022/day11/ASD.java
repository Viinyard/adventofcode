package dev.vinyard.adventofcode.soluce.year2022.day11;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.LongFunction;
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
                monkeys.forEach(Monkey::round);
            });

            return monkeys.stream().mapToLong(Monkey::getInspectedItems).boxed().sorted((a, b) -> Long.compare(b, a)).limit(2).reduce((a, b) -> a * b).orElseThrow();
        }

        public long part2() {
            final long modulo = monkeys.stream().map(Monkey::getDivisor).distinct().reduce(1L, (a, b) -> a * b);
            Item.boredFunction = (x) -> x % modulo;

            IntStream.range(0, 10_000).forEach(i -> {
                monkeys.forEach(Monkey::round);
            });

            return monkeys.stream().mapToLong(Monkey::getInspectedItems).boxed().sorted((a, b) -> Long.compare(b, a)).limit(2).reduce((a, b) -> a * b).orElseThrow();
        }
    }

    @Setter
    public static class Monkey {

        private LinkedList<Item> items;
        private Function<Long, Long> operation;
        private Predicate<Long> test;
        private Monkey ifTrue;
        private Monkey ifFalse;

        @Getter
        private long divisor;

        @Getter
        private long inspectedItems = 0;

        public void round() {
            while (!items.isEmpty()) {
                Item item = items.pollFirst();
                inspect(item);
            }
        }

        public void inspect(Item item) {
            inspectedItems++;

            item.applyOperation(operation);

            item.bored();

            if (test.test(item.worryLevel)) {
                ifTrue.throwItem(item);
            } else {
                ifFalse.throwItem(item);
            }
        }

        public void throwItem(Item item) {
            items.addLast(item);
        }
    }

    public static class Item {
        private Long worryLevel;

        public static LongFunction<Long> boredFunction = (x) -> x / 3;

        public Item(Long worryLevel) {
            this.worryLevel = worryLevel;
        }

        public void bored() {
            worryLevel = boredFunction.apply(worryLevel);
        }

        public void applyOperation(Function<Long, Long> operation) {
            worryLevel = operation.apply(worryLevel);
        }
    }
}
