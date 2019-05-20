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
/// File: IButtonClickListener.java


package com.elkard.asteroidsgame.View;

import com.elkard.asteroidsgame.View.UI.Button;
import com.elkard.asteroidsgame.View.UI.ButtonsGroup;

public interface IButtonClickListener
{
    void onButtonClicked(ButtonsGroup group, Button clicked);
}
