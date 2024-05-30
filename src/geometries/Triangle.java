package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

public class Triangle extends Polygon {
    /**
     * Constructor
     * @param p1 represents first point of the triangle
     * @param p2 represents second point of the triangle
     * @param p3 represents third point of the triangle
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);

    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
