package dev.vinyard.adventofcode.soluce.year2024.day23;

import lombok.Getter;
import org.jgrapht.Graph;
import org.jgrapht.alg.cycle.HawickJamesSimpleCycles;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class ASD {

    public static class Root {

        private final List<Connection> connections;

        public Root(List<Connection> connections) {
            this.connections = connections;
        }

        // https://jgrapht.org/javadoc-1.3.0/org/jgrapht/alg/clique/BronKerboschCliqueFinder.html
        public long part1() {
            Graph<Computer, DefaultEdge> graph = createEmptyGraph();

            connections.stream().flatMap(Connection::stream).distinct().forEach(graph::addVertex);

            connections.forEach(c -> {
                graph.addEdge(c.getComputer1(), c.getComputer2());
                graph.addEdge(c.getComputer2(), c.getComputer1());
            });

            HawickJamesSimpleCycles<Computer, DefaultEdge> c = new HawickJamesSimpleCycles<>(graph);
            c.setPathLimit(3);

            // divide by 2 because the cycles are counted twice, once for each direction (it's a directed graph)
            return c.findSimpleCycles().stream().filter(t -> t.size() == 3)
                    .filter(t -> t.stream().anyMatch(c1 -> c1.getName().startsWith("t"))).count() / 2;
        }

        private Graph<Computer, DefaultEdge> createEmptyGraph() {
            return GraphTypeBuilder.<Computer, DefaultEdge>directed()
                    .allowingMultipleEdges(false)
                    .allowingSelfLoops(false)
                    .edgeClass(DefaultEdge.class)
                    .weighted(true)
                    .buildGraph();
        }
    }

    @Getter
    public static class Connection {

        private final Computer computer1;
        private final Computer computer2;

        public Connection(Computer computer1, Computer computer2) {
            this.computer1 = computer1;
            this.computer2 = computer2;
        }

        public Stream<Computer> stream() {
            return Stream.of(computer1, computer2);
        }

    }

    @Getter
    public static class Computer {

        private final String name;

        public Computer(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Computer computer)) return false;
            return Objects.equals(name, computer.name);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(name);
        }

        @Override
        public String toString() {
            return name;
        }
    }

}
