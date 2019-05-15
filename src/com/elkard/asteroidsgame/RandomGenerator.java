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
}
