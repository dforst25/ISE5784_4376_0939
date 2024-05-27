package geometries;
import primitives.*;

public class Tube extends RadialGeometry{
    protected final Ray axis;

    /**
     * Constructor
     * @param radius represents the radius of the tube
     * @param axis represents the axis of the tube
     */
    public Tube(double radius, Ray axis) {
        super(radius);
        this.axis = axis;
    }

    /**
     *
     * @param p represents a point of to calculate the normal from
     * @return the normal vector
     */
    @Override
    public Vector getNormal(Point p) {
        //vector v is an help vector to calculate p - p0
        Vector v = p.subtract(this.axis.getHead());

        double t = this.axis.getDir().dotProduct(v);
        Point o = this.axis.getHead().add(this.axis.getDir().scale((t)));
        if(p.subtract(o).length() != radius)
            throw new IllegalArgumentException("the point is not on the tube");

        return p.subtract(o).normalize();
    }
}
