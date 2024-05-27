package geometries;

import primitives.*;

public abstract class RadialGeometry implements Geometry{

    protected final double radius;

    /**
     * Constructor
     * @param radius represents the radius of the radial geometry
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }


}
