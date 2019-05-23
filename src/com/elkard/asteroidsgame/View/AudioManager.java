package com.elkard.asteroidsgame.View;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.HashMap;

public class AudioManager
{
    public static final Integer CLICK = 1;
    public static final Integer SELECT = 2;
    public static final Integer SHOT = 3;
    public static final Integer EXPLOSION = 4;
    public static final Integer PAUSE = 5;

    private static AudioManager instance = new AudioManager();

    public static AudioManager getInstance()
    {
        return instance;
    }

    private HashMap<Integer, Clip> sounds = new HashMap<Integer, Clip>();

    private AudioManager()
    {
        loadSound(CLICK, "click.wav");
        loadSound(SELECT, "select.wav");
        loadSound(SHOT, "bullet.wav");
        loadSound(EXPLOSION, "explosion.wav");
        loadSound(PAUSE, "select.wav");
    }

    private void loadSound(int id, String fileName)
    {
        try {
            String soundName = fileName;
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            sounds.put(id, clip);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void playSound(int id)
    {
        Clip clip = sounds.get(id);
        if (clip == null) return;

        clip.setMicrosecondPosition(0);
        clip.start();

//        Thread thread = new Thread()
//        {
//            public void run()
//            {
//                clip.setMicrosecondPosition(0);
//                clip.start();
//            }
//        };
//
//        thread.start();
    }
}