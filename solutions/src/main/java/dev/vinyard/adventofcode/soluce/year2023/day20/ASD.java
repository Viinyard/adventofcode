package dev.vinyard.adventofcode.soluce.year2023.day20;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ASD {

    public static class Root {

        private final Dispatcher dispatcher;

        public Root(Dispatcher dispatcher) {
            this.dispatcher = dispatcher;
            this.dispatcher.init();
        }

        public long pressButton(int times) {


            for (int i = 0; i < times; i++)
                dispatcher.pressButton();

            Map<Pulse, Long> pulseCount = dispatcher.pulses.stream().collect(Collectors.groupingBy(Wire::getPulse, Collectors.counting()));

            return pulseCount.values().stream().reduce((a, b) -> a * b).orElse(0L);
        }
    }

    public static class Dispatcher {

        private final Map<String, Module> modules = new HashMap<>();
        private final LinkedList<Wire> stack = new LinkedList<>();
        private final List<Wire> pulses = new ArrayList<>();
        private final Module dummy = new DummyModule(this);

        public Dispatcher() {
        }

        public void init() {
            modules.values().forEach(Module::init);
        }

        private List<Wire> tick() {
            List<Wire> tick = new ArrayList<>();

            while (!stack.isEmpty()) {
                Wire wire = stack.pop();

                pulses.add(wire);
                tick.add(wire);
            }

            return tick;
        }

        public void pressButton() {
            this.getButton().press();
            while (!stack.isEmpty()) {

                List<Wire> tick = tick();

                tick.forEach(this::process);
            }
        }

        private void process(Wire wire) {
            Module module = modules.getOrDefault(wire.to, dummy);
            module.pulse(wire);
        }

        public ButtonModule getButton() {
            return new ButtonModule(List.of("broadcaster"), this);
        }

        public void registerModule(String name, Module module) {
            this.modules.put(name, module);
        }

        public void dispatch(Wire wire) {
            stack.addLast(wire);
        }

        public void registerInputs(String name, List<String> outputs) {
            outputs.stream().map(this.modules::get).flatMap(Stream::ofNullable).forEach(module -> {
                module.addInput(name);
            });
        }
    }

    @Getter
    public static class Wire {

        private final String from;
        private final String to;
        private final Pulse pulse;

        public Wire(String from, String to, Pulse pulse) {
            this.from = from;
            this.to = to;
            this.pulse = pulse;
        }

        @Override
        public String toString() {
            return "%s -%s-> %s".formatted(from, pulse.toString().toLowerCase(), to);
        }
    }

    public static class DummyModule extends Module {

        public DummyModule(Dispatcher dispatcher) {
            super("", List.of(), dispatcher);
        }

        @Override
        public void pulse(Wire wire) {
            // do nothing
        }
    }

    /**
     * Modules communicate using pulses.
     * Each pulse is either a high pulse or a low pulse.
     * When a module sends a pulse, it sends that type of pulse to each module in its list of destination modules.
     */
    public static abstract class Module {

        protected final String name;
        protected final List<String> outputs;
        protected final Dispatcher dispatcher;

        public Module(String name, List<String> outputs, Dispatcher dispatcher) {
            this.name = name;
            this.outputs = outputs;
            this.dispatcher = dispatcher;
            this.dispatcher.registerModule(name, this);
        }

        public void init() {
            this.dispatcher.registerInputs(name, outputs);
        }

        public void addInput(String input) {
            // do nothing by default
        }

        public abstract void pulse(Wire wire);

        protected void sendPulse(Pulse pulse) {
            outputs.stream().map(to -> new Wire(this.name, to, pulse)).forEach(dispatcher::dispatch);
        }
    }

    public static class BroadcasterModule extends Module {

        public BroadcasterModule(List<String> outputs, Dispatcher dispatcher) {
            super("broadcaster", outputs, dispatcher);
        }

        @Override
        public void pulse(Wire wire) {
            this.sendPulse(wire.pulse);
        }
    }

    public static class ButtonModule extends Module {

        public ButtonModule(List<String> outputs, Dispatcher dispatcher) {
            super("button", outputs, dispatcher);
        }

        public void press() {
            this.sendPulse(Pulse.LOW);
        }

        @Override
        public void pulse(Wire wire) {
            throw new UnsupportedOperationException("Button modules cannot receive pulses.");
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

        public FlipFlopModule(String name, List<String> outputs, Dispatcher dispatcher) {
            super(name, outputs, dispatcher);
        }

        private void switchState() {
            switch (this.state) {
                case OFF -> {
                    this.state = State.ON;
                    this.sendPulse(Pulse.HIGH);
                }
                case ON -> {
                    this.state = State.OFF;
                    this.sendPulse(Pulse.LOW);
                }
            }
        }

        @Override
        public void pulse(Wire wire) {
            switch (wire.pulse) {
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

        private final Map<String, Pulse> pulses = new HashMap<>();

        public ConjunctionModule(String name, List<String> outputs, Dispatcher dispatcher) {
            super(name, outputs, dispatcher);
        }

        @Override
        public void addInput(String input) {
            pulses.put(input, Pulse.LOW);
        }

        @Override
        public void pulse(Wire wire) {
            pulses.put(wire.from, wire.pulse);

            if (pulses.values().stream().allMatch(Pulse.HIGH::equals)) {
                this.sendPulse(Pulse.LOW);
            } else {
                this.sendPulse(Pulse.HIGH);
            }
        }
    }

    public enum State {
        ON, OFF
    }

    public enum Pulse {
        HIGH, LOW
    }

}
