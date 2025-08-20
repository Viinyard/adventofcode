package dev.vinyard.adventofcode.utils;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;

public class PathUtils {

    public static double getLength(Path2D path) {
        double length = 0.0;
        double[] coords = new double[6];
        Point2D.Double previousPoint = null;
        Point2D.Double currentPoint;

        for (PathIterator pi = path.getPathIterator(null); !pi.isDone(); pi.next()) {
            int type = pi.currentSegment(coords);
            switch (type) {
                case PathIterator.SEG_MOVETO:
                    previousPoint = new Point2D.Double(coords[0], coords[1]);
                    break;
                case PathIterator.SEG_LINETO:
                    currentPoint = new Point2D.Double(coords[0], coords[1]);
                    assert previousPoint != null;
                    length += previousPoint.distance(currentPoint);
                    previousPoint.setLocation(currentPoint);
                    break;
            }
        }
        return length;
    }

    /**
     * Calculates the area of a Path2D object using the <a href="https://en.wikipedia.org/wiki/Shoelace_formula">Shoelace formula</a>.
     * @param path
     * @return the area of the path
     */
    public static double getArea(Path2D path) {
        double area = 0.0;
        double[] coords = new double[6];
        Point2D.Double previousPoint = null;
        Point2D.Double currentPoint;

        for (PathIterator pi = path.getPathIterator(null); !pi.isDone(); pi.next()) {
            int type = pi.currentSegment(coords);
            switch (type) {
                case PathIterator.SEG_MOVETO:
                    previousPoint = new Point2D.Double(coords[0], coords[1]);
                    break;
                case PathIterator.SEG_LINETO:
                    currentPoint = new Point2D.Double(coords[0], coords[1]);
                    assert previousPoint != null;
                    area += (previousPoint.x * currentPoint.y) - (currentPoint.x * previousPoint.y);
                    previousPoint.setLocation(currentPoint);
                    break;
            }
        }
        return Math.abs(area / 2.0);
    }
}
