package dev.vinyard.adventofcode.soluce.year2023.day15;

import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class ASD {

    public static class Root {
        List<Instruction> instructions;

        private Box[] boxes = new Box[256];

        public Root(List<Instruction> instructions) {
            this.instructions = instructions;
            IntStream.range(0, boxes.length).forEach(i -> boxes[i] = new Box());
        }

        public long hash() {
            return this.instructions.stream().mapToLong(Instruction::hash).sum();
        }

        public void performOperations() {
            this.instructions.forEach(instruction -> {
                instruction.perform(boxes[instruction.getBoxNumber()]);
            });
        }

        public long getFocusingPower() {
            int cpt = 1;

            long focusingPower = 0;
            for (Box box : this.boxes) {
                focusingPower += box.getFocusingPower() * cpt++;
            }

            return focusingPower;
        }

    }

    public static class Box {

        private LinkedList<Lens> lenses = new LinkedList<>();

        public void addLens(Lens lens) {
            lenses.stream().filter(l -> Objects.equals(l.label, lens.label))
                    .findAny()
                    .ifPresentOrElse(
                        existingLens -> existingLens.setFocalLength(lens.focalLength),
                        () -> this.lenses.add(lens)
                    );
        }

        public void removeLens(String label) {
            this.lenses.removeIf(l -> Objects.equals(l.label, label));
        }

        public long getFocusingPower() {
            int cpt = 1;
            int focusingPower = 0;

            for (Lens lens : this.lenses) {
                focusingPower += lens.focalLength * cpt++;
            }

            return focusingPower;
        }

    }

    @Setter
    public static class Lens {

        private final String label;
        private Integer focalLength;

        public Lens(String label, Integer focalLength) {
            this.label = label;
            this.focalLength = focalLength;
        }
    }

    public abstract static class Instruction {
        public final String label;

        public Instruction(String label) {
            this.label = label;
        }

        protected int hash(String word) {
            int currentValue = 0;
            for (char c : word.toCharArray()) {
                currentValue += c;
                currentValue *= 17;
                currentValue %= 256;
            }
            return currentValue;
        }

        public abstract int hash();

        public int getBoxNumber() {
            return this.hash(this.label);
        }

        public abstract void perform(Box box);
    }

    public static class DashInstruction extends Instruction {
        public DashInstruction(String label) {
            super(label);
        }

        @Override
        public int hash() {
            return super.hash(this.label + "-");
        }

        @Override
        public void perform(Box box) {
            box.removeLens(this.label);
        }
    }

    public static class FocalLengthInstruction extends Instruction {

        private final Integer focalLength;

        public FocalLengthInstruction(String label, Integer focalLength) {
            super(label);
            this.focalLength = focalLength;
        }

        @Override
        public int hash() {
            return super.hash(this.label + "=" + this.focalLength);
        }

        @Override
        public void perform(Box box) {
            box.addLens(new Lens(this.label, this.focalLength));
        }
    }
}
