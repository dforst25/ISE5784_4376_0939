package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class SimpleRayTracer extends RayTracerBase {

    private static final int MAX_CALC_COLOR_LEVEL = 10;

    private static final double MIN_CALC_COLOR_K = 0.001;

    private static final Double3 INITIAL_K = Double3.ONE;

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
     * Calculates the color of a given intersection point based on the local and global effects.
     * This method takes into account the maximum recursion level and the transparency coefficient.
     *
     * @param intersection The intersection point between the ray and the geometry
     * @param ray          The ray that intersected with the geometry
     * @param level        The current recursion level
     * @param k            The transparency coefficient
     * @return The color of the intersection point
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(intersection, ray, k);

        return 1 == level ? color
                : color.add(calcGlobalEffects(intersection, ray, level, k));
    }

    /**
     * Calculates the color of a given intersection point based on the local and global effects.
     * This method takes into account the maximum recursion level and the initial transparency coefficient.
     *
     * @param geoPoint The intersection point between the ray and the geometry
     * @param ray      The ray that intersected with the geometry
     * @return The color of the intersection point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }
    /**
     * Calculates the global effects (reflection and refraction) on the color at the given intersection point.
     * This method recursively traces rays for reflection and refraction to determine the overall color at the intersection point.
     * It takes into account the material properties of the intersected geometry, such as reflection coefficient (kR) and
     * refraction coefficient (kT), to compute the reflection and refraction effects.
     *
     * @param geoPoint    The intersection point on the geometry.
     * @param ray   The ray that intersected the geometry.
     * @param level The recursion level, indicating the number of reflection and refraction bounces.
     * @param k     The accumulated attenuation factor, representing the amount of light that has been absorbed.
     *              It is multiplied by the material's reflection (kR) or refraction (kT) coefficient at each recursion level.
     * @return The color with global effects.
     */
    private Color calcGlobalEffects(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Vector v = ray.getDir();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        Material material = geoPoint.geometry.getMaterial();
        return calcGlobalEffect(constructReflectedRay(geoPoint, v, n), level, k, material.kR)
                .add(calcGlobalEffect(constructRefractedRay(geoPoint, v, n), level, k, material.kT));
    }

    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint geoPoint = findClosestIntersection(ray);
        if (geoPoint == null) return scene.background.scale(kx);
        return isZero(geoPoint.geometry.getNormal(geoPoint.point).dotProduct(ray.getDir())) ? Color.BLACK
                : calcColor(geoPoint, ray, level - 1, kkx);
    }


    /**
     * Calculates the local effects at a given intersection point, such as diffuse and specular reflections.
     *
     * @param gp  The intersection point
     * @param ray The ray that intersected with the geometry
     * @return The color of the local effects at the intersection point
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
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
                Double3 ktr = transparency(gp, l, n, lightSource);
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);

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

    private boolean unshaded(LightSource light, GeoPoint gp, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1);
        Point point = gp.point;
        Ray shadowRay = new Ray(point, lightDirection, n);
        GeoPoint closestGeoPoint = findClosestIntersection(shadowRay);
        return closestGeoPoint == null ||
                !(closestGeoPoint.point.distance(point) < light.getDistance(point) &&
                        closestGeoPoint.geometry.getMaterial().kT == Double3.ZERO);
    }

    /**
     * Calculates the transparency coefficient for a given intersection point with respect to a light source.
     * The transparency coefficient represents the cumulative transparency of all intersected geometries between
     * the intersection point and the light source.
     *
     * @param geoPoint The intersection point between the ray and the geometry
     * @param light       The light source
     * @param l        The light direction vector
     * @param n        The surface normal vector
     * @return The transparency coefficient
     */

    private Double3 transparency(GeoPoint geoPoint, Vector l, Vector n, LightSource light) {
        Vector lightDirection = l.scale(-1);
        Point point = geoPoint.point;
        Ray lightRay = new Ray(point, lightDirection, n);

        double maxDistance = light.getDistance(point);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);

        if (intersections == null)
            return Double3.ONE;

        Double3 ktr = Double3.ONE;

        for (GeoPoint geo : intersections) {
            if (point.distance(geo.point) < maxDistance) {
                ktr = ktr.product(geo.geometry.getMaterial().kT);
                if (ktr.lowerThan(MIN_CALC_COLOR_K)) {
                    return Double3.ZERO;
                }
            }
        }

        return ktr;
    }

    /**
     * Constructs a refracted ray based on the point of intersection, incident vector, and surface normal.
     * The refracted ray is created using the Snell's law.
     *
     * @param geoPoint The point of intersection.
     * @param v        The incident vector.
     * @param n        The surface normal.
     * @return A new Ray object representing the refracted ray.
     */
    private Ray constructRefractedRay(GeoPoint geoPoint, Vector v, Vector n) {
        return new Ray(geoPoint.point, v, n);
    }

    /**
     * Constructs a reflected ray based on the given point, incident vector, and surface normal.
     *
     * @param geoPoint The point of intersection.
     * @param v        The incident vector.
     * @param n        The surface normal.
     * @return The reflected ray.
     */

    private Ray constructReflectedRay(GeoPoint geoPoint, Vector v, Vector n) {

        double vn = alignZero(v.dotProduct(n));

        if (vn == 0) {
            return null;
        }

        // Calculate the reflection direction using the formula: r = v - 2 * (v dot n) * n
        Vector r = v.subtract(n.scale(2 * vn));

        // Create a new ray with the reflected direction starting from the given point.
        return new Ray(geoPoint.point, r, n);
    }

    /**
     * Finds the closest intersection point between the given ray and the geometries in the scene.
     *
     * @param ray The ray for which to find the closest intersection.
     * @return The closest intersection point, or null if no intersection is found.
     */

    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        return intersections == null ? null
                : ray.findClosestGeoPoint(intersections);
    }

    //---------------------------override functions-------------------------
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background
                : calcColor(closestPoint, ray);
    }
}
