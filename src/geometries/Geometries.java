package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable{
    final List<Intersectable> f = new LinkedList<>();

    public Geometries() {
    }
    public Geometries(Intersectable... geometries)
    {
        add( geometries);
    }
    public void add(Intersectable... geometries){
        f.addAll(Arrays.asList(geometries));
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> result = null;
        for(Intersectable element :f )
        {
            if(element.findIntersections(ray) !=null) {
                if (result == null) {
                    result = new LinkedList<>();
                }
                result.addAll(element.findIntersections(ray));
            }
        }
        return result;
    }
}
