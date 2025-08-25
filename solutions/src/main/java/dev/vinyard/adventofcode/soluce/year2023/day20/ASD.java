package dev.vinyard.adventofcode.soluce.year2023.day20;

import lombok.Getter;

import java.math.BigInteger;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ASD {

    public static class Root {

        private final Registry registry;

        public Root(Registry registry) {
            this.registry = registry;
        }

        public long pressButton(int times) {
            Circuit circuit = new Circuit();
            PulseCounter counter = new PulseCounter();
            registry.modules.values().forEach(m -> {
                m.addListener(circuit);
                m.addListener(counter);
            });

            ButtonModule button = (ButtonModule) registry.getModule("button");
            button.addWakeListener(circuit);

            IntStream.range(0, times).forEach(i -> button.press());

            return counter.getProduct();
        }

        public long part2() {
            BroadcasterModule broadcaster = (BroadcasterModule) registry.getModule("broadcaster");

            BinaryOperator<BigInteger> lcm = (a, b) -> a.multiply(b).divide(a.gcd(b));

            return broadcaster.outputs.stream()
                    .map(FlipFlopModule.class::cast)
                    .map(BinaryCounterModule::from)
                    .map(BinaryCounterModule::getNandValue)
                    .reduce(lcm)
                    .orElseThrow()
                    .longValue();
        }
    }

    public static class PulseCounter implements PulseListener {
        @Getter
        private final Map<Signal, Long> pulseCount = new HashMap<>();

        @Override
        public void onPulse(Pulse pulse) {
            pulseCount.merge(pulse.signal(), 1L, Long::sum);
        }

        public long getProduct() {
            return pulseCount.values().stream().reduce((a, b) -> a * b).orElse(0L);
        }
    }

    public static class Registry {

        Map<String, Module> modules = new HashMap<>();

        public void registerModule(Module module) {
            this.modules.putIfAbsent(module.getName(), module);
        }

        public void registerModule(String name) {
            this.modules.putIfAbsent(name, null);
        }

        public void computeAll() {
            modules.keySet().forEach(name -> modules.computeIfAbsent(name, DummyModule::new));
        }

        public Module getModule(String name) {
            return modules.get(name);
        }
    }

    public static class Circuit implements PulseListener, WakeListener {

        private final LinkedList<Pulse> stack = new LinkedList<>();

        @Override
        public void onPulse(Pulse pulse) {
            stack.addLast(pulse);
        }

        @Override
        public void onWake() {
            while (!stack.isEmpty()) {
                List<Pulse> tick = new ArrayList<>(stack);
                stack.clear();

                tick.forEach(s -> s.to().pulse(s));
            }
        }
    }

    public static class BinaryCounterModule {

        private final LinkedList<FlipFlopModule> bits;

        private BinaryCounterModule(LinkedList<FlipFlopModule> bits) {
            this.bits = bits;
            if (bits.size() != 12) {
                throw new IllegalArgumentException("A binary counter must have exactly 12 bits.");
            }
        }

        public static BinaryCounterModule from(FlipFlopModule bit) {
            LinkedList<FlipFlopModule> bits = Stream.iterate(bit, Objects::nonNull, a -> a.outputs.stream().filter(FlipFlopModule.class::isInstance).map(FlipFlopModule.class::cast).findAny().orElse(null))
                    .collect(Collectors.toCollection(LinkedList::new));

            ConjunctionModule nand = bits.stream().flatMap(m -> Stream.of(
                            m.getInputs(),
                            m.getOutputs()
                    )).flatMap(Collection::stream).distinct()
                    .filter(ConjunctionModule.class::isInstance)
                    .map(ConjunctionModule.class::cast)
                    .reduce((a, b) -> {
                        if (!a.equals(b)) throw new IllegalArgumentException("If a binary counter has multiple nand modules, the cycle of the counter is more complex than expected.");
                        return a;
                    })
                    .orElseThrow(() -> new IllegalArgumentException("If a binary counter has no nand module, the cycle of the counter is more complex than expected."));

            for (FlipFlopModule b : bits) {
                boolean isConnectedToNand = b.outputs.stream().anyMatch(nand::equals);
                boolean isNandConnectedToBit = b.inputs.stream().anyMatch(nand::equals);

                if (!isConnectedToNand && !isNandConnectedToBit) {
                    throw new IllegalArgumentException("If a bit of a binary counter is not connected to a nand module as output, it must be connected to a nand module as input. If it's not, the cycle of the counter is more complex than expected.");
                }
            }

            return new BinaryCounterModule(bits);
        }

        public BigInteger getNandValue() {
            BigInteger nandValue = BigInteger.ZERO;

            for (int i = 0; i < bits.size(); i++) {
                FlipFlopModule bit = bits.get(i);

                boolean isConnectedToNand = bit.outputs.stream().anyMatch(m -> m instanceof ConjunctionModule);

                if (isConnectedToNand) {
                    nandValue = nandValue.setBit(i);
                }
            }

            return nandValue;
        }
    }

    public record Pulse(Module from, Module to, Signal signal) {}

    public interface PulseListener {
        void onPulse(Pulse pulse);
    }

    public interface WakeListener {
        void onWake();
    }

    /**
     * Modules communicate using pulses.
     * Each pulse is either a high pulse or a low pulse.
     * When a module sends a pulse, it sends that type of pulse to each module in its list of destination modules.
     */
    @Getter
    public static abstract class Module {

        protected List<PulseListener> listeners = new ArrayList<>();
        protected final String name;
        protected final List<Module> outputs = new ArrayList<>();
        protected final List<Module> inputs = new ArrayList<>();

        public Module(String name) {
            this.name = name;
        }

        public void addListener(PulseListener listener) {
            this.listeners.add(listener);
        }

        public void addOutput(Module module) {
            this.outputs.add(module);
            module.addInput(this);
        }

        public void addInput(Module module) {
            this.inputs.add(module);
        }

        public abstract void pulse(Pulse pulse);

        protected void sendPulse(Signal signal) {
            outputs.stream().map(to -> new Pulse(this, to, signal)).forEach(p -> listeners.forEach(l -> l.onPulse(p)));
        }
    }

    public static class ButtonModule extends Module {

        private final List<WakeListener> wakeListeners = new ArrayList<>();

        public ButtonModule() {
            super("button");
        }

        public void addWakeListener(WakeListener listener) {
            this.wakeListeners.add(listener);
        }

        public void press() {
            sendPulse(Signal.LOW);
            wakeListeners.forEach(WakeListener::onWake);
        }

        @Override
        public void pulse(Pulse pulse) {
            throw new UnsupportedOperationException("A button module cannot receive pulses.");
        }
    }

    public static class DummyModule extends Module {

        public DummyModule(String name) {
            super(name);
        }

        @Override
        public void pulse(Pulse pulse) {
            // do nothing
        }
    }

    public static class BroadcasterModule extends Module {

        public BroadcasterModule() {
            super("broadcaster");
        }

        @Override
        public void pulse(Pulse pulse) {
            this.sendPulse(pulse.signal);
        }
    }

    /**
     * Flip-flop modules (prefix %) are either on or off;
     * they are initially off.
     * If a flip-flop module receives a high pulse, it is ignored and nothing happens.
     * However, if a flip-flop module receives a low pulse, it flips between on and off.
     * If it was off, it turns on and sends a high pulse.
     * If it was on, it turns off and sends a low pulse.
     */
    public static class FlipFlopModule extends Module {

        private State state = State.OFF;

        public FlipFlopModule(String name) {
            super(name);
        }

        private void switchState() {
            switch (this.state) {
                case OFF -> {
                    this.state = State.ON;
                    this.sendPulse(Signal.HIGH);
                }
                case ON -> {
                    this.state = State.OFF;
                    this.sendPulse(Signal.LOW);
                }
            }
        }

        @Override
        public void pulse(Pulse pulse) {
            switch (pulse.signal) {
                case HIGH -> {
                    // do nothing
                }
                case LOW -> this.switchState();
            }
        }
    }

    /**
     * Conjunction modules (prefix &) remember the type of the most recent pulse received from each of their connected input modules;
     * they initially default to remembering a low pulse for each input.
     * When a pulse is received, the conjunction module first updates its memory for that input.
     * Then, if it remembers high pulses for all inputs, it sends a low pulse;
     * otherwise, it sends a high pulse.
     */
    public static class ConjunctionModule extends Module {

        private final Map<Module, Signal> pulses = new HashMap<>();

        public ConjunctionModule(String name) {
            super(name);
        }

        @Override
        public void addInput(Module module) {
            super.addInput(module);
            pulses.put(module, Signal.LOW);
        }

        @Override
        public void pulse(Pulse pulse) {
            pulses.put(pulse.from, pulse.signal);

            if (pulses.values().stream().allMatch(Signal.HIGH::equals)) {
                this.sendPulse(Signal.LOW);
            } else {
                this.sendPulse(Signal.HIGH);
            }
        }
    }

    public enum State {
        ON, OFF
    }

    public enum Signal {
        HIGH, LOW
    }

}
