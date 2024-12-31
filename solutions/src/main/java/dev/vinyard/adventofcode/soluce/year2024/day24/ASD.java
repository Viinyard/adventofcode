package dev.vinyard.adventofcode.soluce.year2024.day24;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ASD {

    public static class Root {

        private final List<Wire> wires;
        private final List<Gate> gates;

        public Root(List<Wire> wires, List<Gate> gates) {
            this.wires = wires;
            this.gates = gates;
        }

        public long evaluate() {
            String binary = wires.stream().filter(w -> w.getName().startsWith("z")).sorted(Comparator.comparing(Wire::getName).reversed())
                    .map(Wire::getValue).map(b -> b ? "1" : "0").collect(Collectors.joining());

            return Long.parseLong(binary, 2);
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

    public static abstract class Gate implements ConnectedWireListener {

        private final Wire left;
        private final Wire right;
        private final Wire output;

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
