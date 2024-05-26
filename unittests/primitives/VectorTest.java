package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for primitives.Vector class
 * @author David forst and moshe goodman
 */
class VectorTest {

    //constructing vectors to use in the next tests
    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);

    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // test if (1,2,3) + (0,3,-2) = (1,5,1)
        assertEquals(new Vector(1,5,1),v1.add(v3),"ERROR: Vector + Vector does not work correctly");
        // =============== Boundary Values Tests ==================
        // test vector +- itself
        assertThrows(IllegalArgumentException.class,
                ()->v1.add(new Vector(-1,-2,-3)),
                "ERROR: Vector + -itself does not throw an exception");

    }

    /**
     * Test method for {@link primitives.Vector#subtract(primitives.Vector)}.
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        // test if (1,2,3) - (0,3,-2) = (1,-1,5)
        assertEquals(new Vector(1,-1,5),v1.subtract(v3),"ERROR: Vector - Vector does not work correctly");
        // =============== Boundary Values Tests ==================
        // test vector - itself
        assertThrows(IllegalArgumentException.class,
                ()->v1.subtract(v1),
                "ERROR: Vector - itself does not throw an exception");

    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     */
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        // test if the length squared of (1,2,3) = 14
        assertEquals(14,v1.lengthSquared(),0.00001,"ERROR: lengthSquared() wrong value");
    }
    /**
     * Test method for {@link primitives.Vector#length()}.
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        // test if the length of (-2,-4,-6) = square root of 56
        assertEquals(Math.sqrt(56),v2.length(),0.00001,"ERROR: length() wrong value");

    }
    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    void testScale() {
        // ============ Equivalence Partitions Tests ==============
        // test if 9*(1,2,3) = (9,18,27)
        assertEquals(new Vector(9,18,27),
                v1.scale(9),
                "ERROR: positive scalar gives a wrong vector");
        // test if (-2)*(1,2,3) = (-2,-4,-6)
        assertEquals(new Vector(-2,-4,-6),
                v1.scale(-2),
                "ERROR: negative scalar gives a wrong vector");
        // =============== Boundary Values Tests ==================
        // test if 0*vector throws an exception
        assertThrows(IllegalArgumentException.class,
                ()->v1.scale(0),
                "ERROR: 0*Vector does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    void testDotProduct() {
        // ============ Equivalence Partitions Tests ==============
        // test if (-2,-4,-6)(1,2,3) = -28
        assertEquals(-28,
                v2.dotProduct(v1),
                0.000001,
                "ERROR: dotProduct() wrong value");
        // =============== Boundary Values Tests ==================
        // test if (1,2,3)(0,3,-2) = 0
        assertEquals(0,
                v1.dotProduct(v3),
                0.000001,
                "ERROR: dotProduct() for orthogonal vectors is not zero");
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    void testCrossProduct() {
        // ============ Equivalence Partitions Tests ==============


         //asserts that a cross product between parallel between vector results in an error
                 assertThrows(IllegalArgumentException.class,
                ()-> v1.crossProduct(v2),
                "ERROR: crossProduct() for parallel vectors does not throw an exception");



        //asserts that the length of the result vector is the multiplication of the two lengths of the original vectors
        Vector vr = v1.crossProduct(v3);
        assertEquals(vr.length(), v1.length() * v3.length(),
                0.0001,"ERROR: crossProduct() wrong result length");
        //checks whether the result vector is orthogonal to the two original vectors
        assertEquals(0,vr.dotProduct(v1),0.0001,
                "ERROR: crossProduct() result is not orthogonal to its operands");
        assertEquals(0,vr.dotProduct(v3),0.0001,
                "ERROR: crossProduct() result is not orthogonal to its operands");




    }



    /**
     * Test method for {@link primitives.Vector#normalize()}.
     */

    @Test
    void testNormalize() {
        // ============ Equivalence Partitions Tests ==============
        //validates that the resulting vector length is one
        assertEquals(1,v1.normalize().length(),0.0001,
                "ERROR: the normalized vector is not a unit vector");
        //validates that the resulting vector is parallel to the original
        assertThrows(IllegalArgumentException.class, ()->v1.crossProduct(v1.normalize()),
                "ERROR: the normalized vector is not parallel to the original one");
        assertTrue(v1.dotProduct(v1.normalize())>=0,
                "ERROR: the normalized vector is opposite to the original one");
        // =============== Boundary Values Tests ==================

    }
}