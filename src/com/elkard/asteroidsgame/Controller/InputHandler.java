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
    public static final int KEY_ESC = 27;
    public static final int KEY_SPACE = 32;
    public static final int KEY_ENTER = 10;

    public static final int KEY_UP = 38;
    public static final int KEY_DOWN = 40;
    public static final int KEY_LEFT = 37;
    public static final int KEY_RIGHT = 39;

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
                upPressed = isPressed;
                upSinglePress = isPressed || upSinglePress;
                break;

            case 'S':
                downPressed = isPressed;
                downSinglePress = isPressed || downSinglePress;
                break;

            case 'A':
                leftPressed = isPressed;
                leftSinglePress = isPressed || leftSinglePress;
                break;

            case 'D':
                rightPressed = isPressed;
                rightSinglePress = isPressed || rightSinglePress;
                break;

            case KEY_UP:
                upSinglePress = isPressed || upSinglePress;
                break;

            case KEY_DOWN:
                downSinglePress = isPressed || downSinglePress;
                break;

            case KEY_LEFT:
                leftSinglePress = isPressed || leftSinglePress;
                break;

            case KEY_RIGHT:
                rightSinglePress = isPressed || rightSinglePress;
                break;

            case KEY_ESC:
                escSinglePress = isPressed || escSinglePress;
                break;

            case KEY_SPACE:
                spaceSinglePress = isPressed || spaceSinglePress;
                spacePressed = isPressed;
                break;

            case KEY_ENTER:
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
