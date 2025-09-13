package dev.vinyard.adventofcode.soluce.year2022.day5;

import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ASD {

    public static class Root {

        private final Map<Integer, CrateStack> stacks;
        private final LinkedList<Move> moves;

        public Root(Map<Integer, CrateStack> stacks, LinkedList<Move> moves) {
            this.stacks = stacks;
            this.moves = moves;
        }

        public String part1() {
            moves.forEach(m -> m.process());

            return stacks.values().stream().map(CrateStack::getTopCrate).map(Crate::name).collect(Collectors.joining());
        }
    }

    public static class CrateStack {

        private final LinkedList<Crate> crates = new LinkedList<>();

        public void push(Crate crate) {
            crates.addFirst(crate);
        }

        public void addLast(Crate crate) {
            crates.addLast(crate);
        }

        public Crate pollLast() {
            return crates.pollLast();
        }

        public Crate getTopCrate() {
            if (crates.isEmpty())
                return null;
            return crates.getLast();
        }

        @Override
        public String toString() {
            return  crates.stream().map(Crate::name).collect(Collectors.joining(", "));
        }
    }

    public record Move(int quantity, CrateStack from, CrateStack to) {
        public void process() {
            IntStream.range(0, quantity).forEach(i -> to.addLast(from.pollLast()));
        }
    }

    public record Crate(String name) { }
}
