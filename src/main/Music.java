package main;

import java.net.URL;

import javax.sound.sampled.*;

/**
 * The Music class handles audio playback in the game.
 */
public class Music {
    
    Clip clip;
    URL musicURL[] = new URL[30];

    /**
     * Constructs a Music object and initializes the URLs for music files.
     */
    public Music(){

        musicURL [0] = getClass().getResource ("/res/sound/BlueBoyAdventure.wav") ;

    }

    /**
     * Sets the audio file to be played based on the specified index.
     *
     * @param i The index of the audio file.
     */
    public void setFile(int i){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(musicURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Starts playing the audio.
     */
    public void play(){
        clip.start();
    }

    /**
     * Loops the audio continuously.
     */
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Stops playing the audio.
     */
    public void stop(){
        clip.stop();
    }
}

