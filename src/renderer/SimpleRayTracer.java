package renderer;

import geometries.Intersectable.*;
import primitives.*;
import scene.Scene;

import java.util.List;

public class SimpleRayTracer extends RayTracerBase {


    //-----------------------------constructor-------------------------

    /**
     * Creates a new instance of the SimpleRayTracer class with the specified scene.
     *
     * @param scene The scene object containing information about the scene to be rendered.
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Calculates the color at the specified point using the ambient light in the scene.
     *
     * @param geoPoint The point at which to calculate the color.
     * @return The color at the specified point.
     */
    private Color calcColor(GeoPoint geoPoint) {

        return scene.ambientLight.getIntensity().add(geoPoint.geometry.getEmission());
    }


    //---------------------------override functions-------------------------
    @Override
    public Color traceRay(Ray ray) {

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);

        if (intersections == null)
            return scene.background;
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint);
    }
}
