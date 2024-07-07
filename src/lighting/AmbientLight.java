package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * The `AmbientLight` class represents ambient lighting in a 3D scene, which is a type of
 * non-directional light that is applied uniformly to all objects in the scene regardless of
 * their position or orientation. Ambient lighting helps to provide a base level of illumination
 * in a scene and can be used to simulate the effect of indirect or diffused light.
 * <p>
 * An ambient light is defined by an intensity value, which determines how much light is emitted,
 * and a scaling factor, which determines how much of that light is applied to each object in the
 * scene. The intensity can be specified as either a single scalar value or a vector of RGB values,
 * while the scaling factor can be specified as either a single scalar or a vector of scaling factors
 * for each color channel.
 * <p>
 * The `AmbientLight` class provides a static constant `NONE`, which represents a null ambient light
 * that has zero intensity and zero scaling factor. This can be used as a default value or to disable
 * ambient lighting entirely.
 *
 * @author David Forst and Moshe Goodman
 */
public class AmbientLight extends Light {

    //----------------------------fields--------------------------

    /**
     * A static constant representing a null ambient light with zero intensity and zero scaling factor.
     * This can be used as a default value or to disable ambient lighting entirely.
     */
    static public final AmbientLight NONE = new AmbientLight(Color.BLACK, 0);

    //-----------------------------constructors-------------------------

    /**
     * Constructs a new `AmbientLight` with the specified intensity and scaling factor.
     * The intensity can be specified as a scalar value or a vector of RGB values, and
     * the scaling factor can be specified as a scalar or a vector of scaling factors
     * for each color channel.
     *
     * @param ia The intensity of the ambient light.
     * @param ka The scaling factor for the ambient light.
     */
    public AmbientLight(Color ia, double ka) {
        super(ia.scale(ka));
    }

    /**
     * Constructs a new `AmbientLight` with the specified intensity and scaling factors.
     * The intensity can be specified as a scalar value or a vector of RGB values, and
     * the scaling factors can be specified as a vector of scaling factors for each color
     * channel.
     *
     * @param ia The intensity of the ambient light.
     * @param ka The scaling factors for the ambient light.
     */
    public AmbientLight(Color ia, Double3 ka) {

        super(ia.scale(ka));
    }


}