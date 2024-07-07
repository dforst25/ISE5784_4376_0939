package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * A type of light source that emits light in a specific direction from a
 * point source and attenuates with distance.
 *
 * @author David Forst and Moshe Goodman
 */
public class SpotLight extends PointLight {
    //----------------------------fields--------------------------

    /**
     * The direction of the spotlight.
     */
    private final Vector direction;


    //-----------------------------constructor-------------------------

    /**
     * Constructs a new SpotLight object with the specified intensity, position and direction.
     *
     * @param intensity The intensity of the spotLight.
     * @param position  The position of the spotLight.
     * @param direction The direction of the spotlight.
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }
    //---------------------------override functions-------------------------

    @Override
    public SpotLight setkC(double kC) {
        return (SpotLight) super.setkC(kC);
    }

    @Override
    public SpotLight setkL(double kL) {
        return (SpotLight) super.setkL(kL);
    }

    @Override
    public SpotLight setkQ(double kQ) {
        return (SpotLight) super.setkQ(kQ);
    }


    @Override
    public Color getIntensity(Point point) {
        return (super.getIntensity(point)).scale(Math.max(0, getL(point).dotProduct(direction)));

    }
}
