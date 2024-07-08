package renderer;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static org.junit.jupiter.api.Assertions.*;

class CameraIntersectionIntegrationTest {
    private static int getSizePlaneIntersections(Intersectable intersectable, Camera camera) {
        int sizeIntersections = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (intersectable.findIntersections(camera.constructRay(3, 3, i, j)) != null)
                    sizeIntersections += intersectable.findIntersections(camera.constructRay(3, 3, i, j)).size();
        return sizeIntersections;
    }

    Camera.Builder builder = new Camera.Builder()
            .setRayTracer(new SimpleRayTracer(new Scene("Test")))
            .setImageWriter(new ImageWriter("Test", 1, 1)).setVpSize(3, 3)
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpDistance(1);

    @Test
    void testCameraIntersectionPlane() {

        Plane plane1 = new Plane(new Point(-1, -2, -3), new Vector(0, 0, 1));
        Plane plane2 = new Plane(new Point(-1, -2, -3), new Vector(1, 1, 11));
        Plane plane3 = new Plane(new Point(0, 0, -5), new Vector(0, 10, -1));


        Camera camera = builder.setLocation(new Point(0, 0, 0.5)).build();

        //3x3 plane parallel to VP exp 9 intersections
        int sizePlaneIntersections = getSizePlaneIntersections(plane1, camera);
        assertEquals(9, sizePlaneIntersections,
                "when the ray from camera intersects a plane the function does not find the intersection");

        //3x3 plane in front of camera exp 9 intersections
        sizePlaneIntersections = getSizePlaneIntersections(plane2, camera);
        assertEquals(9, sizePlaneIntersections,
                "when the ray from camera intersects a plane the function does not find the intersection");

        //3x3 plane in front of camera exp 6 intersections
        sizePlaneIntersections = getSizePlaneIntersections(plane3, camera);
        assertEquals(6, sizePlaneIntersections,
                "when the ray from camera intersects a plane the function does not find the intersection");
    }


    @Test
    void testCameraIntersectionSphere() {
        Camera camera1 = builder.setLocation(new Point(0, 0, 0)).build();
        Camera camera2 = builder.setLocation(new Point(0, 0, 0.5)).build();

        Sphere sphere1 = new Sphere(1, new Point(0, 0, -3));
        Sphere sphere2 = new Sphere(2.5, new Point(0, 0, -2.5));
        Sphere sphere3 = new Sphere(2, new Point(0, 0, -2));
        Sphere sphere4 = new Sphere(4, new Point(0, 0, -2));
        Sphere sphere5 = new Sphere(0.5, new Point(0, 0, 1));

        //3x3 sphere is in front of VP takes the spot of only one pixel exp 2 intersections
        int sizeSphereIntersections = getSizePlaneIntersections(sphere1, camera1);
        assertEquals(2, sizeSphereIntersections,
                "when the ray from camera intersects a sphere the function does not find the intersection");

        //3x3 sphere is in front of VP covers the entire VP exp 19 intersections
        sizeSphereIntersections = getSizePlaneIntersections(sphere2, camera2);
        assertEquals(18, sizeSphereIntersections,
                "when the ray from camera intersects a sphere the function does not find the intersection");

        //3x3 sphere exp 10 intersection (probably 8 of the "side and corner" rays are tangent to the sphere)
        sizeSphereIntersections = getSizePlaneIntersections(sphere3, camera2);
        assertEquals(10, sizeSphereIntersections,
                "when the ray from camera intersects a sphere the function does not find the intersection");

        //3x3 camera location is inside the sphere exp 9 intersections
        sizeSphereIntersections = getSizePlaneIntersections(sphere4, camera2);
        assertEquals(9, sizeSphereIntersections,
                "when the ray from camera intersects a sphere the function does not find the intersection");

        //3x3 sphere is behind the camera no intersections expected
        sizeSphereIntersections = getSizePlaneIntersections(sphere5, camera1);
        assertEquals(0, sizeSphereIntersections,
                "when the ray from camera intersects a sphere the function does not find the intersection");

    }


    @Test
    void testCameraIntersectionTriangle() {
        Camera camera2 = builder.setLocation(new Point(0, 0, 0.5)).build();

        Triangle triangle1 = new Triangle(new Point(0, 1, -2),
                new Point(1, -1, -2),
                new Point(-1, -1, -2));
        Triangle triangle2 = new Triangle(new Point(0, 20, -2),
                new Point(1, -1, -2),
                new Point(-1, -1, -2));

        //triangle is only over the center pixel exp 1 intersection
        int sizeTriangleIntersections = getSizePlaneIntersections(triangle1, camera2);
        assertEquals(1, sizeTriangleIntersections,
                "when the ray from camera intersects a plane the function does not find the intersection");

        //triangle covers 2 pixels exp 2 intersections
        sizeTriangleIntersections = getSizePlaneIntersections(triangle2, camera2);
        assertEquals(2, sizeTriangleIntersections,
                "when the ray from camera intersects a plane the function does not find the intersection");

    }
}