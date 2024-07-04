package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {

    @Test
    void testGetNormal() {

        Cylinder c1 = new Cylinder(10, new Ray(new Point(1, 1, 1), new Vector(0, 6, 8)), 40);

// ============ Equivalence Partitions Tests ==============
        Point pOnTheSurface = new Point(1, 15, 3);
        Point pOnTheBaseSurface = new Point(1, 5, -2);
        Point pOnTheUpperSurface = new Point(1, 33, 27);

        //points for bva
        Point pOriginalPoint = new Point(1, 1, 1);
        Point pOnTheAxis = new Point(1, 7, 9);
        Point pOnTheAxisUpperSurface = new Point(1, 25, 33);
        Point pAtTheCornerBase = new Point(1, -7, 7);
        Point pAtTheCornerUpperBase = new Point(1, 17, 39);

        //points not on the cylinder
        Point pOutsideTheCylinder = new Point(1, 23, -3);
        Point pInsideTheCylinder = new Point(1, 11, 5);


        //validates that when point is off the plane program will throw an exception
        assertThrows(IllegalArgumentException.class, () -> c1.getNormal(pOnTheAxis),
                "does not throw exception when point is not on the tube");
        assertThrows(IllegalArgumentException.class, () -> c1.getNormal(pOutsideTheCylinder),
                "does not throw exception when point is not on the tube");
        assertThrows(IllegalArgumentException.class, () -> c1.getNormal(pInsideTheCylinder),
                "does not throw exception when point is not on the tube");


        //validates that the normal function returns a unit vector
        assertEquals(1, c1.getNormal(pOnTheSurface).length(), 0.0001,
                "the normal of a sphere does not return a unit vector");

        assertEquals(1, c1.getNormal(pOnTheBaseSurface).length(), 0.0001,
                "the normal of a sphere does not return a unit vector");
        assertEquals(1, c1.getNormal(pOnTheUpperSurface).length(), 0.0001,
                "the normal of a sphere does not return a unit vector");
        assertEquals(1, c1.getNormal(pOriginalPoint).length(), 0.0001,
                "the normal of a sphere does not return a unit vector");
        assertEquals(1, c1.getNormal(pOnTheAxisUpperSurface).length(), 0.0001,
                "the normal of a sphere does not return a unit vector");
        assertEquals(1, c1.getNormal(pAtTheCornerBase).length(), 0.0001,
                "the normal of a sphere does not return a unit vector");
        assertEquals(1, c1.getNormal(pAtTheCornerUpperBase).length(), 0.0001,
                "the normal of a sphere does not return a unit vector");

    }


    /**
     * Test method for {@link geometries.Cylinder#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {

    }
}
