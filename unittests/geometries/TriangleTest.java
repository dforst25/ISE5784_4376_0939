package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

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
        //on the plane z=3
        Triangle tr =new Triangle(new Point(1,2,3),new Point(4,0,3),new Point(-3,1,3));
        Vector v011 = new Vector(0,1,1);
        Vector vN011 = new Vector(0,-1,-1);
        Vector prll = new Vector(0,0,1);
        Point goesThroughInterior = new Point(0,1,3);
        Ray rGoesThroughInterior = new Ray(goesThroughInterior.add(vN011),v011);

        Point goesThroughExteriorAcrossSide = new Point(4,1,3);
        Ray rGoesThroughExteriorAcrossSide = new Ray(goesThroughExteriorAcrossSide.add(vN011),v011);

        Point goesThroughExteriorAcrossVertex = new Point(-6,1,3);
        Ray rGoesThroughExteriorAcrossVertex = new Ray(goesThroughExteriorAcrossVertex.add(vN011),v011);


        Point goesThroughSide = new Point(2.5,1,3);
        Ray rGoesThroughSide = new Ray(goesThroughSide.add(vN011),v011);

        Point goesThroughVertex = new Point(1,2,3);
        Ray rGoesThroughVertex = new Ray(goesThroughVertex.add(vN011),v011);

        Point goesThroughContinuationOfSide = new Point(7,-2,3);
        Ray rGoesThroughContinuationOfSide = new Ray(goesThroughContinuationOfSide.add(vN011),v011);

        Ray rNeverIntersectsPlane = new Ray(new Point(34,6,2), prll);

        // ============ Equivalence Partitions Tests ==============
        var exp = List.of(goesThroughInterior);
        // TC01: intersects in the middle of the triangle
        assertEquals(1,tr.findIntersections(rGoesThroughInterior).size(),
                "the function does not return 1 intersection point");
        assertEquals(exp,tr.findIntersections(rGoesThroughInterior),
                "returns wrong point when the ray intersects the triangle");

        // TC02: crosses the plane across a side of a triangle
        assertNull(tr.findIntersections(rGoesThroughExteriorAcrossSide),
                "does not return null when the intersection (with the plane) is outside across the side");
        // TC03: crosses the plane across a vertex of a triangle
        assertNull(tr.findIntersections(rGoesThroughExteriorAcrossVertex),
                "does not return null when the intersection (with the plane) is outside across the vertex");

                // =============== Boundary Values Tests ==================
        // TC11:  crosses the plane on a side of a triangle
        assertNull(tr.findIntersections(rGoesThroughSide),
                "does not return null when the intersection (with the plane) is on the side");
        // TC12:  crosses the plane on a vertex of a triangle
        assertNull(tr.findIntersections(rGoesThroughVertex),
                "does not return null when the intersection (with the plane) is on the vertex");
        // TC13:  crosses the plane on the continuation of a side of a triangle
        assertNull(tr.findIntersections(rGoesThroughContinuationOfSide),
                "does not return null when intersection (with the plane) is  on the continuation of side");
        //TC14: when the ray does not intersect the plane at all
        assertNull(tr.findIntersections(rNeverIntersectsPlane),
                "does not return null when intersection (with the plane) is  on the continuation of side");

    }
}