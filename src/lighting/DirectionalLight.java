package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource {

    private final Vector direction;

    /**
     * @param intensity represents the intensity of a light source
     */
    protected DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }


    @Override
    public Color getIntensity(Point point) {
        return super.getIntensity();
    }

    @Override
    public Vector getL(Point point) {
        return direction;
    }
}
