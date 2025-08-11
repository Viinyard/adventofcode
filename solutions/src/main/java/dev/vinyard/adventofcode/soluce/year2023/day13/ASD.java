package dev.vinyard.adventofcode.soluce.year2023.day13;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ASD {

    public static class Root {

        private List<Pattern> patterns;

        public Root(List<Pattern> patterns) {
            this.patterns = patterns;
        }

        public List<Pattern> getPatterns() {
            return patterns;
        }

    }

    public static class Pattern {
        private List<Point> points;
        private Rectangle dimensions;

        public Pattern(List<Point> points, Rectangle dimensions) {
            this.points = points;
            this.dimensions = dimensions;
        }

        public List<Integer> findHorizontalReflectionRows() {
            int[] rows = new int[dimensions.height];
            this.points.forEach(p -> rows[p.y] ^= (1 << p.x));

            return findReflections(Arrays.stream(rows).boxed().collect(Collectors.toList()));
        }

        public List<Integer> findReflections(List<Integer> rows) {


            return IntStream.rangeClosed(1, rows.size() - 1).filter(raw -> this.isValidReflection(raw, rows)).boxed().toList();
        }

        public List<Integer> findVerticalReflectionColumns() {
            int[] rows = new int[dimensions.width];
            this.points.forEach(p -> rows[p.x] ^= (1 << p.y));

            return findReflections(Arrays.stream(rows).boxed().collect(Collectors.toList()));
        }

        public boolean isValidReflection(int row, List<Integer> lines) {
            Iterator<Integer> firstHalf = new LinkedList<>(lines.subList(0, row)).descendingIterator();
            Iterator<Integer> secondHalf = new LinkedList<>(lines.subList(row, lines.size())).iterator();

            while (firstHalf.hasNext() && secondHalf.hasNext()) {
                if (!Objects.equals(firstHalf.next(), secondHalf.next()))
                    return false;
            }
            return true;
        }
    }
}
