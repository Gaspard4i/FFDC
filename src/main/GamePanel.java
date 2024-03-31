package main;

import tile.*;
import object.*;
import entity.*;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * The GamePanel class represents the main panel where the game is rendered and played.
 * It extends the JPanel class and implements the Runnable interface for multi-threading.
 */
public class GamePanel extends JPanel implements Runnable {
    // SCREEN PARAMETERS
    public final int originalTileSize = 16; // 16x16 tile
    public final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile

    public final int maxSceenCol = 16; // 16 
    public final int maxScreenRow = 12; // 12
    public final int screenWidth = tileSize * maxSceenCol; // 768 pix
    public final int screenHeight = tileSize * maxScreenRow; // 576 pix

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    // public final int worldWidth = tileSize * maxSceenCol;
    // public final int worldHeight = tileSize * maxScreenRow;

    // FPS
    int FPS = 60;

    // SYSTEM
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);

    // SOUND & MUSIC
    Sound se = new Sound();
    Music music = new Music();

    // ABOUT OBJECT
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssertSetter aSetter = new AssertSetter(this);

    // UI
    public UI ui = new UI(this);
    
    // GAME THREAD
    public Thread gameThread;
    
    // PLAYER & OBJECT
    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[300];

    // GAME STATE 
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;

    /**
     * Constructs a new GamePanel object.
     * Initializes the panel dimensions, background color, and other necessary settings.
     */
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    /**
     * Sets up the initial state of the game.
     * Initializes game objects, music, and game state.
     */
    public void setupGame() {
        // OBJECTS
        aSetter.setObject();

        // MUSIC 
        playMusic(1);
        // stopMusic();

        // GAME STATE
        gameState = playState;
    }

    /**
     * Starts the game thread.
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // /**
    //  * Pauses the game.
    //  */
    // public void pauseGame() {
    //     // TODO Implement pause logic here
    // }
    @Override
    /**
     * The main run loop of the game.
     * Implements a delta loop for updating game state and rendering graphics at a consistent frame rate.
     */
    public void run() {
        double drawInterval = 1000000000 / FPS ;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        
        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime) ;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    /**
     * Updates the game state.
     * Checks for input and updates player position accordingly.
     */
    public void update() {
        if (gameState == playState) {
            player.update();
            if (keyH.musicOn) {
                music.play();
            }

        }
        if (gameState == pauseState) {
            if (keyH.musicOn) {
                music.stop();
            }
            // nothing for now
        }   
    }

    /**
     * Renders graphics onto the panel.
     * Draws tiles, objects, player, UI, and debug information.
     * @param g the Graphics object used for rendering
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // DEBUG
        long drawStart = 0;
        if (keyH.checkDrawTime) {
            drawStart = System.nanoTime();
        }

        // TILE
        tileM.draw(g2); // init le terrain

        // OBJECT
        for (int i = 0; i < obj.length; i++) {
            if(obj[i] != null) obj[i].draw(g2, this);
        }

        // PLAYER
        player.draw(g2);
        
        // UI 
        ui.draw(g2);    

        // DEBUG
        if (keyH.checkDrawTime) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time : " + passed , 10, 400);       
            g2.drawString("x : " + player.worldX/tileSize , 10,  400 + tileSize);
            g2.drawString("y : " +  player.worldY/tileSize, 10, 400 + tileSize*2);
            System.out.println("Draw Time: " + passed); 
        }

        // END
        g2.dispose();
    }

    /**
     * Restarts the game.
     * Stops the game thread, creates a new player object, and sets up the game again.
     */
    public void restartGame() {
        this.gameThread = new Thread();
        this.player = new Player(this, this.keyH);
        this.setupGame();   
    }

    /**
     * Plays the specified music track.
     * @param i the index of the music track to play
     */
    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void resumeMusic(){
        music.play();
    }

    /**
     * Stops the currently playing music.
     */
    public void stopMusic() {
        music.stop();
    }

    /**
     * Stops the currently playing sound effect.
     */
    public void stopSound() {
        se.stop();
    }

    /**
     * Plays the specified sound effect.
     * @param i the index of the sound effect to play
     */
    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }
}
