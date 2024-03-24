package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

    public boolean upPressed, downPressed, leftPressed, rightPressed, noKeyMovePressed;
    // DEBUG
    public boolean checkDrawTime = false;

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
    }


}
