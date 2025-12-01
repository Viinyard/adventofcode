package dev.vinyard.adventofcode.soluce.year2025.day1;

import lombok.Getter;
import org.apache.commons.math3.geometry.euclidean.threed.Rotation;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class ASD {

    public static class Root {

        List<DialRotation> rotations;

        public Root(List<DialRotation> rotations) {
            this.rotations = rotations;
        }

        public Long solution1() {
            Dial dial = new Dial(50);
            return rotations.stream().peek(dial::rotate)
                    .filter(rotation -> dial.getPosition() == 0)
                    .count();
        }

        public Object solution2() {
            Dial dial = new Dial(50);
            return rotations.stream()
                    .mapToLong(dial::rotate)
                    .sum();
        }
    }

    public static class Dial {

        @Getter
        private int position;

        public Dial(int position) {
            this.position = position;
        }

        private void move(Direction direction) {
            position = Math.floorMod(position + direction.getStep(), 100);
        }

        public long rotate(DialRotation rotation) {
            return Stream.generate(() -> rotation.direction).limit(rotation.getSteps()).peek(this::move).filter(dir -> Objects.equals(position, 0)).count();
        }

    }

    public static class DialRotation {
        private final Direction direction;
        private final int steps;

        public DialRotation(Direction direction, int steps) {
            this.direction = direction;
            this.steps = steps;
        }

        public Direction getDirection() {
            return direction;
        }

        public int getSteps() {
            return steps;
        }
    }

    public enum Direction {
        LEFT(-1),
        RIGHT(+1);

        private final int step;

        Direction(int step) {
            this.step = step;
        }

        public int getStep() {
            return step;
        }
    }

}
