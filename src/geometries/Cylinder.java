package geometries;

import primitives.*;

import static primitives.Util.isZero;


public class Cylinder extends Tube {

    private final double height;

    /**
     * constructor
     *
     * @param radius is the radius of the cylinder (double)
     * @param axis   is a ray that re[resents the axis of the cylinder
     * @param height the height of the cylinder (double)
     */
    public Cylinder(double radius, Ray axis, double height) {
        super(radius, axis);
        this.height = height;
    }

    /**
     * returns the normal of the cylinder
     *
     * @param p is a point where the normal should be from
     * @return the normal from point p
     */
    @Override
    public Vector getNormal(Point p) {
        Point axisHead = axis.getHead();
        //if the points is on the head of the point
        if (isZero(p.distance(this.axis.getHead())))
            return axis.getDir().scale(-1);

        //vector v is  p - p0
        Vector v = p.subtract(this.axis.getHead());

        double t = this.axis.getDir().dotProduct(v);

        //the cases that the point is on the same plane as the base
        if (isZero(t) && p.distance(axis.getHead()) <= this.radius)
            return axis.getDir().scale(-1);
        else if (isZero(t))
            throw new IllegalArgumentException("the point is not on the cylinder");

        Point o = this.axis.getHead().add(this.axis.getDir().scale((t)));

        if (o.distance(axis.getHead()) < 0 || o.distance(axis.getHead()) > this.height || p.distance(o) > this.radius)
            throw new IllegalArgumentException("the point is not on the cylinder");

        else if ( o.distance(axis.getHead()) != this.height && p.distance(o) < this.radius)
            throw new IllegalArgumentException("the point is not on the cylinder");

        else if (isZero(o.distance(axis.getHead()) - this.height))
            return axis.getDir();
        else
            return super.getNormal(p);
    }
}
