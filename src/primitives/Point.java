package primitives;


/**
 *
 */
public class Point {
    // Constants
    public static final Point ZERO = new Point(Double3.ZERO);

    // Fields
    protected Double3 xyz;

    //ctors
    public Point(double x, double y, double z) {

        xyz = new Double3(x,y,z);
    }
    Point(Double3 xyz) {
        this.xyz = xyz;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return  (o instanceof Point point) && this.xyz.equals( point.xyz);
    }


    @Override
    public String toString() {
        return xyz + "";
    }

    public Point add(Vector v1) {
        return new Point(xyz.add(v1.xyz));
    }
    public Vector subtract(Point p1){
        return new Vector(xyz.subtract(p1.xyz));
    }

    public double distanceSquared(Point p1) {
        double x0 = p1.xyz.d1;
        double y0 = p1.xyz.d2;
        double z0 = p1.xyz.d3;
        double x = xyz.d1;
        double y = xyz.d2;
        double z = xyz.d3;
        return (x-x0)*(x-x0) + (y-y0)*(y-y0) + (z-z0)*(z-z0);
    }

    public double distance(Point p1) {
        return Math.sqrt(distanceSquared(p1));
    }
}


