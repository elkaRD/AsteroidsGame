package com.elkard.asteroidsgame.Game;

public interface IControllable {
    void onAccelerate(float force);
    void onSlowDown(float force);
    void onRotate(float turn);
    void onStartShooting();
    void onEndShooting();
}