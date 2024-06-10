package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTests {
    Plane pl = new Plane(new Point(1,2,8),new Point(6,2,8), new Point(-2,5,8));
    Triangle tr1 = new Triangle(new Point(5,4,2), new Point(1,0,2),new Point(-2,5,2));
    Triangle tr2 = new Triangle(new Point(5,4,5), new Point(1,0,5),new Point(-2,5,5));
    Sphere sphere = new Sphere(5.0,new Point(1,0,0));
    Geometries emptyList = new Geometries();
    Geometries geometries = new Geometries(pl,tr1,tr2,sphere);
    Ray someIntersect = new Ray(new Point(3,0,0),new Vector(0,0,1));
    Ray intersectsAll = new Ray(new Point(1,2,0),new Vector(0,0,1));
    Ray noneIntersect = new Ray(new Point(20,0,0),new Vector(0,10,0));
    Ray intersectsOne = new Ray(new Point(-1,0,0),new Vector(0,1,0));


    @Test
    void testAdd() {
    }

    @Test
    void testFindIntersections() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: some of them intersect
        final var result01 = geometries.findIntersections(someIntersect)
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(someIntersect.getHead())))
                .toList();
        assertEquals(2, result01.size(), "Wrong number of points");

        // =============== Boundary Values Tests ==================
        //TC11: no intersections
        assertNull( geometries.findIntersections(noneIntersect), "Wrong number of points");

        //TC12: 1 intersection
        final var result12 = geometries.findIntersections(intersectsOne);

        assertEquals(1, result12.size(),
                "Wrong number of points there should only be one intersection");

        //TC13: all intersect
        final var result13 = geometries.findIntersections(intersectsAll)
                .stream().sorted(Comparator.comparingDouble(p -> p.distance(intersectsAll.getHead())))
                .toList();
        assertEquals(4, result13.size(), "Wrong number of points");

        //TC14: empty collection
        assertNull( emptyList.findIntersections(intersectsOne),
                "doesn't return null when the collection is empty");
        }
}