package com.elkard.asteroidsgame;

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
}
