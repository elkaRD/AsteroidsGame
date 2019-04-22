package com.elkard.asteroidsgame;

public interface ICollisionable
{
    Line[] getCollisionLines();
    float getCollisionRadius();
    Vec2 getPosition();
    boolean isPhysicsEnabled();

    void onCollisionEnter(ICollisionable other);
}
