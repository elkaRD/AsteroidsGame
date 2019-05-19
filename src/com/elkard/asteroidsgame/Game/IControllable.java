package com.elkard.asteroidsgame.Game;

public interface IControllable {
    public void onAccelerate(float force);

    public void onSlowDown(float force);
}