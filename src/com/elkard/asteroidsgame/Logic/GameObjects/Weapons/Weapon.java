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
/// File: Weapon.java


package com.elkard.asteroidsgame.Logic.GameObjects.Weapons;

import com.elkard.asteroidsgame.Logic.GameLogic;
import com.elkard.asteroidsgame.Logic.GameObject;
import com.elkard.asteroidsgame.Logic.GameObjects.IWeapon;

public abstract class Weapon extends GameObject implements IWeapon
{
    protected GameObject parent;
    protected boolean isShooting = false;

    public Weapon(GameLogic gl, GameObject parentObject)
    {
        super(gl);

        parent = parentObject;
    }

    @Override
    public void update(float delta)
    {
        setPosition(parent.getPosition());
        setRotation(parent.getRotation());
    }

    @Override
    public final void onStartShooting()
    {
        isShooting = true;
    }

    @Override
    public final void onEndShooting()
    {
        isShooting = false;
    }
}
