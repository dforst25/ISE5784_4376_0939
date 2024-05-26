package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {


    /** Test method for {@link geometries.Sphere#getNormal(primitives.Point)}. */
    @Test
    void testGetNormal() {
        Sphere s1 = new Sphere(2,new primitives.Point(0,0,0));

        //
        assertThrows(IllegalArgumentException.class,()->s1.getNormal(new Point(0,0,3)),
                "does not throw exception when point is not on the sphere ");

        //
        assertDoesNotThrow(()->s1.getNormal(new Point(0,0,2)),
                "getNormal doesn't work");
    }
}