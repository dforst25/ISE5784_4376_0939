/**
 *
 */
package renderer;

import static java.awt.Color.*;

import geometries.Tube;
import lighting.DirectionalLight;
import lighting.PointLight;
import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import scene.Scene;



/** Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 * @author dzilb */
public class ReflectionRefractionTests {
   /** Scene for the tests */
   private final Scene          scene         = new Scene("Test scene");
   /** Camera builder for the tests with triangles */
   private final Camera.Builder cameraBuilder = Camera.getBuilder()
      .setDirection(new Vector(0,0,-1), new Vector(0,1,0))
      .setRayTracer(new SimpleRayTracer(scene));

   /** Produce a picture of a sphere lighted by a spotLight */
   @Test
   public void twoSpheres() {
      scene.geometries.add(
                           new Sphere(50d, new Point(0, 0, -50)).setEmission(new Color(BLUE))
                              .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                           new Sphere(25d, new Point(0, 0, -50)).setEmission(new Color(RED))
                              .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)));
      scene.lights.add(
                       new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2))
                          .setkL(0.0004).setkQ(0.0000006));

      Camera buildCamera = cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
         .setVpSize(150, 150)
         .setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500))
         .build();
      buildCamera.renderImage();
      buildCamera.writeToImage();
   }

   /** Produce a picture of a sphere lighted by a spotLight */
   @Test
   public void twoSpheresOnMirrors() {
      scene.geometries.add(
                           new Sphere(400d, new Point(-950, -900, -1000)).setEmission(new Color(0, 50, 100))
                              .setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20)
                                 .setkT(new Double3(0.5, 0, 0))),
                           new Sphere(200d, new Point(-950, -900, -1000)).setEmission(new Color(100, 50, 20))
                              .setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20)),
                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                                        new Point(670, 670, 3000))
                              .setEmission(new Color(20, 20, 20))
                              .setMaterial(new Material().setkR(1)),
                           new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                                        new Point(-1500, -1500, -2000))
                              .setEmission(new Color(20, 20, 20))
                              .setMaterial(new Material().setkR(new Double3(0.5, 0, 0.4))));
      scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
      scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4))
         .setkL(0.00001).setkQ(0.000005));

      Camera buildCamera = cameraBuilder.setLocation(new Point(0, 0, 10000)).setVpDistance(10000)
         .setVpSize(2500, 2500)
         .setImageWriter(new ImageWriter("reflectionTwoSpheresMirrored", 500, 500))
         .build();
      buildCamera.renderImage();
      buildCamera.writeToImage();
   }

   /** Produce a picture of two triangles lighted by a spotLight with a
    * partially
    * transparent Sphere producing partial shadow */
   @Test
   public void trianglesTransparentSphere() {
      scene.geometries.add(
                           new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                                        new Point(75, 75, -150))
                              .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
                           new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
                              .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
                           new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(BLUE))
                              .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.6)));
      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
      scene.lights.add(
                       new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1))
                          .setkL(4E-5).setkQ(2E-7));

      Camera buildCamera = cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
         .setVpSize(200, 200)
         .setImageWriter(new ImageWriter("refractionShadow", 600, 600))
         .build();
      buildCamera.renderImage();
      buildCamera.writeToImage();
   }

   @Test
   public void WhatAnAmazingPicture() {
      scene.geometries.add(
              // Top surface of the table
              new Triangle(new Point(-4, 0, -3), new Point(4, 0, -3), new Point(4, 0, 3))
                      .setMaterial(new Material().setkD(0.000003).setkS(0.00001).setnShininess(60).setkT(0.0001).setkR(0.01))
                      .setEmission(new Color(0,300,0)),
              new Triangle(new Point(-4, 0, -3),new Point(-4, 0, 3), new Point(4, 0, 3))
                      .setEmission(new Color(0,300,0))
                      .setMaterial(new Material().setkD(0.000003).setkS(0.00001).setnShininess(60).setkT(0.0001).setkR(0.01)),

              // Bottom surface of the table
              new Triangle(new Point(-4, -1, -3), new Point(4, -1, -3), new Point(4, -1, 3))
                      .setMaterial(new Material().setkD(0).setkS(0).setnShininess(1)) .setEmission(new Color(5,5,5)),
              new Triangle(new Point(-4, -1, -3), new Point(-4, -1, 3), new Point(4, -1, 3))
                      .setMaterial(new Material().setkD(0).setkS(0).setnShininess(1)) .setEmission(new Color(5,5,5)),

              // Side surface 1 of the table
              new Triangle(new Point(-4, 0, -3), new Point(-4, -1, -3), new Point(4, -1, -3))
                      .setMaterial(new Material().setkD(0).setkS(0).setnShininess(1)) .setEmission(new Color(200,50,150)),
              new Triangle(new Point(-4, 0, -3), new Point(4, 0, -3), new Point(4, -1, -3))
                      .setMaterial(new Material().setkD(0).setkS(0).setnShininess(1)) .setEmission(new Color(200,50,150)),

              // Side surface 2 of the table
              new Triangle(new Point(-4, 0, 3), new Point(-4, -1, 3), new Point(4, -1, 3))
                      .setMaterial(new Material().setkD(0).setkS(0).setnShininess(1)).setEmission(new Color(200,50,150)),
              new Triangle(new Point(-4, 0, 3), new Point(4, 0, 3), new Point(4, -1, 3))
                      .setMaterial(new Material().setkD(0).setkS(0).setnShininess(1)).setEmission(new Color(200,50,150)),

              // Front surface of the table
              new Triangle(new Point(-4, 0, -3), new Point(-4, -1, -3), new Point(-4, -1, 3))
                      .setMaterial(new Material().setkD(0).setkS(0).setnShininess(1)).setEmission(new Color(200,50,150)),
              new Triangle(new Point(-4, 0, -3), new Point(-4, 0, 3), new Point(-4, -1, 3))
                      .setMaterial(new Material().setkD(0).setkS(0).setnShininess(1)).setEmission(new Color(200,50,150)),

              // Back surface of the table
              new Triangle(new Point(4, 0, -3), new Point(4, -1, -3), new Point(4, -1, 3))
                      .setMaterial(new Material().setkD(0).setkS(0).setnShininess(1)).setEmission(new Color(200,50,150)),
              new Triangle(new Point(4, 0, -3), new Point(4, 0, 3), new Point(4, -1, 3))
                      .setMaterial(new Material().setkD(0).setkS(0).setnShininess(1)).setEmission(new Color(200,50,150)),

              // Sphere (ball)
              new Sphere(2, new Point(0, 5, 0))
                      .setEmission(new Color(BLUE))
                      .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.6)),

              // Sphere (ball)
              new Sphere(0.25, new Point(4, 0.25, 3))
                      .setEmission(new Color(BLUE))
                      .setMaterial(new Material().setkD(0).setkS(0).setnShininess(30).setkT(0).setkR(0)),
              new Sphere(0.25, new Point(4, 0.25, -3))
                      .setEmission(new Color(BLACK))
                      .setMaterial(new Material().setkD(0).setkS(0).setnShininess(30).setkT(0).setkR(0)),
              new Sphere(0.25, new Point(-4, 0.25, 3))
                      .setEmission(new Color(RED))
                      .setMaterial(new Material().setkD(0).setkS(0).setnShininess(30).setkT(0).setkR(0)),
              new Sphere(0.25, new Point(-4, 0.25, -3))
                      .setEmission(new Color(WHITE))

                      .setMaterial(new Material().setkD(0).setkS(0).setnShininess(30).setkT(0)),
              new Sphere(0.25, new Point(0, 0.25, -2))
                      .setEmission(new Color(BLACK))
                      .setMaterial(new Material().setkD(0).setkS(0).setnShininess(30).setkT(0)),
              new Sphere(0.25, new Point(-0.25, 0.25, -1.5))
                      .setEmission(new Color(BLUE))
                      .setMaterial(new Material().setkD(0).setkS(0).setnShininess(30).setkT(0)),
              new Sphere(0.25, new Point(0.25, 0.25, -1.5))
                      .setEmission(new Color(BLUE))
                      .setMaterial(new Material().setkD(0).setkS(0).setnShininess(30).setkT(0)),
              new Sphere(0.25, new Point(-0.5, 0.25, -1))
                      .setEmission(new Color(WHITE))
                      .setMaterial(new Material().setkD(0).setkS(0).setnShininess(30).setkT(0)),
              new Sphere(0.25, new Point(0, 0.25, -1))
                      .setEmission(new Color(WHITE))
                      .setMaterial(new Material().setkD(0).setkS(0).setnShininess(30).setkT(0)),
              new Sphere(0.25, new Point(0.5, 0.25, -1))
                      .setEmission(new Color(WHITE))
                      .setMaterial(new Material().setkD(0).setkS(0).setnShininess(30).setkT(0)),
              new Sphere(0.25, new Point(-0.75, 0.25, -0.5))
                      .setEmission(new Color(ORANGE))
                      .setMaterial(new Material().setkD(0).setkS(0).setnShininess(30).setkT(0)),
              new Sphere(0.25, new Point(-0.25, 0.25, -0.5))
                      .setEmission(new Color(ORANGE))
                      .setMaterial(new Material().setkD(0).setkS(0).setnShininess(30).setkT(0)),
              new Sphere(0.25, new Point(0.25, 0.25, -0.5))
                      .setEmission(new Color(ORANGE))
                      .setMaterial(new Material().setkD(0).setkS(0).setnShininess(30).setkT(0)),
              new Sphere(0.25, new Point(0.75, 0.25, -0.5))
                      .setEmission(new Color(ORANGE))
                      .setMaterial(new Material().setkD(0).setkS(0).setnShininess(30).setkT(0)),
              // Sphere (ball)
              //new Sphere(2000, new Point(0, 5, 0))
                //      .setEmission(new Color(9.3,19.5,25.2))
                  //    .setMaterial(new Material().setkD(0.01).setkS(0.0008).setnShininess(0).setkT(0.000006).setkR(0.001)),
              // Rectangular Prism Legs of the table
              // Leg 1
              new Triangle(new Point(-4, 0, -3), new Point(-3.8, 0, -2.8), new Point(-3.8, -5, -2.8))
                      .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
              new Triangle(new Point(-4, 0, -3), new Point(-3.8, -5, -2.8), new Point(-4, -5, -3))
                      .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
              new Triangle(new Point(-4, 0, -3), new Point(-4, -5, -3), new Point(-3.8, -5, -2.8))
                      .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),

              // Leg 2
              new Triangle(new Point(4, 0, -3), new Point(3.8, 0, -2.8), new Point(3.8, -5, -2.8))
                      .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
              new Triangle(new Point(4, 0, -3), new Point(3.8, -5, -2.8), new Point(4, -5, -3))
                      .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
              new Triangle(new Point(4, 0, -3), new Point(4, -5, -3), new Point(3.8, -5, -2.8))
                      .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),

              // Leg 3
              new Triangle(new Point(-4, 0, 3), new Point(-3.8, 0, 2.8), new Point(-3.8, -5, 2.8))
                      .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
              new Triangle(new Point(-4, 0, 3), new Point(-3.8, -5, 2.8), new Point(-4, -5, 3))
                      .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
              new Triangle(new Point(-4, 0, 3), new Point(-4, -5, 3), new Point(-3.8, -5, 2.8))
                      .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),

              // Leg 4
              new Triangle(new Point(4, 0, 3), new Point(3.8, 0, 2.8), new Point(3.8, -5, 2.8))
                      .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
              new Triangle(new Point(4, 0, 3), new Point(3.8, -5, 2.8), new Point(4, -5, 3))
                      .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)),
              new Triangle(new Point(4, 0, 3), new Point(4, -5, 3), new Point(3.8, -5, 2.8))
                      .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60))


      );
      // Add checkered floor
      int numTiles = 5;
      double tileSize = 3;
      for (int i = -numTiles; i < numTiles; i++) {
         for (int j = -numTiles; j < numTiles; j++) {
            Color color = ((i + j) % 2 == 0) ? new Color(200, 210, 210) : new Color(200, 200, 170);
            scene.geometries.add(
                    new Triangle(
                            new Point(i * tileSize, -5, j * tileSize),
                            new Point((i + 1) * tileSize-0.05, -5, j * tileSize),
                            new Point(i * tileSize, -5, (j + 1) * tileSize-0.05))
                            .setEmission(color)
                            .setMaterial(new Material().setkD(0.04).setkS(0.5).setnShininess(20).setkR(0.7)),
                    new Triangle(
                            new Point(i * tileSize, -5, (j + 1) * tileSize-0.05),
                            new Point((i + 1) * tileSize-0.05, -5, j * tileSize),
                            new Point((i + 1) * tileSize-0.05, -5, (j + 1) * tileSize-0.05))
                            .setEmission(color)
                            .setMaterial(new Material().setkD(0.04).setkS(0.5).setnShininess(20).setkR(0.7))
            );
         }
      }
      // Add three walls
      // Example calls to add walls
      //addWall(new Point(-numTiles * tileSize, -5, -numTiles * tileSize), new Point(numTiles * tileSize, -5, -numTiles * tileSize), new Point(numTiles * tileSize, -5, numTiles * tileSize), new Point(-numTiles * tileSize, -5, numTiles * tileSize));
      //addWall(new Point(-numTiles * tileSize, -5, -numTiles * tileSize), new Point(numTiles * tileSize, -5, -numTiles * tileSize), new Point(numTiles * tileSize, 5, -numTiles * tileSize), new Point(-numTiles * tileSize, 5, -numTiles * tileSize));
      //addWall(new Point(-numTiles * tileSize, -5, -numTiles * tileSize), new Point(-numTiles * tileSize, -5, numTiles * tileSize), new Point(-numTiles * tileSize, 5, numTiles * tileSize), new Point(-numTiles * tileSize, 5, -numTiles * tileSize));
      // Adding the front wall
      addWall(
              new Point(-numTiles * tileSize, -5, -numTiles * tileSize),
              new Point(numTiles * tileSize, -5, -numTiles * tileSize),
              new Point(numTiles * tileSize, 5, -numTiles * tileSize),
              new Point(-numTiles * tileSize, 5, -numTiles * tileSize),
              0.01
      );

