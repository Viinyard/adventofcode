package dev.vinyard.adventofcode.soluce.year2025.day11;

import java.util.*;

public class ASD {

    public static class Root {

        private final Map<String, List<String>> wirings;

        public Root(Map<String, List<String>> wirings) {
            this.wirings = wirings;
        }

        public long solution1() {
            PathCounter pathCounter = new PathCounter(this.wirings);
            
            return pathCounter.countPaths("you", "out");
        }
        
        public long solution2() {
            PathCounter pathCounter = new PathCounter(this.wirings);
            
            long p1 = pathCounter.countPaths("svr", "dac");
            System.out.println("Paths from svr to dac: " + p1);
            long p2 = pathCounter.countPaths("dac", "fft");
            System.out.println("Paths from dac to fft: " + p2);
            long p3 = pathCounter.countPaths("fft", "out");
            System.out.println("Paths from fft to out: " + p3);
            
            long p4 = pathCounter.countPaths("svr", "fft");
            System.out.println("Paths from svr to fft: " + p4);
            long p5 = pathCounter.countPaths("fft", "dac");
            System.out.println("Paths from fft to dac: " + p5);
            long p6 = pathCounter.countPaths("dac", "out");
            System.out.println("Paths from dac to out: " + p6);
            
            return (p1 * p2 * p3) + (p4 * p5 * p6);
        }
    }
    
    public static class PathCounter {
        
        private final Map<String, List<String>> wiringMap;
        
        public PathCounter(Map<String, List<String>> wirings) {
            this.wiringMap = wirings;
        }
        
        public long countPaths(String start, String end) {
            return countPaths(start, end, new HashSet<>(), new HashMap<>());
        }

        public long countPaths(String start, String end, Set<String> visited, Map<String, Long> memoizationCache) {
            if (Objects.equals(start, end))
                return 1;
            
            if (memoizationCache.containsKey(start))
                return memoizationCache.get(start);
            
            if (visited.add(start)) {
                long sum = this.wiringMap.getOrDefault(start, Collections.emptyList())
                        .stream().mapToLong(s -> countPaths(s, end, new HashSet<>(visited), memoizationCache)).sum();
                
                memoizationCache.put(start, sum);
                
                return sum;
            }
                
            return 0;
        }
    }

    public record Wiring(String from, List<String> to) { }

}
