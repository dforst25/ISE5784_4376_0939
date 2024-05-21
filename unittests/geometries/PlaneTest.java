package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {
    Point p1         = new Point(1, 2, 3);
    Point  p2         = new Point(2, 4, 6);
    Point  p3         = new Point(2, 4, 5);



    @Test
    void testGetNormal() {
        Plane pl1=new Plane(p1,p2,p3);
        Point pointOffThePlane = p1.add(pl1.getNormal());

        //validates that the normal is a unit vector
        assertEquals(1,pl1.getNormal().length(),"Normal is not a unit vector");

        //  validates that the normal is orthogonal to the vectors contained on the plane
        assertEquals(0,(p1.subtract(p2)).dotProduct(pl1.getNormal()),"Normal is not orthogonal to plane");
        assertEquals(0,(p1.subtract(p3)).dotProduct(pl1.getNormal()),"Normal is not orthogonal to plane");


        //validates that when point is off the plane program will throw an exception
        assertThrows(IllegalArgumentException.class, ()->pl1.getNormal(pointOffThePlane),
                "does not throw exception when point is not on the plane");



    }


}