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