// Adding the back wall
      addWall(
              new Point(-numTiles * tileSize, -5, numTiles * tileSize),
              new Point(numTiles * tileSize, -5, numTiles * tileSize),
              new Point(numTiles * tileSize, 5, numTiles * tileSize),
              new Point(-numTiles * tileSize, 5, numTiles * tileSize),
              0.01
      );

// Adding the left wall
      addWall(
              new Point(-numTiles * tileSize, -5, -numTiles * tileSize),
              new Point(-numTiles * tileSize, -5, numTiles * tileSize),
              new Point(-numTiles * tileSize, 5, numTiles * tileSize),
              new Point(-numTiles * tileSize, 5, -numTiles * tileSize),
              0.01
      );

// Adding the right wall
      addWall(
              new Point(numTiles * tileSize, -5, -numTiles * tileSize),
              new Point(numTiles * tileSize, -5, numTiles * tileSize),
              new Point(numTiles * tileSize, 5, numTiles * tileSize),
              new Point(numTiles * tileSize, 5, -numTiles * tileSize),
              0.01
      );

      scene.setAmbientLight(new AmbientLight(new Color(50,50,50), 0.05));
      //scene.lights.add(new PointLight(new Color(500, 500, 400), new Point(0, 130, 100)).setkL(0).setkQ(0));
      scene.lights.add(new PointLight(new Color(200, 100, 100), new Point(4, 10, 3)).setkL(0).setkQ(0));
      scene.lights.add(new PointLight(new Color(20, 10, 10), new Point(0, 5, 0)).setkL(0).setkQ(0));

      Camera buildCamera = cameraBuilder
              .setDirection(new Vector(-0.8, -1, -2), new Vector(0, 1, -0.5))
              .setLocation(new Point(10, 2, 10))
              .setVpDistance(5)
              .setVpSize(10, 10)
              .setImageWriter(new ImageWriter("WhatAnAmazingPicture", 2000, 2000))
              .build();
      buildCamera.renderImage();
      buildCamera.writeToImage();
      Camera buildCamera2 = cameraBuilder
              .setDirection(new Vector(0, -1, -2), new Vector(0, 1, -0.5))
              .setLocation(new Point(0, 3, 8))
              .setVpDistance(5)
              .setVpSize(10, 10)
              .setImageWriter(new ImageWriter("WhatAnAmazingPicture2", 500, 500))
              .build();
      buildCamera2.renderImage();
      buildCamera2.writeToImage();
   }

   private void addWall(Point p1, Point p2, Point p3, Point p4, double thickness) {
      Vector thicknessVector = new Vector(thickness, thickness, thickness);
      Color wall_color = new Color (10,10,10);
      scene.geometries.add(
              // Front face
              new Triangle(p1, p2, p3)
                      .setMaterial(new Material().setkD(0.4).setkS(0.4).setnShininess(50))
                      .setEmission(wall_color),
              new Triangle(p1, p3, p4)
                      .setMaterial(new Material().setkD(0.4).setkS(0.4).setnShininess(50))
                      .setEmission(wall_color),

              // Back face
              new Triangle(p1.add(thicknessVector), p2.add(thicknessVector), p3.add(thicknessVector))
                      .setMaterial(new Material().setkD(0.4).setkS(0.4).setnShininess(50))
                      .setEmission(wall_color),
              new Triangle(p1.add(thicknessVector), p3.add(thicknessVector), p4.add(thicknessVector))
                      .setMaterial(new Material().setkD(0.4).setkS(0.4).setnShininess(50))
                      .setEmission(wall_color),

              // Side faces
              new Triangle(p1, p2, p2.add(thicknessVector))
                      .setMaterial(new Material().setkD(0.4).setkS(0.4).setnShininess(50))
                      .setEmission(wall_color),
              new Triangle(p1, p2.add(thicknessVector), p1.add(thicknessVector))
                      .setMaterial(new Material().setkD(0.4).setkS(0.4).setnShininess(50))
                      .setEmission(wall_color),

              new Triangle(p2, p3, p3.add(thicknessVector))
                      .setMaterial(new Material().setkD(0.4).setkS(0.4).setnShininess(50))
                      .setEmission(wall_color),
              new Triangle(p2, p3.add(thicknessVector), p2.add(thicknessVector))
                      .setMaterial(new Material().setkD(0.4).setkS(0.4).setnShininess(50))
                      .setEmission(wall_color),

              new Triangle(p3, p4, p4.add(thicknessVector))
                      .setMaterial(new Material().setkD(0.4).setkS(0.4).setnShininess(50))
                      .setEmission(wall_color),
              new Triangle(p3, p4.add(thicknessVector), p3.add(thicknessVector))
                      .setMaterial(new Material().setkD(0.4).setkS(0.4).setnShininess(50))
                      .setEmission(wall_color),

              new Triangle(p4, p1, p1.add(thicknessVector))
                      .setMaterial(new Material().setkD(0.4).setkS(0.4).setnShininess(50))
                      .setEmission(wall_color),
              new Triangle(p4, p1.add(thicknessVector), p4.add(thicknessVector))
                      .setMaterial(new Material().setkD(0.4).setkS(0.4).setnShininess(50))
                      .setEmission(wall_color)
      );
   }



}


