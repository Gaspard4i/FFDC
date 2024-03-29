package main;

import javax.swing.JFrame;

/**
 * The Main class initializes and starts the game.
 */
public class Main {
    
    /**
     * The main method, which is the entry point of the program.
     * 
     * @param args The command-line arguments (not used in this program).
     */
    public static void main(String[] args) {
        
        // Create a new JFrame window
        JFrame window = new JFrame(); 
        
        // Set the default close operation for the window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Disable window resizing
        window.setResizable(false);
        
        // Set the title of the window
        window.setTitle("FinnFoundDaChest");
        
        // Create a new GamePanel instance
        GamePanel gamePanel = new GamePanel();
        
        // Add the GamePanel to the JFrame window
        window.add(gamePanel);
        
        // Pack the components of the window
        window.pack();
        
        // Center the window on the screen
        window.setLocationRelativeTo(null);
        
        // Make the window visible
        window.setVisible(true);
        
        // Setup the game in the GamePanel
        gamePanel.setupGame();
        
        // Start the game thread in the GamePanel
        gamePanel.startGameThread();
    }
}
