package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {
    primitives.Point p1 = new primitives.Point(3,0,0);


    /**
     *
     */
    @Test
    void testGetNormal() {
        Sphere s1 = new Sphere(2.0,new primitives.Point(0,0,0));
        assertThrows(IllegalArgumentException.class,()->s1.getNormal(p1),
                "does not throw exception when point is not on the sphere ");
    }
}