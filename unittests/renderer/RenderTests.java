package renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.AmbientLight;
import primitives.*;
import renderer.*;
import scene.Scene;

/** Test rendering a basic image
 * @author Dan */
public class RenderTests {
    /** Scene of the tests */
    private final Scene          scene  = new Scene("Test scene");
    /** Camera builder of the tests */
    private final Camera.Builder camera = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(scene))
            .setLocation(Point.ZERO).setDirection(new Vector(0, 0, -1),new Vector(0,1,0))
            .setVpDistance(100)
            .setVpSize(500, 500);

    /** Produce a scene with basic 3D model and render it into a png image with a
     * grid */
    @Test
    public void renderTwoColorTest() {
        scene.geometries.add(new Sphere(50d, new Point(0, 0, -100) ),
                new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100),
                        new Point(-100, 100, -100)).setEmission(new Color(GREEN)), // up
                // left
                new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100),
                        new Point(-100, -100, -100)).setEmission(new Color(RED)), // down
                // left
                new Triangle(new Point(100, 0, -100), new Point(0, -100, -100),
                        new Point(100, -100, -100)).setEmission(new Color(BLUE))); // down
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.2)));

        // right
        Camera builtCamera = camera.setImageWriter(
                new ImageWriter("base render test", 1000, 1000)).build();
        builtCamera.renderImage();
        builtCamera.printGrid(100, new Color(WHITE));
        builtCamera.writeToImage();
    }

    /** Test for XML based scene - for bonus */
    @Test
    public void basicRenderXml() {
        // enter XML file name and parse from XML file into scene object
        // using the code you added in appropriate packages
        // .....
        // NB: unit tests is not the correct place to put XML parsing code

        Camera builtCamera = camera.setImageWriter(
                new ImageWriter("xml render test", 1000, 1000)).build();
        builtCamera.renderImage();
        builtCamera.printGrid(100, new Color(YELLOW));
        builtCamera.writeToImage();
    }
}