package dev.vinyard.adventofcode.soluce.year2025.day11;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;

import java.util.List;
import java.util.Objects;

public class ASD {

    public static class Root {

        private final List<Wiring> wirings;

        public Root(List<Wiring> wirings) {
            this.wirings = wirings;
        }

        private Graph<Device, DefaultEdge> createEmptyGraph() {
            return GraphTypeBuilder.<Device, DefaultEdge>directed()
                    .allowingMultipleEdges(false)
                    .allowingSelfLoops(false)
                    .edgeClass(DefaultEdge.class)
                    .weighted(true)
                    .buildGraph();
        }

        public Graph<Device, DefaultEdge> buildGraph() {
            Graph<Device, DefaultEdge> graph = createEmptyGraph();

            wirings.forEach(wiring -> {
                graph.addVertex(wiring.from);
                wiring.to.forEach(device -> {
                    graph.addVertex(device);
                    graph.addEdge(wiring.from, device);
                });
            });

            return graph;
        }

        public long solution1() {
            AllDirectedPaths<Device, DefaultEdge> allDirectedPaths = new AllDirectedPaths<>(buildGraph());

            return allDirectedPaths.getAllPaths(new Device("you"), new Device("out"), true, null).size();
        }

    }

    public static class Wiring {

        private final Device from;
        private final List<Device> to;

        public Wiring(Device from, List<Device> to) {
            this.from = from;
            this.to = to;
        }
    }

    public static class Device {

        private final String id;

        public Device(String id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Device device)) return false;
            return Objects.equals(id, device.id);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }
    }

}
