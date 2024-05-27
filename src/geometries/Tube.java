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

    @Override
    public Vector getNormal(Point p) {
        //vector v is an help vector to calculate p - p0
        Vector v = p.subtract(this.axis.getHead());

        double t = this.axis.getDir().dotProduct(v);
        Point o = this.axis.getHead().add(this.axis.getDir().scale(t));
        return p.subtract(o).normalize();
    }
}
