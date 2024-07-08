package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

public abstract class Intersectable {

    /**
     * @param ray is the ray that intersects the geometry
     * @return a list of the point that the ray intersects the geometrical object
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }

    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

    /**
     *
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            return (obj instanceof GeoPoint geoPoint)
                    && this.geometry.equals(((GeoPoint) obj).geometry)
                    && this.point.equals(((GeoPoint) obj).point);

        }

        @Override
        public String toString() {
            return "geometry:\n " + geometry.toString() + "\npoint\n" + point.toString();
        }
    }

}
