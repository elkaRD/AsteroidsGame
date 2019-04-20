package com.elkard.asteroidsgame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerSpaceship extends GameObject implements IControllable {

    private Vec2 curVelocity;
    private float accelarationFactor = 300f;
    private float frictionFactor = 0.995f;
    private float slowingDownFactor = 0.5f;
    private float rotationFactor = 100f;

    private int resetX;
    private int resetY;

    private float lastAccelerate;
    private float lastSlowDown;
    private float lastRotate;
    private boolean lastShoot;

    private Weapon curWeapon;

    public PlayerSpaceship(GameLogic gl)
    {
        super(gl);

        curWeapon = new StandardWeapon(gameEngine, this);

        resetX = gl.getWidth() / 2;
        resetY = gl.getHeight() / 2;
        reset();
    }

    public void reset()
    {
        curVelocity = new Vec2(0, 0);
        resetTransform();

        setPosition(resetX, resetY);
    }

    public void onAccelerate(float force)
    {
//        Vec2 deltaVelocity = new Vec2();
//        deltaVelocity.x = (float) Math.cos(Math.toRadians(getRotation())) * accelarationFactor * force;
//        deltaVelocity.y = (float) Math.sin(Math.toRadians(getRotation())) * accelarationFactor * force;
//
//        curVelocity.add(deltaVelocity);

        lastAccelerate = force;
    }

    public void onSlowDown(float force)
    {
//        onAccelerate(-force * slowingDownFactor);

        lastSlowDown = force;
    }

    public void onRotate(float turn)
    {
//        updateRotation(turn * rotateFactor);

        lastRotate = turn;
    }

    public void onShoot()
    {
        lastShoot = true;
        System.out.println("player: " + getPosition() + ",  " + getRotation());
    }

    public boolean checkCollision(Asteroid asteroid)
    {
        // TODO: fix
        return false;
    }

    @Override
    public void update(float delta)
    {
//        curVelocity.mul(frictionFactor);
//        move(Vec2.multiply(curVelocity, delta));
//        System.out.println("Cur velocity: " + curVelocity);
        //System.out.println("cur pos: " + getPosition());

        updateShipVelocity(delta);
        updateShipPosition(delta);
        updateShipRotation(delta);
        updateShipWeapon(delta);

//        System.out.println("PLAYER: " + getPosition() + ",    " + getRotation());
//        System.out.println("WEAPON: " + curWeapon.getPosition() + ",    " + getRotation());
    }

    private void updateShipVelocity(float delta)
    {
        lastAccelerate *= delta;

        Vec2 deltaVelocity = new Vec2();
        deltaVelocity.x = (float) Math.cos(Math.toRadians(getRotation())) * accelarationFactor * lastAccelerate;
        deltaVelocity.y = (float) Math.sin(Math.toRadians(getRotation())) * accelarationFactor * lastAccelerate;

        curVelocity.add(deltaVelocity);
        curVelocity.mul(frictionFactor);
    }

    private void updateShipPosition(float delta)
    {
        move(Vec2.multiply(curVelocity, delta));
    }

    private void updateShipRotation(float delta)
    {
        updateRotation(lastRotate * rotationFactor * delta);
    }

    private void updateShipWeapon(float delta)
    {
        if (lastShoot)
        {
            System.out.println("shoot a bullet");
            lastShoot = false;
            curWeapon.onShoot();
        }
    }

    public Vec2[] getRenderPoints()
    {
        //TODO: just for debugging
        Vec2[] renderPoints = new Vec2[5];
        float[] pointDst = new float[4];
        float[] pointRot = new float[4];

        pointDst[0] = 50;   pointRot[0] = 0;
        pointDst[1] = 25;   pointRot[1] = 135;
        pointDst[2] = 5;    pointRot[2] = 180;
        pointDst[3] = 25;   pointRot[3] = -135;

        for (int i = 0; i < 4; i++)
        {
            renderPoints[i] = new Vec2(getPosition());
            renderPoints[i].x += (float) Math.cos(Math.toRadians(pointRot[i] + getRotation())) * pointDst[i];
            renderPoints[i].y += (float) Math.sin(Math.toRadians(pointRot[i] + getRotation())) * pointDst[i];
        }

        renderPoints[4] = curWeapon.getPosition();

        //return new ArrayList<Vec2>(Arrays.asList(renderPoints));
        return renderPoints;
    }
}