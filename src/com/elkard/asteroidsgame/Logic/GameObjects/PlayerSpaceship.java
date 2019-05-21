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
/// File: PlayerSpaceship.java


package com.elkard.asteroidsgame.Logic.GameObjects;

import com.elkard.asteroidsgame.Logic.*;
import com.elkard.asteroidsgame.PolarLayout;
import com.elkard.asteroidsgame.Vec2;

public class PlayerSpaceship extends GameObject implements IControllable, ICollisionable {

    private Vec2 curVelocity;
    private float accelerationFactor = 300f;
    private float frictionFactor = 0.995f;
    private float slowingDownFactor = 0.5f;
    private float rotationFactor = 100f;

    private int resetX;
    private int resetY;

    private float lastAccelerate;
    private float lastSlowDown;
    private float lastRotate;
    private boolean lastShoot;
    private boolean startedShooting = false;
    private boolean stoppedShooting = false;

    private float untouchedTime = 0;
    private float untouchedTickDuration = 0.5f;
    private int untouchedTicks = 6;

    private IWeapon curWeapon;

    private static int playersCounter = 0;

    public ShipBooster booster;

    public PlayerSpaceship(GameLogic gl)
    {
        super(gl);

        setName("player" + playersCounter);
        playersCounter++;

        gameLogic.addObject(this, GameLogic.ObjectType.PLAYER);
        enablePhysics(false);

        booster = new ShipBooster(gl, this);

        resetX = gl.getWidth() / 2;
        resetY = gl.getHeight() / 2;
        reset();
    }

    @Override
    public void cleanUp()
    {
        super.cleanUp();

        onEndShooting();
        gameLogic.removeObject(this);
    }

    public void reset()
    {
        curVelocity = new Vec2(0, 0);
        resetTransform();

        setPosition(resetX, resetY);

        if (curWeapon != null)
            curWeapon.onEndShooting();
        curWeapon = gameLogic.weaponsFactory.getWeapon(this);
    }

    @Override
    public void onAccelerate(float force)
    {
        lastAccelerate += force;
        booster.onAccelerate(force);
    }

    @Override
    public void onSlowDown(float force)
    {
        onAccelerate(-force/2);
        booster.onSlowDown(force/2);
    }

    @Override
    public void onRotate(float turn)
    {
        lastRotate = turn;
    }

    @Override
    public void onStartShooting()
    {
        startedShooting = true;
    }

    @Override
    public void onEndShooting()
    {
        stoppedShooting = true;
    }

    @Override
    public void update(float delta)
    {
        super.update(delta);

        updateUntouched(delta);
        updateShipVelocity(delta);
        updateShipPosition(delta);
        updateShipRotation(delta);
        updateShipWeapon(delta);
    }

    private void updateUntouched(float delta)
    {
        untouchedTime += delta;
        if (untouchedTime < untouchedTickDuration / 2)
        {
            setVisible(false);
        }
        else
        {
            setVisible(true);
        }
        if (untouchedTicks > 0 && untouchedTime > untouchedTickDuration)
        {
            untouchedTicks--;
            untouchedTime = 0;

            if (untouchedTicks == 0)
                enablePhysics(true);
        }
    }

    private void updateShipVelocity(float delta)
    {
        lastAccelerate *= delta;

        Vec2 deltaVelocity = new Vec2();
        deltaVelocity.x = (float) Math.cos(Math.toRadians(getRotation())) * accelerationFactor * lastAccelerate;
        deltaVelocity.y = (float) Math.sin(Math.toRadians(getRotation())) * accelerationFactor * lastAccelerate;

        curVelocity.add(deltaVelocity);
        curVelocity.mul(frictionFactor);

        lastAccelerate = 0;
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
        if (startedShooting)
        {
            startedShooting = false;
            curWeapon.onStartShooting();
        }

        if (stoppedShooting)
        {
            stoppedShooting = false;
            curWeapon.onEndShooting();
        }
    }

    @Override
    protected PolarLayout[] renderPoints()
    {
        PolarLayout[] points = new PolarLayout[4];

        for (int i = 0; i < points.length; i++)
            points[i] = new PolarLayout();

        points[0].dst = 50;     points[0].rot = 0;
        points[1].dst = 25;     points[1].rot = 135;
        points[2].dst = 5;      points[2].rot = 180;
        points[3].dst = 25;     points[3].rot = -135;

        return points;
    }

    @Override
    public void onCollisionEnter(ICollisionable other) {
        super.onCollisionEnter(other);

        if (other instanceof Asteroid)
        {
            enablePhysics(false);
            animateDestruction();
            this.gameLogic.onDeath();
        }
    }
}