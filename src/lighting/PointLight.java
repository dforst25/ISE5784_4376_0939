package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * This class represents a point light source in a scene.
 * A point light emits light uniformly in all directions from a specific position in space.
 * @author David Forst and Moshe Goodman
 */
public class PointLight extends Light implements LightSource {

    //----------------------------fields--------------------------
    /**
     * The position of the point light in 3D space
     */
    private final Point position;
    /**
     * The constant attenuation coefficient
     */
    private double kC;
    /**
     * The linear attenuation coefficient
     */
    private double kL;
    /**
     * The quadratic attenuation coefficient
     */
    private double kQ;

    //-----------------------------constructor-------------------------

    /**
     * Constructs a point light with the given intensity and position.
     *
     * @param intensity The intensity of the point light
     * @param position  The position of the point light in 3D space
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
        kC = 1d;
        kL = 0d;
        kQ = 0d;
    }
    //--------------------------------setters----------------------------

    /**
     * Sets the constant attenuation coefficient of the point light.
     *
     * @param kC The constant attenuation coefficient
     * @return The updated PointLight object
     */
    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Sets the linear attenuation coefficient of the point light.
     *
     * @param kL The linear attenuation coefficient
     * @return The updated PointLight object
     */
    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Sets the quadratic attenuation coefficient of the point light.
     *
     * @param kQ The quadratic attenuation coefficient
     * @return The updated PointLight object
     */
    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }

    //---------------------------override functions-------------------------
    @Override
    public Color getIntensity(Point point) {
        double distance = position.distance(point);
        double distanceSquared = position.distanceSquared(point);
        return intensity.scale(1 / (kC + kL * distance + kQ * distanceSquared));
    }

    @Override
    public Vector getL(Point point) {
        return point.subtract(position).normalize();
    }
}
