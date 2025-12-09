package dev.vinyard.adventofcode.soluce.year2025.day9;

import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ASD {

    public static class Root {

        private final List<Tile> tiles;

        public Root(List<Tile> tiles) {
            this.tiles = tiles;
        }

        public long solution1() {

            List<Pair<Tile, Tile>> pairs = new ArrayList<>();

            for (int i = 0; i < tiles.size(); i++)
                for (int j = i + 1; j < tiles.size(); j++)
                    pairs.add(Pair.of(tiles.get(i), tiles.get(j)));

            return pairs.stream().mapToLong(pair -> pair.getLeft().areaTo(pair.getRight())).max().orElse(0L);
        }
    }

    public static class Tile {

        @Getter
        private final Point position;

        public Tile(int x, int y) {
            this.position = new Point(x, y);
        }

        public double distanceTo(Tile other) {
            return this.position.distance(other.position);
        }

        public long areaTo(Tile other) {
            int height = Math.abs(this.position.y - other.position.y) + 1;
            int width = Math.abs(this.position.x - other.position.x) + 1;
            return (long) height * width;
        }
    }
}
