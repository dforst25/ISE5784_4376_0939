package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    /**
     * Test method for {@link ImageWriter#writeToImage()}.
     */
    @Test
    void testWriteToImage() {
        ImageWriter imageWriter = new ImageWriter("yellow", 800, 500);
        for (int i = 0; i < imageWriter.getNx(); i++)
            for (int j = 0; j < imageWriter.getNy(); j++)
                imageWriter.writePixel(i, j, new Color(java.awt.Color.YELLOW));
        for (int i = 0; i < imageWriter.getNx(); i += 50)
            for (int j = 0; j < imageWriter.getNy(); j++)
                imageWriter.writePixel(i, j, new Color(java.awt.Color.RED));
        for (int i = 0; i < imageWriter.getNx(); i++)
            for (int j = 0; j < imageWriter.getNy(); j += 50)
                imageWriter.writePixel(i, j, new Color(java.awt.Color.RED));
        imageWriter.writeToImage();
    }
}