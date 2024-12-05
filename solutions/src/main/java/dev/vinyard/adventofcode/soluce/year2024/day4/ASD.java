package dev.vinyard.adventofcode.soluce.year2024.day4;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class ASD {

    public static class Root {

        private final LetterInfo[][] array;
        private final List<LetterInfo> letterInfoList;
        private final Rectangle bounds;

        public Root(String[][] letters) {
            array = new LetterInfo[letters[0].length][letters.length];
            bounds = new Rectangle(0, 0, letters[0].length, letters.length);
            for (int y = 0; y < letters.length; y++) {
                for (int x = 0; x < letters[y].length; x++) {
                    array[x][y] = new LetterInfo(letters[y][x], new Point(x, y));
                }
            }
            letterInfoList = Stream.of(array).flatMap(Stream::of).toList();
        }

        public record LetterInfo(String letter, Point position) { }

        public LetterInfo getLetterAt(Point point) {
            return array[point.x][point.y];
        }

        public LetterInfo applyDirection(LetterInfo letter, Direction direction) {
            return this.applyDirection(letter, direction, 1);
        }

        public LetterInfo applyDirection(LetterInfo letter, Direction direction, int translation) {
            return Optional.ofNullable(letter).map(LetterInfo::position).map(point -> direction.move(point, translation)).filter(bounds::contains).map(this::getLetterAt).orElse(null);
        }

        public boolean findWord(LetterInfo letter, Direction direction, String word) {
            return Stream.iterate(applyDirection(letter, direction, - (word.length() / 2)), next -> applyDirection(next, direction))
                    .limit(word.length())
                    .takeWhile(Objects::nonNull)
                    .map(LetterInfo::letter)
                    .reduce("", String::concat)
                    .equals(word);
        }

        public long countWord(String word, Direction... directions) {
            return letterInfoList.stream()
                    .mapToLong(letter -> Arrays.stream(directions)
                            .filter(direction -> findWord(letter, direction, word)).count()).sum();
        }

        private String getMiddleLetter(String word) {
            return word.substring(word.length() / 2, word.length() / 2 + 1);
        }

        public long countPattern(String word, Direction... directions) {
            return letterInfoList.stream()
                    .filter(letter -> Objects.equals(letter.letter(), getMiddleLetter(word)))
                    .mapToLong(letter -> Arrays.stream(directions)
                            .filter(direction -> findWord(letter, direction, word)).count()).filter(l -> l == 2).count();
        }

        public long countWord(String word) {
            return this.countWord(word, Direction.values());
        }
    }

    public enum Direction {
        NORTH(270),
        NORTH_EAST(315),
        EAST(0),
        SOUTH_EAST(45),
        SOUTH(90),
        SOUTH_WEST(135),
        WEST(180),
        NORTH_WEST(225);

        final double rotation;

        Direction(int rotation) {
            this.rotation = Math.toRadians(rotation);
        }

        public Point move(Point point, int translation) {
            AffineTransform affineTransform = AffineTransform.getRotateInstance(rotation, point.x, point.y);
            affineTransform.translate(translation, 0);
            Point moved = new Point();
            affineTransform.transform(point, moved);
            return moved;
        }
    }
}
