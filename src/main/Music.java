package main;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

/**
 * The Music class handles audio playback in the game.
 */
public class Music {

    Clip clip;
    String musicPath[] = new String[30];
    boolean audioErrorOccurred = false; // Flag to check if an error has occurred

    public Music() {
        // Paths to music files
        musicPath[0] = "resources/sound/music/BlueBoyAdventure.wav";
        musicPath[1] = "resources/sound/music/Adventure-Time-Island-Song-Instrumental.wav";

        // Attempt to load music files at startup
        for (int i = 0; i < musicPath.length; i++) {
            if (musicPath[i] != null) { // Vérifie que le chemin n'est pas null
                setFile(i);
            }
        }
    }

    /**
     * Sets the audio file to be played based on the specified index.
     *
     * @param i The index of the audio file.
     */
    public void setFile(int i) {
        try {
            File musicFile = new File(musicPath[i]);
            AudioInputStream ais = AudioSystem.getAudioInputStream(musicFile);
            clip = AudioSystem.getClip();
            clip.open(ais);
            audioErrorOccurred = false; // Reset the error flag on successful load
        } catch (UnsupportedAudioFileException e) {
            if (!audioErrorOccurred) {
                System.out.println("Unsupported audio file: " + musicPath[i]);
                audioErrorOccurred = true; // Set the flag to true to prevent multiple messages
            }
        } catch (IOException e) {
            if (!audioErrorOccurred) {
                System.out.println("Audio file not found: " + musicPath[i]);
                audioErrorOccurred = true; // Set the flag to true to prevent multiple messages
            }
        } catch (LineUnavailableException e) {
            System.out.println("Line unavailable for audio playback.");
        }
    }

    /**
     * Checks if the music clip is loaded and ready to play.
     *
     * @return true if the music is loaded; false otherwise.
     */
    public boolean isMusicLoaded() {
        return clip != null && !audioErrorOccurred;
    }

    /**
     * Starts playing the audio.
     */
    public void play() {
        if (isMusicLoaded()) {
            clip.start();
        } 
    }

    /**
     * Loops the audio continuously.
     */
    public void loop() {
        if (isMusicLoaded()) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } 
    }

    /**
     * Stops playing the audio.
     */
    public void stop() {
        if (isMusicLoaded()) {
            clip.stop();
        } 
    }
}
