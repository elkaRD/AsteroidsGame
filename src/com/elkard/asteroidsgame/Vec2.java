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
/// File: Vec2.java


package com.elkard.asteroidsgame;

public final class Vec2 {
    public float x;
    public float y;

    public Vec2() {
        x = 0;
        y = 0;
    }

    public Vec2(float newX, float newY) {
        x = newX;
        y = newY;
    }

    public Vec2(Vec2 other) {
        x = other.x;
        y = other.y;
    }

    public Vec2 add(Vec2 other) {
        x += other.x;
        y += other.y;
        return this;
    }

    public Vec2 substract(Vec2 other) {
        return sub(other);
    }

    public Vec2 sub(Vec2 other)
    {
        x -= other.x;
        y -= other.y;
        return this;
    }

    public Vec2 multiply(float m)
    {
        return mul(m);
    }

    public Vec2 mul(float m)
    {
        x *= m;
        y *= m;
        return this;
    }

    public Vec2 divide(float d)
    {
        return div(d);
    }

    public Vec2 div(float d)
    {
        x /= d;
        y /= d;
        return this;
    }

    public Vec2 negate() {
        x *= -1;
        y *= -1;
        return this;
    }

    public Vec2 neg() {
        return negate();
    }

    public float magnitude()
    {
        return (float) Math.sqrt(x*x + y*y);
    }

    public float sqrMagnitude()
    {
        return x*x + y*y;
    }

    public Vec2 normalize()
    {
        div(magnitude());
        return this;
    }

    public static Vec2 add(Vec2 a, Vec2 b) {
        return new Vec2(a.x + b.x, a.y + b.y);
    }

    public static Vec2 substract(Vec2 a, Vec2 b) {
        return new Vec2(a.x - b.x, a.y - b.y);
    }

    public static Vec2 sub(Vec2 a, Vec2 b) {
        return new Vec2(a.x - b.x, a.y - b.y);
    }

    public static Vec2 multiply(Vec2 a, float m) {
        return new Vec2(a.x * m, a.y * m);
    }

    public static Vec2 mul(Vec2 a, float m)
    {
        if (a == null) return new Vec2();
        return new Vec2(a.x * m, a.y * m);
    }

    public static Vec2 divide(Vec2 a, float d)
    {
        return a.clone().div(d);
    }

    public static Vec2 div(Vec2 a, float d)
    {
        return a.clone().div(d);
    }

    public static Vec2 negate(Vec2 a) {
        return new Vec2(-a.x, -a.y);
    }

    public static Vec2 neg(Vec2 a) {
        return new Vec2(-a.x, -a.y);
    }

    public static float magnitude(Vec2 a)
    {
        return a.magnitude();
    }

    public static float sqrMagnitude(Vec2 a)
    {
        return a.sqrMagnitude();
    }

    public static Vec2 normal(Vec2 a)
    {
        return a.clone().normalize();
    }

    public static Vec2 lerp(Vec2 b, Vec2 e, float s)
    {
        return new Vec2(b.x + (e.x - b.x)*s, b.y + (e.y - b.y)*s);
    }

    public static Vec2 getNormalVector(float angle)
    {
        return new Vec2((float) Math.cos(Math.toRadians(angle)), (float) Math.sin(Math.toRadians(angle)));
    }

    @Override
    public String toString() {
        return new String("(" + x + ", " + y + ")");
    }

    @Override
    public Vec2 clone()
    {
        return new Vec2(this);
    }

    @Override
    public boolean equals(Object other)
    {
        if (other instanceof Vec2i)
        {
            return other.equals(this);
        }

        if (!(other instanceof Vec2)) return false;

        Vec2 o = (Vec2) other;
        if (o.x == x && o.y == y) return true;

        return false;
    }
}