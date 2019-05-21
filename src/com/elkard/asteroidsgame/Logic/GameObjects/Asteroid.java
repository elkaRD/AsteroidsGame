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
/// File: Asteroid.java


package com.elkard.asteroidsgame.Logic.GameObjects;

import com.elkard.asteroidsgame.Logic.GameLogic;
import com.elkard.asteroidsgame.Logic.GameObject;
import com.elkard.asteroidsgame.Logic.ICollisionable;
import com.elkard.asteroidsgame.PolarLayout;
import com.elkard.asteroidsgame.RandomGenerator;
import com.elkard.asteroidsgame.Vec2;

public class Asteroid extends GameObject implements ICollisionable
{
    private static int asteroidsCounter = 0;

    private static final int minBoundaries = 12;
    private static final int maxBoundaries = 16;
    private static final float diffBoundaries = 40;
    private static final float[] scaleLevels = {0.7f, 0.4f, 0.25f, 0.1f};

    private final float collisionRadius = 2;
    private final float asteroidRadius = 100;

    private final int curScaleLevel;

    private float diffRot;
    private PolarLayout[] boundaryPoints;

    private float randomDirection;
    private Vec2 velocityDirection;
    private float speed;

    protected Asteroid(GameLogic gl, Vec2 startPos, Float initDirection, int scaleLevel)
    {
        super(gl);

        setName("asteroid" + asteroidsCounter);
        asteroidsCounter++;

        gameLogic.addObject(this, GameLogic.ObjectType.ASTEROID);
        curScaleLevel = scaleLevel;

        setPosition(startPos);
        setScale(scaleLevels[curScaleLevel]);

        generateBoundaries();
        setupVelocity(initDirection);
    }

    public Asteroid(GameLogic gl, Vec2 startPos) {
        this(gl, startPos, null, 0);
    }

    private void generateBoundaries()
    {
        int amountBoundary = RandomGenerator.getInt(minBoundaries, maxBoundaries);
        boundaryPoints = new PolarLayout[amountBoundary];

        diffRot = 360 / amountBoundary / 2;

        for (int i = 0; i < amountBoundary; i++)
        {
            boundaryPoints[i] = new PolarLayout();
            boundaryPoints[i].dst = asteroidRadius + RandomGenerator.getFloat(-1, 1) * diffBoundaries;
            boundaryPoints[i].rot = (360f / amountBoundary) * i + RandomGenerator.getFloat(-1, 1) * diffRot;
        }
    }

    private void setupVelocity(Float initDirection)
    {
        speed = RandomGenerator.getFloat(50 ,100);
        if (initDirection != null) randomDirection = initDirection;
        else randomDirection = RandomGenerator.getFloat() * 360f;

        velocityDirection = new Vec2();
        velocityDirection.x = (float) Math.cos(Math.toRadians(randomDirection));
        velocityDirection.y = (float) Math.sin(Math.toRadians(randomDirection));
    }

    @Override
    public void cleanUp()
    {
        super.cleanUp();

        gameLogic.removeObject(this);
    }

    @Override
    public void update(float delta)
    {
        super.update(delta);

        move(Vec2.mul(velocityDirection, speed * delta));
    }

    public void destroy()
    {
        enablePhysics(false);
        animateDestruction();

        if (curScaleLevel != scaleLevels.length-1)
        {
            float diff = RandomGenerator.getFloat(20, 40);

//            new Asteroid(gameLogic, getPosition(), randomDirection + diff, curScaleLevel+1);
//            new Asteroid(gameLogic, getPosition(), randomDirection - diff, curScaleLevel+1);
        }
    }

    @Override
    public float getCollisionRadius()
    {
        return collisionRadius;
    }

    @Override
    protected PolarLayout[] renderPoints()
    {
        return boundaryPoints;
    }

    @Override
    public void onCollisionEnter(ICollisionable other) {
        super.onCollisionEnter(other);

        if (other instanceof Bullet)
        {
            destroy();
            gameLogic.onAsteroidDestroyed(this, curScaleLevel);
        }
    }
}
