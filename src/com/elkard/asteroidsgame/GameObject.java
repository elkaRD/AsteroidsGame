package com.elkard.asteroidsgame;

public class GameObject {

    private Vec2 position;
    private float rotation;
    private String name;

    public GameObject() {

    }

    public void setPosition(Vec2 newPosition)
    {
        position = newPosition;
    }

    public Vec2 getPosition() {
        return position;
    }

    public void setRotation(float newRotation) {
        rotation = newRotation;
    }

    public float getRotation() {
        return rotation;
    }

    public String getName() {
        return name;
    }
}