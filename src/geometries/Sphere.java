package geometries;
import primitives.*;

import java.util.Comparator;
import java.util.List;

import static primitives.Util.alignZero;

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
        Point P0 = ray.getHead();
        if(P0.equals(center))
            return List.of(center.add(ray.getDir().scale(radius)));
        Vector U = center.subtract(P0);
        double Tm = ray.getDir().dotProduct(U);
        double d = Math.sqrt(U.length()* U.length()-Tm*Tm);
        if (d>=radius)
            return null;
        double Th = Math.sqrt(radius*radius-d*d);
        double t1 = alignZero(Tm -Th) , t2 =alignZero( Tm +Th);
        if(t1<=0 && t2 <=0)
            return null;
        if(t1<=0)
            return List.of(P0.add(ray.getDir().scale(t2)));
        if(t2<=0)
            return List.of(P0.add(ray.getDir().scale(t1)));

        return List.of(P0.add(ray.getDir().scale(t1)),  P0.add(ray.getDir().scale(t2)))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(P0)))
                .toList();
    }
}
