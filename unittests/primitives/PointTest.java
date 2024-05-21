package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PointTest {
    Point  p1         = new Point(1, 2, 3);
    Point  p2         = new Point(2, 4, 6);
    Point  p3         = new Point(1,1,2);
    Vector v1         = new Vector(1, 2, 3);
    Vector v1opposite = new Vector(-1, -2, -3);
    /**
     * Test method for {@link primitives.Point#add(primitives.Vector)}.
     */
    @Test
    void testAdd() {

        // ============ Equivalence Partitions Tests ==============
        /**
         * tests a regular addition between two points
         */
        assertEquals(new Point(2,4,6), p1.add(v1),
                "ERROR: (point + vector) does not work correctly");


        // =============== Boundary Values Tests ==================
        /**
         * expected to return 0 when a point is added to its "opposite" vector
         */
        assertEquals(new Point(0,0,0),
                 p1.add(v1opposite),
                "ERROR: (point + vector) does not work correctly when the result its zero");
    }
    /**
     * Test method for {@link primitives.Point#subtract(primitives.Point)}.
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        /**
         * tests a regular subtraction between two points
         */
        assertEquals(new Vector(-1,-2,-3),
                p1.subtract(p2),
                "ERROR: (point2 - point1) does not work correctly");

        // =============== Boundary Values Tests ==================
        /**
         * tests whenever it subtracts itself expected to throw an exception
         */
        assertThrows(IllegalArgumentException.class,
                ()-> p1.subtract(p1),
                "ERROR: (point - itself) does not throw an exception");
    }
    /**
     * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
     */
    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        // Tests the distance squared between 2 points (for both "sides" )
        assertEquals(14,
                p1.distanceSquared(p2),0.00001,
                "ERROR: squared distance between points is wrong");
        assertEquals(14,
                p2.distanceSquared(p1),0.00001,
                "ERROR: squared distance between points is wrong");
        // =============== Boundary Values Tests ==================
        //expected to return 0 for the distance of a point to itself
        assertEquals(0,
                p1.distanceSquared(p1),
                "ERROR: point squared distance to itself is not zero");
    }
    /**
     * Test method for {@link primitives.Point#distance(primitives.Point)}.
     */
    @Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        // Tests the distance between 2 points (for both "sides" )
        assertEquals(Math.sqrt(2),
                p1.distance(p3),0.00001,
                "ERROR: distance between points is wrong");
        assertEquals(Math.sqrt(2),
                p3.distance(p1),0.00001,
                "ERROR: distance between points is wrong");
        // =============== Boundary Values Tests ==================
        //expected to return 0 for the distance of a point to itself
        assertEquals(0,
                p1.distance(p1),
                "ERROR: point distance to itself is not zero");
    }
}