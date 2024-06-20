package primitives;
import java.util.List;

import static primitives.Util.isZero;

public class Ray {
    private final Point head;
    private final Vector direction;

    /**
     * Constructor
     * @param head the head of the ray
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
        return (o instanceof Ray ray) && this.head.equals( ray.head) && this.direction.equals( ray.direction);
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
    public Point getPoint( double t)
    {
        if(isZero(t))
            return head;
        return head.add(direction.scale(t));
    }

    /**
     * Finds the closest point in a list of points to a given point.
     *
     * @param points A list of points to search from
     * @return The closest point to the given point
     *         If the list is empty, returns null.
     */
    public Point findClosestPoint(List<Point> points){

        if (isZero(points.size()))
            return null;

        // Initialize the minimum distance to be the distance from head to the first point in the list
        double minDistance = head.distance(points.getFirst());

        // Initialize the closest point to be the first point in the list
        Point closestPoint = points.getFirst();

        // Iterate over the rest of the points and update the minimum distance and closest point as necessary
        for (int index = 1; index < points.size(); index++) {
            double distance = head.distance(points.get(index));
            if (distance < minDistance) {
                minDistance = distance;
                closestPoint = points.get(index);
            }
        }
        // Iterate over the rest of the points and update the minimum distance and closest point as necessary
        for (Point point : points){
            double distance = head.distance(point);
            if (distance < minDistance) {
                minDistance = distance;
                closestPoint = point;
            }
        }

        // return the closest point
        return closestPoint;

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

