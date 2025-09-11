package dev.vinyard.adventofcode.soluce.year2023.day24;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math3.linear.*;

import java.util.List;
import java.util.stream.IntStream;

public class ASD {

    public static class Root {
        private final List<Hail> hails;
        private final Bounds bounds;

        public Root(List<Hail> hails, Bounds bounds) {
            this.hails = hails;
            this.bounds = bounds;
        }

        public Long getCollisionsCountInBounds() {
            return IntStream.range(0, hails.size()).boxed()
                    .flatMap(i -> IntStream.range(i + 1, hails.size()).mapToObj(j -> Pair.of(hails.get(i), hails.get(j))))
                    .filter(this::getHailCollisionPosition)
                    .count();
        }

        public Long findRockPositionScore() {
            Position rockPosition = findRockPosition(hails);
            return (long) (rockPosition.x + rockPosition.y + rockPosition.z);
        }

        public boolean getHailCollisionPosition(Pair<Hail, Hail> pair) {
            Hail h1 = pair.getLeft();
            Hail h2 = pair.getRight();

            double a1 = h1.a();
            double b1 = h1.b();
            double c1 = h1.c();

            double a2 = h2.a();
            double b2 = h2.b();
            double c2 = h2.c();

            if (a1 * b2 == a2 * b1) return false; // Parallel lines

            double x = (c1 * b2 - c2 * b1) / (a1 * b2 - a2 * b1);
            double y = (c1 * a2 - c2 * a1) / (a2 * b1 - a1 * b2);

            if (h1.velocity.x * (x - h1.position.x) < 0 || h1.velocity.y * (y - h1.position.y) < 0)
                return false; // In the past

            if (h2.velocity.x * (x - h2.position.x) < 0 || h2.velocity.y * (y - h2.position.y) < 0)
                return false; // In the past

            return bounds.contains(x) && bounds.contains(y);
        }

        public Position findRockPosition(List<Hail> hails) {
            if (hails.size() < 4)
                throw new IllegalArgumentException("At least 3 hailstones are required to determine the rock's position and velocity.");

            // Building a system with 6 equations for 6 unknowns (xr, yr, zr, vxr, vyr, vzr)
            double[][] matrixData = new double[6][6];
            double[] vectorData = new double[6];

            // Use the first three pairs of hailstones to build our system
            Hail h0 = hails.get(0);
            Hail h1 = hails.get(1);
            Hail h2 = hails.get(2);
            Hail h3 = hails.get(3);

            setupEquations(Pair.of(h0, h1), 0, matrixData, vectorData);
            setupEquations(Pair.of(h1, h2), 2, matrixData, vectorData);
            setupEquations(Pair.of(h2, h3), 4, matrixData, vectorData);

            // Solving the system using Apache Commons Math
            RealMatrix coefficients = new Array2DRowRealMatrix(matrixData, false);
            DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();

            RealVector constants = new ArrayRealVector(vectorData, false);
            RealVector solution = solver.solve(constants);

            double xr = Math.round(solution.getEntry(0));
            double yr = Math.round(solution.getEntry(1));
            double zr = Math.round(solution.getEntry(2));

            return new Position(xr, yr, zr);
        }

        private void setupEquations(Pair<Hail, Hail> pair, int startRow, double[][] matrix, double[] vector) {
            Hail h1 = pair.getLeft();
            Hail h2 = pair.getRight();

            // For the pair of hailstones h1 and h2, we create 2 equations

            // Equation 1: based on the plane formed by the trajectories in x,y
            // (xr - x1) * (vy1 - vyr) - (yr - y1) * (vx1 - vxr) = 0
            // (xr - x2) * (vy2 - vyr) - (yr - y2) * (vx2 - vxr) = 0
            // By subtracting these equations, we get a linear equation
            matrix[startRow][0] = h1.velocity().y - h2.velocity().y; // coefficient of xr
            matrix[startRow][1] = h2.velocity().x - h1.velocity().x; // coefficient of yr
            matrix[startRow][2] = 0;                                 // coefficient of zr
            matrix[startRow][3] = h2.position().y - h1.position().y; // coefficient of vxr
            matrix[startRow][4] = h1.position().x - h2.position().x; // coefficient of vyr
            matrix[startRow][5] = 0;                                 // coefficient of vzr

            vector[startRow] = h1.position().x * h1.velocity().y - h1.position().y * h1.velocity().x - h2.position().x * h2.velocity().y + h2.position().y * h2.velocity().x;

            // Equation 2: based on the plane formed by the trajectories in y,z
            // (yr - y1) * (vz1 - vzr) - (zr - z1) * (vy1 - vyr) = 0
            // (yr - y2) * (vz2 - vzr) - (zr - z2) * (vy2 - vyr) = 0
            // By subtracting these equations, we get a linear equation
            matrix[startRow + 1][0] = 0;                                 // coefficient of xr
            matrix[startRow + 1][1] = h1.velocity().z - h2.velocity().z; // coefficient of yr
            matrix[startRow + 1][2] = h2.velocity().y - h1.velocity().y; // coefficient of zr
            matrix[startRow + 1][3] = 0;                                 // coefficient of vxr
            matrix[startRow + 1][4] = h2.position().z - h1.position().z; // coefficient of vyr
            matrix[startRow + 1][5] = h1.position().y - h2.position().y; // coefficient of vzr

            vector[startRow + 1] = h1.position().y * h1.velocity().z - h1.position().z * h1.velocity().y - h2.position().y * h2.velocity().z + h2.position().z * h2.velocity().y;
        }

    }

    public record Bounds(double min, double max) {

        public boolean contains(double value) {
            return value >= min && value <= max;
        }
    }

    public record Hail(Position position, Velocity velocity) {
        public double a() {
            return -velocity.y;
        }

        public double b() {
            return velocity.x;
        }

        public double c() {
            return velocity.x * position.y - velocity.y * position.x;
        }
    }

    public record Position(double x, double y, double z) {
    }

    public record Velocity(double x, double y, double z) {
    }

}
