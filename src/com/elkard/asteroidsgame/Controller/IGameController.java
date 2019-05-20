package com.elkard.asteroidsgame.Controller;

import com.elkard.asteroidsgame.Logic.GameLogic;

public interface IGameController
{
    void onAccelerate(float force);
    void onSlowDown(float force);
    void onTurn(float turn);

    void onStartShooting();
    void onEndShooting();
    void onPause();

    void onStartGame();
    void onCloseGame();

    float getScreenRatio();

    void run();

    GameLogic getGameLogic();
}
