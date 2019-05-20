package com.elkard.asteroidsgame.Logic;

public interface IControllable {
    void onAccelerate(float force);
    void onSlowDown(float force);
    void onRotate(float turn);
    void onStartShooting();
    void onEndShooting();
}