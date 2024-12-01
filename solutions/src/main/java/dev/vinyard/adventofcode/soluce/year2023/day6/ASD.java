package dev.vinyard.adventofcode.soluce.year2023.day6;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ASD {

    @Data
    public static class Races {
        private List<Race> races;

        public Races(LinkedList<Long> times, LinkedList<Long> distances) {
            races = Stream.generate(() -> new Race(
                            Optional.ofNullable(times.pollFirst()).orElseThrow(() -> new IllegalStateException("Time is null.")),
                            Optional.ofNullable(distances.pollFirst()).orElseThrow(() -> new IllegalStateException("Distance is null."))
                    )).limit(times.size())
                    .toList();
        }
    }

    @Data
    public static class Race {
        private long time;
        private long distance;

        public Race(long time, long distance) {
            this.time = time;
            this.distance = distance;
        }

        public long getNumberOfWaysToBeatTheDistance() {
            return LongStream.iterate(0L, i -> i < time, i -> i + 1L)
                    .map(i -> i * (time - i))
                    .filter(i -> i > distance)
                    .count();
        }

    }
}
