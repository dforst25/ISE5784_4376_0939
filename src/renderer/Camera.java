package renderer;

import primitives.*;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;

import static java.awt.Color.BLACK;
import static primitives.Util.isZero;


public class Camera implements Cloneable {

    //----------------------fields---------------------

    //the location of the camera
    private Point location;
    //right for the cameras perspective
    private Vector vRight;
    //upwards for the cameras perspective
    private Vector vUp;
    //the direction in which the camera is pointed
    private Vector vTo;
    //the height of the view plane(actual units of the axis)
    private double height = 0.0;
    //the width of the view plane(actual units of the axis)
    private double width = 0.0;
    //the distance between the camera location and the view plane(actual units of the axis)
    private double distance = 0.0;
    //contains the entire matrix of the image
    private ImageWriter imageWriter;
    //to get the color of a given ray within a scene
    private RayTracerBase rayTracer;

    /**
     * default constructor used only by the builder
     */
    private Camera() {
    }

    /**
     * @return a new builder object
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    public Point getLocation() {
        return location;
    }

    public Vector getVRight() {
        return vRight;
    }

    public Vector getVUp() {
        return vUp;
    }

    /**
     *
     */
    public Vector getVTo() {
        return vTo;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public Camera clone() {
        try {
            return (Camera) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Can't happen
        }
    }

    /**
     * @param nX is the amount of pixels for the width of the view plane (how many columns)
     * @param nY is the amount of pixels for the height of the view plane (how many rows)
     * @param j  is the column of the pixel (of type int)
     * @param i  is the row of the pixel (of type int)
     * @return the ray going from the camera location to the point on the view plane
     */
    //Unnecessary just keeping it meanwhile until i change the tests in camera_test
    public Ray constructRay(int nX, int nY, int j, int i) {
        //pc represents center of the view plain
        Point pC = location.add(vTo.scale(distance));
        //Ratio (pixel width and height)
        double rY = height / nY;
        double rX = width / nX;
        //center of pixel[i,j]
        double yI = -(i - (nY - 1) / 2.0) * rY;
        double xJ = (j - (nX - 1) / 2.0) * rX;
        Point pIJ;

        pIJ = pC;
        //checks if the Yi, Xi aren't 0 in order to not get vectors (0, 0, 0)
        if (!isZero(yI))
            pIJ = pIJ.add(vUp.scale(yI));
        if (!isZero(xJ))
            pIJ = pIJ.add(vRight.scale(xJ));


        //vector of the direction from camera to the center of the given pixel
        Vector vIJ = pIJ.subtract(location);
        return new Ray(location, vIJ);
    }

    /**
     * @param nX is the amount of pixels for the width of the view plane (how many columns)
     * @param nY is the amount of pixels for the height of the view plane (how many rows)
     * @param j  is the column of the pixel (of type int)
     * @param i  is the row of the pixel (of type int)
     * @param superSampling is the amount of rays that go through each pixels (amount of rows and column)
     * @return the ray going from the camera location to the point on the view plane
     */

    public List<Ray> constructListRay(int nX, int nY, int j, int i, int superSampling) {
        // Center of the view plane
        Point pC = location.add(vTo.scale(distance));

        // Pixel dimensions
        double rY = height / nY;
        double rX = width / nX;

        // Center of pixel [i, j]
        double yI = -(i - (nY - 1) / 2.0) * rY;
        double xJ = (j - (nX - 1) / 2.0) * rX;
        Point pIJ = pC;

        // Check if yI, xJ are non-zero to avoid zero vectors
        if (!isZero(yI)) {
            pIJ = pIJ.add(vUp.scale(yI));
        }
        if (!isZero(xJ)) {
            pIJ = pIJ.add(vRight.scale(xJ));
        }

        // Move to the top-left corner of the pixel
        pIJ = pIJ.add(vUp.scale(-rY / 2));
        pIJ = pIJ.add(vRight.scale(-rX / 2));

        // Create rays for super-sampling
        List<Ray> allRays = new LinkedList<>();
        for (int k = 0; k <= superSampling; ++k) {
            for (int l = 0; l <= superSampling; ++l) {
                Point p = pIJ;
                // Move to the correct sub-pixel location
                if(k!=0)
                    p = p.add(vUp.scale(rY * k / superSampling));
                if(l!=0)
                    p = p.add(vRight.scale(rX * l / superSampling));

                // Create a new ray from the camera location to the sub-pixel point
                allRays.add(new Ray(location, p.subtract(location)));
            }
        }
        return allRays;
    }

    public void renderImage() {
        renderImage(1);
    }

    /**
     * @param superSampling is the amount of rays that go through each pixels (amount of rows and column)
     */
    public void renderImage(int superSampling) {
        int Ny = imageWriter.getNy();
        int Nx = imageWriter.getNx();
        for (int y = 0; y < Ny; y++) {
            for (int x = 0; x < Nx; x++) {
                castRay(Nx, Ny, y, x,superSampling);
            }
        }
    }

    /**
     * Casts a ray from the camera through a pixel in the image, and writes the color of the intersection point to the
     * corresponding pixel in the image.
     *
     * @param nX     the number of pixels in the x-direction of the image
     * @param nY     the number of pixels in the y-direction of the image
     * @param column the column number of the pixel to cast the ray through
     * @param row    the row number of the pixel to cast the ray through
     * @param superSampling is the amount of rays that go through each pixels (amount of rows and column)
     */
    private void castRay(int nX, int nY, int row, int column,int superSampling) {


        List<Ray> listRay =  constructListRay(nX, nY, row, column,superSampling);
        Color sumRGB = new Color(BLACK);

        for( Ray ray : listRay)
        {
            sumRGB = sumRGB.add(rayTracer.traceRay(ray).scale(1.0/(superSampling*superSampling)));
        }
        imageWriter.writePixel(row, column, sumRGB);

    }

    /**
     * Prints a grid on the camera's image using the specified interval and color.
     *
     * @param interval the interval between the grid lines
     * @param color    the color to use for the grid lines
     */
    public void printGrid(int interval, Color color) {
        /*nested loops that go through every pixel in grid and color it*/
        int nx = imageWriter.getNx();
        int ny = imageWriter.getNy();
        for (int i = 0; i < nx; i++)
            for (int j = 0; j < ny; j += interval)
                imageWriter.writePixel(i, j, color);

        for (int i = 0; i < nx; i += interval)
            for (int j = 0; j < ny; j++)
                imageWriter.writePixel(i, j, color);
    }

    /**
     * Writes the camera's image to a file using the imageWriter.
     */
    public void writeToImage() {
        imageWriter.writeToImage();
    }

    /**
     * builder class
     */
    public static class Builder {
        private final Camera camera = new Camera();

        /**
         * sets the location field in the camera field
         *
         * @param location is the location of  the camera
         * @return the builder object
         */
        public Builder setLocation(Point location) {
            camera.location = location;
            return this;
        }

        /**
         * sets the vTo and vUp fields in the camera field
         *
         * @param vTo is the vector in the direction that the camera is pointing
         * @param vUp is the vector in the direction to the top for the cameras perspective
         * @return the builder object
         */
        public Builder setDirection(Vector vTo, Vector vUp) {
            if (vTo.dotProduct(vUp) != 0)
                throw new IllegalArgumentException("the two vectors are not orthogonal to with other");
            camera.vTo = vTo.normalize();
            camera.vUp = vUp.normalize();
            return this;
        }

        /**
         * sets the height and width fields in the camera field
         *
         * @param height is the height of the view plane
         * @param width  is the width of the view plane
         * @return the builder object
         */
        public Builder setVpSize(double height, double width) {
            if (height <= 0 || width <= 0)
                throw new IllegalArgumentException("height and width must both be positive");
            camera.height = height;
            camera.width = width;
            return this;
        }

        /**
         * sets the distance field in the camera field
         *
         * @param distance is the distance between the camera and the view plane
         * @return the builder object
         */
        public Builder setVpDistance(double distance) {
            if (distance <= 0)
                throw new IllegalArgumentException("distance from camera to view plane must be positive");
            camera.distance = distance;
            return this;
        }

        /**
         * sets the imageWriter field in the camera field
         *
         * @param imageWriter is the ...
         * @return the builder object
         */
        public Builder setImageWriter(ImageWriter imageWriter) {

            camera.imageWriter = imageWriter;
            return this;
        }

        /**
         * sets the rayTracer field in the camera field
         *
         * @param rayTracer is the ...
         * @return the builder object
         */
        public Builder setRayTracer(RayTracerBase rayTracer) {

            camera.rayTracer = rayTracer;
            return this;
        }

        /**
         * sets all the fields of camera and ...
         *
         * @return a new camera object
         */
        public Camera build() {
            String renderMessage = "missing a resource for the renderer";
            if (camera.height == 0)
                throw new MissingResourceException(
                        renderMessage, "camera", "the height of the view plane is missing");
            if (camera.width == 0)
                throw new MissingResourceException(
                        renderMessage, "camera", "the width of the view plane is missing");
            if (camera.distance == 0)
                throw new MissingResourceException(
                        renderMessage, "camera", "the distance from the camera to the view plane is missing");

            if (camera.vTo == null)
                throw new MissingResourceException(
                        renderMessage, "camera", "there isn't a vector indicating where the camera is pointed to");
            if (camera.vUp == null)
                throw new MissingResourceException(
                        renderMessage, "camera", "there isn't a vector indicating where up is for the camera");
            if (camera.location == null)
                throw new MissingResourceException(
                        renderMessage, "camera", "there isn't a point indicating where the right camera is located");
            if (camera.imageWriter == null)
                throw new MissingResourceException(
                        renderMessage, "camera", "there isn't a imageWriter");
            if (camera.rayTracer == null)
                throw new MissingResourceException(
                        renderMessage, "camera", "there isn't a ratTracer");

            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();


            return camera.clone();
        }
    }


}
