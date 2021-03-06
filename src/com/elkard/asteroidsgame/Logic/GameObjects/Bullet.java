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
/// File: Bullet.java


package com.elkard.asteroidsgame.Logic.GameObjects;

import com.elkard.asteroidsgame.Line;
import com.elkard.asteroidsgame.Logic.GameLogic;
import com.elkard.asteroidsgame.Logic.GameObject;
import com.elkard.asteroidsgame.Logic.GameObjects.Weapons.Weapon;
import com.elkard.asteroidsgame.Logic.ICollisionable;
import com.elkard.asteroidsgame.PolarLayout;
import com.elkard.asteroidsgame.Vec2;
import com.elkard.asteroidsgame.View.AudioManager;

import static com.elkard.asteroidsgame.View.AudioManager.SHOT;

public class Bullet extends GameObject
{
    private float speed = 500;
    private final float originalSpeed = 500;
    private float bulletLen = 20;
    private float remainingDst = 500;

    private Vec2 bulletVelocity;
    private Vec2 bulletDirection = new Vec2();
    private Vec2 prevPos;

    public Bullet(GameLogic gl, Weapon weapon, float speedMultiplier, float deltaRotation)
    {
        super(gl);

        gameLogic.addObject(this, GameLogic.ObjectType.BULLET);

        setPosition(weapon.getPosition());
        setRotation(weapon.getRotation() + deltaRotation);
        prevPos = getPosition();

        speed = originalSpeed * speedMultiplier;
        bulletDirection.x = (float) Math.cos(Math.toRadians(getRotation()));
        bulletDirection.y = (float) Math.sin(Math.toRadians(getRotation()));
        bulletVelocity = bulletDirection.clone();
        bulletVelocity.mul(speed);

        move(Vec2.mul(bulletDirection, 50));

        AudioManager.getInstance().playSound(SHOT);
    }

    public Bullet(GameLogic gl, Weapon weapon)
    {
        this(gl, weapon, 1f, 0);
    }

    public Bullet(GameLogic gl, Weapon weapon, float deltaRotation)
    {
        this(gl, weapon, 1f, deltaRotation);
    }

    public Bullet setRemainingDst(float newRemainingDst)
    {
        remainingDst = newRemainingDst;
        return this;
    }

    public Bullet setSpeedMultiplier(float newSpeedMultiplier)
    {
        speed = originalSpeed * newSpeedMultiplier;
        bulletVelocity = Vec2.mul(bulletDirection, speed);
        return this;
    }

    public Bullet setBulletLength(float bulletLen)
    {
        this.bulletLen = bulletLen;
        return this;
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

        prevPos = getPosition();
        move(Vec2.mul(bulletVelocity, delta));

        remainingDst -= speed * delta;
        if (remainingDst <= 0)
            cleanUp();
    }

    @Override
    protected PolarLayout[] renderPoints()
    {
        PolarLayout[] points = new PolarLayout[2];

        for (int i = 0; i < 2; i++)
            points[i] = new PolarLayout();

        points[0].dst = 0;          points[0].rot = 0;
        points[1].dst = bulletLen;  points[1].rot = 180;

        return points;
    }

    @Override
    public float getCollisionRadius()
    {
        return Vec2.sub(getPosition(), prevPos).magnitude();
    }

    @Override
    public Line[] getCollisionLines()
    {
        if (Math.abs(getPosition().x - prevPos.x) > gameLogic.getWidth()/2)
        {
            Vec2 smallerX = getPosition();
            Vec2 greaterX = prevPos;
            if (prevPos.x < getPosition().x)
            {
                greaterX = smallerX;
                smallerX = prevPos;
            }

            Vec2 help1 = new Vec2(0, smallerX.y);
            Vec2 help2 = new Vec2(gameLogic.getWidth(), greaterX.y);

            Line[] temp = new Line[2];
            temp[0] = new Line(help1, smallerX);
            temp[1] = new Line(help2, greaterX);
            return temp;
        }

        if (Math.abs(getPosition().y - prevPos.y) > gameLogic.getHeight()/2)
        {
            Vec2 smallerY = getPosition();
            Vec2 greaterY = prevPos;
            if (prevPos.y < getPosition().y)
            {
                greaterY = smallerY;
                smallerY = prevPos;
            }

            Vec2 help1 = new Vec2(smallerY.x, 0);
            Vec2 help2 = new Vec2(greaterY.x, gameLogic.getHeight());

            Line[] temp = new Line[2];
            temp[0] = new Line(help1, smallerY);
            temp[1] = new Line(help2, greaterY);
            return temp;
        }

        Line[] temp = new Line[1];
        temp[0] = new Line(getPosition(), prevPos);
        return temp;
    }

    @Override
    public void onCollisionEnter(ICollisionable other)
    {
        super.onCollisionEnter(other);

        cleanUp();
    }
}
