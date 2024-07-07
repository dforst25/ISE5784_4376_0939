package lighting;

import primitives.Color;

/**
 * The lighting package contains classes and interfaces related to lighting
 * calculations and light sources in a 3D scene.
 *
 * @author David Forst and Moshe Goodman
 */

abstract class Light {
    //-----------------------------fields-------------------------
    //The intensity (color and brightness) of the light source.
    protected final Color intensity;
    //-----------------------------constructor-------------------------

    /**
     * @param intensity represents the intensity of a light source
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }
    //-----------------------------getter-------------------------


    /**
     * @return intensity of a light source in a Color type
     */
    public Color getIntensity() {
        return intensity;
    }
}
