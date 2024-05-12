package primitives;

public class Vector extends Point{
    //Ctor
    public Vector(double x, double y, double z)
    {
        super(x, y, z);
        if (xyz == Double3.ZERO)//why not use the length() function?
            throw new IllegalArgumentException();

    }
    Vector(Double3 xyz)
    {
        super(xyz);
        if (xyz == Double3.ZERO)//why not use the length() function?
            throw new IllegalArgumentException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return (o instanceof Vector other) && super.equals(other);
    }
    @Override
    public String toString() { return "->" + super.toString(); }

    public Vector add(Vector p1){
        return new Vector(xyz.add(p1.xyz));
    }

    public double lengthSquared() {
        return xyz.d1* xyz.d1  +xyz.d2 * xyz.d2 + xyz.d3 * xyz.d3  ;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }
    public Vector scale(double scalar) {return new Vector(xyz.scale(scalar));}
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
