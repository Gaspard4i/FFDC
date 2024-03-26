package object;

import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

/**
 * The OBJ_Boots class represents a boots object in the game.
 * It extends the SuperObject class.
 */
public class OBJ_Boots extends SuperObject {

    GamePanel gp;

    /**
     * Constructs a new OBJ_Boots object with the specified GamePanel.
     *
     * @param gp The GamePanel object.
     */
    public OBJ_Boots(GamePanel gp){
        this.gp = gp;
        name = "Boots";

        try {
            // Load the image for the boots object
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/boots.png"));
            // Scale the image to match the tile size
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        } catch (IOException e) {
            e.printStackTrace();      
        }
        // Disable collision for the boots object
        collision = false;
    }
}
