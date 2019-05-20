package com.elkard.asteroidsgame.Logic;

import com.elkard.asteroidsgame.PolarLayout;
import com.elkard.asteroidsgame.RandomGenerator;
import com.elkard.asteroidsgame.Vec2;

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

    private float randomDirection;
    private Vec2 velocityDirection;
    private float speed;

    private static final float[] scaleLevels = {0.7f, 0.4f, 0.25f, 0.1f};
    private int curScaleLevel;

    protected Asteroid(GameLogic gl, Vec2 startPos, Float initDirection, int scaleLevel)
    {
        super(gl);

        setName("asteroid" + asteroidsCounter);
        asteroidsCounter++;

        gameLogic.addObject(this, GameLogic.ObjectType.ASTEROID);
        curScaleLevel = scaleLevel;

        setPosition(startPos);
        setScale(scaleLevels[curScaleLevel]);

        int amountBoundary = RandomGenerator.getInt(minBoundaries, maxBoundaries);
        boundaryPoints = new PolarLayout[amountBoundary];

        diffRot = 360 / amountBoundary / 2;

        for (int i = 0; i < amountBoundary; i++)
        {
            boundaryPoints[i] = new PolarLayout();
            boundaryPoints[i].dst = asteroidRadius + RandomGenerator.getFloat(-1, 1) * diffBoundaries;
            boundaryPoints[i].rot = (360f / amountBoundary) * i + RandomGenerator.getFloat(-1, 1) * diffRot;
        }

        speed = RandomGenerator.getFloat(50 ,100);
        if (initDirection != null) randomDirection = initDirection;
        else randomDirection = RandomGenerator.getFloat() * 360f;

        velocityDirection = new Vec2();
        velocityDirection.x = (float) Math.cos(Math.toRadians(randomDirection));
        velocityDirection.y = (float) Math.sin(Math.toRadians(randomDirection));
    }

    public Asteroid(GameLogic gl, Vec2 startPos) {
        this(gl, startPos, null, 0);
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

            new Asteroid(gameLogic, getPosition(), randomDirection + diff, curScaleLevel+1);
            new Asteroid(gameLogic, getPosition(), randomDirection - diff, curScaleLevel+1);
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
