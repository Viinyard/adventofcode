package dev.vinyard.adventofcode.soluce.year2025.day7;

import lombok.Getter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ASD {

    public static class Root {
        
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
        public void visit(EmptySpace emptySpace) {
            moveBeam(emptySpace.getPosition(), Direction.SOUTH);
        }

        @Override
        public void visit(Start start) {
            moveBeam(start.getPosition(), Direction.SOUTH);
        }
        
        public void moveBeam(Point position, Direction direction) {
            Optional.of(position).map(direction::move).flatMap(root::getCellAt).ifPresent(cell -> cell.accept(this));
        }

        @Override
        public void visit(Splitter splitter) {
            if (visitedSplitters.add(splitter)) {
                moveBeam(splitter.getPosition(), Direction.EAST);
                moveBeam(splitter.getPosition(), Direction.WEST);
            }
        }
    }
    
    public interface CellVisitor {
        void visit(EmptySpace emptySpace);
        void visit(Start start);
        void visit(Splitter splitter);
    }
    
    public abstract static class Cell {
        
        @Getter
        private final Point position;
        
        public Cell(Point position) {
            this.position = position;
        }
        
        public abstract void accept(CellVisitor visitor);
    }
    
    public static class EmptySpace extends Cell {
        public EmptySpace(Point position) {
            super(position);
        }


        @Override
        public void accept(CellVisitor visitor) {
            visitor.visit(this);
        }
    }
    
    public static class Start extends Cell {
        public Start(Point position) {
            super(position);
        }

        @Override
        public void accept(CellVisitor visitor) {
            visitor.visit(this);
        }
    }
    
    public static class Splitter extends Cell {
        public Splitter(Point position) {
            super(position);
        }

        @Override
        public void accept(CellVisitor visitor) {
            visitor.visit(this);
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
