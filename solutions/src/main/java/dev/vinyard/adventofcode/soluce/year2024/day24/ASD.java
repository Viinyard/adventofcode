package dev.vinyard.adventofcode.soluce.year2024.day24;

import lombok.Data;
import lombok.Getter;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

            rippleCarryAdder.setFirstHalfAdder(HalfAdder.buildByABSum(a, b, z, gates));

            Wire cin = rippleCarryAdder.getFirstHalfAdder().getCarry();

            while (!aWires.isEmpty() && !bWires.isEmpty()) {
                a = aWires.poll();
                b = bWires.poll();
                z = zWires.poll();

                FullAdder fullAdder = new FullAdder().buildByABCinSum(a, b, cin, z, gates);

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

        public void findBySum(Wire sum, List<Gate> gates) {
            this.xorGate = gates.stream().filter(XorGate.class::isInstance)
                    .map(XorGate.class::cast)
                    .filter(g -> g.getOutput() == sum)
                    .findAny().orElseThrow();

            this.a = this.xorGate.getLeft();
            this.b = this.xorGate.getRight();

            this.andGate = gates.stream().filter(AndGate.class::isInstance)
                    .map(AndGate.class::cast)
                    .filter(g -> g.getLeft() == a && g.getRight() == b)
                    .findAny().orElseThrow();
        }

        public static HalfAdder buildByAB(Wire a, Wire b, List<Gate> gates) {
            HalfAdder halfAdder = new HalfAdder();

            halfAdder.setA(a);
            halfAdder.setB(b);

            Predicate<Gate> gatePredicateAB = g -> g.getLeft() == halfAdder.getA() && g.getRight() == halfAdder.getB();
            Predicate<Gate> gatePredicateBA = g -> g.getLeft() == halfAdder.getB() && g.getRight() == halfAdder.getA();
            Predicate<Gate> gatePredicate = gatePredicateAB.or(gatePredicateBA);

            try {
                halfAdder.setXorGate(gates.stream().filter(XorGate.class::isInstance)
                        .map(XorGate.class::cast)
                        .filter(gatePredicate)
                        .findAny().orElseThrow(() -> new IllegalStateException("XorGate not found for a = %s and b = %s".formatted(a.getName(), b.getName()))));
            } catch (Exception e) {
                Optional<XorGate> xorGateA = gates.stream().filter(XorGate.class::isInstance)
                        .map(XorGate.class::cast)
                        .filter(g -> g.getLeft() == halfAdder.getA() || g.getRight() == halfAdder.getA())
                        .findAny();

                xorGateA.ifPresent(g -> {
                    Wire bWire = halfAdder.getB();

                    if (g.getLeft() == halfAdder.getA()) {
                        gates.stream().filter(gate -> gate.getOutput() == g.getRight())
                                .findAny().ifPresent(gate -> gate.setOutput(bWire));

                        halfAdder.setB(g.getRight());

                        halfAdder.setXorGate(gates.stream().filter(XorGate.class::isInstance)
                                .map(XorGate.class::cast)
                                .filter(gatePredicate)
                                .findAny().orElseThrow());
                    } else {
                        gates.stream().filter(gate -> gate.getOutput() == g.getLeft())
                                .findAny().ifPresent(gate -> gate.setOutput(bWire));

                        halfAdder.setB(g.getLeft());

                        halfAdder.setXorGate(gates.stream().filter(XorGate.class::isInstance)
                                .map(XorGate.class::cast)
                                .filter(gatePredicate)
                                .findAny().orElseThrow());
                    }
                });

                Optional<XorGate> xorGateB = gates.stream().filter(XorGate.class::isInstance)
                        .map(XorGate.class::cast)
                        .filter(g -> g.getLeft() == halfAdder.getB() || g.getRight() == halfAdder.getB())
                        .findAny();

                xorGateB.ifPresent(g -> {
                    Wire aWire = halfAdder.getA();

                    if (g.getLeft() == halfAdder.getB()) {
                        gates.stream().filter(gate -> gate.getOutput() == g.getRight())
                                .findAny().ifPresent(gate -> gate.setOutput(aWire));

                        halfAdder.setA(g.getRight());

                        halfAdder.setXorGate(gates.stream().filter(XorGate.class::isInstance)
                                .map(XorGate.class::cast)
                                .filter(gatePredicate)
                                .findAny().orElseThrow());
                    } else {
                        gates.stream().filter(gate -> gate.getOutput() == g.getLeft())
                                .findAny().ifPresent(gate -> gate.setOutput(aWire));

                        halfAdder.setA(g.getLeft());

                        halfAdder.setXorGate(gates.stream().filter(XorGate.class::isInstance)
                                .map(XorGate.class::cast)
                                .filter(gatePredicate)
                                .findAny().orElseThrow());
                    }
                });
            }

            try {
                halfAdder.setAndGate(gates.stream().filter(AndGate.class::isInstance)
                        .map(AndGate.class::cast)
                        .filter(gatePredicate)
                        .findAny().orElseThrow(() -> new IllegalStateException("AndGate not found for a = %s and b = %s".formatted(a.getName(), b.getName()))));
            } catch (Exception e) {
                Optional<AndGate> andGateA = gates.stream().filter(AndGate.class::isInstance)
                        .map(AndGate.class::cast)
                        .filter(g -> g.getLeft() == halfAdder.getA() || g.getRight() == halfAdder.getA())
                        .findAny();

                andGateA.ifPresent(g -> {
                    Wire bWire = halfAdder.getB();

                    if (g.getLeft() == halfAdder.getA()) {
                        gates.stream().filter(gate -> gate.getOutput() == g.getRight())
                                .findAny().ifPresent(gate -> gate.setOutput(bWire));

                        halfAdder.setB(g.getRight());

                        halfAdder.setAndGate(gates.stream().filter(AndGate.class::isInstance)
                                .map(AndGate.class::cast)
                                .filter(gatePredicate)
                                .findAny().orElseThrow());
                    } else {
                        gates.stream().filter(gate -> gate.getOutput() == g.getLeft())
                                .findAny().ifPresent(gate -> gate.setOutput(bWire));

                        halfAdder.setB(g.getLeft());

                        halfAdder.setAndGate(gates.stream().filter(AndGate.class::isInstance)
                                .map(AndGate.class::cast)
                                .filter(gatePredicate)
                                .findAny().orElseThrow());
                    }
                });

                if (andGateA.isEmpty()) {
                    Optional<AndGate> andGateB = gates.stream().filter(AndGate.class::isInstance)
                            .map(AndGate.class::cast)
                            .filter(g -> g.getLeft() == halfAdder.getB() || g.getRight() == halfAdder.getB())
                            .findAny();

                    andGateB.ifPresent(g -> {
                        Wire aWire = halfAdder.getA();

                        if (g.getLeft() == halfAdder.getB()) {
                            gates.stream().filter(gate -> gate.getOutput() == g.getRight())
                                    .findAny().ifPresent(gate -> gate.setOutput(aWire));

                            halfAdder.setA(g.getRight());

                            halfAdder.setAndGate(gates.stream().filter(AndGate.class::isInstance
                                    ).map(AndGate.class::cast)
                                    .filter(gatePredicate)
                                    .findAny().orElseThrow());
                        } else {
                            gates.stream().filter(gate -> gate.getOutput() == g.getLeft())
                                    .findAny().ifPresent(gate -> gate.setOutput(aWire));

                            halfAdder.setA(g.getLeft());

                            halfAdder.setAndGate(gates.stream().filter(AndGate.class::isInstance)
                                    .map(AndGate.class::cast)
                                    .filter(gatePredicate)
                                    .findAny().orElseThrow());
                        }
                    });
                }
            }


            return halfAdder;
        }

        public static HalfAdder buildByABSum(Wire a, Wire b, Wire sum, List<Gate> gates) {
            HalfAdder halfAdder = buildByAB(a, b, gates);

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

        public FullAdder buildByABCinSum(Wire a, Wire b, Wire cin, Wire sum, List<Gate> gates) {
            FullAdder fullAdder = new FullAdder();

            fullAdder.setA(a);
            fullAdder.setB(b);
            fullAdder.setCin(cin);

            fullAdder.setHalfAdderAB(HalfAdder.buildByAB(a, b, gates));

            fullAdder.setHalfAdderSumCin(HalfAdder.buildByAB(fullAdder.getHalfAdderAB().getSum(), cin, gates));

            Predicate<Gate> gatePredicateAB = g -> g.getLeft() == fullAdder.getHalfAdderSumCin().getCarry() && g.getRight() ==  fullAdder.getHalfAdderAB().getCarry();
            Predicate<Gate> gatePredicateBA = g -> g.getLeft() == fullAdder.getHalfAdderAB().getCarry() && g.getRight() == fullAdder.getHalfAdderSumCin().getCarry();
            Predicate<Gate> gatePredicate = gatePredicateAB.or(gatePredicateBA);

            try {
                fullAdder.setOrGateCout(gates.stream().filter(OrGate.class::isInstance)
                        .map(OrGate.class::cast)
                        .filter(gatePredicate)
                        .findAny().orElseThrow(() -> new IllegalStateException("OrGate not found for a = %s and b = %s".formatted(a.getName(), b.getName()))));
            } catch (Exception e) {
                Optional<OrGate> orGateHalfAdderSumCim = gates.stream().filter(OrGate.class::isInstance)
                        .map(OrGate.class::cast)
                        .filter(g -> g.getLeft() == fullAdder.getHalfAdderSumCin().getCarry() || g.getRight() ==  fullAdder.getHalfAdderSumCin().getCarry())
                        .findAny();

                orGateHalfAdderSumCim.ifPresent(g -> {
                    Wire carry = fullAdder.getHalfAdderAB().getCarry();

                    if (g.getLeft() == fullAdder.getHalfAdderSumCin().getCarry()) {
                        gates.stream().filter(gate -> gate.getOutput() == g.getRight())
                                .findAny().ifPresent(gate -> gate.setOutput(carry));

                        fullAdder.getHalfAdderAB().setCarry(g.getRight());

                        fullAdder.setOrGateCout(gates.stream().filter(OrGate.class::isInstance)
                                .map(OrGate.class::cast)
                                .filter(gatePredicate)
                                .findAny().orElseThrow());

                    } else {
                        gates.stream().filter(gate -> gate.getOutput() == g.getLeft())
                                .findAny().ifPresent(gate -> gate.setOutput(carry));

                        fullAdder.getHalfAdderAB().setCarry(g.getLeft());

                        fullAdder.setOrGateCout(gates.stream().filter(OrGate.class::isInstance)
                                .map(OrGate.class::cast)
                                .filter(gatePredicate)
                                .findAny().orElseThrow());


                    }
                });

                if (orGateHalfAdderSumCim.isEmpty()) {
                    Optional<OrGate> orGateHalfAdderAB = gates.stream().filter(OrGate.class::isInstance)
                            .map(OrGate.class::cast)
                            .filter(g -> g.getLeft() == fullAdder.getHalfAdderAB().getCarry() || g.getRight() == fullAdder.getHalfAdderAB().getCarry())
                            .findAny();

                    orGateHalfAdderAB.ifPresent(g -> {
                        Wire carry = fullAdder.getHalfAdderSumCin().getCarry();

                        if (g.getLeft() == fullAdder.getHalfAdderAB().getCarry()) {
                            gates.stream().filter(gate -> gate.getOutput() == g.getRight())
                                    .findAny().ifPresent(gate -> gate.setOutput(carry));

                            fullAdder.getHalfAdderSumCin().setCarry(g.getRight());

                            fullAdder.setOrGateCout(gates.stream().filter(OrGate.class::isInstance)
                                    .map(OrGate.class::cast)
                                    .filter(gatePredicate)
                                    .findAny().orElseThrow());
                        } else {
                            gates.stream().filter(gate -> gate.getOutput() == g.getLeft())
                                    .findAny().ifPresent(gate -> gate.setOutput(carry));

                            fullAdder.getHalfAdderSumCin().setCarry(g.getLeft());

                            fullAdder.setOrGateCout(gates.stream().filter(OrGate.class::isInstance)
                                    .map(OrGate.class::cast)
                                    .filter(gatePredicate)
                                    .findAny().orElseThrow());
                        }
                    });
                }
            }

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
