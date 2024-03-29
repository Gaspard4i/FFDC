package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import main.*;

/**
 * The Player class represents the player entity in the game.
 * It extends the Entity class and contains methods to update the player's position,
 * handle player interactions with objects, and draw the player on the screen.
 */
public class Player extends Entity{
    
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX ;
    public final int screenY;
    public final int spriteSpeed = 7; // speed of sprites changes 
    public int hasKey = 0;
    public boolean speedUp = false;
    public boolean hasBoots = false;

    /**
     * Constructs a new Player object with the specified GamePanel and KeyHandler.
     *
     * @param gp The GamePanel object associated with the player.
     * @param keyH The KeyHandler object for controlling player movement.
     */
    public Player(GamePanel gp, KeyHandler keyH){

        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle(); // collison rectangle

        solidArea.x = 16; // 8
        solidArea.y = 16; // 16
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 16; // 32
        solidArea.height = 28; // 32

        setDefaultValues();
        getPlayerImage();
    }

    /**
     * Sets default values for the player's position, speed, and direction.
     */
    public void setDefaultValues(){

        worldX = gp.tileSize * 23; // 23
        worldY = gp.tileSize * 21; // 21
        speed = 8; // speed of the player in pixel 
        direction = "stay"; // direction that the player iniv s facing entring the gmame
        
    }

    /**
     * Loads the player's sprite images based on the current character type (boy).
     */
    public void getPlayerImage(){

       // UP
        up1 = setup("boy_up_1");
        up2 = setup("boy_up_2");
        up3 = setup("boy_up_3");
        up4 = setup("boy_up_4");
        up5 = setup("boy_up_5");
        up6 = setup("boy_up_6");

        // DOWN
        down1 = setup("boy_down_1");
        down2 = setup("boy_down_2");
        down3 = setup("boy_down_3");
        down4 = setup("boy_down_4");
        down5 = setup("boy_down_5");
        down6 = setup("boy_down_6");

        // LEFT

        left1 = setup("boy_left_1");
        left2 = setup("boy_left_2");
        left3 = setup("boy_left_3");
        left4 = setup("boy_left_4");
        left5 = setup("boy_left_5");
        left6 = setup("boy_left_6");
        
        // RIGHT

        right1 = setup("boy_right_1");
        right2 = setup("boy_right_2");
        right3 = setup("boy_right_3");
        right4 = setup("boy_right_4");
        right5 = setup("boy_right_5");
        right6 = setup("boy_right_6");

        // STAY

        stay1 = setup("boy_stay_1");
        stay2 = setup("boy_stay_2");
        stay3 = setup("boy_stay_3");
        stay4 = setup("boy_stay_4");
        stay5 = setup("boy_stay_5");
        stay6 = setup("boy_stay_6");

    }

