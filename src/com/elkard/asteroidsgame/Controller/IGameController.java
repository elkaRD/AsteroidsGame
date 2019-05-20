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
/// File: IGameController.java


package com.elkard.asteroidsgame.Controller;

import com.elkard.asteroidsgame.Logic.GameLogic;

public interface IGameController
{
    void onAccelerate(float force);
    void onSlowDown(float force);
    void onTurn(float turn);

    void onStartShooting();
    void onEndShooting();
    void onPause();

    void onStartGame();
    void onCloseGame();

    float getScreenRatio();

    void run();

    GameLogic getGameLogic();
}
