package dev.vinyard.adventofcode.soluce.year2023.day24;

import org.apache.commons.math3.linear.*;

import java.util.List;

public class ASD {

    public static class Root {
        private final List<Hail> hails;
        private final Bounds bounds;

        public Root(List<Hail> hails, Bounds bounds) {
            this.hails = hails;
            this.bounds = bounds;
        }

        public Long getCollisionsCountInBounds() {
            long count = 0;

            for (int i = 0; i < hails.size(); i++) {
                for (int j = i + 1; j < hails.size(); j++) {
                    Hail first = hails.get(i);
                    Hail second = hails.get(j);

                    if (getHailCollisionPosition(first, second)) {
                        count++;
                    }
                }
            }

            return count;
        }

        public Long findRockPositionScore() {
            Position rockPosition = findRockPosition(hails);
            System.out.println("%s, %s, %s".formatted(rockPosition.x, rockPosition.y, rockPosition.z));
            return (long) (rockPosition.x + rockPosition.y + rockPosition.z);
        }

        public boolean getHailCollisionPosition(Hail first, Hail second) {
            double a1 = first.a();
            double b1 = first.b();
            double c1 = first.c();

            double a2 = second.a();
            double b2 = second.b();
            double c2 = second.c();

            if (a1 * b2 == a2 * b1)
                return false; // Parallel lines

            double x = (c1 * b2 - c2 * b1) / (a1 * b2 - a2 * b1);
            double y = (c1 * a2 - c2 * a1) / (a2 * b1 - a1 * b2);

            if (first.velocity.x * (x - first.position.x) < 0 || first.velocity.y * (y - first.position.y) < 0)
                return false; // In the past

            if (second.velocity.x * (x - second.position.x) < 0 || second.velocity.y * (y - second.position.y) < 0)
                return false; // In the past

            return bounds.contains(x) && bounds.contains(y);
        }

        public Position findRockPosition(List<Hail> hails) {
            // Nous avons besoin d'au moins 4 grêlons pour résoudre complètement (6 inconnues)
            if (hails.size() < 4) {
                throw new IllegalArgumentException("Au moins 4 grêlons sont nécessaires");
            }

            // Construire un système avec 6 équations pour 6 inconnues (xr, yr, zr, vxr, vyr, vzr)
            double[][] matrixData = new double[6][6];
            double[] vectorData = new double[6];

            // Utilisons 3 paires de grêlons pour construire notre système
            Hail h0 = hails.get(0);
            Hail h1 = hails.get(1);
            Hail h2 = hails.get(2);
            Hail h3 = hails.get(3);

            // Pour chaque paire de grêlons, nous créons 2 équations
            // Premier hailstone - équations de collision en x,y
            setupEquations(h0, h1, 0, matrixData, vectorData);

            // Deuxième paire - équations de collision en y,z
            setupEquations(h1, h2, 2, matrixData, vectorData);

            // Troisième paire - équations en x,z
            setupEquations(h2, h3, 4, matrixData, vectorData);

            // Résoudre le système avec Commons Math
            RealMatrix coefficients = new Array2DRowRealMatrix(matrixData, false);
            DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();

            if (!solver.isNonSingular()) {
                throw new IllegalStateException("Le système n'a pas de solution unique");
            }

            RealVector constants = new ArrayRealVector(vectorData, false);
            RealVector solution = solver.solve(constants);

            // Extraire la position de la solution
            double xr = solution.getEntry(0);
            double yr = solution.getEntry(1);
            double zr = solution.getEntry(2);
            double vxr = solution.getEntry(3);
            double vyr = solution.getEntry(4);
            double vzr = solution.getEntry(5);


            // Vérification avec un 5ème grêlon si disponible
            if (hails.size() > 4) {
                Hail h4 = hails.get(4);

                // Calculer le temps d'intersection
                double tx = Double.NaN;
                double ty = Double.NaN;
                double tz = Double.NaN;

                if (vxr != h4.velocity().x) {
                    tx = (h4.position().x - xr) / (vxr - h4.velocity().x);
                }

                if (vyr != h4.velocity().y) {
                    ty = (h4.position().y - yr) / (vyr - h4.velocity().y);
                }

                if (vzr != h4.velocity().z) {
                    tz = (h4.position().z - zr) / (vzr - h4.velocity().z);
                }

                System.out.println("Vérification avec le 5ème grêlon - temps: tx=" + tx + ", ty=" + ty + ", tz=" + tz);

                // Vérifier que les points d'intersection correspondent
                double x5 = xr + vxr * tx;
                double y5 = yr + vyr * ty;
                double z5 = zr + vzr * tz;

                double x5_hail = h4.position().x + h4.velocity().x * tx;
                double y5_hail = h4.position().y + h4.velocity().y * ty;
                double z5_hail = h4.position().z + h4.velocity().z * tz;

                System.out.println("Position calculée du point d'intersection: " + x5 + ", " + y5 + ", " + z5);
                System.out.println("Position attendue du point d'intersection: " + x5_hail + ", " + y5_hail + ", " + z5_hail);
            }


            return new Position(-xr, -yr, -zr);
        }

