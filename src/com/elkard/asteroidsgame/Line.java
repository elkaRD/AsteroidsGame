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
}
