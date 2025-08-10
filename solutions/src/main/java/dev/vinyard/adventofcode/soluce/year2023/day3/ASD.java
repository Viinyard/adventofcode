package dev.vinyard.adventofcode.soluce.year2023.day3;

import lombok.Data;
import lombok.ToString;

import java.awt.*;
import java.util.List;

public class ASD {

    @Data
    public static class Engine {

        public List<Part> parts;

        public Engine(List<Part> parts) {
            this.parts = parts;
        }
    }

    @Data
    @ToString
    public static class Part {

        private Rectangle size;

        private Rectangle rectangle;

        private String value;

        public Part(int x, int y, int width, int height, String value) {
            this.size = new Rectangle(x, y, width, height);
            this.rectangle = new Rectangle(x - 1, y - 1, width + 2, height + 2);
            this.value = value;
        }
    }

    public static class Number extends Part {
        public Number(int x, int y, int width, int height, String value) {
            super(x, y, width, height, value);
        }
    }

    public static class Symbol extends Part {
        public Symbol(int x, int y, int width, int height, String value) {
            super(x, y, width, height, value);
        }
    }

    public static class Empty extends Part {
        public Empty(int x, int y, int width, int height, String value) {
            super(x, y, width, height, value);
        }
    }

    public static class Gear extends Symbol {
        public Gear(int x, int y, int width, int height, String value) {
            super(x, y, width, height, value);
        }
    }

}
