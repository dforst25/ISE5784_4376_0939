package geometries;

import primitives.*;

public abstract class Geometry extends Intersectable {

    protected Color emission = Color.BLACK;

    /**
     * @param p represents a point of yhe geometry
     * @return a vector representing the normal of the geometry at this point
     */
    public abstract Vector getNormal(Point p);

    /**
     * getter for the emission field
     *
     * @return the emission value
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * sets the value of emission and returns the class
     *
     * @param emission is the emission field
     * @return this (Geometry type)
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }
}
