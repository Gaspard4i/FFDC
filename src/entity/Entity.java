package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    
    public int worldX, worldY;
    public int speed; // speed of the entity

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2; // sprites of the entity
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea;
    public boolean collisonOn = false;
}
