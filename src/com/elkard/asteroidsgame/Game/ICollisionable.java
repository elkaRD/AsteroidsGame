package com.elkard.asteroidsgame.Game;

import com.elkard.asteroidsgame.Line;
import com.elkard.asteroidsgame.Vec2;

public interface ICollisionable
{
    Line[] getCollisionLines();
    float getCollisionRadius();
    Vec2 getPosition();
    boolean isPhysicsEnabled();

    void onCollisionEnter(ICollisionable other);
}
