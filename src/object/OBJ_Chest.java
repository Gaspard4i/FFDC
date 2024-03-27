package object;

import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

/**
 * The OBJ_Chest class represents a chest object in the game.
 * It extends the SuperObject class.
 */
public class OBJ_Chest extends SuperObject {

    GamePanel gp;

    /**
     * Constructs a new OBJ_Chest object with the specified GamePanel.
     *
     * @param gp The GamePanel object.
     */
    public OBJ_Chest(GamePanel gp){
        this.gp = gp;
        name = "Chest";

        try {
            // Load the image for the chest object
            image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
            // Scale the image to match the tile size
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        } catch (IOException e) {
            e.printStackTrace();
        }
        // Enable collision for the chest object
        collision = true;
    }
}
