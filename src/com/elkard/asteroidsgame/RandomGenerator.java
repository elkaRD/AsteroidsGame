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
/// File: RandomGenerator.java


package com.elkard.asteroidsgame;

import java.util.Random;

public class RandomGenerator
{
    private static Random generator = new Random();

    public static float getFloat()
    {
        return generator.nextFloat();
    }

    public static float getFloat(float maxRange)
    {
        return generator.nextFloat() * maxRange;
    }

    public static float getFloat(float minRange, float maxRange)
    {
        float d = maxRange - minRange;
        return generator.nextFloat() * d + minRange;
    }

    public static int getInt()
    {
        return generator.nextInt();
    }

    public static int getInt(int maxRange)
    {
        return generator.nextInt(maxRange);
    }

    public static int getInt(int minRange, int maxRange)
    {
        int d = maxRange - minRange;
        return generator.nextInt(d) + minRange;
    }
}
