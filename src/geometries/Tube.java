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
        return null;
    }
}
