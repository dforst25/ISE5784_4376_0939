package geometries;

import primitives.Point;
import primitives.Vector;

public abstract class RadialGeometry implements Geometry{

    protected final double radius;

    /**
     * Constructor
     * @param radius
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }


}
