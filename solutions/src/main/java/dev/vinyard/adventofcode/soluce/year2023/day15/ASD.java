package dev.vinyard.adventofcode.soluce.year2023.day15;

import java.util.List;

public class ASD {

    public static class Root {
        List<Instruction> instructions;

        public Root(List<Instruction> instructions) {
            this.instructions = instructions;
        }

        public long hash() {
            return this.instructions.stream().mapToLong(Instruction::hash).sum();
        }
    }

    public abstract static class Instruction {
        public final String label;

        public Instruction(String label) {
            this.label = label;
        }

        protected long hash(String word) {
            long currentValue = 0;
            for (char c : word.toCharArray()) {
                currentValue += (int) c;
                currentValue *= 17;
                currentValue %= 256;
            }
            return currentValue;
        }

        public abstract long hash();
    }

    public static class DashInstruction extends Instruction {
        public DashInstruction(String label) {
            super(label);
        }

        @Override
        public long hash() {
            return super.hash(this.label + "-");
        }
    }

    public static class FocalLengthInstruction extends Instruction {

        private final Integer focalLength;

        public FocalLengthInstruction(String label, Integer focalLength) {
            super(label);
            this.focalLength = focalLength;
        }

        @Override
        public long hash() {
            return super.hash(this.label + "=" + this.focalLength);
        }
    }
}
