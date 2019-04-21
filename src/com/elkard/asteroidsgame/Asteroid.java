package com.elkard.asteroidsgame;

import java.util.Random;

public class Asteroid extends GameObject implements ICollisionable
{
    private float collisionRadius = 2;
    private float asteroidRadius = 100;

    private static final int minBoundaries = 12;
    private static final int maxBoundaries = 16;
    private static final float diffBoundaries = 40;
    private final float diffRot;

    private PolarLayout[] boundaryPoints;

    private Vec2 velocityDirection;
    private float speed;


    public Asteroid(GameLogic gl)
    {
        super(gl);

        gameEngine.addAsteroid(this);

        //TODO: remove this line; it's just for testing
        setPosition(300, 300);

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
