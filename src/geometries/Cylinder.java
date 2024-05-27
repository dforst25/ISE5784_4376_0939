package geometries;

import primitives.*;


public class Cylinder extends Tube{

    private final double height;

    /**
     *constructor
     * @param radius is the radius of the cylinder (double)
     * @param axis is a ray that re[resents the axis of the cylinder
     * @param height the height of the cylinder (double)
     */
    public Cylinder(double radius, Ray axis, double height) {
        super(radius, axis);
        this.height = height;
    }

    /**
     * returns the normal of the cylinder
     * @param p is a point where the normal should be from
     * @return the normal from point p
     */
    @Override
    public Vector getNormal(Point p) {

        super.getNormal(p);
        return axis.getDir().normalize();
    }
}
