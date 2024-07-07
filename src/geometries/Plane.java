package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

public class Plane extends Geometry {
    //Fields
    private final Point q;
    private final Vector normal;

    /**
     * Constructor
     *
     * @param x first point on the given plane
     * @param y second point on the given plane
     * @param z third point on the given plane
     */
    public Plane(Point x, Point y, Point z) {
        this.q = x;
        //v1, v2 are 2 help vectors for calculating the normal of the plane
        Vector v1 = y.subtract(x);
        Vector v2 = z.subtract(x);
        this.normal = (v1.crossProduct(v2)).normalize();
    }

    /**
     * Constructor
     *
     * @param q      represents some point at the plane
     * @param normal represents the normal of the plane
     */
    public Plane(Point q, Vector normal) {
        this.q = q;
        this.normal = normal.normalize();
    }

    public Vector getNormal() {
        return normal;
    }

    @Override
    public Vector getNormal(Point p) {
        if (p.subtract(this.q).dotProduct(normal) != 0)
            throw new IllegalArgumentException("point is not on the plane");
        return normal;
    }


    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        if (q.equals(ray.getHead()))
            return null;
        if (isZero(ray.getDir().dotProduct(normal))) {
            if (!isZero(ray.getHead().subtract(q).dotProduct(normal)))
                return null;
            //else it is contained in the plane and i don't know what i'm meant to do
        }
        double temp = normal.dotProduct(q.subtract(ray.getHead()));
        double t = temp / (normal.dotProduct(ray.getDir()));
        if (t <= 0)
            return null;
        return List.of(new GeoPoint(this, ray.getPoint(t)));
    }
}
