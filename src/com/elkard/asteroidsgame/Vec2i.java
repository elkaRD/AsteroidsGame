package com.elkard.asteroidsgame;

public final class Vec2i {
    public int x;
    public int y;

    public Vec2i() {
        x = 0;
        y = 0;
    }

    public Vec2i(int newX, int newY) {
        x = newX;
        y = newY;
    }

    public Vec2i(float newX, float newY)
    {
        x = (int) newX;
        y = (int) newY;
    }

    public Vec2i(Vec2i other) {
        x = other.x;
        y = other.y;
    }

    public Vec2i(Vec2 other)
    {
        x = (int) other.x;
        y = (int) other.y;
    }

    public Vec2i add(Vec2i other) {
        x += other.x;
        y += other.y;
        return this;
    }

    public Vec2i substract(Vec2i other) {
        return sub(other);
    }

    public Vec2i sub(Vec2i other)
    {
        x -= other.x;
        y -= other.y;
        return this;
    }

    public Vec2i multiply(float m)
    {
        return mul(m);
    }

    public Vec2i mul(float m)
    {
        x *= m;
        y *= m;
        return this;
    }

    public Vec2i divide(float d)
    {
        return div(d);
    }

    public Vec2i div(float d)
    {
        x /= d;
        y /= d;
        return this;
    }

    public Vec2i negate() {
        x *= -1;
        y *= -1;
        return this;
    }

    public Vec2i neg() {
        return negate();
    }

    public int magnitude()
    {
        return (int) Math.sqrt(x*x + y*y);
    }

    public int sqrMagnitude()
    {
        return x*x + y*y;
    }

    public Vec2i normalize()
    {
        div(magnitude());
        return this;
    }

    public static Vec2i add(Vec2i a, Vec2i b) {
        return new Vec2i(a.x + b.x, a.y + b.y);
    }

    public static Vec2i substract(Vec2i a, Vec2i b) {
        return new Vec2i(a.x - b.x, a.y - b.y);
    }

    public static Vec2i sub(Vec2i a, Vec2i b) {
        return new Vec2i(a.x - b.x, a.y - b.y);
    }

    public static Vec2i multiply(Vec2i a, float m) {
        return new Vec2i(a.x * m, a.y * m);
    }

    public static Vec2i mul(Vec2i a, float m)
    {
        if (a == null) return new Vec2i();
        return new Vec2i(a.x * m, a.y * m);
    }

    public static Vec2i divide(Vec2i a, float d)
    {
        return a.clone().div(d);
    }

    public static Vec2i div(Vec2i a, float d)
    {
        return a.clone().div(d);
    }

    public static Vec2i negate(Vec2i a) {
        return new Vec2i(-a.x, -a.y);
    }

    public static Vec2i neg(Vec2i a) {
        return new Vec2i(-a.x, -a.y);
    }

    public static int magnitude(Vec2i a)
    {
        return a.magnitude();
    }

    public static int sqrMagnitude(Vec2i a)
    {
        return a.sqrMagnitude();
    }

    public static Vec2i normal(Vec2i a)
    {
        return a.clone().normalize();
    }

    public static Vec2i lerp(Vec2i b, Vec2i e, float s)
    {
        return new Vec2i(b.x + (e.x - b.x)*s, b.y + (e.y - b.y)*s);
    }

    public static Vec2i getNormalVector(float angle)
    {
        return new Vec2i((int) Math.cos(Math.toRadians(angle)), (int) Math.sin(Math.toRadians(angle)));
    }

    public String toString() {
        return new String("(" + x + ", " + y + ")");
    }

    public Vec2i clone()
    {
        return new Vec2i(this);
    }
}