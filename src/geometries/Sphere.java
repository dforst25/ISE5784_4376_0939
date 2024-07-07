package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Sphere extends RadialGeometry {

    private final Point center;


    /**
     * Constructor
     *
     * @param radius represents the radius of the sphere
     * @param center represents the center of the sphere
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    /**
     * @param p represents a point to get the vector normal from
     * @return the normal from that point
     */
    @Override
    public Vector getNormal(Point p) {
        return p.subtract(this.center).normalize();
    }


    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Point P0 = ray.getHead();
        if (P0.equals(center))
            return List.of(new GeoPoint(this, ray.getPoint(radius)));
        Vector U = center.subtract(P0);
        double Tm = ray.getDir().dotProduct(U);
        double d = Math.sqrt(U.length() * U.length() - Tm * Tm);
        if (d >= radius)
            return null;
        double Th = Math.sqrt(radius * radius - d * d);
        double t1 = alignZero(Tm - Th), t2 = alignZero(Tm + Th);
        if ((isZero(t1) || t1 < 0) && (isZero(t2) || t2 < 0))
            return null;
        if (isZero(t1) || t1 < 0)
            return List.of(new GeoPoint(this, ray.getPoint(t2)));
        if (isZero(t2) || t2 < 0)
            return List.of(new GeoPoint(this, ray.getPoint(t1)));

        return Stream.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2))).
                sorted(Comparator.comparingDouble(p -> p.point.distance(P0)))
                .toList();
    }
}
