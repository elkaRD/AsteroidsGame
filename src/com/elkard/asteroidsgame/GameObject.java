package com.elkard.asteroidsgame;

public class GameObject {

    private Vec2 position = new Vec2();
    private float rotation;
    private String name = "";

    protected GameLogic gameEngine;

    public GameObject(GameLogic gl)
    {
        gameEngine = gl;
        gameEngine.registerObject(this);
    }

    public void cleanUp()
    {
        gameEngine.removeObject(this);
    }

    public void setPosition(Vec2 newPosition)
    {
        position = new Vec2(newPosition);
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
        return new Vec2(position);
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

    //TODO: think about this concept
//    protected Vec2[] renderPoints()
//    {
//
//    }
//
//    public Vec2[] getRenderPoints()
//    {
//        Vec2[] renderPoints = renderPoints();
//        for (Vec2 p : renderPoints)
//            p.add(getPosition());
//
//
//    }
}