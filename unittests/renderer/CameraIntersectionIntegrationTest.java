package renderer;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

class CameraIntersectionIntegrationTest {

    @Test
    void testConstructRay() {
        Plane plane1 = new Plane(new Point(-1,-2,-3), new Vector(0,0,1));
        Plane plane2 = new Plane(new Point(-1,-2,-3), new Vector(1,1,11));
        Plane plane3 = new Plane(new Point(0,0,-5), new Vector(0,10,-1));
        Sphere sphere1 = new Sphere(1,new Point(0,0,-3));
        Sphere sphere2 = new Sphere(2.5,new Point(0,0,-2.5));
        Sphere sphere3 = new Sphere(2,new Point(0,0,-2));
        Sphere sphere4 = new Sphere(4,new Point(0,0,-2));
        Sphere sphere5 = new Sphere(0.5,new Point(0,0,1));

        Triangle triangle1 = new Triangle(new Point(0,1,-2),
                new Point(1,-1,-2),
                new Point(-1,-1,-2));
        Triangle triangle2 = new Triangle(new Point(0,20,-2),
                new Point(1,-1,-2),
                new Point(-1,-1,-2));
        Camera camera1 =new Camera.Builder().setLocation(new Point(0,0,0))
                .setVpSize(3,3)
                .setDirection(new Vector(0,0,-1), new Vector(0,1,0))
                .setVpDistance(1).build();
        Camera camera2 =new Camera.Builder().setLocation(new Point(0,0,0.5))
                .setVpSize(3,3)
                .setDirection(new Vector(0,0,-1), new Vector(0,1,0))
                .setVpDistance(1).build();
        Camera camera3 =new Camera.Builder().setLocation(new Point(0,0,0))
                .setVpSize(3,3)
                .setDirection(new Vector(0,0,-1), new Vector(0,1,0))
                .setVpDistance(1).build();

        //3x3 plane parallel to VP exp 9 intersections
        int sizePlaneIntersections = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if(plane1.findIntersections(camera2.constructRay(3,3,i,j)) != null)
                    sizePlaneIntersections += plane1.findIntersections(camera2.constructRay(3,3,i,j)).size();
        assertEquals(9,sizePlaneIntersections,
                "when the ray from camera intersects a plane the function does not find the intersection");

        //3x3 plane in front of camera exp 9 intersections
        sizePlaneIntersections = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if(plane2.findIntersections(camera2.constructRay(3,3,i,j)) != null)
                    sizePlaneIntersections += plane2.findIntersections(camera2.constructRay(3,3,i,j)).size();
        assertEquals(9,sizePlaneIntersections,
                "when the ray from camera intersects a plane the function does not find the intersection");

        //3x3 plane in front of camera exp 6 intersections
        sizePlaneIntersections = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if(plane3.findIntersections(camera2.constructRay(3,3,i,j)) != null)
                    sizePlaneIntersections += plane3.findIntersections(camera2.constructRay(3,3,i,j)).size();
        assertEquals(6,sizePlaneIntersections,
                "when the ray from camera intersects a plane the function does not find the intersection");



        //3x3 sphere is in front of VP takes the spot of only one pixel exp 2 intersections
        int sizeSphereIntersections = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if(sphere1.findIntersections(camera1.constructRay(3,3,i,j)) != null)
                    sizeSphereIntersections += sphere1.findIntersections(camera1.constructRay(3,3,i,j)).size();
        assertEquals(2,sizeSphereIntersections,
                "when the ray from camera intersects a plane the function does not find the intersection");

        //3x3 sphere is in front of VP covers the entire VP exp 19 intersections
        sizeSphereIntersections = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if(sphere2.findIntersections(camera2.constructRay(3,3,i,j)) != null)
                    sizeSphereIntersections += sphere2.findIntersections(camera2.constructRay(3,3,i,j)).size();
        assertEquals(18,sizeSphereIntersections,
                "when the ray from camera intersects a plane the function does not find the intersection");

        //3x3 sphere exp 10 intersection (probably 8 of the "side and corner" rays are tangent to the sphere)
        sizeSphereIntersections = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if(sphere3.findIntersections(camera2.constructRay(3,3,i,j)) != null)
                    sizeSphereIntersections += sphere3.findIntersections(camera2.constructRay(3,3,i,j)).size();
        assertEquals(10,sizeSphereIntersections,
                "when the ray from camera intersects a plane the function does not find the intersection");

        //3x3 camera location is inside the sphere exp 9 intersections
        sizeSphereIntersections = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if(sphere4.findIntersections(camera2.constructRay(3,3,i,j)) != null)
                    sizeSphereIntersections += sphere4.findIntersections(camera2.constructRay(3,3,i,j)).size();
        assertEquals(9,sizeSphereIntersections,
                "when the ray from camera intersects a plane the function does not find the intersection");

        //3x3 sphere is behind the camera no intersections expected
        sizeSphereIntersections = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if(sphere5.findIntersections(camera3.constructRay(3,3,i,j)) != null)
                    sizeSphereIntersections += sphere5.findIntersections(camera3.constructRay(3,3,i,j)).size();
        assertEquals(0,sizeSphereIntersections,
                "when the ray from camera intersects a plane the function does not find the intersection");

        //triangle is only over the center pixel exp 1 intersection
        int sizeTriangleIntersections = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if(triangle1.findIntersections(camera2.constructRay(3,3,i,j)) != null)
                    sizeTriangleIntersections += triangle1.findIntersections(camera2.constructRay(3,3,i,j)).size();
        assertEquals(1,sizeTriangleIntersections,
                "when the ray from camera intersects a plane the function does not find the intersection");

        //triangle covers 2 pixels exp 2 intersections
        sizeTriangleIntersections = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if(triangle2.findIntersections(camera2.constructRay(3,3,i,j)) != null)
                    sizeTriangleIntersections += triangle2.findIntersections(camera2.constructRay(3,3,i,j)).size();
        assertEquals(2,sizeTriangleIntersections,
                "when the ray from camera intersects a plane the function does not find the intersection");

    }
}