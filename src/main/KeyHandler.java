package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The KeyHandler class handles keyboard input for the game.
 */
public class KeyHandler implements KeyListener {
    GamePanel gp;

    // Variables to track key states
    public boolean upPressed, downPressed, leftPressed, rightPressed, noKeyMovePressed, restartPressed, run;

    // DEBUG
    public boolean checkDrawTime = false;
    public boolean musicOn = true;

    /**
     * Constructs a new KeyHandler object with the specified GamePanel.
     *
     * @param gp The GamePanel object.
     */
    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Unused
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // Check which key is pressed and set the corresponding boolean flag
        switch (code) {
            case KeyEvent.VK_Z:
                noKeyMovePressed = false;
                upPressed = true;
                break;
            case KeyEvent.VK_S:
                noKeyMovePressed = false;
                downPressed = true;
                break;
            case KeyEvent.VK_Q:
                noKeyMovePressed = false;
                leftPressed = true;
                break;
            case KeyEvent.VK_D:
                noKeyMovePressed = false;
                rightPressed = true;
                break;
            case KeyEvent.VK_ENTER:
                restartPressed = true;
                break;
            case KeyEvent.VK_SHIFT:
                run = true;
                break;
            case KeyEvent.VK_P:
                // Toggle game pause state
                if (gp.gameState == gp.playState) {
                    gp.gameState = gp.pauseState;
                } else if (gp.gameState == gp.pauseState) {
                    gp.gameState = gp.playState;
                }
                break;
            case KeyEvent.VK_T:
                // Toggle draw time debug mode
                checkDrawTime = !checkDrawTime;
                break;
            case KeyEvent.VK_M:
                // Toggle Music
                musicOn = !musicOn;
                if (musicOn) {
                    gp.resumeMusic();
                }else{
                    gp.stopMusic();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        // Check which key is released and reset the corresponding boolean flag
        switch (code) {
            case KeyEvent.VK_Z:
                noKeyMovePressed = true;
                upPressed = false;
                break;
            case KeyEvent.VK_S:
                noKeyMovePressed = true;
                downPressed = false;
                break;
            case KeyEvent.VK_Q:
                noKeyMovePressed = true;
                leftPressed = false;
                break;
            case KeyEvent.VK_D:
                noKeyMovePressed = true;
                rightPressed = false;
                break;
            case KeyEvent.VK_ENTER:
                restartPressed = false;
                break;
            case KeyEvent.VK_SHIFT:
                run = false;
                break;
            default:
                break;
        }
    }
}
