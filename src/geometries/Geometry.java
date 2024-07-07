package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

public abstract class Geometry extends Intersectable {
    //--------------------------------fields----------------------------

    protected Color emission = Color.BLACK;
    /**
     * This field represents the material properties of this Geometry object.
     * A Material object contains information about the material's properties,
     * such as its color and texture.
     */
    private Material material = new Material();

    //--------------------------------function----------------------------

    /**
     * @param p represents a point of yhe geometry
     * @return a vector representing the normal of the geometry at this point
     */
    public abstract Vector getNormal(Point p);

    //--------------------------------getters----------------------------

    /**
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

    //--------------------------------setters----------------------------

    /**
     * @return the material of this geometry
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Sets the material of this geometry.
     *
     * @param material The material to set.
     * @return This geometry instance.
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

}
