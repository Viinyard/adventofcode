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

        public boolean findWord(LetterInfo letter, Direction direction, String word) {
            return Stream.iterate(letter, next -> Optional.ofNullable(next).map(LetterInfo::position).map(direction::move).filter(bounds::contains).map(this::getLetterAt).orElse(null))
                    .limit(word.length())
                    .takeWhile(Objects::nonNull)
                    .map(LetterInfo::letter)
                    .reduce("", String::concat)
                    .equals(word);
        }

        public long countWord(String word) {
            return letterInfoList.stream()
                    .mapToLong(letter -> Arrays.stream(Direction.values())
                            .filter(direction -> findWord(letter, direction, word)).count()).sum();
        }
    }

    public enum Direction {
        NORTH(Math.toRadians(270)),
        NORTH_EAST(Math.toRadians(315)),
        EAST(Math.toRadians(0)),
        SOUTH_EAST(Math.toRadians(45)),
        SOUTH(Math.toRadians(90)),
        SOUTH_WEST(Math.toRadians(135)),
        WEST(Math.toRadians(180)),
        NORTH_WEST(Math.toRadians(225));

        final double rotation;

        Direction(double rotation) {
            this.rotation = rotation;
        }

        public Point move(Point point) {
            AffineTransform affineTransform = AffineTransform.getRotateInstance(rotation, point.x, point.y);
            affineTransform.translate(1, 0);
            Point moved = new Point();
            affineTransform.transform(point, moved);
            return moved;
        }
    }
}
