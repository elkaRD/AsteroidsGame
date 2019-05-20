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
/// File: InputHandler.java


package com.elkard.asteroidsgame.Controller;

import com.elkard.asteroidsgame.View.UI.IButtonsManager;

public class InputHandler implements IInputHandler
{
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean spacePressed = false;

    private boolean upSinglePress = false;
    private boolean downSinglePress = false;
    private boolean leftSinglePress = false;
    private boolean rightSinglePress = false;

    private boolean enterSinglePress = false;
    private boolean spaceSinglePress = false;
    private boolean escSinglePress = false;

    private boolean prevShootState = false;

    @Override
    public void onKeyPressed(int key, boolean isPressed)
    {
        switch(key)
        {
            case 'W':
            case 38:
                upPressed = isPressed;
                upSinglePress = isPressed || upSinglePress;
                break;

            case 'S':
            case 40:
                downPressed = isPressed;
                downSinglePress = isPressed || downSinglePress;
                break;

            case 'A':
            case 37:
                leftPressed = isPressed;
                leftSinglePress = isPressed || leftSinglePress;
                break;

            case 'D':
            case 39:
                rightPressed = isPressed;
                rightSinglePress = isPressed || rightSinglePress;
                break;

            case 27:
                escSinglePress = isPressed || escSinglePress;
                break;

            case 32:
                spaceSinglePress = isPressed || spaceSinglePress;
                spacePressed = isPressed;
                break;

            case 10:
                enterSinglePress = isPressed || enterSinglePress;
                break;
        }
    }

    private void handleMovement(IGameController controller)
    {
        if (upPressed)
            controller.onAccelerate(1.0f);

        if (downPressed)
            controller.onSlowDown(1.0f);

        float turnFactor = 0f;
        if (leftPressed) turnFactor -= 1f;
        if (rightPressed) turnFactor += 1f;
        controller.onTurn(turnFactor);
    }

    private void handleShooting(IGameController controller)
    {
        if (spacePressed != prevShootState)
        {
            prevShootState = spacePressed;
            if (spacePressed) controller.onStartShooting();
            else controller.onEndShooting();
        }
    }

    @Override
    public void handleInput(IGameController controller)
    {
        handleMovement(controller);
        handleShooting(controller);

        if (escSinglePress)
            controller.onPause();
    }

    @Override
    public void handleInput(IButtonsManager buttonsManager)
    {
        if (upSinglePress || leftSinglePress)
            buttonsManager.prevButton();
        
        if (downSinglePress || rightSinglePress)
            buttonsManager.nextButton();

        if (spaceSinglePress || enterSinglePress)
            buttonsManager.pickButton();
    }

    @Override
    public void refresh()
    {
        upSinglePress = false;
        downSinglePress = false;
        leftSinglePress = false;
        rightSinglePress = false;

        spaceSinglePress = false;
        escSinglePress = false;
        enterSinglePress = false;
    }
}
