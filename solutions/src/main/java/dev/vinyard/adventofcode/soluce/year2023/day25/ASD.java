package dev.vinyard.adventofcode.soluce.year2023.day25;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.scoring.EdgeBetweennessCentrality;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.*;

public class ASD {

    public static class Root {
        private final HashMap<String, Machine> machines;

        public Root(HashMap<String, Machine> machines) {
            this.machines = machines;
        }

        public Graph<String, DefaultEdge> createEmptyGraph() {
            return new SimpleGraph<>(DefaultEdge.class);
        }

        public Graph<String, DefaultEdge> buildGraph() {
            Graph<String, DefaultEdge> graph = createEmptyGraph();

            machines.keySet().forEach(graph::addVertex);

            machines.values().forEach(left -> left.connectedTo.forEach(right -> graph.addEdge(left.name, right.name)));

            return graph;
        }

        public List<DefaultEdge> findStrongestConnections(Graph<String, DefaultEdge> graph) {
            EdgeBetweennessCentrality<String, DefaultEdge> edgeBetweennessCentrality = new EdgeBetweennessCentrality<>(graph);

            return graph.edgeSet().stream().sorted(Comparator.comparingDouble(edgeBetweennessCentrality::getEdgeScore).reversed())
                    .limit(3)
                    .toList();
        }

        public long getBlockProductOfGraph() {
            Graph<String, DefaultEdge> graph = buildGraph();
            findStrongestConnections(graph).forEach(graph::removeEdge);

            ConnectivityInspector<String, DefaultEdge> inspector = new ConnectivityInspector<>(graph);

            List<Set<String>> connectedComponents = inspector.connectedSets();

            return connectedComponents.stream()
                    .mapToLong(Set::size)
                    .reduce(1, (a, b) -> a * b);
        }
    }

    public static class Machine {
        private final String name;
        private final List<Machine> connectedTo = new ArrayList<>();

        public Machine(String name) {
            this.name = name;
        }

        public void addConnection(Machine machine) {
            connectedTo.add(machine);
        }
    }
}
