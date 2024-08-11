package renderer;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.PointLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.*;
import static java.awt.Color.ORANGE;

public class PoolTable {
    /** Scene for the tests */
    private final Scene scene         = new Scene("Test scene");
    /** Camera builder for the tests with triangles */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setDirection(new Vector(0,0,-1), new Vector(0,1,0))
            .setRayTracer(new SimpleRayTracer(scene));

    @Test
    public void WhatAnAmazingPicture() {
        scene.geometries.add(
                new Triangle(new Point(-4000, -5.5, -300000), new Point(4000, -5.5, -30000), new Point(0, -5.5, 30000))
                        .setMaterial(new Material().setkD(new Double3(0, 0, 0)).setnShininess(0).setkT(0).setkS(new Double3(0, 0, 0))),

                // Top surface of the table
                new Triangle(new Point(-4, 0, -3), new Point(4, 0, -3), new Point(4, 0, 3))
                        .setMaterial(new Material().setkD(new Double3(0, .71, 0)).setkS(new Double3(0, .91, 0)).setnShininess(60).setkT(0.0001).setkS(new Double3(0, 0.11, 0))),
                new Triangle(new Point(-4, 0, -3), new Point(-4, 0, 3), new Point(4, 0, 3))
                        .setMaterial(new Material().setkD(new Double3(0, .71, 0)).setkS(new Double3(0, .91, 0)).setnShininess(60).setkT(0.0001).setkS(new Double3(0, 0.1, 0))),

                // Bottom surface of the table
                new Triangle(new Point(-4, -1, -3), new Point(4, -1, -3), new Point(4, -1, 3))
                        .setMaterial(new Material().setkD(0).setkS(0).setnShininess(20)).setEmission(new Color(5, 5, 5)),
                new Triangle(new Point(-4, -1, -3), new Point(-4, -1, 3), new Point(4, -1, 3))
                        .setMaterial(new Material().setkD(0).setkS(0).setnShininess(20)).setEmission(new Color(5, 5, 5)),

                // Side surface 1 of the table
                new Triangle(new Point(-4, 0, -3), new Point(-4, -1, -3), new Point(4, -1, -3))
                        .setMaterial(new Material().setkD(new Double3(0.8, .21, .6)).setnShininess(60).setkT(0.0001).setkS(new Double3(0.8, .21, .6))),
                new Triangle(new Point(-4, 0, -3), new Point(4, 0, -3), new Point(4, -1, -3))
                        .setMaterial(new Material().setkD(new Double3(0.8, .21, .6)).setnShininess(60).setkT(0.0001).setkS(new Double3(0.8, .21, .6))),

                // Side surface 2 of the table
                new Triangle(new Point(-4, 0, 3), new Point(-4, -1, 3), new Point(4, -1, 3))
                        .setMaterial(new Material().setkD(new Double3(0.8, .21, .6)).setnShininess(60).setkT(0.0001).setkS(new Double3(0.8, .21, .6))),
                new Triangle(new Point(-4, 0, 3), new Point(4, 0, 3), new Point(4, -1, 3))
                        .setMaterial(new Material().setkD(new Double3(0.8, .21, .6)).setnShininess(60).setkT(0.0001).setkS(new Double3(0.8, .21, .6))),

                // Front surface of the table
                new Triangle(new Point(-4, 0, -3), new Point(-4, -1, -3), new Point(-4, -1, 3))
                        .setMaterial(new Material().setkD(new Double3(0.8, .21, .6)).setnShininess(60).setkT(0.0001).setkS(new Double3(0.8, .21, .6))),
                new Triangle(new Point(-4, 0, -3), new Point(-4, 0, 3), new Point(-4, -1, 3))
                        .setMaterial(new Material().setkD(new Double3(0.8, .21, .6)).setnShininess(60).setkT(0.0001).setkS(new Double3(0.8, .21, .6))),

                // Back surface of the table
                new Triangle(new Point(4, 0, -3), new Point(4, -1, -3), new Point(4, -1, 3))
                        .setMaterial(new Material().setkD(new Double3(0.8, .21, .6)).setnShininess(60).setkT(0.0001).setkS(new Double3(0.8, .21, .6))),
                new Triangle(new Point(4, 0, -3), new Point(4, 0, 3), new Point(4, -1, 3))
                        .setMaterial(new Material().setkD(new Double3(0.8, .21, .6)).setnShininess(60).setkT(0.0001).setkS(new Double3(0.8, .21, .6))),

                // Sphere (ball)
                new Sphere(2, new Point(6, -3, -10))
                        .setEmission(new Color(BLUE))
                        .setMaterial(new Material().setkD(0.4).setkS(0.4).setnShininess(30).setkT(0.4)),
                new Sphere(2, new Point(1, -3, -9))
                        .setEmission(new Color(BLUE))
                        .setMaterial(new Material().setkD(0.4).setkS(0.4).setnShininess(30).setkT(0.4)),

                new Sphere(0.25, new Point(0, 0.25, -2))
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material().setkD(0.4).setkS(0.2).setnShininess(50)),
                new Sphere(0.25, new Point(-0.25, 0.25, -1.5))
                        .setEmission(new Color(BLUE))
                        .setMaterial(new Material().setkD(0.4).setkS(0.2).setnShininess(50)),
                new Sphere(0.25, new Point(0.25, 0.25, -1.5))
                        .setEmission(new Color(BLUE))
                        .setMaterial(new Material().setkD(0.4).setkS(0.2).setnShininess(50)),
                new Sphere(0.25, new Point(-0.5, 0.25, -1))
                        .setEmission(new Color(WHITE))
                        .setMaterial(new Material().setkD(0.4).setkS(0.2).setnShininess(50)),
                new Sphere(0.25, new Point(0, 0.25, -1))
                        .setEmission(new Color(WHITE))
                        .setMaterial(new Material().setkD(0.4).setkS(0.2).setnShininess(50)),
                new Sphere(0.25, new Point(0.5, 0.25, -1))
                        .setEmission(new Color(WHITE))
                        .setMaterial(new Material().setkD(0.4).setkS(0.2).setnShininess(50)),
                new Sphere(0.25, new Point(-0.75, 0.25, -0.5))
                        .setEmission(new Color(ORANGE))
                        .setMaterial(new Material().setkD(0.4).setkS(0.2).setnShininess(50)),
                new Sphere(0.25, new Point(-0.25, 0.25, -0.5))
                        .setEmission(new Color(ORANGE))
                        .setMaterial(new Material().setkD(0.4).setkS(0.2).setnShininess(50)),
                new Sphere(0.25, new Point(0.25, 0.25, -0.5))
                        .setEmission(new Color(ORANGE))
                        .setMaterial(new Material().setkD(0.4).setkS(0.2).setnShininess(50)),
                new Sphere(0.25, new Point(0.75, 0.25, -0.5))
                        .setEmission(new Color(ORANGE))
                        .setMaterial(new Material().setkD(0.4).setkS(0.2).setnShininess(50)),
                // Sphere (ball)
                //new Sphere(2000, new Point(0, 5, 0))
                //      .setEmission(new Color(9.3,19.5,25.2))
                //    .setMaterial(new Material().setkD(0.01).setkS(0.0008).setnShininess(0).setkT(0.000006).setkR(0.001)),
                // Rectangular Prism Legs of the table
                // Leg 1
                new Triangle(new Point(-4, 0, -3), new Point(-4, 0, -2.8), new Point(-4, -5, -2.9))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60))
                        .setEmission(new Color(222, 184, 135)),
                new Triangle(new Point(-4, 0, -3), new Point(-4, -5, -3), new Point(-4, -5, -2.9))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60))
                        .setEmission(new Color(222, 184, 135)),
                new Triangle(new Point(-4, 0, -3), new Point(-3.8, 0, -3), new Point(-3.9, -5, -3))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60))
                        .setEmission(new Color(222, 184, 135)),
                new Triangle(new Point(-4, 0, -3), new Point(-4, -5, -3), new Point(-3.9, -5, -3))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60))
                        .setEmission(new Color(222, 184, 135)),

                // Leg 2
                new Triangle(new Point(4, 0, -3), new Point(4, 0, -2.8), new Point(4, -5, -2.9))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60))
                        .setEmission(new Color(222, 184, 135)),
                new Triangle(new Point(4, 0, -3), new Point(4, -5, -3), new Point(4, -5, -2.9))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60))
                        .setEmission(new Color(222, 184, 135)),
                new Triangle(new Point(4, 0, -3), new Point(3.8, 0, -3), new Point(3.9, -5, -3))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60))
                        .setEmission(new Color(222, 184, 135)),
                new Triangle(new Point(4, 0, -3), new Point(4, -5, -3), new Point(3.9, -5, -3))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60))
                        .setEmission(new Color(222, 184, 135)),
                // Leg 3
                new Triangle(new Point(-4, 0, 3), new Point(-4, 0, 2.8), new Point(-4, -5, 2.9))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60))
                        .setEmission(new Color(222, 184, 135)),
                new Triangle(new Point(-4, 0, 3), new Point(-4, -5, 3), new Point(-4, -5, 2.9))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60))
                        .setEmission(new Color(222, 184, 135)),
                new Triangle(new Point(-4, 0, 3), new Point(-3.8, 0, 3), new Point(-3.9, -5, 3))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60))
                        .setEmission(new Color(222, 184, 135)),
                new Triangle(new Point(-4, 0, 3), new Point(-4, -5, 3), new Point(-3.9, -5, 3))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60))
                        .setEmission(new Color(222, 184, 135)),
                // Leg 4
                new Triangle(new Point(4, 0, 3), new Point(4, 0, 2.8), new Point(4, -5, 2.9))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60))
                        .setEmission(new Color(222, 184, 135)),
                new Triangle(new Point(4, 0, 3), new Point(4, -5, 3), new Point(4, -5, 2.9))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60))
                        .setEmission(new Color(222, 184, 135)),
                new Triangle(new Point(4, 0, 3), new Point(3.8, 0, 3), new Point(3.9, -5, 3))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60))
                        .setEmission(new Color(222, 184, 135)),
                new Triangle(new Point(4, 0, 3), new Point(4, -5, 3), new Point(3.9, -5, 3))
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60))
                        .setEmission(new Color(222, 184, 135))


        );
        // Add checkered floor
        int numTiles = 5;
        double tileSize = 3;
        for (int i = -numTiles; i < numTiles; i++) {
            for (int j = -numTiles; j < numTiles; j++) {
                Double3 dcolor = ((i + j) % 2 == 0) ? new Double3(0.4, 0.42, 0.42) : new Double3(0.4, 0.4, 0.34);
                Color color = ((i + j) % 2 == 0) ? new Color(200, 210, 210) : new Color(200, 200, 170);
                scene.geometries.add(
                        new Triangle(
                                new Point(i * tileSize, -5, j * tileSize),
                                new Point((i + 1) * tileSize - 0.05, -5, j * tileSize),
                                new Point(i * tileSize, -5, (j + 1) * tileSize - 0.05))
                                .setMaterial(new Material().setkD(dcolor).setkS(dcolor).setnShininess(60).setkR(0.2)),
                        new Triangle(
                                new Point(i * tileSize, -5, (j + 1) * tileSize - 0.05),
                                new Point((i + 1) * tileSize - 0.05, -5, j * tileSize),
                                new Point((i + 1) * tileSize - 0.05, -5, (j + 1) * tileSize - 0.05))
                                .setMaterial(new Material().setkD(dcolor).setkS(dcolor).setnShininess(60).setkR(0.2))
                );
            }
        }
        // Adding the front wall
        addWall(
                new Point(-numTiles * tileSize, -5, -numTiles * tileSize),
                new Point(numTiles * tileSize, -5, -numTiles * tileSize),
                new Point(numTiles * tileSize, 10, -numTiles * tileSize),
                new Point(-numTiles * tileSize, 10, -numTiles * tileSize),
                0.01
        );

