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
/// File: ShotGun.java


package com.elkard.asteroidsgame.Logic.GameObjects.Weapons;

import com.elkard.asteroidsgame.Logic.GameLogic;
import com.elkard.asteroidsgame.Logic.GameObject;
import com.elkard.asteroidsgame.Logic.GameObjects.Bullet;

public class ShotGun extends Weapon
{
    private float bulletRange = 5f;

    public ShotGun(GameLogic gl, GameObject parentObject)
    {
        super(gl, parentObject);
    }

    @Override
    public void update(float delta)
    {
        super.update(delta);

        if (isShooting)
        {
            new Bullet(gameLogic, this);
            new Bullet(gameLogic, this, bulletRange);
            new Bullet(gameLogic, this, -bulletRange);
        }
    }
}
