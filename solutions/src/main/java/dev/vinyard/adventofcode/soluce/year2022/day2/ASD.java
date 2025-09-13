package dev.vinyard.adventofcode.soluce.year2022.day2;

import lombok.Getter;

import java.util.List;

public class ASD {

    public static class Part1 {

        private final List<Round> rounds;

        public Part1(List<Round> rounds) {
            this.rounds = rounds;
        }

        public long result() {
            return rounds.stream().mapToLong(Round::score).sum();
        }
    }

    public static class Part2 {

        private final List<Strategy> strategies;

        public Part2(List<Strategy> strategies) {
            this.strategies = strategies;
        }

        public long result() {
            return strategies.stream().mapToLong(Strategy::score).sum();
        }
    }

    public record Round(Shape opponent, Shape player) {
        public int score() {
            return player.valueAgainst(opponent);
        }
    }

    public record Strategy(Shape opponent, Outcome outcome) {
        public Shape player() {
            return switch (opponent) {
                case Rock ignored -> switch (outcome) {
                    case WIN -> new Paper();
                    case DRAW -> new Rock();
                    case LOSE -> new Scissors();
                };
                case Paper ignored -> switch (outcome) {
                    case WIN -> new Scissors();
                    case DRAW -> new Paper();
                    case LOSE -> new Rock();
                };
                case Scissors ignored -> switch (outcome) {
                    case WIN -> new Rock();
                    case DRAW -> new Scissors();
                    case LOSE -> new Paper();
                };
                default -> throw new IllegalStateException("Unexpected value: " + opponent);
            };
        }

        public int score() {
            return player().valueAgainst(opponent);
        }
    }



    public static abstract class Shape {

        protected abstract int score();

        protected abstract Outcome play(Shape other);

        protected int valueAgainst(Shape other) {
            return play(other).score + score();
        }

    }

    public static class Rock extends Shape {

        @Override
        protected int score() {
            return 1;
        }

        @Override
        protected Outcome play(Shape other) {
            return switch (other) {
                case Rock ignored -> Outcome.DRAW;
                case Paper ignored -> Outcome.LOSE;
                case Scissors ignored -> Outcome.WIN;
                default -> throw new IllegalStateException("Unexpected value: " + other);
            };
        }
    }

    public static class Paper extends Shape {

        @Override
        protected int score() {
            return 2;
        }

        @Override
        protected Outcome play(Shape other) {
            return switch (other) {
                case Rock ignored -> Outcome.WIN;
                case Paper ignored -> Outcome.DRAW;
                case Scissors ignored -> Outcome.LOSE;
                default -> throw new IllegalStateException("Unexpected value: " + other);
            };
        }
    }

    public static class Scissors extends Shape {

        @Override
        protected int score() {
            return 3;
        }

        @Override
        protected Outcome play(Shape other) {
            return switch (other) {
                case Rock ignored -> Outcome.LOSE;
                case Paper ignored -> Outcome.WIN;
                case Scissors ignored -> Outcome.DRAW;
                default -> throw new IllegalStateException("Unexpected value: " + other);
            };
        }
    }

    @Getter
    public enum Outcome {
        WIN(6),
        DRAW(3),
        LOSE(0);

        private final int score;

        Outcome(int score) {
            this.score = score;
        }

    }

}
