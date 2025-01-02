package dev.vinyard.adventofcode.soluce.year2024.day24;

import lombok.Data;
import lombok.Getter;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ASD {

    public static class Root {

        private final List<Wire> wires;
        private final List<Gate> gates;

        public Root(List<Wire> wires, List<Gate> gates) {
            this.wires = wires;
            this.gates = gates;
        }

        public long evaluate() {
            return wires.stream().filter(w -> w.getName().startsWith("z")).sorted(Comparator.comparing(Wire::getName).reversed())
                    .mapToLong(Wire::getValue).reduce(0, (a, b) -> a * 2 + b);
        }

        public String part2() {
            RippleCarryAdder rippleCarryAdder = new RippleCarryAdder(wires, gates);

            long x = wires.stream().filter(w -> w.getName().startsWith("x")).sorted(Comparator.comparing(Wire::getName).reversed())
                    .mapToLong(Wire::getValue).reduce(0, (a, b) -> a * 2 + b);
            long y = wires.stream().filter(w -> w.getName().startsWith("y")).sorted(Comparator.comparing(Wire::getName).reversed())
                    .mapToLong(Wire::getValue).reduce(0, (a, b) -> a * 2 + b);
            long z = wires.stream().filter(w -> w.getName().startsWith("z")).sorted(Comparator.comparing(Wire::getName).reversed())
                    .mapToLong(Wire::getValue).reduce(0, (a, b) -> a * 2 + b);

            assert x + y == z : "x + y should be equal to z";

            return rippleCarryAdder.getFixedOutputs().stream().map(Wire::getName).sorted().collect(Collectors.joining(","));
        }

    }

    /**
     * <a href="https://upload.wikimedia.org/wikipedia/commons/8/85/RippleCarry2.gif">Ripple Carry Adder</a>
     */
    @Data
    public static class RippleCarryAdder implements OutputFixListener {

        private HalfAdder firstHalfAdder;
        private LinkedList<FullAdder> fullAdders = new LinkedList<>();
        private final List<Wire> fixedOutputs = new ArrayList<>();

        public RippleCarryAdder(List<Wire> wires, List<Gate> gates) {

            gates.forEach(g -> g.addOutputFixListener(this));

            LinkedList<Wire> aWires = wires.stream().filter(w -> w.getName().startsWith("x")).sorted(Comparator.comparing(Wire::getName)).collect(Collectors.toCollection(LinkedList::new));
            LinkedList<Wire> bWires = wires.stream().filter(w -> w.getName().startsWith("y")).sorted(Comparator.comparing(Wire::getName)).collect(Collectors.toCollection(LinkedList::new));
            LinkedList<Wire> zWires = wires.stream().filter(w -> w.getName().startsWith("z")).sorted(Comparator.comparing(Wire::getName)).collect(Collectors.toCollection(LinkedList::new));

            Wire a = aWires.poll();
            Wire b = bWires.poll();
            Wire z = zWires.poll();

            this.setFirstHalfAdder(new HalfAdder(a, b, z, gates));

            Supplier<Wire> getCin = this.getFirstHalfAdder()::getCarry;
            Consumer<Wire> setCin = this.getFirstHalfAdder()::setCarry;

            while (!aWires.isEmpty() && !bWires.isEmpty()) {
                a = aWires.poll();
                b = bWires.poll();
                z = zWires.poll();

                FullAdder fullAdder = new FullAdder().buildFrom(a, b, getCin, setCin, z, gates);

                this.getFullAdders().add(fullAdder);

                getCin = fullAdder::getCout;
                setCin = fullAdder::setCout;
            }
        }

        @Override
        public void onOutputFix(Wire oldOutput, Wire newOutput) {
            fixedOutputs.add(oldOutput);
        }
    }

    /**
     * <a href="https://upload.wikimedia.org/wikipedia/commons/9/92/Halfadder.gif">Half Adder</a>
     */
    @Data
    public static class HalfAdder {

        private Wire a;
        private Wire b;
        private Wire carry;

        private XorGate xorGate;
        private AndGate andGate;

        public HalfAdder(Supplier<Wire> getA, Consumer<Wire> setA, Supplier<Wire> getB, Consumer<Wire> setB, List<Gate> gates) {
            this.setA(getA.get());
            this.setB(getB.get());

            this.setXorGate((XorGate) Gate.findGate(
                    getA, setA,
                    getB, setB,
                    XorGate.class, gates));

            this.setAndGate((AndGate) Gate.findGate(
                    getA, setA,
                    getB, setB,
                    AndGate.class, gates));
        }

        public HalfAdder(Wire a, Wire b, Wire sum, List<Gate> gates) {
            this(() -> a, setA -> {}, () -> b, setB -> {}, gates);

            if (this.getSum() != sum) {
                throw new IllegalStateException("Sum wire should be %s the same for a = %s and b = %s, but we found %s".formatted(sum.getName(), a.getName(), b.getName(), this.getSum()));
            }
        }

        public void setSum(Wire sum) {
            this.getXorGate().setOutput(sum);
        }

        public Wire getSum() {
            return this.getXorGate().getOutput();
        }

        public void setCarry(Wire carry) {
            this.getAndGate().setOutput(carry);
        }

        public Wire getCarry() {
            return this.getAndGate().getOutput();
        }
    }

    /**
     * <a href="https://upload.wikimedia.org/wikipedia/commons/5/57/Fulladder.gif">Full Adder</a>
     */
    @Data
    public static class FullAdder {

        private Wire a;
        private Wire b;
        private Wire cin;

        private HalfAdder halfAdderAB;
        private HalfAdder halfAdderSumCin;
        private OrGate orGateCout;

        public FullAdder() {
        }


        public FullAdder buildFrom(Wire a, Wire b, Supplier<Wire> getCin, Consumer<Wire> setCin, Wire sum, List<Gate> gates) {
            FullAdder fullAdder = new FullAdder();

            fullAdder.setA(a);
            fullAdder.setB(b);
            fullAdder.setCin(cin);

            Consumer<Wire> cantSet = w -> {
                throw new IllegalStateException("Cannot set wire %s".formatted(w.getName()));
            };

            fullAdder.setHalfAdderAB(new HalfAdder(() -> a, cantSet, () -> b, cantSet, gates));

            fullAdder.setHalfAdderSumCin(new HalfAdder(fullAdder.getHalfAdderAB()::getSum, fullAdder.getHalfAdderAB()::setSum, getCin, setCin, gates));

            fullAdder.setOrGateCout((OrGate) Gate.findGate(
                    fullAdder.getHalfAdderSumCin()::getCarry, fullAdder.getHalfAdderSumCin()::setCarry,
                    fullAdder.getHalfAdderAB()::getCarry, fullAdder.getHalfAdderAB()::setCarry,
                    OrGate.class, gates));

            if (fullAdder.getSum() != sum) {
                gates.stream().filter(g -> g.getOutput() == sum)
                        .findAny().ifPresent(g -> g.setOutput(fullAdder.getSum()));

                fullAdder.setSum(sum);
            }

            return fullAdder;
        }

        public void setSum(Wire sum) {
            this.getHalfAdderSumCin().setSum(sum);
        }

        public Wire getSum() {
            return this.getHalfAdderSumCin().getSum();
        }

        public void setCout(Wire cout) {
            this.getOrGateCout().setOutput(cout);
        }

        public Wire getCout() {
            return this.getOrGateCout().getOutput();
        }

    }

    @Getter
    public static class Wire {

        private final String name;
        private Long value;

        private final List<ConnectedWireListener> connectedWireListeners = new ArrayList<>();

        public Wire(String name) {
            this.name = name;
        }

        public void addConnectedWireListener(ConnectedWireListener connectedWireListener) {
            connectedWireListeners.add(connectedWireListener);
        }

        public void connect(Long value) {
            this.value = value;
            getConnectedWireListeners().forEach(connectedWireListener -> connectedWireListener.onConnected(this));
        }

        public long getValue() {
            return Optional.ofNullable(value).orElseThrow(() -> new IllegalStateException("Wire is not connected"));
        }

        public boolean isConnected() {
            return value != null;
        }
    }

    @Data
    public static abstract class Gate implements ConnectedWireListener {

        private final Wire left;
        private final Wire right;
        private Wire output;

        private final List<OutputFixListener> outputFixListeners = new ArrayList<>();

        public Gate(Wire left, Wire right, Wire output) {
            this.left = left;
            this.right = right;
            this.output = output;

            left.addConnectedWireListener(this);
            right.addConnectedWireListener(this);

            if (left.isConnected() && right.isConnected()) {
                output.connect(this.evaluate(left.getValue(), right.getValue()));
            }
        }

        public void addOutputFixListener(OutputFixListener outputFixListener) {
            outputFixListeners.add(outputFixListener);
        }

        public boolean isConnected(Wire... wire) {
            return Arrays.stream(wire).allMatch(l -> List.of(left, right).contains(l));
        }

        public void setOutput(Wire output) {
            this.getOutputFixListeners().forEach(outputFixListener -> outputFixListener.onOutputFix(this.output, output));

            this.output = output;

            if (left.isConnected() && right.isConnected()) {
                output.connect(this.evaluate(left.getValue(), right.getValue()));
            }
        }

        @Override
        public void onConnected(Wire wire) {
            if (left.isConnected() && right.isConnected()) {
                output.connect(this.evaluate(left.getValue(), right.getValue()));
            }
        }

        protected abstract long evaluate(Long left, Long right);

        public static Gate findGate(Supplier<Wire> getA, Consumer<Wire> setA, Supplier<Wire> getB, Consumer<Wire> setB, Class<? extends Gate> clazz, List<Gate> gates) {
            try {
                return gates.stream().filter(clazz::isInstance)
                        .filter(g -> g.isConnected(getA.get(), getB.get()))
                        .findAny().orElseThrow(() -> new IllegalStateException("Gate %s not found for a = %s and b = %s".formatted(clazz.getName(), getA.get().getName(), getB.get().getName())));
            } catch (IllegalStateException e) {
                Stream.of(
                        fixGate(getA.get(), getB.get(), setB, clazz, gates),
                        fixGate(getB.get(), getA.get(), setA, clazz, gates)
                ).filter(Supplier::get).findAny().orElseThrow(() -> e);

                return gates.stream().filter(clazz::isInstance)
                        .filter(g -> g.isConnected(getA.get(), getB.get()))
                        .findAny().orElseThrow(() -> new IllegalStateException("Gate %s not found for a = %s and b = %s".formatted(clazz.getName(), getA.get().getName(), getB.get().getName())));
            }
        }

        public static Supplier<Boolean> fixGate(Wire current, Wire missing, Consumer<Wire> replaceWire, Class<? extends Gate> clazz, List<Gate> gates) {
            return () -> {
                Optional<? extends Gate> replaceGate = gates.stream().filter(clazz::isInstance)
                        .map(clazz::cast)
                        .filter(g -> g.isConnected(current))
                        .findAny();

                replaceGate.ifPresent(g -> {
                    if (g.getLeft() == current) {
                        gates.stream().filter(gate -> gate.getOutput() == g.getRight())
                                .forEach(gate -> gate.setOutput(missing));

                        replaceWire.accept(g.getRight());
                    } else if (g.getRight() == current) {
                        gates.stream().filter(gate -> gate.getOutput() == g.getLeft())
                                .findAny().ifPresent(gate -> gate.setOutput(missing));

                        replaceWire.accept(g.getLeft());
                    }
                });

                return replaceGate.isPresent();
            };
        }
    }

    public static class AndGate extends Gate {

        public AndGate(Wire left, Wire right, Wire output) {
            super(left, right, output);
        }

        @Override
        protected long evaluate(Long left, Long right) {
            return left & right;
        }
    }

    public static class OrGate extends Gate {

        public OrGate(Wire left, Wire right, Wire output) {
            super(left, right, output);
        }

        @Override
        protected long evaluate(Long left, Long right) {
            return left | right;
        }
    }

    public static class XorGate extends Gate {

        public XorGate(Wire left, Wire right, Wire output) {
            super(left, right, output);
        }

        @Override
        protected long evaluate(Long left, Long right) {
            return left ^ right;
        }
    }

    public interface ConnectedWireListener {
        void onConnected(Wire wire);
    }

    public interface OutputFixListener {
        void onOutputFix(Wire oldOutput, Wire newOutput);
    }
}
