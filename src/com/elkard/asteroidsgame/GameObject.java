package com.elkard.asteroidsgame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class GameObject implements ICollisionable{

    private Vec2 position = new Vec2();
    private float rotation = 0f;
    private float scale = 1f;

    private String name = "";

    private boolean infinitySpace = true;

    private float halfOfDimension = 0;

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

    public final void setPosition(Vec2 newPosition)
    {
        position = new Vec2(newPosition);
    }

    public final void setPosition(float x, float y)
    {
        if (infinitySpace) checkPosition();
        position = new Vec2(x, y);
    }

    public final void move(Vec2 deltaPosition)
    {
        if (infinitySpace) checkPosition();
        position.add(deltaPosition);
    }

    public final Vec2 getPosition() {
        return position.clone();
    }

    public final void setRotation(float newRotation) {
        rotation = newRotation;
    }

    public final void updateRotation(float deltaRotation)
    {
        rotation += deltaRotation;
    }

    public final float getRotation() {
        return rotation;
    }

    public String getName()
    {
        return name;
    }

    protected void setName(String newName)
    {
        name = newName;
    }

    public final float getScale()
    {
        return scale;
    }

    public final void setScale(float newScale)
    {
        scale = newScale;
    }

    public void resetTransform()
    {
        position = new Vec2(0, 0);
        rotation = 0;
        scale = 1f;
    }

    private void checkPosition()
    {
        if (infinitySpace) {
            while (position.x > gameEngine.screenWidth)
                position.x -= gameEngine.screenWidth;

            while (position.x < 0)
                position.x += gameEngine.screenWidth;

            while (position.y > gameEngine.screenHeight)
                position.y -= gameEngine.screenHeight;

            while (position.y < 0)
                position.y += gameEngine.screenHeight;
        }
    }

    public final void updateObject(float delta)
    {
        update(delta);
    }

    public void update(float delta)
    {

    }

    protected PolarLayout[] renderPoints()
    {
        return new PolarLayout[0];
    }

    public final Line[] getRenderLines()
    {
        PolarLayout[] definedPoints = renderPoints();
        Vec2[] renderPoints = new Vec2[definedPoints.length];

        float objectDimension = Collections.max(Arrays.asList(definedPoints)).dst; // actually half of the object dimension
        halfOfDimension = objectDimension;

        ArrayList<Vec2> positionsToRender = new ArrayList<>();
        positionsToRender.add(position);

        if (infinitySpace) {
            if (position.x < objectDimension)
                positionsToRender.add(Vec2.add(position, new Vec2(gameEngine.getWidth(), 0)));
            if (position.y < objectDimension)
                positionsToRender.add(Vec2.add(position, new Vec2(0, gameEngine.getHeight())));
            if (position.x > gameEngine.getWidth() - objectDimension)
                positionsToRender.add(Vec2.add(position, new Vec2(-gameEngine.getWidth(), 0)));
            if (position.y > gameEngine.getHeight() - objectDimension)
                positionsToRender.add(Vec2.add(position, new Vec2(0, -gameEngine.getHeight())));
        }

        Line[] renderLines = new Line[definedPoints.length * positionsToRender.size()];
        int linesIter = 0;

        for (Vec2 renderPos : positionsToRender) {

            for (int i = 0; i < renderPoints.length; i++) {
                renderPoints[i] = renderPos.clone();
                renderPoints[i].x += (float) Math.cos(Math.toRadians(definedPoints[i].rot + getRotation())) * definedPoints[i].dst * scale;
                renderPoints[i].y += (float) Math.sin(Math.toRadians(definedPoints[i].rot + getRotation())) * definedPoints[i].dst * scale;
            }

            for (int i = 0; i < renderPoints.length; i++, linesIter++) {
                int j = (i + 1) % renderPoints.length;
                renderLines[linesIter] = new Line();
                renderLines[linesIter].b = renderPoints[i];
                renderLines[linesIter].e = renderPoints[j];
            }
        }

        return renderLines;
    }

    public final Line[] getRenderLines(boolean multiplyObjectOnBoundaries)
    {
        boolean temp = infinitySpace;
        infinitySpace = multiplyObjectOnBoundaries;
        Line[] result = getRenderLines();
        infinitySpace = temp;
        return result;
    }

    public Line[] getCollisionLines()
    {
        return getRenderLines(false);
    }

    public float getCollisionRadius()
    {
        return halfOfDimension;
    }

    public void onCollisionEnter(ICollisionable other)
    {
//        GameObject o = (GameObject) other;
//        System.out.println(getName() + ": collision with " + o.getName());
    }
}