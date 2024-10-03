package main;

import javax.sound.sampled.*;
import java.io.File;
import java.util.ArrayList;

/**
 * The Sound class handles sound effects in the game.
 */
public class Sound {
    
    private Clip clip;
    private ArrayList<String> soundFiles;

    /**
     * Constructs a new Sound object and initializes the sound file list.
     */
    public Sound() {
        soundFiles = new ArrayList<>();
        
        // Remplace les chemins par des chemins absolus ou relatifs
        soundFiles.add("resources/sound/yay.wav");
        soundFiles.add("resources/sound/powerup.wav");
        soundFiles.add("resources/sound/unlock.wav");
        soundFiles.add("resources/sound/fanfare.wav");
    }

    /**
     * Sets the sound file to be played based on the provided index.
     *
     * @param i The index of the sound file to be played.
     */
    public void setFile(int i) {
        if (i < 0 || i >= soundFiles.size()) {
            System.out.println("Index invalide : " + i);
            return;
        }
        
        try {
            // Get the audio input stream for the specified sound file
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(soundFiles.get(i)));
            // Get a new clip
            clip = AudioSystem.getClip();
            // Open the audio input stream
            clip.open(ais);
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement du fichier audio : " + soundFiles.get(i) + '\n' + e.getMessage());
        }
    }

    /**
     * Starts playing the sound.
     */
    public void play() {
        if (clip != null) {
            clip.start();
        }
    }

    /**
     * Loops the sound continuously.
     */
    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * Stops playing the sound.
     */
    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }
}
