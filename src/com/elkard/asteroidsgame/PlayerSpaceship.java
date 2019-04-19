package com.elkard.asteroidsgame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerSpaceship extends GameObject implements IControllable {

    private Vec2 curVelocity;
    private float accelarationFactor = 1f;
    private float frictionFactor = 0.999f;
    private float slowingDownFactor = 0.5f;



    public PlayerSpaceship()
    {
        reset();
    }

    public void reset()
    {
        curVelocity = new Vec2(0, 0);
        resetTransform();
    }

    public void onAccelerate(float force)
    {
        Vec2 deltaVelocity = new Vec2();
        deltaVelocity.x = (float) Math.cos(Math.toRadians(getRotation())) * accelarationFactor * force;
        deltaVelocity.y = (float) Math.sin(Math.toRadians(getRotation())) * accelarationFactor * force;

        curVelocity.add(deltaVelocity);
    }

    public void onSlowDown(float force)
    {
        onAccelerate(-force * slowingDownFactor);
    }

    public void onRotate(float turn)
    {
        updateRotation(turn);
    }

    public boolean checkCollision(Asteroid asteroid)
    {
        // TODO: fix
        return false;
    }

    @Override
    public void update(float delta)
    {
        curVelocity.mul(frictionFactor);
        move(Vec2.multiply(curVelocity, delta));
//        System.out.println("Cur velocity: " + curVelocity);
        //System.out.println("cur pos: " + getPosition());
    }

    public Vec2[] getRenderPoints()
    {
        Vec2[] renderPoints = new Vec2[4];
        float[] pointDst = new float[4];
        float[] pointRot = new float[4];

        pointDst[0] = 100;  pointRot[0] = 0;
        pointDst[1] = 50;   pointRot[1] = 150;
        pointDst[2] = 10;   pointRot[2] = 180;
        pointDst[3] = 50;   pointRot[3] = -150;

        for (int i = 0; i < 4; i++)
        {
            renderPoints[i] = new Vec2(getPosition());
            renderPoints[i].x += (float) Math.cos(Math.toRadians(pointRot[i])) * pointDst[i];
            renderPoints[i].y += (float) Math.sin(Math.toRadians(pointRot[i])) * pointDst[i];
        }

        //return new ArrayList<Vec2>(Arrays.asList(renderPoints));
        return renderPoints;
    }
}