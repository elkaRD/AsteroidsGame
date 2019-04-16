package com.elkard.asteroidsgame;

public interface IGameController
{
    public void onAccelerate(float force);
    public void onSlowDown(float force);
    public void onTurn(float turn);
    public void onShoot();
    public void onPause();
}
