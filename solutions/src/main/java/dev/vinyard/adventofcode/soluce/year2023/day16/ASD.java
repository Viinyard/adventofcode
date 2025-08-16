package dev.vinyard.adventofcode.soluce.year2023.day16;

import lombok.Getter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ASD {

    public static class Root {

        public final List<Entity> entities;
        public final Entity[][] grid;
        public final Rectangle bounds;

        public Root(List<Entity> entities, Dimension dimension) {
            this.entities = entities;
            this.bounds = new Rectangle(dimension);
            this.grid = new Entity[this.bounds.height][this.bounds.width];
            this.entities.forEach(entity -> grid[entity.position.y][entity.position.x] = entity);
        }

        public Optional<Entity> getEntityAt(Point position) {
            return Optional.of(position).filter(this.bounds::contains).map(p -> grid[p.y][p.x]);
        }

        public long getEngergizedCount() {
            BeamVisitor beamVisitor = new BeamVisitor(this);

            beamVisitor.visit(new Point(0, 0), Direction.EAST);

            return beamVisitor.getEnergizedEntitiesCount();
        }

        @Override
        public String toString() {
            return Arrays.stream(grid).map(row -> Arrays.stream(row).map(Entity::toString).collect(Collectors.joining())).collect(Collectors.joining("\n"));
        }
    }

    public interface EntityVisitor {

        void visit(Point position, Direction direction);

    }

    public static class BeamVisitor implements EntityVisitor {

        public record Beam(Entity entity, Direction direction) {}

        private final Root root;
        private Set<Beam> beams = new HashSet<>();

        public BeamVisitor(Root root) {
            this.root = root;
        }

        @Override
        public void visit(Point position, Direction direction) {
            this.root.getEntityAt(position).filter(e -> beams.add(new Beam(e, direction))).ifPresent(entity -> entity.accept(this, direction));
        }

        public long getEnergizedEntitiesCount() {
            System.out.println();
            System.out.println(this);
            System.out.println();
            return beams.stream().map(Beam::entity).distinct().count();
        }

        @Override
        public String toString() {
            String[][] gridToPrint = new String[this.root.bounds.width][this.root.bounds.height];

            this.root.entities.forEach(entity -> gridToPrint[entity.position.y][entity.position.x] = entity.toString());
            beams.forEach(e -> {
                switch (gridToPrint[e.entity.position.y][e.entity.position.x]) {
                    case "." -> gridToPrint[e.entity.position.y][e.entity.position.x] = switch (e.direction) {
                        case NORTH -> "^";
                        case SOUTH -> "v";
                        case EAST -> ">";
                        case WEST -> "<";
                    };
                    case ">", "<", "v", "^" -> gridToPrint[e.entity.position.y][e.entity.position.x] = "2";
                    case "2" -> gridToPrint[e.entity.position.y][e.entity.position.x] = "3";
                    case "3" -> gridToPrint[e.entity.position.y][e.entity.position.x] = "4";
                    default -> {
                        // Do nothing, already energized
                    }
                }
            });

            return Arrays.stream(gridToPrint).map(row -> String.join("", row)).collect(Collectors.joining("\n"));
        }
    }

    @Getter
    public static abstract class Entity {

        protected final Point position;

        public Entity(Point position) {
            this.position = position;
        }

        @Override
        public abstract String toString();

        public abstract void accept(EntityVisitor visitor, Direction direction);
    }

    public static class EmptySpace extends Entity {


        public EmptySpace(Point position) {
            super(position);
        }

        @Override
        public String toString() {
            return ".";
        }

        @Override
        public void accept(EntityVisitor visitor, Direction direction) {
            visitor.visit(direction.move(this.position), direction);
        }
    }

    public static class PositiveMirror extends Entity {
        public PositiveMirror(Point position) {
            super(position);
        }

        @Override
        public String toString() {
            return "\\";
        }

        @Override
        public void accept(EntityVisitor visitor, Direction direction) {
            Direction newDirection = switch (direction) {
                case NORTH -> Direction.WEST;
                case SOUTH -> Direction.EAST;
                case EAST -> Direction.SOUTH;
                case WEST -> Direction.NORTH;
            };

            visitor.visit(newDirection.move(this.position), newDirection);
        }
    }

    public static class NegativeMirror extends Entity {
        public NegativeMirror(Point position) {
            super(position);
        }

        @Override
        public String toString() {
            return "/";
        }

        @Override
        public void accept(EntityVisitor visitor, Direction direction) {
            Direction newDirection = switch (direction) {
                case NORTH -> Direction.EAST;
                case SOUTH -> Direction.WEST;
                case EAST -> Direction.NORTH;
                case WEST -> Direction.SOUTH;
            };

            visitor.visit(newDirection.move(this.position), newDirection);
        }
    }

    public static class VerticalSplitter extends Entity {
        public VerticalSplitter(Point position) {
            super(position);
        }

        @Override
        public String toString() {
            return "|";
        }

        @Override
        public void accept(EntityVisitor visitor, Direction direction) {
            switch (direction) {
                case EAST, WEST -> {
                    visitor.visit(Direction.NORTH.move(this.position), Direction.NORTH);
                    visitor.visit(Direction.SOUTH.move(this.position), Direction.SOUTH);
                }
                case NORTH -> visitor.visit(Direction.NORTH.move(this.position), Direction.NORTH);
                case SOUTH ->  visitor.visit(Direction.SOUTH.move(this.position), Direction.SOUTH);
            }
        }


    }

    public static class HorizontalSplitter extends Entity {
        public HorizontalSplitter(Point position) {
            super(position);
        }

        @Override
        public String toString() {
            return "-";
        }

        @Override
        public void accept(EntityVisitor visitor, Direction direction) {
            switch (direction) {
                case NORTH, SOUTH -> {
                    visitor.visit(Direction.EAST.move(this.position), Direction.EAST);
                    visitor.visit(Direction.WEST.move(this.position), Direction.WEST);
                }
                case EAST -> visitor.visit(Direction.EAST.move(this.position), Direction.EAST);
                case WEST -> visitor.visit(Direction.WEST.move(this.position), Direction.WEST);
            }
        }
    }

    public enum Direction {
        NORTH(3),
        EAST(0),
        SOUTH(1),
        WEST(2);

        final int rotation;

        Direction(int rotation) {
            this.rotation = rotation;
        }

        public Point move(Point point) {
            AffineTransform affineTransform = AffineTransform.getQuadrantRotateInstance(rotation, point.x, point.y);
            affineTransform.translate(1, 0);
            return (Point) affineTransform.transform(point, new Point());
        }
    }


}
