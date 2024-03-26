package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * The UtilityTool class provides utility methods for image manipulation.
 */
public class UtilityTool {
    
    /**
     * Scales the provided image to the specified width and height.
     *
     * @param original The original image to be scaled.
     * @param width The width of the scaled image.
     * @param height The height of the scaled image.
     * @return The scaled BufferedImage.
     */
    public BufferedImage scaleImage(BufferedImage original, int width, int height){
        // Create a new BufferedImage with the specified width and height
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        // Create a Graphics2D object for drawing on the scaled image
        Graphics2D g2 = scaledImage.createGraphics();
        // Draw the original image onto the scaled image with the specified width and height
        g2.drawImage(original, 0, 0, width, height, null);
        // Dispose of the Graphics2D object
        g2.dispose();
        // Return the scaled image
        return scaledImage;
    }
}
