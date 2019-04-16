package com.elkard.asteroidsgame;

public interface IControllable {
    public void onAccelerate(float force);

    public void onSlowDown(float force);
}