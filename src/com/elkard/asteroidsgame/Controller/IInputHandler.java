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
/// File: IInputHandler.java


package com.elkard.asteroidsgame.Controller;

import com.elkard.asteroidsgame.View.UI.IButtonsManager;

public interface IInputHandler
{
    void onKeyPressed(int key, boolean isPressed);
    void handleInput(IGameController asteroidsGame);
    void handleInput(IButtonsManager buttonsManager);
    void refresh();
}
