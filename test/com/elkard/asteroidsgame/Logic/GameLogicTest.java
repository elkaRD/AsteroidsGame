package com.elkard.asteroidsgame.Logic;

import com.elkard.asteroidsgame.Controller.IGameController;

public class GameLogicTest
{

    private class TestController implements IGameController
    {
        public TestController()
        {
            reset();
        }

        public void reset()
        {

        }

        @Override
        public void onAccelerate(float force)
        {

        }

        @Override
        public void onSlowDown(float force)
        {

        }

        @Override
        public void onTurn(float turn)
        {

        }

        @Override
        public void onStartShooting()
        {

        }

        @Override
        public void onEndShooting()
        {

        }

        @Override
        public void onPause()
        {

        }

        @Override
        public void onStartGame()
        {

        }

        @Override
        public void onCloseGame()
        {

        }

        @Override
        public float getScreenRatio()
        {
            return -1;
        }

        @Override
        public void run()
        {

        }

        @Override
        public GameLogic getGameLogic()
        {
            return null;
        }
    }
}
