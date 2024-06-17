package renderer;

import primitives.*;

import java.util.MissingResourceException;

import static primitives.Util.isZero;


public class Camera implements Cloneable{
    /**the location of the camera*/
    private Point location;
    /**right for the cameras perspective*/
    private Vector vRight;
    /**upwards for the cameras perspective */
    private Vector vUp;
    /**the direction in which the camera is pointed*/
    private Vector vTo;
    /**the height of the view plane(actual units of the axis)*/
    private double height = 0.0;
    /**the width of the view plane(actual units of the axis)*/
    private double width = 0.0;
    /**the distance between the camera location and the view plane(actual units of the axis)*/
    private double distance = 0.0;

    /**
     *default constructor used only by the builder
     */
    private Camera() {
    }

    /**
     * @return a new builder object
     */
    public static Builder getBuilder()
    {
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
    /** */
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
     * builder class
     */
    public static class Builder {
        private final Camera camera = new Camera();

       /**
        * sets the location field in the camera field
        * @param location is the location of  the camera
        * @returns the builder object
        */
        public Builder setLocation(Point location) {
            camera.location = location;
            return this;
        }

       /**
        * sets the vTo and vUp fields in the camera field
        * @param vTo is the vector in the direction that the camera is pointing
        * @param vUp is the vector in the direction to the top for the cameras perspective
        * @return the builder object
        */
       public Builder setDirection(Vector vTo, Vector vUp) {
            if(vTo.dotProduct(vUp) != 0)
                throw new IllegalArgumentException("the two vectors are not orthogonal to with other");
            camera.vTo = vTo.normalize();
            camera.vUp = vUp.normalize();
           return this;
       }

       /**
        * sets the height and width fields in the camera field
        * @param height is the height of the view plane
        * @param width is the width of the view plane
        * @return the builder object
        */
       public Builder setVpSize(double height, double width) {
            if(height <= 0 || width <= 0)
                throw new IllegalArgumentException("height and width must both be positive");
            camera.height = height;
            camera.width = width;
           return this;
       }

       /**
        * sets the distance field in the camera field
        * @param distance is the distance between the camera and the view plane
        * @return the builder object
        */
       public Builder setVpDistance(double distance) {
           if(distance <= 0)
               throw new IllegalArgumentException("distance from camera to view plane must be positive");
            camera.distance = distance;
           return this;
       }

       /**
        *  sets all the fields of camera and ...
        * @return a new camera object
        */
       public Camera build() {
           String renderMessage = "missing a resource for the renderer";
           if(camera.height==0)
               throw new MissingResourceException(
                       renderMessage, "camera", "the height of the view plane is missing");
           if(camera.width==0)
               throw new MissingResourceException(
                       renderMessage, "camera", "the width of the view plane is missing");
           if(camera.distance==0)
               throw new MissingResourceException(
                       renderMessage, "camera", "the distance from the camera to the view plane is missing");

           if(camera.vTo == null)
               throw new MissingResourceException(
                       renderMessage, "camera","there isn't a vector indicating where the camera is pointed to");
           if(camera.vUp == null)
               throw new MissingResourceException(
                       renderMessage, "camera","there isn't a vector indicating where up is for the camera");
           if(camera.location == null)
               throw new MissingResourceException(
                       renderMessage, "camera","there isn't a point indicating where the right camera is located");

           camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();


           return (Camera) camera.clone();
       }
    }
    /**
     *
     * @param nX is the amount of pixels for the width of the view plane (how many columns)
     * @param nY is the amount of pixels for the height of the view plane (how many rows)
     * @param j is the column of the pixel (of type int)
     * @param i is the row of the pixel (of type int)
     * @return the ray going from the camera location to the point on the view plane
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        //pc represents center of the view plain
        Point pC = location.add(vTo.scale(distance));
        //Ratio (pixel width and height)
        double rY = height/nY;
        double rX = width/nX;
        //center of pixel[i,j]
        double yI = -(i - (nY-1)/2.0)*rY;
        double xJ = (j - (nX-1)/2.0)*rX;
        Point pIJ;

        pIJ  = pC;
        if(!isZero(yI))
            pIJ = pIJ.add(vUp.scale(yI));
        if(!isZero(xJ))
            pIJ = pIJ.add(vRight.scale(xJ));


        //vector of the direction from camera to the center of the given pixel
        Vector vIJ = pIJ.subtract(location).normalize();
        return new Ray(location, vIJ);
    }
}
