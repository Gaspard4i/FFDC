package dev.gaspard.ffdc.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Centralises all music and sound-effect management for the game.
 *
 * <p>Only one background music track is active at a time. The currently
 * loaded track is automatically disposed when a new one is loaded or when
 * {@link #dispose()} is called.
 */
public final class AudioManager {

    private static final String TAG = "AudioManager";

    private Music currentMusic;

    /**
     * Loads and immediately starts looping a music track.
     *
     * <p>Any previously loaded track is disposed before the new one is played.
     *
     * @param path internal asset path to the music file (e.g. {@code "audio/music/BlueBoyAdventure.wav"})
     */
    public void playMusic(String path) {
        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic.dispose();
        }
        currentMusic = Gdx.audio.newMusic(Gdx.files.internal(path));
        currentMusic.setLooping(true);
        currentMusic.play();
        Gdx.app.log(TAG, "Playing music: " + path);
    }

    /**
     * Toggles playback between playing and paused.
     * Has no effect if no music has been loaded yet.
     */
    public void toggleMusic() {
        if (currentMusic == null) {
            return;
        }
        if (currentMusic.isPlaying()) {
            currentMusic.pause();
            Gdx.app.log(TAG, "Music paused");
        } else {
            currentMusic.play();
            Gdx.app.log(TAG, "Music resumed");
        }
    }

    /**
     * Pauses the currently playing music.
     * Has no effect if music is already paused or not loaded.
     */
    public void pauseMusic() {
        if (currentMusic != null && currentMusic.isPlaying()) {
            currentMusic.pause();
        }
    }

    /**
     * Resumes the currently paused music.
     * Has no effect if music is already playing or not loaded.
     */
    public void resumeMusic() {
        if (currentMusic != null && !currentMusic.isPlaying()) {
            currentMusic.play();
        }
    }

    /**
     * Returns {@code true} if background music is currently playing.
     *
     * @return {@code true} when music is active
     */
    public boolean isMusicPlaying() {
        return currentMusic != null && currentMusic.isPlaying();
    }

    /**
     * Releases all audio resources held by this manager.
     * Must be called when the game exits or the screen is destroyed.
     */
    public void dispose() {
        if (currentMusic != null) {
            currentMusic.stop();
            currentMusic.dispose();
            currentMusic = null;
        }
    }
}
