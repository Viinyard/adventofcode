package dev.vinyard.adventofcode.soluce.year2025.day1;

import java.util.List;

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
    }

    public static class Dial {
        private int position;

        public Dial(int position) {
            this.position = position;
        }

        public void rotate(DialRotation rotation) {
            int delta = rotation.getDirection() == Direction.LEFT ? -rotation.getSteps() : rotation.getSteps();
            position = Math.floorMod(position + delta, 100);
        }

        public int getPosition() {
            return position;
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
        LEFT,
        RIGHT
    }

}
