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
            moves.forEach(Move::process9000);
            return stacks.values().stream().map(CrateStack::getTopCrate).map(Crate::name).collect(Collectors.joining());
        }

        public String part2() {
            moves.forEach(Move::process9001);
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
    }

    public record Move(int quantity, CrateStack from, CrateStack to) {
        public void process9000() {
            IntStream.range(0, quantity).forEach(i -> to.addLast(from.pollLast()));
        }

        public void process9001() {
            LinkedList<Crate> temp = new LinkedList<>();
            IntStream.range(0, quantity).forEach(i -> temp.addFirst(from.pollLast()));
            temp.forEach(to::addLast);
        }
    }

    public record Crate(String name) { }
}
