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
        public static Set<Wire> switchedWires = new HashSet<>();

        public Root(List<Wire> wires, List<Gate> gates) {
            this.wires = wires;
            this.gates = gates;
        }

        public long evaluate() {
            String binary = wires.stream().filter(w -> w.getName().startsWith("z")).sorted(Comparator.comparing(Wire::getName).reversed())
                    .map(Wire::getValue).map(b -> b ? "1" : "0").collect(Collectors.joining());

            return Long.parseLong(binary, 2);
        }

        public RippleCarryAdder buildRippleCarryAdder() {
            return new RippleCarryAdder().build(wires, gates);
        }

        public String part2() {
            RippleCarryAdder rippleCarryAdder = buildRippleCarryAdder();

            return switchedWires
                    .stream().map(Wire::getName).sorted().collect(Collectors.joining(","));
        }

    }

    /**
     * https://upload.wikimedia.org/wikipedia/commons/8/85/RippleCarry2.gif
     */
    @Data
    public static class RippleCarryAdder {

        private HalfAdder firstHalfAdder;
        private LinkedList<FullAdder> fullAdders = new LinkedList<>();

        public RippleCarryAdder() {

        }

        public RippleCarryAdder build(List<Wire> wires, List<Gate> gates) {
            RippleCarryAdder rippleCarryAdder = new RippleCarryAdder();

            LinkedList<Wire> aWires = wires.stream().filter(w -> w.getName().startsWith("x")).sorted(Comparator.comparing(Wire::getName)).collect(Collectors.toCollection(LinkedList::new));
            LinkedList<Wire> bWires = wires.stream().filter(w -> w.getName().startsWith("y")).sorted(Comparator.comparing(Wire::getName)).collect(Collectors.toCollection(LinkedList::new));
            LinkedList<Wire> zWires = wires.stream().filter(w -> w.getName().startsWith("z")).sorted(Comparator.comparing(Wire::getName)).collect(Collectors.toCollection(LinkedList::new));

            Wire a = aWires.poll();
            Wire b = bWires.poll();
            Wire z = zWires.poll();

            rippleCarryAdder.setFirstHalfAdder(HalfAdder.buildFrom(a, b, z, gates));

            Wire cin = rippleCarryAdder.getFirstHalfAdder().getCarry();

            while (!aWires.isEmpty() && !bWires.isEmpty()) {
                a = aWires.poll();
                b = bWires.poll();
                z = zWires.poll();

                FullAdder fullAdder = new FullAdder().buildFrom(a, b, cin, z, gates);

                rippleCarryAdder.getFullAdders().add(fullAdder);

                cin = fullAdder.getCout();
            }

            return rippleCarryAdder;
        }
    }

    /**
     * https://upload.wikimedia.org/wikipedia/commons/9/92/Halfadder.gif
     */
    @Data
    public static class HalfAdder {

        private Wire a;
        private Wire b;
        private Wire carry;

        private XorGate xorGate;
        private AndGate andGate;

        public HalfAdder() {
        }

        public static HalfAdder buildFrom(Wire a, Wire b, List<Gate> gates) {
            HalfAdder halfAdder = new HalfAdder();

            halfAdder.setA(a);
            halfAdder.setB(b);

            Gate xorGate = Gate.findGate(
                    halfAdder::getA, halfAdder::setA,
                    halfAdder::getB, halfAdder::setB,
                    XorGate.class, gates);

            halfAdder.setXorGate((XorGate) xorGate);

            Gate andGate = Gate.findGate(
                    halfAdder::getA, halfAdder::setA,
                    halfAdder::getB, halfAdder::setB,
                    AndGate.class, gates);

            halfAdder.setAndGate((AndGate) andGate);

            return halfAdder;
        }

        public static HalfAdder buildFrom(Wire a, Wire b, Wire sum, List<Gate> gates) {
            HalfAdder halfAdder = buildFrom(a, b, gates);

            if (halfAdder.getSum() != sum) {
                throw new IllegalStateException("Sum wire should be %s the same for a = %s and b = %s, but we found %s".formatted(sum.getName(), a.getName(), b.getName(), halfAdder.getSum()));
            }

            return halfAdder;
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
     * https://upload.wikimedia.org/wikipedia/commons/5/57/Fulladder.gif
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


        public FullAdder buildFrom(Wire a, Wire b, Wire cin, Wire sum, List<Gate> gates) {
            FullAdder fullAdder = new FullAdder();

            fullAdder.setA(a);
            fullAdder.setB(b);
            fullAdder.setCin(cin);

            fullAdder.setHalfAdderAB(HalfAdder.buildFrom(a, b, gates));

            fullAdder.setHalfAdderSumCin(HalfAdder.buildFrom(fullAdder.getHalfAdderAB().getSum(), cin, gates));

            Gate gate = Gate.findGate(
                    fullAdder.getHalfAdderSumCin()::getCarry, fullAdder.getHalfAdderSumCin()::setCarry,
                    fullAdder.getHalfAdderAB()::getCarry, fullAdder.getHalfAdderAB()::setCarry,
                    OrGate.class, gates);
            fullAdder.setOrGateCout((OrGate) gate);

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
        private Optional<Boolean> value = Optional.empty();

        private final List<ConnectedWireListener> connectedWireListeners = new ArrayList<>();

        public Wire(String name) {
            this.name = name;
        }

        public void addConnectedWireListener(ConnectedWireListener connectedWireListener) {
            connectedWireListeners.add(connectedWireListener);
        }

        public void removeConnectedWireListener(ConnectedWireListener connectedWireListener) {
            connectedWireListeners.remove(connectedWireListener);
        }

        public void connect(boolean value) {
            this.value = Optional.of(value);
            List<ConnectedWireListener> connectedWireListeners = getConnectedWireListeners().stream().toList();
            connectedWireListeners.forEach(connectedWireListener -> connectedWireListener.onConnected(this));
        }

        public boolean getValue() {
            return this.value.orElseThrow(() -> new IllegalStateException("Wire is not connected"));
        }

        public boolean isConnected() {
            return this.value.isPresent();
        }
    }

    @Data
    public static abstract class Gate implements ConnectedWireListener {

        private final Wire left;
        private final Wire right;
        private Wire output;

        public Gate(Wire left, Wire right, Wire output) {
            this.left = left;
            this.right = right;
            this.output = output;

            if (!left.isConnected()) {
                left.addConnectedWireListener(this);
            }

            if (!right.isConnected()) {
                right.addConnectedWireListener(this);
            }

            if (left.isConnected() && right.isConnected()) {
                output.connect(this.evaluate(left.getValue(), right.getValue()));
            }
        }

        public boolean isConnected(Wire... wire) {
            return Arrays.stream(wire).allMatch(l -> List.of(left, right).contains(l));
        }

        public void setOutput(Wire output) {
            Root.switchedWires.add(this.output);
            Root.switchedWires.add(output);

            this.output = output;

            if (left.isConnected() && right.isConnected()) {
                output.connect(this.evaluate(left.getValue(), right.getValue()));
            }
        }

        @Override
        public void onConnected(Wire wire) {
            wire.removeConnectedWireListener(this);
            if (left.isConnected() && right.isConnected()) {
                output.connect(this.evaluate(left.getValue(), right.getValue()));
            }
        }

        protected abstract boolean evaluate(boolean left, boolean right);

        public static Supplier<Boolean> fixGate(Wire current, Wire missing, Consumer<Wire> replaceWire, Class<? extends  Gate> clazz, List<Gate> gates) {
            return () -> {
                Optional<? extends Gate> replaceGate = gates.stream().filter(clazz::isInstance)
                        .map(clazz::cast)
                        .filter(g -> g.isConnected(current))
                        .findAny();

                replaceGate.ifPresent(g -> {

                    if (g.getLeft() == current) {
                        gates.stream().filter(gate -> gate.getOutput() == g.getRight())
                                .findAny().ifPresent(gate -> gate.setOutput(missing));

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

        public static Gate findGate(Supplier<Wire> getA, Consumer<Wire> setA, Supplier<Wire> getB, Consumer<Wire> setB, Class<? extends Gate> clazz, List<Gate> gates) {

            try {
                return gates.stream().filter(clazz::isInstance)
                        .filter(g -> g.isConnected(getA.get(), getB.get()))
                        .findAny().orElseThrow(() -> new IllegalStateException("OrGate not found for a = %s and b = %s".formatted(getA.get().getName(), getB.get().getName())));
            } catch (IllegalStateException e) {
                Stream.of(
                        fixGate(getA.get(), getB.get(), setB, clazz, gates),
                        fixGate(getB.get(), getA.get(), setA, clazz, gates)
                ).filter(Supplier::get).findAny().orElseThrow(() -> e);

                return gates.stream().filter(clazz::isInstance)
                        .filter(g -> g.isConnected(getA.get(), getB.get()))
                        .findAny().orElseThrow(() -> new IllegalStateException("OrGate not found for a = %s and b = %s".formatted(getA.get().getName(), getB.get().getName())));
            }
        }
    }

    public static class AndGate extends Gate {

        public AndGate(Wire left, Wire right, Wire output) {
            super(left, right, output);
        }

        @Override
        protected boolean evaluate(boolean left, boolean right) {
            return left && right;
        }
    }

    public static class OrGate extends Gate {

        public OrGate(Wire left, Wire right, Wire output) {
            super(left, right, output);
        }

        @Override
        protected boolean evaluate(boolean left, boolean right) {
            return left || right;
        }
    }

    public static class XorGate extends Gate {

        public XorGate(Wire left, Wire right, Wire output) {
            super(left, right, output);
        }

        @Override
        protected boolean evaluate(boolean left, boolean right) {
            return left ^ right;
        }
    }

    public interface ConnectedWireListener {

        void onConnected(Wire wire);

    }

}
