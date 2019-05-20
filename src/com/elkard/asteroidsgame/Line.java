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
/// File: Line.java


package com.elkard.asteroidsgame;

public class Line
{
    public Vec2 b;
    public Vec2 e;

    public Line()
    {

    }

    public Line(Vec2 begOfLine, Vec2 endofLine)
    {
        b = begOfLine.clone();
        e = endofLine.clone();
    }

    public Vec2 getMiddle()
    {
        return Vec2.add(b, e).div(2);
    }
}
