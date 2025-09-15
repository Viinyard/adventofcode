package dev.vinyard.adventofcode.soluce.year2022.day10;

import org.apache.commons.lang3.Range;

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

        public String part2() {
            CRTVisitor visitor = new CRTVisitor(40);

            instructions.forEach(instruction -> instruction.accept(visitor));

            return visitor.getScreen();
        }
    }

    public static class Sprites {
        private int x = 1;

        public String getPixel(int position) {
            Range<Integer> range = Range.between(x, x + 2);
            if (range.contains(position)) {
                return "#";
            } else {
                return ".";
            }
        }

        public void addX(int value) {
            x += value;
        }
    }

    public static class CRTVisitor implements Visitor {

        private final StringBuilder screen = new StringBuilder();
        private int cycle = 1;
        private final Sprites sprites = new Sprites();
        private final int lineLength;

        public CRTVisitor(int lineLength) {
            this.lineLength = lineLength;
        }

        @Override
        public void visit(NoOp noop) {
            drawPixel();
        }

        @Override
        public void visit(AddX addX) {
            drawPixel();
            drawPixel();
            sprites.addX(addX.value);
        }

        private void drawPixel() {
            screen.append(sprites.getPixel(cycle));

            if ((cycle) % lineLength == 0) {
                screen.append("\n");
                cycle = 0;
            }
            cycle++;
        }

        public String getScreen() {
            return screen.toString();
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
