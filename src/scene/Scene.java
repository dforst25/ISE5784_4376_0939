package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

public class Scene {

    //----------------------------fields--------------------------
    public String name;
    public Color background = Color.BLACK;
    public Geometries geometries = new Geometries();
    public AmbientLight ambientLight = AmbientLight.NONE;
    public List<LightSource> lights = new LinkedList<>();

    //-----------------------------constructor-------------------------

    /**
     * Constructs a new Scene object by receiving only the name of the scene
     *
     * @param name represents the name to scene.
     */
    public Scene(String name) {
        this.name = name;
    }

    //----------------------------setters------------------------------------

    //setters as a builder pattern that returns the object himself

    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}
