package geometries;

import org.junit.jupiter.api.Test;

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



}