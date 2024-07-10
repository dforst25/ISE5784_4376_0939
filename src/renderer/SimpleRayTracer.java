package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

public class SimpleRayTracer extends RayTracerBase {

    private static final double DELTA = 0.1;

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
     * Calculates the color of the given intersection point by considering the ambient light and local effects
     * such as diffuse and specular reflections.
     *
     * @param point The intersection point on the geometry
     * @param ray   The ray that intersected with the geometry
     * @return The color at the intersection point
     */
    private Color calcColor(GeoPoint point, Ray ray) {
        Color ambientIntensity = scene.ambientLight.getIntensity();
        Color localEffects = calcLocalEffects(point, ray);
        // Return the calculated color
        return ambientIntensity.add(localEffects);
    }

    /**
     * Calculates the local effects at a given intersection point, such as diffuse and specular reflections.
     *
     * @param gp  The intersection point
     * @param ray The ray that intersected with the geometry
     * @return The color of the local effects at the intersection point
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Color color = gp.geometry.getEmission();

        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return color;

        Material material = gp.geometry.getMaterial();

        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));

            if (nl * nv > 0) {
                if(unshaded(lightSource, gp,l,n)) {
                    Color iL = lightSource.getIntensity(gp.point);

                    // Calculate the contributions of diffuse and specular reflections
                    color = color.add(
                            calcDiffusive(material.kD, nl, iL),
                            calcSpecular(material.kS, n, l, nl, v, iL, material.nShininess)
                    );
                }
            }
        }
        return color;
    }


    /**
     * Calculates the specular reflection color at a given intersection point.
     *
     * @param kS         The specular reflection coefficient of the material
     * @param n          The surface normal vector at the intersection point
     * @param l          The direction vector towards the light source
     * @param nl         The dot product between the surface normal and light direction vectors
     * @param v          The view direction vector
     * @param iL         The intensity of the light source at the intersection point
     * @param nShininess The shininess factor of the material
     * @return The color resulting from the specular reflection at the intersection point
     */
    private Color calcSpecular(Double3 kS, Vector n, Vector l, double nl, Vector v, Color iL, int nShininess) {
        Vector reflectionVector = l.subtract(n.scale(nl * 2));
        double minusVR = -alignZero(v.dotProduct(reflectionVector));
        if (minusVR <= 0)
            return Color.BLACK;

        Double3 shine = kS.scale(Math.pow(minusVR, nShininess)); // Calculate the specular reflection color based on the shininess factor
        return iL.scale(shine);
    }


    /**
     * Calculates the diffuse reflection color at a given intersection point.
     *
     * @param kD The diffuse coefficient of the material
     * @param nl The dot product between the normal and light direction vectors
     * @param iL The intensity of the light source
     * @return The calculated diffuse reflection color at the given intersection point
     */
    private Color calcDiffusive(Double3 kD, double nl, Color iL) {

        Double3 diffuseCoefficient = kD.scale(Math.abs(nl));

        return iL.scale(diffuseCoefficient);
    }
    private boolean unshaded(LightSource light, GeoPoint gp, Vector l, Vector n){
        Vector lightDirection = l.scale(-1);
        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
        Point point = gp.point.add(delta);
        Ray shadowRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(shadowRay);
        if(intersections==null)
            return true;
        Point closestPoint = shadowRay.findClosestGeoPoint(intersections).point;
        return !(closestPoint.distance(point) < light.getDistance(point));
    }

    //---------------------------override functions-------------------------
    @Override
    public Color traceRay(Ray ray) {

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);

        if (intersections == null)
            return scene.background;
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint, ray);
    }
}
