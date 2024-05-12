package geometries;

import primitives.*;

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
