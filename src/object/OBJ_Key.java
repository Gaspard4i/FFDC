package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

/**
 * The OBJ_Key class represents a key object in the game.
 */
public class OBJ_Key extends SuperObject {

    GamePanel gp;

    /**
     * Constructs a new OBJ_Key object with the specified GamePanel.
     *
     * @param gp The GamePanel object.
     */
    public OBJ_Key(GamePanel gp){
        this.gp = gp;
        
        name = "Key";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/key000.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        } catch (IOException e) {
            e.printStackTrace();        
        }
    }
}
