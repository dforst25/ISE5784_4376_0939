package renderer;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

class CameraIntersectionIntegrationTest {

    @Test
    void testConstructRay() {
        Plane plane1 = new Plane(new Point(1,2,3), new Point(2,2,3),new Point(4,0,3));
        Plane plane2 = new Plane(new Point(-1,-2,-3), new Vector(0,0,1));
        Plane plane3 = new Plane(new Point(-1,-2,-3), new Vector(1,1,11));
        Plane plane4 = new Plane(new Point(-1,-2,-3), new Vector(1,1,11));
        Sphere sphere1 = new Sphere(1,new Point(0,0,-3));
        Sphere sphere2 = new Sphere(2.5,new Point(0,0,-2.5));
        Sphere sphere3 = new Sphere(2,new Point(0,0,-2));
        Sphere sphere4 = new Sphere(4,new Point(0,0,-2));
        Sphere sphere5 = new Sphere(0.5,new Point(0,0,1));

        Triangle triangle = new Triangle(new Point(-15,2,3),
                                        new Point(2,2,40),
                                        new Point(40,2,3));
        Camera camera1 =new Camera.Builder().setLocation(new Point(0,0,0))
                .setVpSize(3,3)
                .setDirection(new Vector(0,0,-1), new Vector(0,1,0))
                .setVpDistance(1).build();
        Camera camera2 =new Camera.Builder().setLocation(new Point(0,0,0.5))
                .setVpSize(3,3)
                .setDirection(new Vector(0,0,-1), new Vector(0,1,0))
                .setVpDistance(1).build();
        Camera camera3 =new Camera.Builder().setLocation(new Point(0,0,1))
                .setVpSize(3,3)
                .setDirection(new Vector(0,0,-1), new Vector(0,1,0))
                .setVpDistance(1).build();
        int sizePlaneIntersections = 0;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                sizePlaneIntersections += plane.findIntersections(camera.constructRay(3,3,i,j)).size();

            }
        }

        assertEquals(3,sizePlaneIntersections,
                "when the ray from camera intersects a plane the function does not find the intersection");


        int sizeSphereIntersections = 0;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                sizeSphereIntersections += sphere1.findIntersections(camera1.constructRay(3,3,i,j)).size();

            }
        }
        assertEquals(2,sizeSphereIntersections,
                "when the ray from camera intersects a plane the function does not find the intersection");

        sizeSphereIntersections = 0;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                sizeSphereIntersections += sphere2.findIntersections(camera2.constructRay(3,3,i,j)).size();

            }
        }
        assertEquals(18,sizeSphereIntersections,
                "when the ray from camera intersects a plane the function does not find the intersection");

        sizeSphereIntersections = 0;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                sizeSphereIntersections += sphere3.findIntersections(camera2.constructRay(3,3,i,j)).size();

            }
        }
        assertEquals(10,sizeSphereIntersections,
                "when the ray from camera intersects a plane the function does not find the intersection");

        sizeSphereIntersections = 0;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                sizeSphereIntersections += sphere4.findIntersections(camera2.constructRay(3,3,i,j)).size();

            }
        }
        assertEquals(9,sizeSphereIntersections,
                "when the ray from camera intersects a plane the function does not find the intersection");

        sizeSphereIntersections = 0;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                sizeSphereIntersections += sphere5.findIntersections(camera3.constructRay(3,3,i,j)).size();

            }
        }
        assertEquals(0,sizeSphereIntersections,
                "when the ray from camera intersects a plane the function does not find the intersection");

        int sizeTriangleIntersections = 0;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                sizeTriangleIntersections += plane.findIntersections(camera.constructRay(3,3,1,0)).size();

            }
        }
        assertEquals(3,sizeTriangleIntersections,
                "when the ray from camera intersects a plane the function does not find the intersection");
    }
}