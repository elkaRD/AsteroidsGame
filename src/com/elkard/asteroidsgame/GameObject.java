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

    public void setPosition(float x, float y)
    {
        position = new Vec2(x, y);
    }

    public void move(Vec2 deltaPosition)
    {
        position.add(deltaPosition);
    }

    public Vec2 getPosition() {
        return position;
    }

    public void setRotation(float newRotation) {
        rotation = newRotation;
    }

    public void updateRotation(float deltaRotation)
    {
        rotation += deltaRotation;
    }

    public float getRotation() {
        return rotation;
    }

    public String getName() {
        return name;
    }

    public void resetTransform()
    {
        position = new Vec2(0, 0);
        rotation = 0;
    }

    public void update(float delta)
    {

    }
}