///
/// EN: Project for the Event-Driven Programming course
///     Warsaw University of Technology
///     Java Asteroids Game
///
/// PL: Projekt PROZ (Programowanie zdarzeniowe)
///     PW WEiTI 19L
///     Gra typu Asteroids napisana w Javie
///
/// Copyright (C) Robert Dudzinski 2019
///
/// File: GameObject.java


package com.elkard.asteroidsgame.Logic;

import com.elkard.asteroidsgame.Line;
import com.elkard.asteroidsgame.PolarLayout;
import com.elkard.asteroidsgame.Vec2;

import java.util.*;

public class GameObject implements ICollisionable
{
    protected final GameLogic gameLogic;

    private Vec2 position = new Vec2();
    private float rotation = 0f;
    private float scale = 1f;
    private String name = "";

    private boolean infinitySpace = true;
    private boolean physicsEnabled = true;
    private boolean isVisible = true;
    private boolean isStatic = false;

    private boolean isDestructing = false;
    private float destructionDuration = 2f;
    private float destructionTime = 0;
    private float destructionScale = 100;

    private float halfOfDimension = 0;

    private boolean areLinesValid = false;
    private Line[] validLines;

    private PolarLayout[] definedPoints;
    private boolean areLinesStatic = true;
    private boolean refreshLines = true;

    public GameObject(GameLogic gl)
    {
        gameLogic = gl;
        gameLogic.registerObject(this);
    }

    public void cleanUp()
    {
        gameLogic.removeObject(this);
    }

    public final GameObject setPosition(Vec2 newPosition)
    {
        position = new Vec2(newPosition);
        return this;
    }

    public final GameObject setPosition(float x, float y)
    {
        position = new Vec2(x, y);
        if (infinitySpace)
            checkPosition();

        return this;
    }

    public final void move(Vec2 deltaPosition)
    {
        if (isStatic) return;

        position.add(deltaPosition);
        if (infinitySpace)
            checkPosition();
    }

    public final Vec2 getPosition() {
        return position.clone();
    }

