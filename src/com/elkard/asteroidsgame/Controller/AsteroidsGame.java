package com.elkard.asteroidsgame.Controller;

import com.elkard.asteroidsgame.Debug;
import com.elkard.asteroidsgame.Logic.GameLogic;
import com.elkard.asteroidsgame.View.GameRenderer;

public class AsteroidsGame implements IGameController
{
    private GameLogic gameLogic;
    private GameRenderer gameRenderer;

    private long prevTime;
    private boolean endGame;

    public AsteroidsGame()
    {
        startNewGame();
        gameRenderer = new GameRenderer(this);
    }

    private void startNewGame()
    {
        gameLogic = new GameLogic();
        gameLogic.attachController(this);
    }

    @Override
    public GameLogic getGameLogic()
    {
        return gameLogic;
    }

    @Override
    public void run()
    {
        System.out.println("Logic started");

        gameRenderer.showWindow();

        prevTime = System.currentTimeMillis();

        endGame = false;

        while (!endGame)
        {
            float deltaTime = getDeltaTime();

            handleInput();
            gameLogic.onUpdate(deltaTime);
            gameRenderer.onUpdate(deltaTime);
            Debug.FlushLog();
        }

        gameRenderer.cleanUp();
    }

    private float getDeltaTime()
    {
        long curTime = System.currentTimeMillis();
        long elapsedTimeMillis = curTime - prevTime;
        float deltaTime = elapsedTimeMillis/1000F;
        prevTime = curTime;

        if (deltaTime < 0.013f)
        {
            try {
                int toSleep  = (int) (13 - deltaTime * 1000);
                Thread.sleep(toSleep);

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        //TODO: just to  slow down main loop - to have cooler computer
        try {
            Thread.sleep(16);
        }catch(Exception e)
        {
            e.printStackTrace();
        }

        if (deltaTime > 0.25f) deltaTime = 0.25f;

        return deltaTime;
    }

    private void handleInput()
    {
        gameRenderer.handleInput();
    }

    @Override
    public void onAccelerate(float force)
    {
        gameLogic.onAccelerate(force);
    }

    @Override
    public void onSlowDown(float force)
    {
        gameLogic.onSlowDown(force);
    }

    @Override
    public void onTurn(float turn)
    {
        gameLogic.onTurn(turn);
    }

    @Override
    public void onStartShooting()
    {
        gameLogic.onStartShooting();
    }

    @Override
    public void onEndShooting()
    {
        gameLogic.onEndShooting();
    }

    @Override
    public void onStartGame()
    {
        gameRenderer.setScreenResolution();
    }

    @Override
    public void onPause()
    {
        gameLogic.onPause();
    }

    @Override
    public void onCloseGame()
    {
        endGame = true;
    }

    @Override
    public float getScreenRatio()
    {
        return gameRenderer.getScreenRatio();
    }
}