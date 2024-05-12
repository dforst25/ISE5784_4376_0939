package primitives;

public class Vector extends Point{
    public Vector(double x, double y, double z){
        super(x, y, z);
    }
    public Vector(Double3 xyz){
        super(xyz);
    }


    public double lengthSquared() {
        return super.distanceSquared(ZERO);
    }

    public double length() {
        return super.distance(ZERO);
    }

    public double dotProduct(Vector v3) {
        double x0 = v3.xyz.d1;
        double y0 = v3.xyz.d2;
        double z0 = v3.xyz.d3;
        double x = xyz.d1;
        double y = xyz.d2;
        double z = xyz.d3;
        return x * x0 + y * y0 + z * z0;
    }

    public Vector crossProduct(Vector v3) {
        double x0 = v3.xyz.d1;
        double y0 = v3.xyz.d2;
        double z0 = v3.xyz.d3;
        double x = xyz.d1;
        double y = xyz.d2;
        double z = xyz.d3;
        return new Vector(y * z0 - y0 * z, z * x0 - z0 * x, x * y0 - x0 * y);
    }

    public Vector normalize() {
        return new Vector(xyz.reduce(length()));
    }
}
