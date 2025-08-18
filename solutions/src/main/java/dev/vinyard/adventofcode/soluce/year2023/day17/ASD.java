package dev.vinyard.adventofcode.soluce.year2023.day17;

import lombok.Getter;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ASD {

    public static class Root {
        private final List<Block> blocks;
        private final Rectangle bounds;
        private final Block[][] grid;

        public Root(List<Block> blocks, Dimension dimension) {
            this.blocks = blocks;
            this.bounds = new Rectangle(dimension);
            this.grid = new Block[this.bounds.height][this.bounds.width];
            this.blocks.forEach(block -> grid[block.position.y][block.position.x] = block);
        }

        private Optional<Block> getBlockAt(Point position) {
            return Optional.of(position).filter(this.bounds::contains).map(p -> grid[p.y][p.x]);
        }

        public long getMinHeatLost() {
            Graph<LayerVertex, HeatLossEdge> graph = createHeatLossGraph();

            LayerVertex start = new LayerVertex(this.blocks.getFirst().getPosition(), null);
            LayerVertex dest = new LayerVertex(this.blocks.getLast().getPosition(), null);

            BiConsumer<LayerVertex, LayerVertex> addEdge = (s, d) -> {
                HeatLossEdge heatLossEdge = graph.addEdge(s, d);
                graph.setEdgeWeight(heatLossEdge, 0.0);
            };

            graph.addVertex(start);

            addEdge.accept(start, new LayerVertex(this.blocks.getFirst().getPosition(), Layer.HORIZONTAL));
            addEdge.accept(start, new LayerVertex(this.blocks.getFirst().getPosition(), Layer.VERTICAL));

            graph.addVertex(dest);

            addEdge.accept(new LayerVertex(this.blocks.getLast().getPosition(), Layer.HORIZONTAL), dest);
            addEdge.accept(new LayerVertex(this.blocks.getLast().getPosition(), Layer.VERTICAL), dest);

            ShortestPathAlgorithm<LayerVertex, HeatLossEdge> dijkstra = new DijkstraShortestPath<>(graph);

            GraphPath<LayerVertex, HeatLossEdge> path = dijkstra.getPath(start, dest);

            return (long) path.getWeight();
        }



        private Graph<LayerVertex, HeatLossEdge> createEmptyGraph() {
            return GraphTypeBuilder.<LayerVertex, HeatLossEdge>directed()
                    .allowingMultipleEdges(false)
                    .allowingSelfLoops(false)
                    .edgeClass(HeatLossEdge.class)
                    .weighted(true)
                    .buildGraph();
        }

        private Graph<LayerVertex, HeatLossEdge> createHeatLossGraph() {
            Graph<LayerVertex, HeatLossEdge> graph = createEmptyGraph();

            LayerVertex[][] verticalLayer = new LayerVertex[this.bounds.height][this.bounds.width];
            LayerVertex[][] horizontalLayer = new LayerVertex[this.bounds.height][this.bounds.width];

            this.blocks.forEach(block -> {
                LayerVertex verticalVertex = new LayerVertex(block.position, Layer.VERTICAL);
                verticalLayer[block.position.y][block.position.x] = verticalVertex;
                graph.addVertex(verticalVertex);

                LayerVertex horizontalVertex = new LayerVertex(block.position, Layer.HORIZONTAL);
                horizontalLayer[block.position.y][block.position.x] = new LayerVertex(block.position, Layer.HORIZONTAL);
                graph.addVertex(horizontalVertex);
            });

            BiFunction<Point, Direction, Stream<Point>> vertexStream = (position, direction) -> Stream.iterate(direction.move(position), this.bounds::contains, direction::move).limit(3);
            BiFunction<Point, Direction, List<LayerVertex>> horizontalStream = vertexStream.andThen(s -> s.map(p -> horizontalLayer[p.y][p.x]).toList());
            BiFunction<Point, Direction, List<LayerVertex>> verticalStream = vertexStream.andThen(s -> s.map(p -> verticalLayer[p.y][p.x]).toList());

            BiConsumer<LayerVertex, List<LayerVertex>> addEdgesBetween = (origin, vertices) -> {
                double heatLoss = 0;
                for (LayerVertex v : vertices) {
                    heatLoss += grid[v.position.y][v.position.x].getHeatLoss();
                    HeatLossEdge heatLossEdge = graph.addEdge(origin, v);
                    graph.setEdgeWeight(heatLossEdge, heatLoss);
                }
            };


            this.blocks.forEach(block -> {
                LayerVertex verticalVertex = verticalLayer[block.position.y][block.position.x];
                addEdgesBetween.accept(verticalVertex, horizontalStream.apply(block.position, Direction.EAST));
                addEdgesBetween.accept(verticalVertex, horizontalStream.apply(block.position, Direction.WEST));

                LayerVertex horizontalVertex = horizontalLayer[block.position.y][block.position.x];
                addEdgesBetween.accept(horizontalVertex, verticalStream.apply(block.position, Direction.NORTH));
                addEdgesBetween.accept(horizontalVertex, verticalStream.apply(block.position, Direction.SOUTH));
            });

            return graph;
        }

        @Override
        public String toString() {
            return Arrays.stream(grid).map(row -> Arrays.stream(row).map(b -> Integer.toString(b.getHeatLoss())).collect(Collectors.joining())).collect(Collectors.joining("\n"));
        }
    }

    public enum Layer {
        HORIZONTAL,
        VERTICAL,
    }

    public static class HeatLossEdge extends DefaultWeightedEdge {
        private double weight;

        public HeatLossEdge() {
        }

        public HeatLossEdge(double weight) {
            this.weight = weight;
        }

        @Override
        protected double getWeight() {
            return this.weight;
        }
    }

    public record LayerVertex(Point position, Layer layer) {

    }

    public record HeatLossVertex(Point position, int heatLoss) {

    }

    @Getter
    public static class Block {
        private Point position;
        private int heatLoss;

        public Block(Point position, int heatLoss) {
            this.position = position;
            this.heatLoss = heatLoss;
        }

        @Override
        public String toString() {
            return Integer.toString(heatLoss);
        }
    }

    public enum Direction {
        NORTH(270, '^'),
        EAST(0, '>'),
        SOUTH(90, 'v'),
        WEST(180, '<');

        final double rotation;
        final Character symbol;

        Direction(int rotation, Character symbol) {
            this.rotation = Math.toRadians(rotation);
            this.symbol = symbol;
        }

        public Point move(Point point) {
            return move(point, 1);
        }

        public Point move(Point point, int distance) {
            AffineTransform affineTransform = AffineTransform.getRotateInstance(rotation, point.x, point.y);
            affineTransform.translate(distance, 0);
            Point moved = new Point();
            affineTransform.transform(point, moved);
            return moved;
        }
    }
}
