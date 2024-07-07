package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * The `RayTracerBase` class is an abstract base class for implementing ray tracing algorithms.
 * It provides a basic framework for rendering a 3D scene by casting rays from a virtual camera
 * and computing the color of each pixel based on the intersection of those rays with objects
 * in the scene.
 * <p>
 * To use `RayTracerBase`, you need to implement the `traceRay()` method, which takes a ray
 * as input and returns the color of the closest object that the ray intersects with. The
 * `traceRay()` method should use the `Scene` object provided in the constructor to determine
 * the geometry and materials of the objects in the scene, and should recursively trace secondary
 * rays to compute shadows, reflections, and other lighting effects.
 * <p>
 * The `RayTracerBase` class also provides a protected `scene` field that can be accessed by
 * subclasses to obtain information about the scene, such as the camera position and orientation
 * and the list of objects in the scene.
 *
 * @author David Forst and Moshe Goodman
 */
public abstract class RayTracerBase {

    //----------------------------fields--------------------------

    /**
     * The `Scene` object that represents the 3D scene being rendered.
     */
    protected Scene scene;

    //------------------------------functions---------------------------

    /**
     * Constructs a new `RayTracerBase` with the specified `Scene`.
     *
     * @param scene The `Scene` object representing the 3D scene to be rendered.
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Computes the color of a pixel by casting a ray from the virtual camera and tracing it
     * through the scene to determine the closest object that the ray intersects with. The
     * `traceRay()` method should recursively trace secondary rays to compute shadows, reflections,
     * and other lighting effects.
     *
     * @param ray The `Ray` object representing the ray cast from the virtual camera.
     * @return The color of the closest object that the ray intersects with.
     */
    public Color traceRay(Ray ray) {
        return null;
    }
}
