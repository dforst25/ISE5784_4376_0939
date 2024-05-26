package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {
    Tube t1 = new Tube(5,new Ray(new Point(0,0,0),new Vector(1,4, 3)));
    Point pOriginalPoint = new Point(0,0,0);
    Point pOnTheAxis = new Point(2,8,6);
    Point pAtTheCornerBase = new Point(0,3,-4);
    Point pOnTheSurface = new Point(1,7,-1);
    Point pOutsideTheTube = new Point(1,10,-5);
    Point pInsideTheTube = new Point(1,5.5,1);
    Point pOnTheBaseSurface = new Point(0,1.5,-2);
    Point pSamePlaneAsBaseSurface = new Point(0,6,-8);

    @Test
    void testGetNormal() {
        //validates that when point is off the plane program will throw an exception

        assertThrows(IllegalArgumentException.class,()->t1.getNormal(pOnTheAxis),
                "does not throw exception when point is not on the tube");
        assertThrows(IllegalArgumentException.class,()->t1.getNormal(pOutsideTheTube),
                "does not throw exception when point is not on the tube");
        assertThrows(IllegalArgumentException.class,()->t1.getNormal(pInsideTheTube),
                "does not throw exception when point is not on the tube");
        assertThrows(IllegalArgumentException.class,()->t1.getNormal(pSamePlaneAsBaseSurface),
                "does not throw exception when point is not on the tube");

        //validates that the normal function returns a unit vector
        assertEquals(1,t1.getNormal(pOnTheSurface).length(),0.0001,
                "the normal of a sphere does not return a unit vector");
        assertEquals(1,t1.getNormal(pAtTheCornerBase).length(),0.0001,
                "the normal of a sphere does not return a unit vector");
        assertEquals(1,t1.getNormal(pOnTheBaseSurface).length(),0.0001,
                "the normal of a sphere does not return a unit vector");
        assertEquals(1,t1.getNormal(pOriginalPoint).length(),0.0001,
                "the normal of a sphere does not return a unit vector");






    }
}