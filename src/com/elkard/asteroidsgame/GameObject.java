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
    protected PolarLayout[] renderPoints()
    {
        return null;
    }

    public final Line[] getRenderLines()
    {
        PolarLayout[] definedPoints = renderPoints();
        Vec2[] renderPoints = new Vec2[definedPoints.length];
        Line[] renderLines = new Line[definedPoints.length];

        for (int i = 0; i < renderPoints.length; i++)
        {
            renderPoints[i] = new Vec2(getPosition());
            renderPoints[i].x += (float) Math.cos(Math.toRadians(definedPoints[i].rot + getRotation())) * definedPoints[i].dst;
            renderPoints[i].y += (float) Math.sin(Math.toRadians(definedPoints[i].rot + getRotation())) * definedPoints[i].dst;
        }

        for (int i = 0; i < renderLines.length; i++)
        {

            int j = (i+1) % renderLines.length;
            renderLines[i] = new Line();
            renderLines[i].b = renderPoints[i];
            renderLines[i].e = renderPoints[j];
        }

        return renderLines;
    }
}