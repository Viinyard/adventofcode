package dev.vinyard.adventofcode.soluce.year2025.day8;

import org.apache.commons.geometry.euclidean.threed.Vector3D;
import org.apache.commons.lang3.tuple.Pair;
import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.util.UnionFind;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;

import java.util.*;

public class ASD {

    public static class Root {

        private final List<JunctionBoxe> junctionBoxes;

        private final int connections;

        public Root(List<JunctionBoxe> junctionBoxes, int connections) {
            this.junctionBoxes = junctionBoxes;
            this.connections = connections;
        }

        private Graph<JunctionBoxe, DefaultWeightedEdge> createEmptyGraph() {
            return GraphTypeBuilder.<JunctionBoxe, DefaultWeightedEdge>undirected()
                    .allowingMultipleEdges(false)
                    .allowingSelfLoops(false)
                    .edgeClass(DefaultWeightedEdge.class)
                    .weighted(true)
                    .buildGraph();
        }

        public long solution1() {
            Graph<JunctionBoxe, DefaultWeightedEdge> graph = createEmptyGraph();

            junctionBoxes.forEach(graph::addVertex);

            List<Pair<JunctionBoxe, JunctionBoxe>> strings = new ArrayList<>();

            for (int i = 0; i < junctionBoxes.size(); i++)
                for (int j = i + 1; j < junctionBoxes.size(); j++)
                    strings.add(Pair.of(junctionBoxes.get(i), junctionBoxes.get(j)));

            UnionFind<JunctionBoxe> unionFind = new UnionFind<>(new HashSet<>(junctionBoxes));

            strings.stream()
                    .sorted(Comparator.comparingDouble(p -> p.getLeft().distanceTo(p.getRight())))
                    .limit(connections)
                    .filter(p -> !unionFind.inSameSet(p.getLeft(), p.getRight()))
                    .forEach(pair -> {
                        unionFind.union(pair.getLeft(), pair.getRight());
                        DefaultWeightedEdge defaultWeightedEdge = graph.addEdge(pair.getLeft(), pair.getRight());
                        Optional.ofNullable(defaultWeightedEdge).ifPresent(edge -> graph.setEdgeWeight(edge, pair.getLeft().distanceTo(pair.getRight())));
                    });

            ConnectivityInspector<JunctionBoxe, DefaultWeightedEdge> inspector = new ConnectivityInspector<>(graph);

            return inspector.connectedSets().stream()
                    .map(Set::size)
                    .sorted(Comparator.reverseOrder())
                    .limit(3)
                    .reduce(1, (a, b) -> a * b);
        }

    }

    public record JunctionBoxe(Vector3D vector3D) {
        public double distanceTo(JunctionBoxe junctionBoxe) {
            return this.vector3D.distance(junctionBoxe.vector3D);
        }
    }
}
