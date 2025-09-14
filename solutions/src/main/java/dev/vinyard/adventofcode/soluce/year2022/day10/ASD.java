package dev.vinyard.adventofcode.soluce.year2022.day10;

import java.util.LinkedList;
import java.util.stream.IntStream;

public class ASD {

    public static class Root {

        private final LinkedList<Instruction> instructions;

        public Root(LinkedList<Instruction> instructions) {
            this.instructions = instructions;
        }

        public long part1() {
            ProgramVisitor visitor = new ProgramVisitor();

            instructions.forEach(instruction -> instruction.accept(visitor));

            return IntStream.of(20, 60, 100, 140, 180, 220)
                    .map(i -> i * visitor.values.get(i - 1))
                    .sum();
        }

    }

    public static class ProgramVisitor implements Visitor {

        private final LinkedList<Integer> values = new LinkedList<>();
        private int x = 1;

        @Override
        public void visit(NoOp noop) {
            values.add(x);
        }

        @Override
        public void visit(AddX addX) {
            values.add(x);
            values.add(x);
            x += addX.value;
        }
    }

    public interface Visitor {
        void visit(NoOp noop);
        void visit(AddX addX);
    }

    public interface Node {
        void accept(Visitor visitor);
    }

    public static abstract class Instruction implements Node {

    }

    public static class NoOp extends Instruction {

        @Override
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }
    }

    public static class AddX extends Instruction {
        public final int value;

        public AddX(int value) {
            this.value = value;
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }
    }

}
