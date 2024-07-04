package geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.Polygon;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Testing Polygons
 *
 * @author Dan
 */
public class PolygonTests {
    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;

    /**
     * Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        assertDoesNotThrow(() -> new Polygon(new Point(0, 0, 1),
                        new Point(1, 0, 0),
                        new Point(0, 1, 0),
                        new Point(-1, 1, 1)),
                "Failed constructing a correct polygon");

        // TC02: Wrong vertices order
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0),
                        new Point(1, 0, 0), new Point(-1, 1, 1)), //
                "Constructed a polygon with wrong order of vertices");

        // TC03: Not in the same plane
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
                "Constructed a polygon with vertices that are not in the same plane");

        // TC04: Concave quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                        new Point(0.5, 0.25, 0.5)), //
                "Constructed a concave polygon");

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                        new Point(0, 0.5, 0.5)),
                "Constructed a polygon with vertex on a side");

        // TC11: Last point = first point
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
                "Constructed a polygon with vertice on a side");

        // TC12: Co-located points
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
                "Constructed a polygon with vertice on a side");

    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here - using a quad
        Point[] pts =
                {new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1)};
        Polygon pol = new Polygon(pts);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> pol.getNormal(new Point(0, 0, 1)), "");
        // generate the test result
        Vector result = pol.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), DELTA, "Polygon's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        for (int i = 0; i < 3; ++i)
            assertEquals(0d, result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1])), DELTA,
                    "Polygon's normal is not orthogonal to one of the edges");
    }


    /**
     * Test method for {@link geometries.Polygon#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        //on the plane y=4
        Polygon pentagon = new Polygon(new Point(1, 4, 3),
                new Point(4, 4, 3),
                new Point(2, 4, -2),
                new Point(-1, 4, -1),
                new Point(-1, 4, 2));
        Vector v011 = new Vector(0, 1, 1);
        Vector vN011 = new Vector(0, -1, -1);
        Vector prll = new Vector(0, 0, 1);
        Point goesThroughInterior = new Point(1, 4, 1);
        Ray rGoesThroughInterior = new Ray(goesThroughInterior.add(vN011), v011);

        Point goesThroughExteriorAcrossSide = new Point(4, 4, 6);
        Ray rGoesThroughExteriorAcrossSide = new Ray(goesThroughExteriorAcrossSide.add(vN011), v011);

        Point goesThroughExteriorAcrossVertex = new Point(1, 4, 6);
        Ray rGoesThroughExteriorAcrossVertex = new Ray(goesThroughExteriorAcrossVertex.add(vN011), v011);


        Point goesThroughSide = new Point(3, 4, 0.5);
        Ray rGoesThroughSide = new Ray(goesThroughSide.add(vN011), v011);

        Point goesThroughVertex = new Point(2, 4, -2);
        Ray rGoesThroughVertex = new Ray(goesThroughVertex.add(vN011), v011);

        Point goesThroughContinuationOfSide = new Point(6, 4, 8);
        Ray rGoesThroughContinuationOfSide = new Ray(goesThroughContinuationOfSide.add(vN011), v011);

        Ray rNeverIntersectsPlane = new Ray(new Point(34, 6, 2), prll);

        // ============ Equivalence Partitions Tests ==============
        var exp = List.of(goesThroughInterior);
        // TC01: intersects in the middle of the triangle
        assertEquals(1, pentagon.findIntersections(rGoesThroughInterior).size(),
                "the function does not return 1 intersection point");
        assertEquals(exp, pentagon.findIntersections(rGoesThroughInterior),
                "returns wrong point when the ray intersects the triangle");

        // TC02: crosses the plane across a side of a triangle
        assertNull(pentagon.findIntersections(rGoesThroughExteriorAcrossSide),
                "does not return null when the intersection (with the plane) is outside across the side");
        // TC03: crosses the plane across a vertex of a triangle
        assertNull(pentagon.findIntersections(rGoesThroughExteriorAcrossVertex),
                "does not return null when the intersection (with the plane) is outside across the vertex");

        // =============== Boundary Values Tests ==================
        // TC11:  crosses the plane on a side of a triangle
        assertNull(pentagon.findIntersections(rGoesThroughSide),
                "does not return null when the intersection (with the plane) is on the side");
        // TC12:  crosses the plane on a vertex of a triangle
        assertNull(pentagon.findIntersections(rGoesThroughVertex),
                "does not return null when the intersection (with the plane) is on the vertex");
        // TC13:  crosses the plane on the continuation of a side of a triangle
        assertNull(pentagon.findIntersections(rGoesThroughContinuationOfSide),
                "does not return null when intersection (with the plane) is  on the continuation of side");
        //TC14: when the ray does not intersect the plane at all
        assertNull(pentagon.findIntersections(rNeverIntersectsPlane),
                "does not return null when there is no intersection with the plane");

    }
}
