///
/// EN: Project for the Event-Driven Programming course
///     Warsaw University of Technology
///     Java Asteroids Game
///
/// PL: Projekt PROZ (Programowanie zdarzeniowe)
///     PW WEiTI 19L
///     Gra typu Asteroids napisana w Javie
///
/// Copyright (C) Robert Dudzinski 2019
///
/// File: AsteroidsGame.java


package com.elkard.asteroidsgame.Controller;

import com.elkard.asteroidsgame.Logic.GameLogic;
import com.elkard.asteroidsgame.View.AudioManager;
import com.elkard.asteroidsgame.View.GameRenderer;
import com.elkard.asteroidsgame.View.MenuManager;
import com.elkard.asteroidsgame.View.UI.ButtonsManager;

import static com.elkard.asteroidsgame.Logic.GameLogic.GameState.MAINMENU;
import static com.elkard.asteroidsgame.Logic.GameLogic.GameState.PAUSED;

public class AsteroidsGame implements IGameController
{
    private GameLogic gameLogic;
    private GameRenderer gameRenderer;

    private long prevTime;
    private boolean endGame;

    private final IInputHandler inputHandler;

    public AsteroidsGame()
    {
        gameLogic = new GameLogic();
        gameLogic.attachController(this);

        inputHandler = new InputHandler();

        gameRenderer = new GameRenderer(this, inputHandler);

        MenuManager.getInstance().setGameLogic(gameLogic);
    }

    @Override
    public GameLogic getGameLogic()
    {
        return gameLogic;
    }

    @Override
    public void run()
    {
        gameRenderer.showWindow();
        prevTime = System.currentTimeMillis();
        endGame = false;

        while (!endGame)
        {
            float deltaTime = updateDeltaTime();

            handleInput();
            gameLogic.onUpdate(deltaTime);
            gameRenderer.onUpdate(deltaTime);
        }

        gameRenderer.cleanUp();
    }

    private float updateDeltaTime()
    {
        long curTime = System.currentTimeMillis();
        long elapsedTimeMillis = curTime - prevTime;
        float deltaTime = elapsedTimeMillis/1000F;
        prevTime = curTime;

        try
        {
            if (gameLogic.getCurState() == MAINMENU || gameLogic.getCurState() == PAUSED)
                Thread.sleep(80);

            if (deltaTime < 0.013f)
            {
                int toSleep = (int) (13 - deltaTime * 1000);
                Thread.sleep(toSleep);
            }

            //just to  slow down main loop - to have cooler computer
            Thread.sleep(16);
        }
        catch (InterruptedException ex)
        {
            ex.printStackTrace();
        }

        if (deltaTime > 0.25f) deltaTime = 0.25f;

        return deltaTime;
    }

    private void handleInput()
    {
        inputHandler.handleInput(this);
        inputHandler.handleInput(ButtonsManager.getInstance());
        inputHandler.refresh();
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