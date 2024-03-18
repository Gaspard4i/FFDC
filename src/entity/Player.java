package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
    
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX ;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH){

        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle(); // collison rectangle

        solidArea.x = 16; // 8
        solidArea.y = 16; // 16
        solidArea.width = 16; // 32
        solidArea.height = 28; // 32

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){


        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 5; // speed of the player in pixel 
        direction = "down"; // direction that the player is facing entring the gmame
    }

    public void getPlayerImage(){
        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/player/walking_sprites/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/player/walking_sprites/boy_up_2.png"));
            up3 = ImageIO.read(getClass().getResourceAsStream("/res/player/walking_sprites/boy_up_3.png"));
            up4 = ImageIO.read(getClass().getResourceAsStream("/res/player/walking_sprites/boy_up_4.png"));
            up5 = ImageIO.read(getClass().getResourceAsStream("/res/player/walking_sprites/boy_up_5.png"));
            up6 = ImageIO.read(getClass().getResourceAsStream("/res/player/walking_sprites/boy_up_6.png"));


            down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/walking_sprites/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/walking_sprites/boy_down_2.png"));
            down3 = ImageIO.read(getClass().getResourceAsStream("/res/player/walking_sprites/boy_down_3.png"));
            down4 = ImageIO.read(getClass().getResourceAsStream("/res/player/walking_sprites/boy_down_4.png"));
            down5 = ImageIO.read(getClass().getResourceAsStream("/res/player/walking_sprites/boy_down_5.png"));
            down6 = ImageIO.read(getClass().getResourceAsStream("/res/player/walking_sprites/boy_down_6.png"));


            left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/walking_sprites/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/player/walking_sprites/boy_left_2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/res/player/walking_sprites/boy_left_3.png"));
            left4 = ImageIO.read(getClass().getResourceAsStream("/res/player/walking_sprites/boy_left_4.png"));
            left5 = ImageIO.read(getClass().getResourceAsStream("/res/player/walking_sprites/boy_left_5.png"));
            left6 = ImageIO.read(getClass().getResourceAsStream("/res/player/walking_sprites/boy_left_6.png"));


            right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/walking_sprites/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/player/walking_sprites/boy_right_2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/res/player/walking_sprites/boy_right_3.png"));
            right4 = ImageIO.read(getClass().getResourceAsStream("/res/player/walking_sprites/boy_right_4.png"));
            right5 = ImageIO.read(getClass().getResourceAsStream("/res/player/walking_sprites/boy_right_5.png"));
            right6 = ImageIO.read(getClass().getResourceAsStream("/res/player/walking_sprites/boy_right_6.png"));


            stay1 = ImageIO.read(getClass().getResourceAsStream("/res/player/walking_sprites/boy_stay_1.png"));
            stay2 = ImageIO.read(getClass().getResourceAsStream("/res/player/walking_sprites/boy_stay_2.png"));
            stay3 = ImageIO.read(getClass().getResourceAsStream("/res/player/walking_sprites/boy_stay_3.png"));
            stay4 = ImageIO.read(getClass().getResourceAsStream("/res/player/walking_sprites/boy_stay_4.png"));
            stay5 = ImageIO.read(getClass().getResourceAsStream("/res/player/walking_sprites/boy_stay_5.png"));
            stay6 = ImageIO.read(getClass().getResourceAsStream("/res/player/walking_sprites/boy_stay_6.png"));

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
            

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
            collisonOn = false;
            gp.cChecker.checkTile(this);

            // IF COLLISON IS FALSE, PLAYER CAN MOVE
            if (collisonOn == false) {
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
            if (spriteCounter > 7) {
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
