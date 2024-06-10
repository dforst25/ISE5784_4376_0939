package geometries;

import primitives.*;

import java.util.List;

public interface Intersectable {

    /**
     *
     * @param ray is the ray that intersects the geometry
     * @return a list of the point that the ray intersects the geometrical object
     */
    List<Point> findIntersections(Ray ray);


}
