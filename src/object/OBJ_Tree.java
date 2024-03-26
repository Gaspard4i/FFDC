package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Tree extends SuperObject{
    
    GamePanel gp;

    public OBJ_Tree(GamePanel gp){
        this.gp = gp;

        name = "Tree";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/tree.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        } catch (IOException e) {
            e.printStackTrace();      
        }
        collison = true;
    }
}
