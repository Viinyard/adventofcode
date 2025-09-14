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

        public Sprites() {
            Range<Integer> range = Range.between(x, x + 2);
            StringBuilder builder = new StringBuilder();

            for (int i = 1; i <= 40; i++) {
                if (range.contains(i)) {
                    builder.append("#");
                } else {
                    builder.append(".");
                }
            }
            System.out.println("Sprite position: " + builder);
            System.out.println();
        }

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
            Range<Integer> range = Range.between(x, x + 2);
            StringBuilder builder = new StringBuilder();

            for (int i = 1; i <= 40; i++) {
                if (range.contains(i)) {
                    builder.append("#");
                } else {
                    builder.append(".");
                }
            }
            System.out.println("Sprite position: " + builder);
            System.out.println();
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
            System.out.println("Start cycle %s: begin executing addx %d".formatted(String.format("%4d", cycle), addX.value));
            drawPixel();
            drawPixel();
            System.out.println("End of cycle %s: finish executing addx %d (Register X is now %d)".formatted(String.format("%4d", cycle - 1), addX.value, sprites.x + addX.value));
            sprites.addX(addX.value);
        }

        private void drawPixel() {
            System.out.println("During cycle %s: CRT draws pixel in position %d".formatted(String.format("%3d", cycle), (cycle - 1) % lineLength));
            screen.append(sprites.getPixel(cycle % lineLength));
            System.out.println("Current CRT row: " + screen.substring(screen.lastIndexOf("\n") + 1));
            System.out.println();

            if ((cycle) % lineLength == 0) {
                screen.append("\n");
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
