package primitives;

import org.junit.jupiter.api.Test;

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
}