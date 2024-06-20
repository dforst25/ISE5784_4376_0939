package primitives;

public class Vector extends Point{

    /**
     * Constructor
     * @param x is for the x axis
     * @param y  is for the y axis
     * @param z  is for the z axis
     */
    public Vector(double x, double y, double z)
    {
        super(x, y, z);
        if (this.xyz.equals(Double3.ZERO))//why not use the length() function?
            throw new IllegalArgumentException();

    }

    /**
     * Constructor
     * @param xyz will be used for the point
     */
    public Vector(Double3 xyz)
    {
        super(xyz);
        if (this.xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("cannot create vector 0");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return (o instanceof Vector other) && super.equals(other);
    }
    @Override
    public String toString() { return "->" + super.toString(); }

    /**
     * sum of two vectors returns a vector
     * @param vector
     * @return vector
     */
    public Vector add(Vector vector){
        return new Vector(xyz.add(vector.xyz));
    }

    /**
     * returns the square of the vector length (double)
     * @return double
     */
    public double lengthSquared() {
        return xyz.d1* xyz.d1  +xyz.d2 * xyz.d2 + xyz.d3 * xyz.d3  ;
    }

    /**
     * returns the vector length (double)
     * @return double
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * changes the size
     * @param scalar
     * @return vector
     */
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