    public final GameObject setRotation(float newRotation)
    {
        rotation = newRotation;
        return this;
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

    protected GameObject setName(String newName)
    {
        name = newName;
        return this;
    }

    protected GameObject setStaticLines(boolean areStatic)
    {
        areLinesStatic = areStatic;
        refreshLines = true;
        return this;
    }

    protected void updateLines()
    {
        refreshLines = true;
    }

    public final float getScale()
    {
        return scale;
    }

    public final GameObject setScale(float newScale)
    {
        scale = newScale;
        return this;
    }

    public void resetTransform()
    {
        position = new Vec2(0, 0);
        rotation = 0;
        scale = 1f;
    }

    private void checkPosition()
    {
        if (infinitySpace)
        {
            while (position.x > gameLogic.getWidth())
                position.x -= gameLogic.getWidth();

            while (position.x < 0)
                position.x += gameLogic.getWidth();

            while (position.y > gameLogic.getHeight())
                position.y -= gameLogic.getHeight();

            while (position.y < 0)
                position.y += gameLogic.getHeight();
        }
    }

    public final void updateObject(float delta)
    {
        areLinesValid = false;
        update(delta);
    }

    public void update(float delta)
    {
        if (isDestructing)
        {
            destructionTime += delta;
            if (destructionTime >= destructionDuration)
                cleanUp();
        }
    }

    protected PolarLayout[] renderPoints()
    {
        return new PolarLayout[0];
    }

    private Line[] generateLines()
    {
        if (!areLinesStatic || refreshLines)
        {
            refreshLines = false;
            definedPoints = renderPoints();
        }
        if (definedPoints == null)
            return new Line[0];

        Vec2[] renderPoints = new Vec2[definedPoints.length];
        float objectDimension = getMaxDst(definedPoints);   // actually half of the object dimension
        halfOfDimension = objectDimension;

        ArrayList<Vec2> positionsToRender = new ArrayList<>();
        positionsToRender.add(position);

        if (infinitySpace)
        {
            if (position.x < objectDimension)
                positionsToRender.add(Vec2.add(position, new Vec2(gameLogic.getWidth(), 0)));
            if (position.y < objectDimension)
                positionsToRender.add(Vec2.add(position, new Vec2(0, gameLogic.getHeight())));
            if (position.x > gameLogic.getWidth() - objectDimension)
                positionsToRender.add(Vec2.add(position, new Vec2(-gameLogic.getWidth(), 0)));
            if (position.y > gameLogic.getHeight() - objectDimension)
                positionsToRender.add(Vec2.add(position, new Vec2(0, -gameLogic.getHeight())));

            if (positionsToRender.size() == 3)
            {
                Vec2 opposedPosition = position.clone();

                if (position.x < objectDimension)
                    opposedPosition.x += gameLogic.getWidth();
                else
                    opposedPosition.x -= gameLogic.getWidth();
                if (position.y < objectDimension)
                    opposedPosition.y += gameLogic.getHeight();
                else
                    opposedPosition.y =- gameLogic.getHeight();

                positionsToRender.add(opposedPosition);
            }
        }

        Line[] renderLines = new Line[definedPoints.length * positionsToRender.size()];
        int linesIter = 0;

        for (Vec2 renderPos : positionsToRender)
        {
            for (int i = 0; i < renderPoints.length; i++)
            {
                renderPoints[i] = renderPos.clone();
                if (definedPoints[i] == null) continue;
                renderPoints[i].x += (float) Math.cos(Math.toRadians(definedPoints[i].rot + getRotation())) * definedPoints[i].dst * scale;
                renderPoints[i].y += (float) Math.sin(Math.toRadians(definedPoints[i].rot + getRotation())) * definedPoints[i].dst * scale;
            }

            for (int i = 0; i < renderPoints.length; i++, linesIter++)
            {
                int j = (i + 1) % renderPoints.length;
                renderLines[linesIter] = new Line();
                renderLines[linesIter].b = renderPoints[i];
                renderLines[linesIter].e = renderPoints[j];

                if (isDestructing)
                {
                    float s = destructionTime / destructionDuration;
                    Line line = renderLines[linesIter];

                    //first kind of animation
                    Vec2 destructionVector = line.getMiddle().sub(renderPos).normalize().mul(destructionScale).neg();
                    //Vec2 destructionVector = Vec2.getNormalVector(generator.nextFloat() * 360f).mul(destructionScale);
                    line.b = Vec2.lerp(line.b, Vec2.add(line.b, destructionVector), s);
                    line.e = Vec2.lerp(line.e, Vec2.add(line.e, destructionVector), s);
                }
            }
        }

        areLinesValid = true;
        return renderLines;
    }

    public final Line[] getRenderLines()
    {
        if (!isVisible)
            return new Line[0];

        if (!areLinesValid)
            validLines = generateLines();

        return validLines;
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
        if (!areLinesValid)
            validLines = generateLines();

        return validLines;
    }

    public float getCollisionRadius()
    {
        return halfOfDimension;
    }

    public boolean isPhysicsEnabled()
    {
        return physicsEnabled;
    }

    public GameObject enablePhysics(boolean isEnabled)
    {
        physicsEnabled = isEnabled;
        return this;
    }

    public GameObject setVisible(boolean isEnabled)
    {
        isVisible = isEnabled;
        return this;
    }

    public GameObject setStatic(boolean isStatic)
    {
        this.isStatic = isStatic;
        return this;
    }

    public void onCollisionEnter(ICollisionable other)
    {

    }

    protected void animateDestruction(float duration, float destructScale)
    {
        destructionDuration = duration;
        destructionTime = 0;
        isDestructing = true;
    }

    protected void animateDestruction(float duration)
    {
        animateDestruction(duration, destructionScale);
    }

    protected void animateDestruction()
    {
        animateDestruction(destructionDuration, destructionScale);
    }

    private float getMaxDst(PolarLayout[] points)
    {
        if (points.length == 0)
            return 0;

        float result = points[0].dst;
        for (int i = 1; i < points.length; i++)
        {
            if (points[i] == null) continue;

            if (points[i].dst > result)
                result = points[i].dst;
        }

        return result;
    }
}