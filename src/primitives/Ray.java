package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.Comparator;
import java.util.List;

import static primitives.Util.isZero;


public class Ray {
    private final Point head;
    private final Vector direction;

    /**
     * Constructor
     *
     * @param head      the head of the ray
     * @param direction the vector of the ray normalized
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        this.direction = direction.normalize();
    }

    //----------------------------------OverrideFunctions----------------------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return (o instanceof Ray ray) && this.head.equals(ray.head) && this.direction.equals(ray.direction);
    }


    @Override
    public String toString() {
        return "Ray{" +
                "head=" + head +
                ", direction=" + direction +
                '}';
    }
    //----------------------functions------------------------------------

    /**
     * @param t is the distance from the original point
     * @return the new point on the ray at distance t
     */
    public Point getPoint(double t) {
        if (isZero(t))
            return head;
        return head.add(direction.scale(t));
    }

    /**
     * Finds the closest point in a list of points to a given point.
     *
     * @param points A list of points to search from
     * @return The closest point to the given point
     * If the list is empty, returns null.
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoints) {

        // return the closest point
        return geoPoints.stream().min(Comparator.comparingDouble(p -> p.point.distance(head))).orElse(null);

    }
    //--------------------------------getters----------------------------

    /**
     * getter for the head, the starting point of the ray
     *
     * @return point of vector
     */
    public Point getHead() {
        return head;
    }

    /**
     * getter for direction,the direction vector of ray
     *
     * @return vector of ray
     */
    public Vector getDir() {
        return direction;
    }

}