// Adding the back wall
        addWall(
                new Point(-numTiles * tileSize, -5, numTiles * tileSize),
                new Point(numTiles * tileSize, -5, numTiles * tileSize),
                new Point(numTiles * tileSize, 10, numTiles * tileSize),
                new Point(-numTiles * tileSize, 10, numTiles * tileSize),
                0.01
        );

// Adding the left wall
        addWall(
                new Point(-numTiles * tileSize, -5, -numTiles * tileSize),
                new Point(-numTiles * tileSize, -5, numTiles * tileSize),
                new Point(-numTiles * tileSize, 10, numTiles * tileSize),
                new Point(-numTiles * tileSize, 10, -numTiles * tileSize),
                0.01
        );

// Adding the right wall
        addWall(
                new Point(numTiles * tileSize, -5, -numTiles * tileSize),
                new Point(numTiles * tileSize, -5, numTiles * tileSize),
                new Point(numTiles * tileSize, 10, numTiles * tileSize),
                new Point(numTiles * tileSize, 10, -numTiles * tileSize),
                0.01
        );
        //to make it day
        scene.setBackground(new Color(64, 156, 255));
        scene.setAmbientLight(new AmbientLight(new Color(64, 156, 255), 0.05));
        scene.lights.add(new PointLight(new Color(220, 221, 250).scale(2), new Point(4, 15, 10)).setkL(0).setkQ(0));

     /* Camera buildCamera = cameraBuilder
              .setDirection(new Vector(0.1, -1,0 ), new Vector(0, 0, 1))
              .setLocation(new Point(0.1, 10, 0.1))
              .setVpDistance(5)
              .setVpSize(10, 10)
              .setImageWriter(new ImageWriter("Pool Attempt", 200, 200))
              .build();
      buildCamera.setMultithreading(3);
      buildCamera.renderImage(5);
      buildCamera.writeToImage();*/

      /*Camera buildCamera2 = cameraBuilder
              .setDirection(new Vector(0, -1, -2), new Vector(0, 2, -1))
              .setLocation(new Point(0, 3, 7))
              .setVpDistance(5)
              .setVpSize(10, 10)
              .setImageWriter(new ImageWriter("PoolTableFromTheSideDayWithoutAnti", 300, 300))
              .build();
      buildCamera2.setMultithreading(5);
      buildCamera2.renderImage(5);
      buildCamera2.writeToImage();*/
        Camera buildCamera3 = cameraBuilder
                .setDirection(new Vector(-0.8, -1, -2), new Vector(0, 1, -0.5))
                .setLocation(new Point(10, 2, 10))
                .setVpDistance(5)
                .setVpSize(10, 10)
                .setImageWriter(new ImageWriter("Pool attempt", 1000, 1000))
                .build();
        buildCamera3.setMultithreading(3);
        buildCamera3.renderImage(17);
        buildCamera3.writeToImage();
    }

    private void addWall(Point p1, Point p2, Point p3, Point p4, double thickness) {
        Vector thicknessVector = new Vector(thickness, thickness, thickness);
        Double3 wallKd = new Double3(.5, .4, .219).scale(1);
        scene.geometries.add(
                // Front face

                new Triangle(p1, p2, p3)
                        .setMaterial(new Material().setkD(wallKd).setkS(0.01).setnShininess(60).setkT(0.0001).setkS(new Double3(0.15, 0.15, 0.15))),

                new Triangle(p1, p3, p4)
                        .setMaterial(new Material().setkD(wallKd).setkS(0.01).setnShininess(60).setkT(0.0001).setkS(new Double3(0.15, 0.15, 0.15))),


                // Back face
                new Triangle(p1.add(thicknessVector), p2.add(thicknessVector), p3.add(thicknessVector))
                        .setMaterial(new Material().setkD(wallKd).setkS(0.01).setnShininess(60).setkT(0.0001).setkS(new Double3(0.15, 0.15, 0.15))),

                new Triangle(p1.add(thicknessVector), p3.add(thicknessVector), p4.add(thicknessVector))
                        .setMaterial(new Material().setkD(wallKd).setkS(0.01).setnShininess(60).setkT(0.0001).setkS(new Double3(0.15, 0.15, 0.15))),


                // Side faces
                new Triangle(p1, p2, p2.add(thicknessVector))
                        .setMaterial(new Material().setkD(wallKd).setkS(0.01).setnShininess(60).setkT(0.0001).setkS(new Double3(0.15, 0.15, 0.15))),

                new Triangle(p1, p2.add(thicknessVector), p1.add(thicknessVector))
                        .setMaterial(new Material().setkD(wallKd).setkS(0.01).setnShininess(60).setkT(0.0001).setkS(new Double3(0.15, 0.15, 0.15))),


                new Triangle(p2, p3, p3.add(thicknessVector))
                        .setMaterial(new Material().setkD(wallKd).setkS(0.01).setnShininess(60).setkT(0.0001).setkS(new Double3(0.15, 0.15, 0.15))),

                new Triangle(p2, p3.add(thicknessVector), p2.add(thicknessVector))
                        .setMaterial(new Material().setkD(wallKd).setkS(0.01).setnShininess(60).setkT(0.0001).setkS(new Double3(0.15, 0.15, 0.15))),

                new Triangle(p3, p4, p4.add(thicknessVector))
                        .setMaterial(new Material().setkD(wallKd).setkS(0.01).setnShininess(60).setkT(0.0001).setkS(new Double3(0.15, 0.15, 0.15))),
                new Triangle(p3, p4.add(thicknessVector), p3.add(thicknessVector))
                        .setMaterial(new Material().setkD(wallKd).setkS(0.01).setnShininess(60).setkT(0.0001).setkS(new Double3(0.15, 0.15, 0.15))),


                new Triangle(p4, p1, p1.add(thicknessVector))
                        .setMaterial(new Material().setkD(wallKd).setkS(0.01).setnShininess(60).setkT(0.0001).setkS(new Double3(0.15, 0.15, 0.15))),
                new Triangle(p4, p1.add(thicknessVector), p4.add(thicknessVector))
                        .setMaterial(new Material().setkD(0.4).setkS(0.4).setnShininess(50))

        );
    }
}