        private void setupEquations(Hail h1, Hail h2, int startRow, double[][] matrix, double[] vector) {
            // Pour la paire de hailstones h1 et h2, nous créons 2 équations

            // Équation 1: basée sur le plan formé par les trajectoires en x,y
            // (xr - x1) * (vy1 - vyr) - (yr - y1) * (vx1 - vxr) = 0
            // (xr - x2) * (vy2 - vyr) - (yr - y2) * (vx2 - vxr) = 0
            // En soustrayant ces équations, on obtient une équation linéaire

            matrix[startRow][0] = h2.velocity().y - h1.velocity().y; // coefficient de xr
            matrix[startRow][1] = h1.velocity().x - h2.velocity().x; // coefficient de yr
            matrix[startRow][2] = 0;                                 // coefficient de zr
            matrix[startRow][3] = h1.position().y - h2.position().y; // coefficient de vxr
            matrix[startRow][4] = h2.position().x - h1.position().x; // coefficient de vyr
            matrix[startRow][5] = 0;                                 // coefficient de vzr

            vector[startRow] = h1.position().x * h1.velocity().y - h1.position().y * h1.velocity().x
                    - h2.position().x * h2.velocity().y + h2.position().y * h2.velocity().x;

            // Équation 2: basée sur le plan formé par les trajectoires en y,z
            // (yr - y1) * (vz1 - vzr) - (zr - z1) * (vy1 - vyr) = 0
            // (yr - y2) * (vz2 - vzr) - (zr - z2) * (vy2 - vyr) = 0
            // En soustrayant ces équations, on obtient une équation linéaire

            matrix[startRow+1][0] = 0;                                 // coefficient de xr
            matrix[startRow+1][1] = h2.velocity().z - h1.velocity().z; // coefficient de yr
            matrix[startRow+1][2] = h1.velocity().y - h2.velocity().y; // coefficient de zr
            matrix[startRow+1][3] = 0;                                 // coefficient de vxr
            matrix[startRow+1][4] = h1.position().z - h2.position().z; // coefficient de vyr
            matrix[startRow+1][5] = h2.position().y - h1.position().y; // coefficient de vzr

            vector[startRow+1] = h1.position().y * h1.velocity().z - h1.position().z * h1.velocity().y
                    - h2.position().y * h2.velocity().z + h2.position().z * h2.velocity().y;
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

        @Override
        public String toString() {
            return "%s @ %s".formatted(position, velocity);
        }
    }
    public record Position(double x, double y, double z) {
        @Override
        public String toString() {
            return "%s, %s, %s".formatted(x, y, z);
        }
    }
    public record Velocity(double x, double y, double z) {
        @Override
        public String toString() {
            return "%s, %s, %s".formatted(x, y, z);
        }
    }

}
