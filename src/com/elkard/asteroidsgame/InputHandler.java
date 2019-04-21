package com.elkard.asteroidsgame;

public class InputHandler implements IInputHandler
{
    public Boolean acceleratePressed = false;
    private boolean slowDownPressed = false;
    private boolean leftTurnPressed = false;
    private boolean rightTurnPressed = false;
    private boolean shootHeld = false;

    private boolean pressedShoot = false;
    private boolean pressedPause = false;

    private boolean prevShootState = false;

//    public void onAcceleratePressed(boolean isPressed)
//    {
//        acceleratePressed = isPressed;
//    }
//
//    public void onSlowDownPressed(boolean isPressed)
//    {
//        slowDownPressed = isPressed;
//
//    }
//
//    public void onLeftPressed(boolean isPressed)
//    {
//        leftTurnPressed = isPressed;
//    }
//
//    public void onRightPressed(boolean isPressed)
//    {
//        rightTurnPressed = isPressed;
//    }

    public void onKeyPressed(char key, boolean isPressed)
    {
        Debug.Log("Pressed " + key + ", status: " + isPressed);

        switch(key)
        {
            case 'w':
                this.acceleratePressed = isPressed;
                //acceleratePressed = true;
                break;

            case 's':
                slowDownPressed = isPressed;
                break;

            case 'a':
                leftTurnPressed = isPressed;
                break;

            case 'd':
                rightTurnPressed = isPressed;
                break;

            case 27:
                pressedPause = isPressed || pressedShoot;
                break;

            case 32:
                pressedShoot = isPressed || pressedShoot;
                shootHeld = isPressed;
                break;
        }
    }

    public void onPause(boolean isPaused)
    {
        pressedPause = true;
    }

    public void onShoot()
    {
        pressedShoot = true;
    }

    public void handleInput(IGameController asteroidsGame)
    {
        //Debug.Log("input handler addr: " + this);
        //Debug.Log("handling input");
        if (this.acceleratePressed) {
            Debug.Log("testing");
            asteroidsGame.onAccelerate(1.0f);
        }
        if (slowDownPressed) asteroidsGame.onSlowDown(1.0f);

        float turnFactor = 0f;
        if (leftTurnPressed) turnFactor -= 1f;
        if (rightTurnPressed) turnFactor += 1f;
        asteroidsGame.onTurn(turnFactor);

//        if (pressedShoot)
//        {
//            pressedShoot = false;
//            asteroidsGame.onSingleShoot();
//        }

        if (shootHeld != prevShootState)
        {
            prevShootState = shootHeld;
            if (shootHeld) asteroidsGame.onStartShooting();
            else asteroidsGame.onEndShooting();
        }

        if (pressedPause)
        {
            pressedPause = false;
            asteroidsGame.onPause();
        }
    }


}
