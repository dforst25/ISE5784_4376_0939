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
        this.normal = null;
    }

    /**
     * Constructor
     * @param q
     * @param normal
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
