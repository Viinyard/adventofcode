package dev.vinyard.adventofcode.soluce.year2024.day11;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class ASD {

    public static class Root {

        private Map<Long, Long> stones;

        public Root(Map<Long, Long> stones) {
            this.stones = stones;
        }

        public void blink() {
            Map<Long, Long> newStones = new HashMap<>();

            stones.forEach((k, v) -> {
                BiFunction<Long, Long, Long> add = (a, b) -> b == null ? v : b + v;
                if (k == 0) {
                    newStones.compute(1L, add);
                } else if (String.valueOf(k).length() % 2 == 0) {
                    String s = String.valueOf(k);
                    int half = s.length() / 2;
                    newStones.compute(Long.parseLong(s.substring(0, half)), add);
                    newStones.compute(Long.parseLong(s.substring(half)), add);
                } else {
                    newStones.compute(k * 2024, add);
                }
            });

            stones = newStones;
        }

        public void blink(int times) {
            for (int i = 0; i < times; i++) {
                blink();
            }
        }

        public long countStones() {
            return stones.values().parallelStream().mapToLong(Long::longValue).sum();
        }
    }

}
