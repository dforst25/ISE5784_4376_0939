package geometries;

import primitives.*;

public class Plane implements Geometry {
    //Fields
    private final Point q;
    private final Vector normal;

    /**
     * Constructor
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
     * @param q represents some point at the plane
     * @param normal represents the normal of the plane
     */
    public Plane(Point q, Vector normal) {
        this.q = q;
        this.normal = normal.normalize();
    }

    @Override
    public Vector getNormal(Point p) {
        return normal;
    }
    public Vector getNormal() {
        return normal;
    }
}
