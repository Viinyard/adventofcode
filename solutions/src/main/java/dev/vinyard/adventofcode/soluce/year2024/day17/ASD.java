package dev.vinyard.adventofcode.soluce.year2024.day17;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ASD {

    public static class Root {

    }

    public static class Program {

        private final AtomicLong registerA;
        private final AtomicLong registerB;
        private final AtomicLong registerC;

        private final AtomicInteger ptr = new AtomicInteger(0);

        @Getter
        private final List<Long> output = new ArrayList<>();

        private final List<Instruction> instructions;

        private final List<Long> source;

        public Program (AtomicLong registerA, AtomicLong registerB, AtomicLong registerC, List<Instruction> instructions, List<Long> source) {
            this.registerA = registerA;
            this.registerB = registerB;
            this.registerC = registerC;
            this.instructions = instructions;
            this.source = source;
        }

        public void evaluate() {
            while (ptr.get() < instructions.size()) {
                instructions.get(ptr.get()).execute(this);
            }
        }

        public void evaluate(long a, long b, long c) {
            output.clear();
            ptr.set(0);
            registerA.set(a);
            registerB.set(b);
            registerC.set(c);

            while (ptr.get() < instructions.size()) {
                instructions.get(ptr.get()).execute(this);
            }
        }

        public long findA() {
            final long b = getB();
            final long c = getC();

            PriorityQueue<Long> stack = new PriorityQueue<>();
            stack.add(0L);

            while (!stack.isEmpty()) {
                long a = stack.peek();
                for (long nextA = a; nextA <= a + 0b111; nextA += 0b001) {
                    evaluate(nextA, b, c);

                    List<Long> expected = source.subList(source.size() - output.size(), source.size());
                    if (Arrays.equals(output.stream().mapToLong(Long::longValue).toArray(), expected.stream().mapToLong(Long::longValue).toArray())) {
                        if (source.size() == output.size()) {
                            return nextA;
                        } else {
                            if (!stack.contains(nextA << 3)) {
                                stack.add(nextA << 3);
                            }
                        }
                    }
                }
                stack.poll();
            }

            throw new IllegalStateException("No solution found");
        }

        public long getA() {
            return registerA.get();
        }

        public long getB() {
            return registerB.get();
        }

        public long getC() {
            return registerC.get();
        }

        public void setA(long value) {
            registerA.set(value);
        }

        public void setB(long value) {
            registerB.set(value);
        }

        public void setC(long value) {
            registerC.set(value);
        }

        public void jump(long value) {
            ptr.set((int) value);
        }

        public void next() {
            ptr.incrementAndGet();
        }

        public void output(long value) {
            output.add(value);
        }
    }

    public static abstract class Instruction {

        protected AtomicLong value;

        public Instruction setValue(AtomicLong value) {
            this.value = value;
            return this;
        }

        abstract void execute(Program program);
    }

    public static class AdvInstruction extends Instruction {

        /**
         * <p>The <b>adv</b> instruction (opcode <b>0</b>) performs <b>division</b>. The numerator is the value in the A register. The denominator is found by raising 2 to the power of the instruction's <b>combo</b> operand. (So, an operand of 2 would divide A by 4 (2^2); an operand of 5 would divide A by 2^B.) The result of the division operation is <b>truncated</b> to an integer and then written to the A register.</p>
         */
        @Override
        public void execute(Program program) {
            program.setA(program.getA() >> value.get());
            program.next();
        }
    }


    public static class BxlInstruction extends Instruction {

        /**
         * <p>The <b>bxl</b> instruction (opcode <b>1</b>) calculates the <a href="https://en.wikipedia.org/wiki/Bitwise_operation#XOR">bitwise XOR</a> of register B and the instruction's <b>literal</b> operand, then stores the result in register B.</p>
         */
        @Override
        public void execute(Program program) {
            program.setB(program.getB() ^ value.get());
            program.next();
        }
    }

    public static class BstInstruction extends Instruction {

        /**
         * <p>The <b>bst</b> instruction (opcode <b>2</b>) calculates the value of its <b>combo</b> operand <a href="https://en.wikipedia.org/wiki/Modulo">modulo</a> 8 (thereby keeping only its lowest 3 bits), then writes that value to the B register.</p>
         */
        @Override
        public void execute(Program program) {
            program.setB(value.get() % 8);
            program.next();
        }
    }

    public static class JnzInstruction extends Instruction {

        /**
         * <p>The <b>jnz</b> instruction (opcode <b>3</b>) does <b>nothing</b> if the A register is 0. However, if the A register is <b>not zero</b>, it <b>jumps</b> by setting the instruction pointer to the value of its <b>literal</b> operand; if this instruction jumps, the instruction pointer is <b>not</b> increased by 2 after this instruction.</p>
         */
        @Override
        public void execute(Program program) {
            if (program.getA() != 0) {
                program.jump(value.get());
            } else {
                program.next();
            }
        }
    }

    public static class BxcInstruction extends Instruction {

        /**
         * <p>The <b>bxc</b> instruction (opcode <b>4</b>) calculates the <b>bitwise XOR</b> of register B and register C, then stores the result in register B. (For legacy reasons, this instruction reads an operand but <b>ignores</b> it.)</p>
         */
        @Override
        public void execute(Program program) {
            program.setB(program.getB() ^ program.getC());
            program.next();
        }
    }

    public static class OutInstruction extends Instruction {

        /**
         * <p>The <b>out</b> instruction (opcode <b>5</b>) calculates the value of its <b>combo</b> operand modulo 8, then <b>outputs</b> that value. (If a program outputs multiple values, they are separated by commas.)</p>
         */
        @Override
        public void execute(Program program) {
            program.output(value.get() % 8);
            program.next();
        }
    }

    public static class BdvInstruction extends Instruction {

        /**
         * <p>The <b>bdv</b> instruction (opcode <b>6</b>) works exactly like the adv instruction except that the result is stored in the <b>B register</b>. (The numerator is still read from the A register.)</p>
         */
        @Override
        public void execute(Program program) {
            program.setB(program.getA() >> value.get());
            program.next();
        }
    }

    public static class CdvInstruction extends Instruction {

        /**
         * <p>The <b>cdv</b> instruction (opcode <b>7</b>) works exactly like the adv instruction except that the result is stored in the <b>C register</b>. (The numerator is still read from the A register.)</p>
         */
        @Override
        public void execute(Program program) {
            program.setC(program.getA() >> value.get());
            program.next();
        }
    }
}
