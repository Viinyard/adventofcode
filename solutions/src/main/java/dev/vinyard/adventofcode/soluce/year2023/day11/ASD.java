package dev.vinyard.adventofcode.soluce.year2023.day11;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ASD {

    public record Root(List<Galaxy> galaxies) {

        public long calculateSumOfShortestPathBetweenEveryPairOfGalaxies(long distance) {
            LinkedList<Galaxy> stack = new LinkedList<>(galaxies);

            Set<Integer> xRows = galaxies.stream().map(Galaxy::position).mapToInt(p -> p.x).boxed().collect(Collectors.toSet());
            Set<Integer> yRows = galaxies.stream().map(Galaxy::position).mapToInt(p -> p.y).boxed().collect(Collectors.toSet());

            int width = xRows.stream().mapToInt(Integer::intValue).max().orElseThrow();
            int height = yRows.stream().mapToInt(Integer::intValue).max().orElseThrow();

            Set<Line2D> emptyXRows = Stream.iterate(0, i -> i < width, i -> i + 1).filter(x -> !xRows.contains(x)).map(x -> new Line2D.Double(x, 0, x, height)).collect(Collectors.toSet());
            Set<Line2D> emptyYRows = Stream.iterate(0, i -> i < height, i -> i + 1).filter(x -> !yRows.contains(x)).map(y -> new Line2D.Double(0, y, width, y)).collect(Collectors.toSet());

            Set<Line2D> empty = Stream.concat(emptyXRows.stream(), emptyYRows.stream()).collect(Collectors.toSet());

            long sum = 0;

            while (!stack.isEmpty()) {
                Galaxy galaxy = stack.pop();
                Point position = galaxy.position();

                for (Galaxy g : stack) {
                    Point gPosition = g.position();
                    Line2D.Double line = new Line2D.Double(position, gPosition);

                    sum += (long) (Math.abs(line.x1 - line.x2) + Math.abs(line.y1 - line.y2) + (empty.stream().filter(line::intersectsLine).count() * (distance - 1)));
                }
            }

            return sum;
        }


    }

    public record Galaxy(Point position) {}

}
