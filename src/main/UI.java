package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Key;

/**
 * The UI class handles user interface elements in the game.
 */
public class UI {
    
    GamePanel gp;
    Font arial_40, arial_80B;
    BufferedImage keyImg;
    public boolean messageOn = false;
    public String message = "";
    public int messageCounter = 0;
    public boolean gameFinished = false;
    public double playTime = 0.0;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    /**
     * Constructs a new UI object with the specified GamePanel.
     *
     * @param gp The GamePanel object.
     */
    public UI(GamePanel gp){
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        OBJ_Key key = new OBJ_Key(gp);
        keyImg = key.image;
    }

    /**
     * Displays a message on the screen.
     *
     * @param text The message to be displayed.
     */
    public void showMessage(String text){
        message = text;
        messageOn = true;
    }

    /**
     * Draws UI elements on the screen.
     *
     * @param g2 The Graphics2D object used for drawing.
     */
    public void draw(Graphics2D g2){
        
        if (gameFinished) {
            // Display game completion message
            drawGameCompletion(g2);
        }else{
            // Display UI elements during gameplay
            drawGameUI(g2);
        }
    }

    /**
     * Draws UI elements during gameplay.
     *
     * @param g2 The Graphics2D object used for drawing.
     */
    private void drawGameUI(Graphics2D g2) {
        g2.setFont(arial_40);
        g2.setColor(Color.white);
        g2.drawImage(keyImg, gp.tileSize/2, gp.tileSize/2,gp.tileSize, gp.tileSize, null);
        g2.drawString("x " + gp.player.hasKey, 74, 65);
        
        // Update play time
        playTime += (double) 1/60;
        g2.drawString("Time : "+ dFormat.format(playTime),gp.tileSize*11,  65);

        // Display message if there is one
        if (messageOn) {
            g2.setFont(g2.getFont().deriveFont(30F));
            g2.drawString(message, gp.tileSize/2, gp.tileSize*5);
            
            messageCounter++;
            
            if (messageCounter > 120) {
                messageCounter = 0;
                messageOn = false;
            }
        }
    }

    /**
     * Draws the game completion message.
     *
     * @param g2 The Graphics2D object used for drawing.
     */
    private void drawGameCompletion(Graphics2D g2) {
        g2.setFont(arial_40);
        g2.setColor(Color.white);

        // Display completion message
        String text = "You found the treasure!";
        int textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - textLength/2;
        int y = gp.screenHeight/2 - (gp.tileSize*3);
        g2.drawString(text, x, y);

        // Display play time
        text = "Your time is : " + dFormat.format(playTime) + "s!";
        textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = gp.screenWidth/2 - textLength/2;
        y = gp.screenHeight/2 + (gp.tileSize*4);
        g2.drawString(text, x, y);

        // Display congratulations message
        g2.setFont(arial_80B);
        g2.setColor(Color.YELLOW);
        text = "Congratulations!";
        textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = gp.screenWidth/2 - textLength/2;
        y = gp.screenHeight/2 + (gp.tileSize*2);
        g2.drawString(text, x, y);

        // Reset player position and sprite
        gp.aSetter.setEndChest();
        gp.player.spriteNum = 1;
        gp.player.direction = "stay";
        gp.player.worldX = gp.tileSize * 23;
        gp.player.worldY = gp.tileSize * 21;

        // Stop the game thread
        gp.gameThread = null;
    }
}
