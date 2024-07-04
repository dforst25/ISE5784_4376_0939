package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {
    Point p1 = new Point(1, 2, 3);
    Point p2 = new Point(2, 4, 6);
    Point p3 = new Point(2, 4, 5);
    Point p1PlusP2 = new Point(3, 6, 9);

    @Test
    void testConstructor() {

        //validates that when two points are the same it will throw an exception
        assertThrows(IllegalArgumentException.class, () -> new Plane(p1, p1, p2),
                "does not throw an exception when two points are the same");

        //validated that it throws an exception when all three points are on a single line
        assertThrows(IllegalArgumentException.class, () -> new Plane(p1, p2, p1PlusP2),
                "does not throw an exception when all three points are on a single line");

    }

    @Test
    void testGetNormal() {
        Plane pl1 = new Plane(p1, p2, p3);
        Point pointOffThePlane = p1.add(pl1.getNormal());

        //validates that the normal is a unit vector
        assertEquals(1, pl1.getNormal().length(), 0.00001, "Normal is not a unit vector");

        //  validates that the normal is orthogonal to the vectors contained on the plane
        assertEquals(0, (p1.subtract(p2)).dotProduct(pl1.getNormal()), 0.0001,
                "Normal is not orthogonal to plane");
        assertEquals(0, (p1.subtract(p3)).dotProduct(pl1.getNormal()), 0.00001,
                "Normal is not orthogonal to plane");


        //validates that when point is off the plane program will throw an exception
        assertThrows(IllegalArgumentException.class, () -> pl1.getNormal(pointOffThePlane),
                "does not throw exception when point is not on the plane");


    }

    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(1, 1, 0);
        Point p3 = new Point(1, 0, 1);
        Plane pl = new Plane(p1, p2, p3);


        Ray rNoIntersection = new Ray(new Point(2, 1, 1), new Vector(1, 2, 3));
        Ray rIntersection = new Ray(new Point(-1, 1, 1), new Vector(1, 2, 3));
        var expIntersection = List.of(new Point(1, 5, 7));

        Ray rParallelNotContained = new Ray(new Point(4, 2, 3), new Vector(0, 3, 4));
        Ray rParallelContained = new Ray(new Point(1, 1, 1), new Vector(0, 5, -8));

        Ray rOrthogonalNoIntersection = new Ray(new Point(1.5, 5, 13), new Vector(5, 0, 0));
        Ray rOrthogonalIntersectionAtHead = new Ray(new Point(1, 1, 1), new Vector(-8, 0, 0));
        Ray rOrthogonalIntersection = new Ray(new Point(-1, 10, -51), new Vector(1, 0, 0));
        var expOrthogonalIntersection = List.of(new Point(1, 10, -51));

        Ray rHeadIntersection = new Ray(new Point(1, -43, 1.65), new Vector(1, 54, -5));
        Ray rHeadIntersectionOnOriginalPoint = new Ray(new Point(1, 2, 3), new Vector(4, 18, 22));


        // ============ Equivalence Partitions Tests ==============

        //expects to return null when there is no intersection between the ray and the plane
        assertNull(pl.findIntersections(rNoIntersection), "does not return null when there is no intersection");
        //checks whether it returns the correct intersection between the ray and the plane
        assertEquals(expIntersection, pl.findIntersections(rIntersection),
                "does not return the correct point for intersection");

        // =============== Boundary Values Tests ==================

        //expects to return null when there is no intersection between the ray and the plane
        assertNull(pl.findIntersections(rParallelNotContained),
                "does not return null when the ray is parallel to plane");
        //expects to return null when there is no intersection between the ray and the plane
        assertNull(pl.findIntersections(rOrthogonalNoIntersection),
                "does not return null when orthogonal and starts after plane ");
        //expects to return null when there is no intersection between the ray and the plane
        assertNull(pl.findIntersections(rOrthogonalIntersectionAtHead),
                "does not return null when orthogonal and the rays head is on the plane");
        //expects to return null when there is no intersection between the ray and the plane
        assertNull(pl.findIntersections(rHeadIntersection),
                "does not return null when rays head starts on the plane");
        //expects to return null when there is no intersection between the ray and the plane
        assertNull(pl.findIntersections(rHeadIntersectionOnOriginalPoint),
                "does not return null when the head of the ray is the same point as the plane q point");
        //checks whether it returns the correct intersection between the ray and the plane
        assertEquals(expOrthogonalIntersection, pl.findIntersections(rOrthogonalIntersection),
                "does not return the correct point for intersection when orthogonal and starts before plane");


    }

}