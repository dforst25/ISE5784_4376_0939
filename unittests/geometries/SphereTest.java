package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

class SphereTest {
    primitives.Point p1 = new primitives.Point(3,0,0);
    primitives.Point p2 = new primitives.Point(2,0,0);
    Sphere s1 = new Sphere(2.0,new primitives.Point(0,0,0));


    /**
     *
     */
    @Test
    void testGetNormal() {

        //validates that when point is off the plane program will throw an exception
        assertThrows(IllegalArgumentException.class,()->s1.getNormal(p1),
                "does not throw exception when point is not on the sphere ");


        //validates that the normal function returns a unit vector
        assertEquals(1,s1.getNormal(p2).length(),0.0001,
                "the normal of a sphere does not return a unit vector");




    }


    private final Point p001 = new Point(0, 0, 1);
    private final Point p100 = new Point(1, 0, 0);
    private final Vector v001 = new Vector(0, 0, 1);
    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere( 1d,p100);
        final Point pIntersect1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        final Point pIntersect2 = new Point(1.53484692283495, 0.844948974278318, 0);
        final var exp = List.of(pIntersect1, pIntersect2);
        final Vector v310 = new Vector(3, 1, 0);
        final Vector v110 = new Vector(1, 1, 0);
        final Point p01 = new Point(-1, 0, 0);

        Point pInsideTheSphere = new Point(1.5,0.5,0);
        Point pOutsideTheSphere = new Point(4,5,0);
        Point pOnSphereInwards = new Point(2,0,0);
        Point pOnSphereOutwards = new Point(2,0,0);
        Point pTangentIntersects = new Point(2,-1,3);
        Point pTangentNoIntersects = new Point(-1,0,0);
        Point pTangentHeadOnSphere = new Point(1,1,0);
        Point pOnCenter = new Point(1,0,0);
        Point pOutsideThroughCenter = new Point(1,-2,0);
        Point pOnSphereInwardsThroughCenter = new Point(1,-1,0);
        Point pInsideThroughCenter = new Point(0.5,-0.5,0);
        Point pOnSphereOutwardsThroughCenter = new Point(.29289321881,-0.70710678119,0);
        Point pOutsideTroughCenterNoIntersections = new Point(-1,0,-2);
        Point pRayOrthogonalToHead = new Point(3,0,0);


        Vector vInsideTheSphere = new Vector(1,-1,0);
        Vector vOutsideTheSphere = new Vector(0,8,15);
        Vector vOnSphereInwards = new Vector(-1, 1, 0);
        Vector vOnSphereOutwards = new Vector(1, -1, 0);
        Vector vTangentIntersects = new Vector(0, 1, -3);
        Vector vTangentNoIntersects = new Vector(-1, 0, 0);
        Vector vTangentHeadOnSphere = new Vector(-3, 0, 0);
        Vector vOnCenter = new Vector(1, 2, 2);
        Vector vOutsideThroughCenter = new Vector(0, 1, 0);
        Vector vOnSphereInwardsThroughCenter = new Vector(0, 1, 0);
        Vector vInsideThroughCenter = new Vector(1, 1, 0);
        Vector vOnSphereOutwardsThroughCenter = new Vector(1, -1, 0);
        Vector vOutsideTroughCenterNoIntersections = new Vector(-1, 0, -1);
        Vector vRayOrthogonalToHead = new Vector(0, 45, -15);


        var expInsideSphere = List.of(new Point(2,0,0));
        var expOnSphereInwards = List.of(new Point(1,1,0));
        var expOnCenter = List.of(new Point(4.0/3,2.0/3,2.0/3));
        var expOutsideThroughCenter = List.of(new Point(1,-1,0), new Point(1,1,0));
        var expOnSphereInwardsThroughCenter = List.of(new Point(1,1,0));
        var expInsideThroughCenter = List.of(new Point(1.7071067811865475,0.7071067811865475,0));







        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p01, v110)), "Ray's line out of sphere");
        // TC02: Ray starts before and crosses the sphere (2 points)
        final var result1 = sphere.findIntersections(new Ray(p01, v310))
                                    .stream().sorted(Comparator.comparingDouble(p -> p.distance(p01)))
                                    .toList();
        assertEquals(2, result1.size(), "Wrong number of points");
        assertEquals(exp, result1, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        final var result2 = sphere.findIntersections(new Ray(pInsideTheSphere, vInsideTheSphere))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(pInsideTheSphere)))
                .toList();
        assertEquals(1, result2.size(), "Wrong number of points");
        assertEquals(expInsideSphere, result2, "Ray crosses sphere");
        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(pOutsideTheSphere, vOutsideTheSphere)), "Ray's line out of sphere");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 point)
        final var result11 = sphere.findIntersections(new Ray(pOnSphereInwards, vOnSphereInwards))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(pOnSphereInwards)))
                .toList();
        assertEquals(1, result11.size(), "Wrong number of points");
        assertEquals(expOnSphereInwards, result11, "Ray crosses sphere");

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(pOnSphereOutwards, vOnSphereOutwards)), "Ray's line out of sphere");



        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        final var result13 = sphere.findIntersections(new Ray(pOutsideThroughCenter, vOutsideThroughCenter))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(pOutsideThroughCenter)))
                .toList();
        assertEquals(2, result13.size(), "Wrong number of points");
        assertEquals(expOutsideThroughCenter, result13, "Ray crosses sphere");

        // TC14: Ray starts at sphere and goes inside (1 point)
        final var result14 = sphere.findIntersections(new Ray(pOnSphereInwardsThroughCenter, vOnSphereInwardsThroughCenter))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(pOnSphereInwardsThroughCenter)))
                .toList();
        assertEquals(1, result14.size(), "Wrong number of points");
        assertEquals(expOnSphereInwardsThroughCenter, result14, "Ray crosses sphere");

        // TC15: Ray starts inside (1 point)
        final var result15 = sphere.findIntersections(new Ray(pInsideThroughCenter, vInsideThroughCenter))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(pInsideThroughCenter)))
                .toList();
        assertEquals(1, result15.size(), "Wrong number of points");
        assertEquals(expInsideThroughCenter, result15, "Ray crosses sphere");

        // TC16: Ray starts at the center (1 point)
        final var result16 = sphere.findIntersections(new Ray(pOnCenter, vOnCenter))
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(pOnCenter)))
                .toList();
        assertEquals(1, result16.size(), "Wrong number of points");
        assertEquals(expOnCenter, result16, "Ray crosses sphere");

        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(pOnSphereOutwardsThroughCenter, vOnSphereOutwardsThroughCenter)),
                "Ray's line out of sphere");

        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(pOutsideTroughCenterNoIntersections,
                vOutsideTroughCenterNoIntersections)), "Ray's line out of sphere");


        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findIntersections(new Ray(pTangentIntersects,
                vTangentIntersects)), "Ray's line out of sphere");
        // TC20: Ray starts at the tangent point
        assertNull(sphere.findIntersections(new Ray(pTangentHeadOnSphere,
                vTangentHeadOnSphere)), "Ray's line out of sphere");

        // TC21: Ray starts after the tangent point
        assertNull(sphere.findIntersections(new Ray(pTangentNoIntersects,
                vTangentNoIntersects)), "Ray's line out of sphere");

        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findIntersections(new Ray(pRayOrthogonalToHead,
                vRayOrthogonalToHead)), "Ray's line out of sphere");
    }
}