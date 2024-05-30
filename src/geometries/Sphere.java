package geometries;
import primitives.*;

import java.util.List;

public class Sphere extends RadialGeometry{

    private final Point center;


    /**
     * Constructor
     * @param radius represents the radius of the sphere
     * @param center represents the center of the sphere
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    /**
     *
     * @param p represents a point to get the vector normal from
     * @return the normal from that point
     */
    @Override
    public Vector getNormal(Point p) {
        //v is a help vector for calculating if the point is on the sphere and also for calculating the normal
        Vector v = p.subtract(this.center);
        if(v.length() != this.radius)
            throw new IllegalArgumentException("The point isn't correct!");
        return v.normalize();
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
