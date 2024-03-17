package main;

import javax.sound.midi.Track;
import javax.swing.JPanel;
import javax.swing.text.PlainDocument;
import javax.swing.text.Style;

import entity.Player;
import tile.Tile;
import tile.TileManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.SystemTray;

public class GamePanel extends JPanel implements Runnable{
    // PARAMETRE DE L'ECRAN
    public final int originalTileSize = 16; // 16x16 tile
    public final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile

    final int maxSceenCol = 16; // 16 
    final int maxScreenRow = 12; // 12
    final int screenWidth = tileSize * maxSceenCol; // 768 pix
    final int screenHeight = tileSize * maxScreenRow; // 576 pix


    // FPS
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameTrThread;
    Player player = new Player(this, keyH);

    // Set player's default position
    int playerX = player.x ; 
    int playerY = player.y ;
    int playerSpeed = player.speed ;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread(){

        gameTrThread = new Thread(this);
        gameTrThread.start();
    }


    @Override
    // DELTA LOOP METHOD
    public void run() {

        double drawInterval = 1000000000 / FPS ;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        
        while (gameTrThread != null) {
            
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

   public void update(){
    player.update();
   }

    public void paintComponent(Graphics g){

        super.paintComponent(g);
        
        Graphics2D  g2 = (Graphics2D)g;

        tileM.draw(g2); // init le terrain
        player.draw(g2);

        g2.dispose();


    }
}
