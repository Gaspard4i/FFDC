package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

/**
 * The OBJ_Door class represents a door object in the game.
 */
public class OBJ_Door extends SuperObject{
    
    GamePanel gp;

    /**
     * Constructs a new OBJ_Door object with the specified GamePanel.
     *
     * @param gp The GamePanel object.
     */
    public OBJ_Door(GamePanel gp){
        this.gp = gp;

        name = "Door";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        } catch (IOException e) {
            e.printStackTrace();      
        }
        collision = true;
    }
}
