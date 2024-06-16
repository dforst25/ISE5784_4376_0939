package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable{
    final List<Intersectable> geometries = new LinkedList<>();

    public Geometries() {
    }
    public Geometries(Intersectable... geometries)
    {
        add( geometries);
    }
    public void add(Intersectable... geometries){
        this.geometries.addAll(Arrays.asList(geometries));
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> result = null;
        for(Intersectable geometry : geometries)
        {
            List<Point> intersections = geometry.findIntersections(ray);
            if(intersections !=null) {
                if (result == null) {
                    result = new LinkedList<>();
                }
                result.addAll(intersections);
            }
        }
        return result;
    }
}
