package com.elkard.asteroidsgame.Logic;

import com.elkard.asteroidsgame.PolarLayout;

public class ShipBooster extends GameObject
{
    private float thrust = 0;
    private PlayerSpaceship parent;
    private final float maxThrust = 100f;

    public ShipBooster(GameLogic gl, PlayerSpaceship parentObject)
    {
        super(gl);
        parent = parentObject;
    }

    @Override
    public void update(float delta)
    {
        super.update(delta);

        setPosition(parent.getPosition());
        setRotation(parent.getRotation());

        thrust -= 100 * delta;
        if (thrust < 0) thrust = 0;
    }

    public void onAccelerate(float force)
    {
        thrust += force * 5;
        if (thrust > maxThrust) thrust = maxThrust;
    }

    public void onSlowDown(float force)
    {
        thrust -= force * 5;
        if (thrust < 0) thrust = 0;
    }

    @Override
    protected PolarLayout[] renderPoints()
    {
        if (thrust > 0)
        {
            float factor = thrust / maxThrust;

            PolarLayout[] points = new PolarLayout[3];

            for (int i = 0; i < points.length; i++)
                points[i] = new PolarLayout();

            points[0].dst = 15;                 points[0].rot = 165 - factor * 10;
            points[1].dst = 15;                 points[1].rot = -165 + factor * 10;
            points[2].dst = 15 + factor * 40;   points[2].rot = 180;

            return points;
        }

        return new PolarLayout[0];
    }
}
