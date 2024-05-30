package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {
    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;

    /** Test method for {@link geometries.Triangle#Triangle(primitives.Point,primitives.Point,primitives.Point)}. */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        assertDoesNotThrow(() -> new Triangle(new Point(0, 0, 1),
                        new Point(1, 0, 0),
                        new Point(0, 1, 0)),
                "Failed constructing a correct Triangle");
        // =============== Boundary Values Tests ==================

        // TC2: All 3 points are on a single line
        assertThrows(IllegalArgumentException.class, //
                () -> new Triangle(new Point(0, 0, 1), new Point(0, 0, 2),
                        new Point(0, 0, 3)),
                "Constructed a Triangle with wrong order of vertices");

        // TC3: Last point = first point
        assertThrows(IllegalArgumentException.class,
                () -> new Triangle(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 0, 1)),
                "Constructed a triangle with vertex on a side");

        // TC4: Co-located points
        assertThrows(IllegalArgumentException.class, //
                () -> new Triangle(new Point(0, 0, 1), new Point(0, 1, 0), new Point(0, 1, 0)),
                "Constructed a triangle with vertex on a side");

    }

    /** Test method for {@link geometries.Triangle#getNormal(primitives.Point)}. */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here - using a triangle
        Point[] pts =
                { new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0)};
        Triangle tr = new Triangle(pts[0],pts[1],pts[2]);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> tr.getNormal(new Point(0, 0, 1)), "getNormal doesn't work");
        // generate the test result
        Vector result = tr.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), DELTA, "Triangle's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        for (int i = 0; i < 2; ++i)
            assertEquals(0d, result.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : i - 1])), DELTA,
                    "Triangle's normal is not orthogonal to one of the edges");
    }

    /** Test method for {@link geometries.Triangle#findIntersections(primitives.Ray)}. */
    @Test
    public void testFindIntersections() {
    }
}