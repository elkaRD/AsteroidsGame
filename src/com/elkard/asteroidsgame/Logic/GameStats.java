package com.elkard.asteroidsgame.Logic;

import com.elkard.asteroidsgame.FileIO;

public class GameStats
{
    private static GameStats instance;

    public static GameStats getInstance()
    {
        if (instance == null)
            instance = new GameStats();

        return instance;
    }

    private String dataFileName = "stats";
    private int highScore = 0;

    private GameStats()
    {
        readStats();
    }

    public void readStats()
    {
        String data = FileIO.read(dataFileName);
        if (data.length() == 0)
            data = "0";
        highScore = Integer.parseInt(data);
    }

    public void writeStats()
    {
        FileIO.save(dataFileName, Integer.toString(highScore));
    }

    public void setHighScore(int highScore)
    {
        this.highScore = highScore;
        writeStats();
    }

    public int getHighScore()
    {
        return highScore;
    }
}
