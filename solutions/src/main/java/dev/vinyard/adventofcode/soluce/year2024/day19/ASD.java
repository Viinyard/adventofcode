package dev.vinyard.adventofcode.soluce.year2024.day19;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ASD {

    public static class Root {

        private final List<String> towels;
        private final List<String> patterns;
        private final Map<String, Long> cache = new HashMap<>();

        public Root(List<String> towels, List<String> patterns) {
            this.towels = towels;
            this.patterns = patterns;
        }

        public long countValidPatterns() {
            return patterns.stream().mapToLong(this::cacheIsValidPattern).filter(l -> l > 0L).count();
        }

        public long countAllValidPatterns() {
            return patterns.stream().mapToLong(this::isValidPattern).sum();
        }

        long cacheIsValidPattern(String design) {
            if (cache.containsKey(design)) {
                return cache.get(design);
            }
            cache.put(design, isValidPattern(design));
            return cache.get(design);
        }

         long isValidPattern(String design) {
             if (design.isEmpty())
                 return 1L;

            return towels.stream().filter(design::startsWith).map(towel -> design.substring(towel.length())).mapToLong(this::cacheIsValidPattern).sum();
        }
    }
}
