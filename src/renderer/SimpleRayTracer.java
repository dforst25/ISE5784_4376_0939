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
     * @param point The point at which to calculate the color.
     * @return The color at the specified point.
     */
    private Color calcColor(GeoPoint geoPoint) {

        return scene.ambientLight.getIntensity();
    }


    //---------------------------override functions-------------------------
    @Override
    public Color traceRay(Ray ray) {

        List<GeoPoint> geoPoints = scene.geometries.findGeoIntersections(ray);

        if (geoPoints == null)
            return scene.background;

        return calcColor(ray.findClosestGeoPoint(geoPoints));
    }
}
