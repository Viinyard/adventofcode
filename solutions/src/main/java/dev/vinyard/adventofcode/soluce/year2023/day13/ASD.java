package dev.vinyard.adventofcode.soluce.year2023.day13;

import java.awt.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
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



        public List<Integer> findHorizontalReflection(Predicate<Integer> isValid) {
            int[] rows = new int[dimensions.height];
            this.points.forEach(p -> rows[p.y] ^= (1 << p.x));

            return findReflections(Arrays.stream(rows).boxed().collect(Collectors.toList()), isValid);
        }

        public List<Integer> findReflections(List<Integer> rows, Predicate<Integer> isValid) {
            return IntStream.rangeClosed(1, rows.size() - 1).filter(raw -> this.isValidReflection(raw, rows, isValid)).boxed().toList();
        }

        public List<Integer> findVerticalReflection(Predicate<Integer> isValid) {
            int[] rows = new int[dimensions.width];
            this.points.forEach(p -> rows[p.x] ^= (1 << p.y));

            return findReflections(Arrays.stream(rows).boxed().collect(Collectors.toList()), isValid);
        }

        public boolean isValidReflection(int row, List<Integer> lines, Predicate<Integer> isValid) {
            Iterator<Integer> firstHalf = new LinkedList<>(lines.subList(0, row)).descendingIterator();
            Iterator<Integer> secondHalf = new LinkedList<>(lines.subList(row, lines.size())).iterator();

            int diff = 0;
            while (firstHalf.hasNext() && secondHalf.hasNext()) {
                diff |= firstHalf.next() ^ secondHalf.next();
            }

            return isValid.test(diff);
        }
    }
}
