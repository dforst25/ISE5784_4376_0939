package geometries;

import primitives.*;


public class Cylinder extends Tube{

    private final double height;

    /**
     *constructor
     * @param radius
     * @param axis
     * @param height
     */
    public Cylinder(double radius, Ray axis, double height) {
        super(radius, axis);
        this.height = height;
    }

    /**
     * returns the normal of the cylinder
     * @param p
     * @return
     */
    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
