package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * The Entity class represents a basic entity in the game world.
 * It includes attributes and methods common to all entities.
 */
public class Entity {
    
    /** The x-coordinate of the entity in the game world. */
    public int worldX;
    
    /** The y-coordinate of the entity in the game world. */
    public int worldY;
    
    /** The speed of the entity. */
    public int speed;
    
    // Sprites for movement in different directions
    public BufferedImage up1, up2, up3, up4, up5, up6;
    public BufferedImage down1, down2, down3, down4, down5, down6;
    public BufferedImage left1, left2, left3, left4, left5, left6;
    public BufferedImage right1, right2, right3, right4, right5, right6;
    public BufferedImage stay1, stay2, stay3, stay4, stay5, stay6;
    
    /** The current direction of the entity. */
    public String direction;
    
    /** The current sprite counter. */
    public int spriteCounter = 0;
    
    /** The total number of sprites. */
    public int spriteNum = 1;
    
    /** The collision area of the entity. */
    public Rectangle solidArea;
    
    /** The default x-coordinate of the solid area. */
    public int solidAreaDefaultX;
    
    /** The default y-coordinate of the solid area. */
    public int solidAreaDefaultY;
    
    /** A boolean indicating if collision detection is enabled for the entity. */
    public boolean collisionOn = false;
}
