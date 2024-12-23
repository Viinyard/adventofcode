package dev.vinyard.adventofcode.soluce.year2024.day19;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ASD {

    public static class Root {

        private final List<String> towels;
        private final List<String> patterns;
        private final Map<String, Boolean> cache = new HashMap<>();

        public Root(List<String> towels, List<String> patterns) {
            this.towels = towels;
            this.patterns = patterns;
        }

        public long countValidPatterns() {
            return patterns.stream().filter(p -> isValidPattern(p, this::any)).count();
        }

        boolean cacheIsValidPattern(String design, Function<List<Boolean>, Boolean> op) {
            if (cache.containsKey(design)) {
                return cache.get(design);
            }
            cache.putIfAbsent(design, isValidPattern(design, op));
            return cache.get(design);
        }

         boolean isValidPattern(String design, Function<List<Boolean>, Boolean> op) {
             if (design.isEmpty())
                 return true;

            return op.apply(towels.stream().filter(design::startsWith).map(towel -> design.substring(towel.length())).map(e -> cacheIsValidPattern(e, op)).toList());
        }

        private boolean any(List<Boolean> results) {
            return results.stream().anyMatch(Boolean::booleanValue);
        }

        private boolean sum(List<Boolean> results) {
            return results.stream().mapToInt(b -> b ? 1 : 0).sum() > 0;
        }
    }
}
