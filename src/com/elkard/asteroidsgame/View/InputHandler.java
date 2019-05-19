package com.elkard.asteroidsgame.View;

import com.elkard.asteroidsgame.Debug;
import com.elkard.asteroidsgame.IGameController;
import com.elkard.asteroidsgame.View.UI.ButtonsManager;
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
        Debug.Log("Pressed " + key + ", status: " + isPressed);

        switch(key)
        {
            case 'w':
            case 'W':
            case 38:
                upPressed = isPressed;
                upSinglePress = isPressed || upSinglePress;
                break;

            case 's':
            case 'S':
            case 40:
                downPressed = isPressed;
                downSinglePress = isPressed || downSinglePress;
                break;

            case 'a':
            case 'A':
            case 37:
                leftPressed = isPressed;
                leftSinglePress = isPressed || leftSinglePress;
                break;

            case 'd':
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

    @Override
    public void handleInput(IGameController asteroidsGame)
    {
        if (upPressed)
            asteroidsGame.onAccelerate(1.0f);

        if (downPressed)
            asteroidsGame.onSlowDown(1.0f);

        float turnFactor = 0f;
        if (leftPressed) turnFactor -= 1f;
        if (rightPressed) turnFactor += 1f;
        asteroidsGame.onTurn(turnFactor);

        if (spacePressed != prevShootState)
        {
            prevShootState = spacePressed;
            if (spacePressed) asteroidsGame.onStartShooting();
            else asteroidsGame.onEndShooting();
        }

        if (escSinglePress)
        {
            asteroidsGame.onPause();
        }
    }

    @Override
    public void handleInput(IButtonsManager buttonsManager)
    {
        if (upSinglePress || leftSinglePress)
        {
            buttonsManager.prevButton();
        }
        
        if (downSinglePress || rightSinglePress)
        {
            buttonsManager.nextButton();
        }

        if (spaceSinglePress || enterSinglePress)
        {
            buttonsManager.pickButton();
        }
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
