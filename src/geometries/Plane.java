package geometries;

import primitives.*;

public class Plane implements Geometry {
    //Fields
    private final Point q;
    private final Vector normal;

    /**
     * Constructor
     * @param x
     * @param y
     * @param z
     */
    public Plane(Point x, Point y, Point z) {
        this.q = x;
        this.normal = (x.subtract(y)).crossProduct(y.subtract(z)).normalize();
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
