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
/// File: ICollisionable.java


package com.elkard.asteroidsgame.Logic;

import com.elkard.asteroidsgame.Line;
import com.elkard.asteroidsgame.Vec2;

public interface ICollisionable
{
    Line[] getCollisionLines();
    float getCollisionRadius();
    Vec2 getPosition();
    boolean isPhysicsEnabled();

    void onCollisionEnter(ICollisionable other);
}
