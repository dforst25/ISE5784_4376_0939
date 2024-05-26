package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {

    @Test
    void testGetNormal() {

        Cylinder c1 =new Cylinder(5,new Ray(new Point(1,1,1),new Vector(0,6, 8)),40);
        Point pOriginalPoint = new Point(1,1,1);
        Point pOnTheAxis = new Point(2,7,9);
        Point pOnTheAxisUpperSurface = new Point(5,25,33);
        Point pAtTheCornerBase = new Point(1,-3,4);
        Point pAtTheCornerUpperBase = new Point(5,21,36);
        Point pOnTheSurface = new Point(2,3,12);
        Point pOfutsideTheCylinder = new Point(1,10,-5);
        Point pOnTheBaseSurface = new Point(1,10,-5);
        Point pOnTheUpperSurface = new Point(1,10,-5);

        @Test
        void testGetNormal() {
            //validates that when point is off the plane program will throw an exception
            assertThrows(IllegalArgumentException.class,()->t1.getNormal(pOriginalPoint),
                    "does not throw exception when point is not on the tube");
            assertThrows(IllegalArgumentException.class,()->t1.getNormal(pOnTheAxis),
                    "does not throw exception when point is not on the tube");
            assertThrows(IllegalArgumentException.class,()->t1.getNormal(pOffTheSurface),
                    "does not throw exception when point is not on the tube");


            //validates that the normal function returns a unit vector
            assertEquals(1,t1.getNormal(pOnTheSurface).length(),0.0001,
                    "the normal of a sphere does not return a unit vector");
            assertEquals(1,t1.getNormal(pAtTheCornerBase).length(),0.0001,
                    "the normal of a sphere does not return a unit vector");


        }
}