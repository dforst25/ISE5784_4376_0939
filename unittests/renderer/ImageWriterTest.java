package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static java.awt.Color.*;


class ImageWriterTest {

    /**
     * Test method for {@link ImageWriter#writeToImage()}.
     */
    @Test
    void testWriteToImage() {
        ImageWriter imageWriter = new ImageWriter("yellow", 800, 500);
        int nx = imageWriter.getNx();
        int ny = imageWriter.getNy();
        for (int i = 0; i < nx; i++)
            for (int j = 0; j < ny; j++)
                imageWriter.writePixel(i, j, new Color(YELLOW));
        for (int i = 0; i < nx; i += 50)
            for (int j = 0; j < ny; j++)
                imageWriter.writePixel(i, j, new Color(RED));
        for (int i = 0; i < nx; i++)
            for (int j = 0; j < ny; j += 50)
                imageWriter.writePixel(i, j, new Color(RED));
        imageWriter.writeToImage();
    }
}