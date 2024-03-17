package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

    public boolean upPressed, downPressed, leftPressed, rightPressed;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e){

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_Z){ // W pour le clavier anglais
            upPressed = true;
        }
        if(code == KeyEvent.VK_S){ // S pour le clavier anglais 
            downPressed = true;
        }
        if(code == KeyEvent.VK_Q){ // A pour le clavier anglais
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D){ // D pour le clavier anglais
            rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e){

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_Z){ // W pour le clavier anglais
            upPressed = false;
        }
        if(code == KeyEvent.VK_S){ // S pour le clavier anglais 
            downPressed = false;
        }
        if(code == KeyEvent.VK_Q){ // A pour le clavier anglais
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D){ // D pour le clavier anglais
            rightPressed = false;
        }
    }


}
