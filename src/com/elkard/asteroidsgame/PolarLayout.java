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
/// File: PolarLayout.java


package com.elkard.asteroidsgame;

public class PolarLayout implements Comparable
{
    public float dst = 0;
    public float rot = 0;


    public int compareTo(Object o) {
        if (!(o instanceof PolarLayout))
            throw new ClassCastException();

        if (dst > ((PolarLayout) o).dst)
            return 1;
        if (dst < ((PolarLayout) o).dst)
            return -1;
        return 0;
    }
}
