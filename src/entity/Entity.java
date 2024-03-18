package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    
    public int worldX, worldY;
    public int speed; // speed of the entity

    public BufferedImage up1, up2, up3, up4, up5, up6; // sprites up for the entity
    public BufferedImage down1, down2, down3, down4, down5, down6; // sprites down for the entity
    public BufferedImage left1, left2, left3, left4, left5, left6; // sprites left for the entity
    public BufferedImage right1, right2, right3, right4, right5, right6; // sprites right for the entity
    public BufferedImage stay1, stay2, stay3, stay4, stay5, stay6; // sprites who stay for the entity
        public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea;
    public boolean collisonOn = false;
}
