package geometries;
import primitives.*;

public interface Geometry {
    /**
     * Constructor
     * @param p represents a point of yhe geometry
     * @return a vector representing the normal of the geometry at this point
     */
    public Vector getNormal(Point p);
}
