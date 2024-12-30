package dev.vinyard.adventofcode.soluce.year2024.day22;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ASD {

    public record Root(List<Secret> secrets) {

        private void getNthSecret(int n) {
            secrets.parallelStream().forEach(s -> {
                for (int i = 0; i < n; i++) {
                    s.next();
                }
            });
        }

        public long getNthSecretValue(int n) {
            getNthSecret(n);
            return secrets.stream().mapToLong(Secret::getValue).sum();
        }

        public long getBestSequence(int n) {
            Map<Sequence, Long> bestSequence = secrets.parallelStream().map(secret -> Stream.generate(secret::next).limit(n).dropWhile(s -> s.getSequence().values().length < 4).collect(Collectors.toMap(Secret::getSequence, Secret::getPrice, (p1, p2) -> p1)))
                    .map(Map::entrySet).flatMap(Set::stream).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Long::sum));

            return bestSequence.values().stream().mapToLong(Long::longValue).max().orElse(0);
        }

    }

    public record Sequence(long... values) {

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Sequence(long[] values1))) return false;
            return Objects.deepEquals(values, values1);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(values);
        }

        @Override
        public String toString() {
            return Arrays.toString(values);
        }
    }

    @Getter
    public static class Secret implements Iterator<Secret> {

        private Map<Sequence, Long> sequenceMap;
        private final long initialValue;
        private long value;
        private final LinkedList<Long> sequence = new LinkedList<>();

        public Secret(long value) {
            this.initialValue = value;
            this.value = value;
        }

        private void mix(long nextSequence) {
            value ^= nextSequence;
        }

        private void prune() {
            value &= 0xFFFFFF;
        }

        private void generateNextSequence(long nextSequence) {
            mix(nextSequence);
            prune();
        }

        @Override
        public boolean hasNext() {
            return true;
        }

        public Sequence getSequence() {
            return new Sequence(sequence.stream().mapToLong(Long::longValue).toArray());
        }

        public long getPrice() {
            return value % 10;
        }

        @Override
        public Secret next() {
            long previousValue = getPrice();

            generateNextSequence(value << 6);
            generateNextSequence(value >> 5);
            generateNextSequence(value << 11);

            sequence.addLast(getPrice() - previousValue);

            if (sequence.size() > 4)
                sequence.pollFirst();

            return this;
        }
    }

}
