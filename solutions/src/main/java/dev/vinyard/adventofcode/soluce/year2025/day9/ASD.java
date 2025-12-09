package dev.vinyard.adventofcode.soluce.year2025.day9;

import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ASD {

    public static class Root {

        private final List<Tile> tiles;

        public Root(List<Tile> tiles) {
            this.tiles = tiles;
        }

        public long solution1() {
            return getAllPairs().stream().mapToLong(pair -> pair.getLeft().areaTo(pair.getRight())).max().orElse(0L);
        }

        public long solution2() {
            Path2D path = new Path2D.Double(Path2D.WIND_EVEN_ODD);

            LinkedList<Tile> linkedList = new LinkedList<>(tiles);

            Optional.ofNullable(linkedList.pollFirst()).ifPresent(origin -> path.moveTo(origin.getPosition().x, origin.getPosition().y));

            while (!linkedList.isEmpty()) {
                Tile next = linkedList.pollFirst();
                path.lineTo(next.getPosition().x, next.getPosition().y);
            }

            path.closePath();

            return getAllPairs().stream().map(p -> p.getLeft().getRectangleTo(p.getRight())).filter(path::contains).mapToLong(r -> ((long) r.getWidth() + 1) * ((long) r.getHeight() + 1)).max().orElse(0L);
        }

        private List<Pair<Tile, Tile>> getAllPairs() {
            List<Pair<Tile, Tile>> pairs = new ArrayList<>();

            for (int i = 0; i < tiles.size(); i++)
                for (int j = i + 1; j < tiles.size(); j++)
                    pairs.add(Pair.of(tiles.get(i), tiles.get(j)));

            return pairs;
        }
    }

    public static class Tile {

        @Getter
        private final Point position;

        public Tile(int x, int y) {
            this.position = new Point(x, y);
        }

        public long areaTo(Tile other) {
            int height = Math.abs(this.position.y - other.position.y) + 1;
            int width = Math.abs(this.position.x - other.position.x) + 1;
            return (long) height * width;
        }

        public Rectangle2D getRectangleTo(Tile other) {
            int minX = Math.min(this.position.x, other.position.x);
            int minY = Math.min(this.position.y, other.position.y);
            int width = Math.abs(this.position.x - other.position.x);
            int height = Math.abs(this.position.y - other.position.y);
            return new Rectangle2D.Double(minX, minY, width, height);
        }
    }


}
