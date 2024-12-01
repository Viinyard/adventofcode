package dev.vinyard.adventofcode.soluce.year2023.day2;

import lombok.Data;

import java.util.List;

public class ASD {

    public enum Color {
        GREEN, RED, BLUE
    }

    @Data
    public static class Games {
        List<Game> games;

        public Games(List<Game> games) {
            this.games = games;
        }
    }

    @Data
    public static class Game {
        Long value;
        List<Hide> hides;

        public Game(String value, List<Hide> hides) {
            this.value = Long.parseLong(value);
            this.hides = hides;
        }
    }

    @Data
    public static class Hide {
        List<Cube> cubes;

        public Hide(List<Cube> cubes) {
            this.cubes = cubes;
        }
    }

    @Data
    public static class Cube {
        Color color;
        Integer value;

        public Cube(String value, Color color) {
            this.value = Integer.parseInt(value);
            this.color = color;
        }
    }
}
