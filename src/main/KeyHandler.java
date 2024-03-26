package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
    GamePanel gp;

    public boolean upPressed, downPressed, leftPressed, rightPressed, noKeyMovePressed, restartPressed, run;
    // DEBUG
    public boolean checkDrawTime = false;

    public KeyHandler(GamePanel gp){
        this.gp = gp; 

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e){

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_Z){ // W pour le clavier anglais
            noKeyMovePressed = false;
            upPressed = true;
        }
        if(code == KeyEvent.VK_S ){ // S pour le clavier anglais 
            noKeyMovePressed = false;
            downPressed = true;
        }
        if(code == KeyEvent.VK_Q ){ // A pour le clavier anglais
            noKeyMovePressed = false;
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D ){ // D pour le clavier anglais
            noKeyMovePressed = false;
            rightPressed = true;
        }
        if (code == KeyEvent.VK_ENTER) { // Q pour le clavier anglais 
            restartPressed = true;
        }
        if (code == KeyEvent.VK_SHIFT) {
            run = true;
        }
        if (code == KeyEvent.VK_P) {
            if (gp.gameState == gp.playState) {
                gp.gameState = gp.pauseState;
            }else if (gp.gameState == gp.pauseState) {
                gp.gameState = gp.playState;
            }
        }

        // DEBUG 
        if(code == KeyEvent.VK_T){ 
            if (!checkDrawTime) {
                checkDrawTime = true;
            }else if (checkDrawTime) {
                checkDrawTime = false;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e){

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_Z){ // W pour le clavier anglais
            noKeyMovePressed = true;
            upPressed = false;
        }
        if(code == KeyEvent.VK_S){ // S pour le clavier anglais 
            noKeyMovePressed = true;
            downPressed = false;
        }
        if(code == KeyEvent.VK_Q){ // A pour le clavier anglais
            noKeyMovePressed = true;
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D){ // D pour le clavier anglais
            noKeyMovePressed = true;
            rightPressed = false;
        }
        if (code == KeyEvent.VK_ENTER) { // Q pour le clavier anglais 
            restartPressed = false;
        }
        if (code == KeyEvent.VK_SHIFT) {
            run = false;
        }
    }


}
