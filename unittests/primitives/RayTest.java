package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {
    Ray r = new Ray(new Point(4,6,3),new Vector(1,2,2));
    @Test
    void testGetPoint() {
        // ============ Equivalence Partitions Tests ==============
        //TC01:positive parameter
        assertEquals(new Point(8,14,11),r.getPoint(12),"does not return the correct point");
        //TC02:negative parameter
        assertEquals(new Point(6,10,7),r.getPoint(6),"does not return the correct point");

        // =============== Boundary Values Tests ==================
        //TC11:parameter is 0
        assertEquals(new Point(4,6,3),r.getPoint(0),"does not return the correct point");

    }

    /**
     * Test method for {@link Ray#findClosestPoint(List)}
     */
    @Test
    void findClosestPoint() {
        // creating a new ray to check closest point for
        Ray ray = new Ray(new Point(1,0,0),new Vector(0,5,1));

        // ============ Equivalence Partitions Tests ==============
        //TC01- EP test, the closest point is in the middle of the list

        // list of points to check the closest one to the ray
        List<Point> middlePointList = List.of(new Point(1,0,8),new Point(1,0,2),new Point(1,0,4));

        // making sure that the received point from the function is as expected
        assertEquals(new Point(1,0,2),ray.findClosestPoint(middlePointList),"EP test, the closest point is in the middle of the list");


        // =============== Boundary Values Tests ==================
        //TC02-empty list, is supposed to return null
        assertNull(ray.findClosestPoint(List.of()),"empty list, is supposed to return null");


        //TC03- the closest point is in the beginning of the list

        // list of points to check the closest one to the ray
        List<Point> beginPointList = List.of(new Point(1,0,2),new Point(1,0,8),new Point(1,0,4));

        // making sure that the received point from the function is as expected
        assertEquals(new Point(1,0,2),ray.findClosestPoint(beginPointList),"the closest point is in the beginning of the list");


        //TC04- the closest point is in the end of the list

        // list of points to check the closest one to the ray
        List<Point> endPointList = List.of(new Point(1,0,8),new Point(1,0,4),new Point(1,0,2));

        // making sure that the received point from the function is as expected
        assertEquals(new Point(1,0,2),ray.findClosestPoint(endPointList),"the closest point is in the end of the list");
    }
}