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
        if (dst > ((PolarLayout) o).dst)
            return -1;
        return 0;
    }
}
