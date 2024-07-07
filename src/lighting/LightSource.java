package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * An interface representing a light source in a 3D scene.
 *
 * @author David Forst and Moshe Goodman
 */
public interface LightSource {

    /**
     * @param point The point at which to evaluate the intensity of the light.
     * @return The intensity of the light at the specified point.
     */
    Color getIntensity(Point point);

    /**
     * @param point The point at which to evaluate the direction of the light.
     * @return The direction of the light at the specified point.
     */
    Vector getL(Point point);
}