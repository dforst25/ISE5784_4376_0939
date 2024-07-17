package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;


public class Triangle extends Polygon {
    /**
     * Constructor
     *
     * @param p1 represents first point of the triangle
     * @param p2 represents second point of the triangle
     * @param p3 represents third point of the triangle
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);

    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<Point> intersections = plane.findIntersections(ray);
        if (intersections == null)
            return null;
        Point P0 = ray.getHead();
        Vector rDir = ray.getDir();
        Vector V0 = vertices.get(0).subtract(P0);

        Vector V1 = vertices.get(1).subtract(P0);
        Vector V2 = vertices.get(2).subtract(P0);
        Vector n0 = V0.crossProduct(V1).normalize();
        Vector n1 = V1.crossProduct(V2).normalize();
        Vector n2 = V2.crossProduct(V0).normalize();
        if (!(rDir.dotProduct(n0) > 0 && rDir.dotProduct(n1) > 0 && rDir.dotProduct(n2) > 0) &&
                !(rDir.dotProduct(n0) < 0 && rDir.dotProduct(n1) < 0 && rDir.dotProduct(n2) < 0))
            return null;
        return List.of(new GeoPoint(this, intersections.getFirst()));
    }
}
