package main;

import java.net.URL;
import javax.sound.sampled.*;

/**
 * The Sound class handles sound effects in the game.
 */
public class Sound {
    
    Clip clip;
    URL soundURL[] = new URL[30];

    /**
     * Constructs a new Sound object and initializes sound URLs.
     */
    public Sound(){
        // Initialize sound URLs for different sound effects
        soundURL[0] = getClass().getResource("/sound/coin.wav");
        soundURL[1] = getClass().getResource("/sound/powerup.wav");
        soundURL[2] = getClass().getResource("/sound/unlock.wav");
        soundURL[3] = getClass().getResource("/sound/fanfare.wav");
    }

    /**
     * Sets the sound file to be played based on the provided index.
     *
     * @param i The index of the sound file to be played.
     */
    public void setFile(int i){
        try {
            // Get the audio input stream for the specified sound file
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            // Get a new clip
            clip = AudioSystem.getClip();
            // Open the audio input stream
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts playing the sound.
     */
    public void play(){
        clip.start();
    }

    /**
     * Loops the sound continuously.
     */
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Stops playing the sound.
     */
    public void stop(){
        clip.stop();
    }
}
