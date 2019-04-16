package com.elkard.asteroidsgame;

public class InputHandler implements IInputHandler
{
    private boolean acceleratePressed = false;
    private boolean slowDownPressed = false;
    private boolean leftTurnPressed = false;
    private boolean rightTurnPressed = false;

    private boolean pressedShoot = false;
    private boolean pressedPause = false;

    public void onAcceleratePressed(boolean isPressed)
    {
        acceleratePressed = isPressed;
    }

    public void onSlowDownPressed(boolean isPressed)
    {
        slowDownPressed = isPressed;

    }

    public void onLeftPressed(boolean isPressed)
    {
        leftTurnPressed = isPressed;
    }

    public void onRightPressed(boolean isPressed)
    {
        rightTurnPressed = isPressed;
    }

    public void onPause(boolean isPaused)
    {
        pressedPause = true;
    }

    public void onShoot()
    {
        pressedShoot = true;
    }

    public void HandleInput(IGameController asteroidsGame)
    {
        if (acceleratePressed) asteroidsGame.onAccelerate(1.0f);
        if (slowDownPressed) asteroidsGame.onSlowDown(1.0f);

        float turnFactor = 0f;
        if (leftTurnPressed) turnFactor += 1f;
        if (rightTurnPressed) turnFactor -= 1f;
        asteroidsGame.onTurn(turnFactor);

        if (pressedShoot)
        {
            pressedShoot = false;
            asteroidsGame.onShoot();
        }

        if (pressedPause)
        {
            pressedPause = false;
            asteroidsGame.onPause();
        }
    }


}
