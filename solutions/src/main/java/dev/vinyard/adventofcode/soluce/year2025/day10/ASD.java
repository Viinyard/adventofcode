package dev.vinyard.adventofcode.soluce.year2025.day10;

import lombok.Getter;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class ASD {

    public static class Root {

        private final List<Machine> machines;

        public Root(List<Machine> machines) {
            this.machines = machines;
        }

        public long solution1() {
            return machines.stream().mapToLong(Machine::solve).sum();
        }
    }

    public static class Machine {
        private final List<ButtonWiringSchematic> buttonWiringSchematics;
        private final JoltageRequirements joltageRequirements;
        private final IndicatorLightDiagram indicateurLightDiagram;

        public Machine(IndicatorLightDiagram indicateurLightDiagram, List<ButtonWiringSchematic> buttonWiringSchematics, JoltageRequirements joltageRequirements) {
            this.indicateurLightDiagram = indicateurLightDiagram;
            this.buttonWiringSchematics = buttonWiringSchematics;
            this.joltageRequirements = joltageRequirements;
        }

        private Graph<Integer, DefaultWeightedEdge> createEmptyGraph() {
            return GraphTypeBuilder.<Integer, DefaultWeightedEdge>undirected()
                    .allowingMultipleEdges(false)
                    .allowingSelfLoops(false)
                    .edgeClass(DefaultWeightedEdge.class)
                    .weighted(true)
                    .buildGraph();
        }

        public long solve() {
            Graph<Integer, DefaultWeightedEdge> graph = createEmptyGraph();

            indicateurLightDiagram.getAllSubDiagrams().forEach(graph::addVertex);

            graph.vertexSet().forEach(startVertex -> {
                buttonWiringSchematics.stream().map(ButtonWiringSchematic::getWiringState).forEach(schematic -> {
                    graph.addEdge(startVertex, startVertex ^ schematic);
                });
            });

            ShortestPathAlgorithm<Integer, DefaultWeightedEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);

            return (long) dijkstraAlg.getPathWeight(0, indicateurLightDiagram.getState());
        }
    }

    public static class Button {

        private final int id;

        public Button(int id) {
            this.id = id;
        }

        public int getState() {
            return 1 << id;
        }
    }

    public static class ButtonWiringSchematic {
        private final List<Button> buttons;

        @Getter
        private final int wiringState;

        public ButtonWiringSchematic(List<Button> buttons) {
            this.buttons = buttons;
            this.wiringState = buttons.stream().map(Button::getState).reduce(0, (a, b) -> a | b);
        }
    }

    public static class JoltageRequirements {

        private final List<Integer> joltages;

        public JoltageRequirements(List<Integer> joltages) {
            this.joltages = joltages;
        }
    }

    public static class IndicatorLightDiagram {
        private final List<LightState> lightStates;

        public IndicatorLightDiagram(List<LightState> lightStates) {
            this.lightStates = lightStates;
        }

        public Integer getState() {
            return Stream.iterate(0, i -> i + 1).limit(lightStates.size())
                    .filter(i -> Objects.equals(lightStates.get(i), LightState.ON))
                    .reduce(0, (a, b) -> a | (1 << b));
        }

        public List<Integer> getAllSubDiagrams() {
            return Stream.iterate(0, i -> i + 1).limit(1L << lightStates.size()).toList();
        }
    }

    public enum LightState {
        OFF,
        ON
    }

}
