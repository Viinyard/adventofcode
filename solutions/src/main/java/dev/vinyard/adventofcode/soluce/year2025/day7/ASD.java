package dev.vinyard.adventofcode.soluce.year2025.day7;

import lombok.Getter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.List;

public class ASD {

    public static class Root {

        @Getter
        private final List<Cell> cells;

        private final Rectangle bounds;
        private Cell[][] manifold;

        public Root(List<Cell> cells, Dimension dimension) {
            this.cells = cells;
            this.bounds = new Rectangle(dimension);
            this.manifold = new Cell[dimension.width][dimension.height];

            this.cells.forEach(cell -> {
                this.manifold[cell.position.x][cell.position.y] = cell;
            });
        }

        public Start getStart() {
            return cells.stream()
                    .filter(Start.class::isInstance)
                    .map(Start.class::cast)
                    .findAny().orElseThrow();
        }

        public Optional<Cell> getCellAt(Point position) {
            return Optional.of(position).filter(bounds::contains)
                    .map(bounds -> manifold[bounds.x][bounds.y]);
        }

        public long solution1() {
            BeamVisitor beamVisitor = new BeamVisitor(this);

            getStart().accept(beamVisitor);

            return beamVisitor.getBeamCount();
        }

        public long solution2() {
            QuantumVisitor quantumVisitor = new QuantumVisitor(this);

            return getStart().accept(quantumVisitor);
        }
    }

    public static class QuantumVisitor implements CellVisitor {

        private final Root root;

        private final Set<Splitter> visitedSplitters = new HashSet<>();

        public QuantumVisitor(Root root) {
            this.root = root;
        }

        public Optional<Cell> getCellAt(Point position, Direction direction) {
            return Optional.of(position).map(direction::move).flatMap(root::getCellAt);
        }

        public long moveBeam(Point position, Direction direction) {
            return Optional.of(position).map(direction::move).flatMap(root::getCellAt).map(cell -> cell.accept(this)).orElse(1L);
        }

        @Override
        public long visit(EmptySpace emptySpace) {
            return moveBeam(emptySpace.getPosition(), Direction.SOUTH);
        }

        @Override
        public long visit(Start start) {
            return moveBeam(start.getPosition(), Direction.SOUTH);
        }

        @Override
        public long visit(Splitter splitter) {
            if (visitedSplitters.add(splitter))
                return moveBeam(splitter.getPosition(), Direction.EAST) + moveBeam(splitter.getPosition(), Direction.WEST);

            return 0L;
        }
    }
    

    public static class BeamVisitor implements CellVisitor {

        private final Root root;

        private Set<Splitter> visitedSplitters = new HashSet<>();

        public long getBeamCount() {
            return visitedSplitters.size();
        }

        public BeamVisitor(Root root) {
            this.root = root;
        }

        @Override
        public long visit(EmptySpace emptySpace) {
            return moveBeam(emptySpace.getPosition(), Direction.SOUTH);
        }

        @Override
        public long visit(Start start) {
            return moveBeam(start.getPosition(), Direction.SOUTH);
        }

        public long moveBeam(Point position, Direction direction) {
            return Optional.of(position).map(direction::move).flatMap(root::getCellAt).map(cell -> cell.accept(this)).orElse(0L);
        }

        @Override
        public long visit(Splitter splitter) {
            if (visitedSplitters.add(splitter)) {
                long res = 1;
                res += moveBeam(splitter.getPosition(), Direction.EAST);
                res += moveBeam(splitter.getPosition(), Direction.WEST);
                
                return res;
            }
            
            return 0L;
        }
    }

    public interface CellVisitor {
        long visit(EmptySpace emptySpace);

        long visit(Start start);

        long visit(Splitter splitter);
    }
    
    public abstract static class Cell {

        @Getter
        private final Point position;
        
        protected Long paths;

        public Cell(Point position) {
            this.position = position;
        }

        public abstract long accept(CellVisitor visitor);
    }

    public static class EmptySpace extends Cell {
        public EmptySpace(Point position) {
            super(position);
        }


        @Override
        public long accept(CellVisitor visitor) {
            if (Objects.isNull(this.paths))
                this.paths = visitor.visit(this);

            return paths;
        }
    }

    public static class Start extends Cell {
        public Start(Point position) {
            super(position);
        }

        @Override
        public long accept(CellVisitor visitor) {
            if (Objects.isNull(this.paths))
                this.paths = visitor.visit(this);

            return paths;
        }
    }

    public static class Splitter extends Cell {
        public Splitter(Point position) {
            super(position);
        }

        @Override
        public long accept(CellVisitor visitor) {
            if (Objects.isNull(this.paths))
                this.paths = visitor.visit(this);
            
            return paths;
        }
    }
    
    public enum Direction {
        NORTH(270, '^'),
        EAST(0, '>'),
        SOUTH(90, 'v'),
        WEST(180, '<');

        final double rotation;
        final Character symbol;

        Direction(int rotation, Character symbol) {
            this.rotation = Math.toRadians(rotation);
            this.symbol = symbol;
        }

        public Point move(Point point) {
            return move(point, 1);
        }

        public Point move(Point point, int distance) {
            AffineTransform affineTransform = AffineTransform.getRotateInstance(rotation, point.x, point.y);
            affineTransform.translate(distance, 0);
            Point moved = new Point();
            affineTransform.transform(point, moved);
            return moved;
        }
    }
}