    /**
     * Loads a specific image file as a BufferedImage.
     *
     * @param imageName The name of the image file to load.
     * @return The loaded BufferedImage.
     */
    public BufferedImage setup(String imageName){

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {

            image = ImageIO.read(getClass().getResourceAsStream("/res/player/walking_sprites/boy/" + imageName + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        } catch (IOException e) {
            e.printStackTrace();    
        }
        return image;

    }

    /**
     * Updates the player's position based on keyboard input and handles interactions with objects.
     */
    public void update(){

        if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true || keyH.noKeyMovePressed == true) {

            if(keyH.upPressed == true){
                direction = "up";
            }else if(keyH.downPressed == true){
                direction = "down";
            }else if (keyH.leftPressed == true) {
                direction = "left";
            }else if (keyH.rightPressed == true) {
                direction = "right";
            }else if (keyH.noKeyMovePressed == true) {
                direction = "stay";
            }

            // CHECK TILE COLLISON
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // IF COLLISON IS FALSE, PLAYER CAN MOVE
            if (collisionOn == false) {
                switch (direction) {
                    case "up":worldY -= speed;
                        break;
                    case "down":worldY += speed;
                        break;
                    case "left": worldX -= speed;
                    break;
                case "right":worldX += speed;
                    break;
                default:
                    break;
            }
        }

        spriteCounter++ ;
        if (spriteCounter > spriteSpeed) {
            if (spriteNum == 1) {
                spriteNum =2;
            }else if (spriteNum ==2 ) {
                spriteNum = 3;
            }else if (spriteNum ==3 ) {
                spriteNum = 4;
            }else if (spriteNum ==4 ) {
                spriteNum = 5;
            }else if (spriteNum == 5) {
                spriteNum = 6;
            }else if (spriteNum == 6) {
                spriteNum = 1;
            }

            spriteCounter = 0;
        }

    }

}

/**
 * Handles picking up objects and updates player state accordingly.
 *
 * @param i The index of the object being picked up.
 */
public void pickUpObject(int i){

    if (i != 999) {
        
        String objectName = gp.obj[i].name;
        switch (objectName) {
            case "Key":
                gp.playSE(0);
                hasKey++;
                gp.obj[i] = null;
                gp.ui.showMessage("You got a key !");
                break;
            case "Door":
            if (hasKey > 0) {
                gp.playSE(2);
                gp.obj[i] = null;
                hasKey--;
                gp.ui.showMessage("You opened the door !");
            }else{
                gp.ui.showMessage("You need a key !");
            }
            System.out.println("Has " + hasKey +" key(s).");
            break;
            case "Boots":
                gp.playSE(1);
                hasBoots = true;
                speed +=2;
                gp.obj[i] = null;
                gp.ui.showMessage("You got boots ! Speed up !");
                break;
            case "Chest":
            if (hasKey > 0) {
                if(!gp.ui.gameFinished){
                    gp.stopMusic();
                    gp.playSE(3);
                    gp.ui.gameFinished = true;
                }
                gp.ui.showMessage("You won !");
                hasKey--;
                gp.ui.showMessage("You opened the chest !");
            }else{
                gp.ui.showMessage("You need a key !");
            }
            break;
            default:
                break;
        }
    }
}

/**
 * Draws the player on the screen using the appropriate sprite image based on the player's direction.
 *
 * @param g2 The Graphics2D object used for drawing.
 */
public void draw(Graphics2D g2){

    BufferedImage image = null;

    switch (direction) {
        case "up":
            switch (spriteNum) {
                case 1:
                    image = up1;
                    break;
                case 2:
                    image = up2;
                    break;
                case 3:
                    image = up3;
                    break;
                case 4:
                    image = up4;
                    break;
                case 5:
                    image = up5;
                    break;
                case 6:
                    image = up6;
                    break;
                default:
                    break;
            }
            break;
        case "down":
            switch (spriteNum) {
                case 1:
                    image = down1;
                    break;
                case 2:
                    image = down2;
                    break;
                case 3:
                    image = down3;
                    break;
                case 4:
                    image = down4;
                    break;
                case 5:
                    image = down5;
                    break;
                case 6:
                    image = down6;
                    break;
                default:
                    break;
            }
            break;
        case "left":
            switch (spriteNum) {
                case 1:
                    image = left1;
                    break;
                case 2:
                    image = left2;
                    break;
                case 3:
                    image = left3;
                    break;
                case 4:
                    image = left4;
                    break;
                case 5:
                    image = left5;
                    break;
                case 6:
                    image = left6;
                    break;
                default:
                    break;
            }
            break;
        case "right":
            switch (spriteNum) {
                case 1:
                    image = right1;
                    break;
                case 2:
                    image = right2;
                    break;
                case 3:
                    image = right3;
                    break;
                case 4:
                    image = right4;
                    break;
                case 5:
                    image = right5;
                    break;
                case 6:
                    image = right6;
                    break;
                default:
                    break;
            }
            break;
        case "stay":
            switch (spriteNum) {
                case 1:
                    image = stay1;
                    break;
                case 2:
                    image = stay2;
                    break;
                case 3:
                    image = stay3;
                    break;
                case 4:
                    image = stay4;
                    break;
                case 5:
                    image = stay5;
                    break;
                case 6:
                    image = stay6;
                    break;
                default:
                    break;
            }
            break;
        default:
            break;
    }

    g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

}

}   
