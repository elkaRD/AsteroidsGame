package com.elkard.asteroidsgame;

public interface ICollisionable
{
    Line[] getCollisionLines();
    float getCollisionRadius();
    Vec2 getPosition();

    void onCollisionEnter(ICollisionable other);
}
