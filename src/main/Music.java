package main;

import java.net.URL;

import javax.sound.sampled.*;


public class Music {
    
    Clip clip;
    URL musicURL[] = new URL[30];

    public Music(){

        musicURL [0] = getClass().getResource ("/res/sound/BlueBoyAdventure.wav") ;

    }

    public void setFile(int i){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(musicURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            
        } catch (Exception e) {
        }

    }

    public void play(){
        clip.start();
    }

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        clip.stop();
    }
}
