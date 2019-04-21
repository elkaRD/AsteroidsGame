package com.elkard.asteroidsgame;

import java.security.PolicySpi;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerSpaceship extends GameObject implements IControllable, ICollisionable {

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
        super.update(delta);

        updateShipVelocity(delta);
        updateShipPosition(delta);
        updateShipRotation(delta);
        updateShipWeapon(delta);
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

    protected PolarLayout[] renderPoints()
    {
        PolarLayout[] points = new PolarLayout[4];

        for (int i = 0; i < 4; i++)
            points[i] = new PolarLayout();

        points[0].dst = 50;     points[0].rot = 0;
        points[1].dst = 25;     points[1].rot = 135;
        points[2].dst = 5;      points[2].rot = 180;
        points[3].dst = 25;     points[3].rot = -135;

        return points;
    }
}