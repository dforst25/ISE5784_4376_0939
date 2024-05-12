package primitives;

/**
 *
 */
public class Point {
    public static final Point ZERO = new Point(Double3.ZERO);
    final Double3 xyz;
    public Point(double x, double y, double z) {

        xyz = new Double3(x,y,z);
    }

    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    public Vector subtract(Point p1){
        return new Vector(xyz.subtract(p1.xyz));
    }

    public Point add(Vector v1) {
        return new Point(xyz.add(v1.xyz));
    }

    public double distanceSquared(Point p1) {
        double x0 = p1.xyz.d1;
        double y0 = p1.xyz.d2;
        double z0 = p1.xyz.d3;
        double x = xyz.d1;
        double y = xyz.d2;
        double z = xyz.d3;
        return Math.pow(x-x0,2) + Math.pow(y-y0,2) + Math.pow(z-z0,2);
    }

    public double distance(Point p1) {
        return Math.sqrt(distanceSquared(p1));
    }
}


