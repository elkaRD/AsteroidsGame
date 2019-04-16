package com.elkard.asteroidsgame;

public class PlayerSpaceship extends GameObject implements IControllable {

    private Vec2 curVelocity;

    public PlayerSpaceship()
    {
        reset();
    }

    public void reset()
    {
        curVelocity = new Vec2(0, 0);
    }

    public void onAccelerate(float force) {

    }

    public void onSlowDown(float force) {

    }
}