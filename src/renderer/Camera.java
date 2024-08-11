package renderer;

import primitives.*;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.stream.IntStream;

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
   /* public void renderImage(int superSampling) {
        int Ny = imageWriter.getNy();
        int Nx = imageWriter.getNx();
        for (int y = 0; y < Ny; y++) {
            for (int x = 0; x < Nx; x++) {
                castRay(Nx, Ny, y, x,superSampling);
            }
        }
    }
*/






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
        /*Color sumRGB = new Color(BLACK);

        for( Ray ray : listRay)
        {
            sumRGB = sumRGB.add(rayTracer.traceRay(ray).scale(1.0/((1+superSampling)*(1+superSampling))));
        }*/
        Color sumRGB = recPixelColor(0,superSampling-1,superSampling*(superSampling-1),superSampling*superSampling-1,listRay);
        imageWriter.writePixel(row, column, sumRGB);
        Pixel.pixelDone();

    }
    private Color recPixelColor( int TL,int TR , int BL, int BR,List<Ray> listRay)
    {
        Color TLColor = rayTracer.traceRay(listRay.get(TL));
        Color TRColor = rayTracer.traceRay(listRay.get(TR));
        Color BLColor = rayTracer.traceRay(listRay.get(BL));
        Color BRColor = rayTracer.traceRay(listRay.get(BR));
        return recPixelColor(  TL, TR ,  BL,  BR, TLColor, TRColor, BLColor, BRColor, listRay);
    }

    private Color recPixelColor( int TL,int TR , int BL, int BR,Color TLColor,Color TRColor,Color BLColor,Color BRColor,List<Ray> listRay)
    {

        if (  TL + 1 >= TR  || TLColor.equals( TRColor)  && TLColor.equals(BLColor)   && TLColor.equals( BRColor) )
        {
            return TLColor.scale(0.25).add(TRColor.scale(0.25)).add(BRColor.scale(0.25)).add(BLColor.scale(0.25));
        }
        int midT = (TL + TR) / 2;
        int midL = (TL + BL) / 2;
        int mid = (TL + BR) / 2;
        int midR = (TR + BR) / 2;
        int midB = (BL + BR) / 2;

        Color midTColor = rayTracer.traceRay(listRay.get(midT));
        Color midLColor = rayTracer.traceRay(listRay.get(midL));
        Color midColor =  rayTracer.traceRay(listRay.get(mid));
        Color midRColor = rayTracer.traceRay(listRay.get(midR));
        Color midBColor = rayTracer.traceRay(listRay.get(midB));

        TLColor = recPixelColor(TL,midT,midL,mid,TLColor,midTColor,midLColor,midColor,listRay);
        TRColor = recPixelColor( midT,TR,mid,midR,midTColor,TRColor,midColor,midRColor,listRay);
        BLColor = recPixelColor(midL,mid,BL,midB, midLColor,midColor,BLColor,midBColor,listRay);
        BRColor = recPixelColor(mid,midR,midB,BR, midColor,midRColor,midBColor,BRColor,listRay);
        return TLColor.scale(0.25).add(TRColor.scale(0.25)).add(BRColor.scale(0.25)).add(BLColor.scale(0.25));

    }
    private Color recPixelColor( int length, List<Ray> listRay)//first time is 1
    {
        if(length<2)
        {
            return rayTracer.traceRay(listRay.getFirst());
        }
        Color TLColor = rayTracer.traceRay(listRay.getFirst());
        Color TRColor = rayTracer.traceRay(listRay.get(length));
        Color BLColor = rayTracer.traceRay(listRay.get(length * (length + 1)));
        Color BRColor = rayTracer.traceRay(listRay.get(length * (length+1) ));
        if ( TLColor== TRColor  && TLColor == BLColor   && TLColor== BRColor)
        {
            return TLColor.scale(0.25).add(TRColor.scale(0.25)).add(BRColor.scale(0.25)).add(BLColor.scale(0.25));
        }

        List<Ray> TL = new LinkedList<>();
        List<Ray> TR = new LinkedList<>();
        List<Ray> BL = new LinkedList<>();
        List<Ray> BR = new LinkedList<>();
        for (int i=0;i< listRay.size();++i)
        {
            if(i<(length+1)*(length/2+1) &&  i%(length+1)<=length/2)
                TL.add(listRay.get(i));
            if(i<(length+1)*(length/2+1) &&  i%(length+1)>=length/2)
                TR.add(listRay.get(i));
            if(i>=(length+1)*length/2 &&  i%(length+1)<=length/2)
                BL.add(listRay.get(i));
            if(i>=(length+1)*length/2 &&  i%(length+1)>=length/2)
                BR.add(listRay.get(i));
        }
        TLColor =  recPixelColor( length/2,  TL).scale(0.25);
        TRColor = recPixelColor(   length/2,  TR).scale(0.25);
        BLColor = recPixelColor(   length/2,  BL).scale(0.25);
        BRColor = recPixelColor(   length/2,  BR).scale(0.25);
        return TLColor.add(TRColor).add(BLColor).add(BRColor);
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

    private int threadsCount = 0; // -2 auto, -1 range/stream, 0 no threads, 1+ number of threads
    private final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
    private double printInterval = 0; // printing progress percentage interval
    public Camera setMultithreading(int threads) {
        if (threads < -2) throw new IllegalArgumentException("Multithreading must be -2 or higher");if (threads >= -1) threadsCount = threads;
        else { // == -2
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            threadsCount = cores <= 2 ? 1 : cores;
        }
        return this;
    }
    public Camera setDebugPrint(double interval) {
        printInterval = interval;
        return this;
    }

    public Camera renderImage(int superSampling) {
        final int nX = imageWriter.getNx();
        final int nY = imageWriter.getNy();
        Pixel.initialize(nY, nX, printInterval);
        if (threadsCount == 0)
            for (int i = 0; i < nY; ++i)
                for (int j = 0; j < nX; ++j)
                    castRay(nX, nY, j, i, superSampling);
        else if (threadsCount == -1) {
            IntStream.range(0, nY).parallel() //
                    .forEach(i -> IntStream.range(0, nX).parallel() //
                            .forEach(j -> castRay(nX, nY, j, i, superSampling)));
        }
        else {
                var threads = new LinkedList<Thread>();
                while (threadsCount-- > 0)
                    threads.add(new Thread(() -> {
                        Pixel pixel;
                        while ((pixel = Pixel.nextPixel()) != null)
                            castRay(nX, nY, pixel.col(), pixel.row(),superSampling);
                    }));
                for (var thread : threads) thread.start();
                try { for (var thread : threads) thread.join(); } catch (InterruptedException ignore) {}}
            return this;
        }
}
