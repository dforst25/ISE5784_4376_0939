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

    /**
     *
     * @param point is the point that I calculate the distance between this point and the light
     * @return the distance between the light and the current point
     */
    double getDistance(Point point);
}