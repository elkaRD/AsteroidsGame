package com.elkard.asteroidsgame;

import java.util.Random;

public class Asteroid extends GameObject implements ICollisionable
{
    private static int asteroidsCounter = 0;

    private float collisionRadius = 2;
    private float asteroidRadius = 100;

    private static final int minBoundaries = 12;
    private static final int maxBoundaries = 16;
    private static final float diffBoundaries = 40;
    private final float diffRot;

    private PolarLayout[] boundaryPoints;

    private Vec2 velocityDirection;
    private float speed;

    private float[] scaleLevels = {0.7f, 0.4f, 0.25f, 0.1f};


    public Asteroid(GameLogic gl, Vec2 startPos)
    {
        super(gl);

        setName("asteroid" + asteroidsCounter);
        asteroidsCounter++;

        gameEngine.addAsteroid(this);

        setPosition(startPos);
        setScale(0.5f);

        Random generator = new Random();
        int amountBoundary = generator.nextInt(maxBoundaries - minBoundaries) + minBoundaries;
        boundaryPoints = new PolarLayout[amountBoundary];

        diffRot = 360 / amountBoundary / 2;

        for (int i = 0; i < amountBoundary; i++)
        {
            boundaryPoints[i] = new PolarLayout();
            boundaryPoints[i].dst = asteroidRadius + (generator.nextFloat()*2 - 1) * diffBoundaries;
            boundaryPoints[i].rot = (360f / amountBoundary) * i + (generator.nextFloat()*2 - 1) * diffRot;

            System.out.println("DST: " + boundaryPoints[i].dst + ",   ROT: " + boundaryPoints[i].rot);
        }

        speed = generator.nextFloat() * 50f + 50f;
        float randomDirection = generator.nextFloat() * 360f;
        velocityDirection = new Vec2();
        velocityDirection.x = (float) Math.cos(Math.toRadians(randomDirection));
        velocityDirection.y = (float) Math.sin(Math.toRadians(randomDirection));
    }

    public Asteroid(GameLogic gl)
    {
        this(gl, new Vec2());
    }

    protected void cleanup()
    {
        super.cleanUp();

        gameEngine.removeAsteroid(this);
    }

    public void update(float delta)
    {
        super.update(delta);

        move(Vec2.mul(velocityDirection, speed * delta));
    }

    public float getCollisionRadius()
    {
        return collisionRadius;
    }

    protected PolarLayout[] renderPoints()
    {
        return boundaryPoints;
    }
}
