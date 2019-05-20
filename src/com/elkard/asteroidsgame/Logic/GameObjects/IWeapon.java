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
/// File: IWeapon.java


package com.elkard.asteroidsgame.Logic.GameObjects;

public interface IWeapon
{
    void onStartShooting();
    void onEndShooting();
}
