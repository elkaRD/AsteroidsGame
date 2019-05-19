package com.elkard.asteroidsgame;

import org.junit.Test;

import static org.junit.Assert.*;

public class Vec2Test {

    @Test
    public void add() {
        Vec2 a = new Vec2(1, 2);
        Vec2 b = new Vec2(3, 4);

        Vec2 r1 = new Vec2(4, 6);
        Vec2 r2 = new Vec2(3, 4);

        a.add(b);

        if (!r1.equals(a) || !r2.equals(b))
            fail();
    }

    @Test
    public void substract() {
        Vec2 a = new Vec2(1, 2);
        Vec2 b = new Vec2(3, 4);

        Vec2 r1 = new Vec2(-2, -2);
        Vec2 r2 = new Vec2(3, 4);

        a.substract(b);

        if (!r1.equals(a) || !r2.equals(b))
            fail();
    }

    @Test
    public void sub() {
        Vec2 a = new Vec2(1, 2);
        Vec2 b = new Vec2(3, 4);

        Vec2 r1 = new Vec2(-2, -2);
        Vec2 r2 = new Vec2(3, 4);

        a.sub(b);

        if (!r1.equals(a) || !r2.equals(b))
            fail();
    }

    @Test
    public void multiply() {
        Vec2 a = new Vec2(1, 2);
        int b = 3;

        Vec2 r1 = new Vec2(3, 6);

        a.multiply(b);

        if (!r1.equals(a))
            fail();
    }

    @Test
    public void mul() {
        Vec2 a = new Vec2(1, 2);
        int b = 3;

        Vec2 r1 = new Vec2(3, 6);

        a.mul(b);

        if (!r1.equals(a))
            fail();
    }

    @Test
    public void divide() {
        Vec2 a = new Vec2(9, 12);
        int b = 3;

        Vec2 r1 = new Vec2(3, 4);

        a.divide(b);

        if (!r1.equals(a))
            fail();
    }

    @Test
    public void div() {
        Vec2 a = new Vec2(9, 12);
        int b = 3;

        Vec2 r1 = new Vec2(3, 4);

        a.div(b);

        if (!r1.equals(a))
            fail();
    }

    @Test
    public void negate() {
        Vec2 a = new Vec2(9, 12);

        Vec2 r1 = new Vec2(-9, -12);

        a.negate();

        if (!r1.equals(a))
            fail();
    }

    @Test
    public void neg() {
        Vec2 a = new Vec2(9, 12);

        Vec2 r1 = new Vec2(-9, -12);

        a.neg();

        if (!r1.equals(a))
            fail();
    }

    @Test
    public void magnitude() {
        Vec2 a = new Vec2(3, 4);

        if (a.magnitude() != 5)
            fail();
    }

    @Test
    public void sqrMagnitude() {
        Vec2 a = new Vec2(3, 4);

        if (a.sqrMagnitude() != 25)
            fail();
    }

    @Test
    public void normalize() {
        Vec2 a = new Vec2(3, 4);

        a.normalize();

        if (a.magnitude() != 1)
            fail();
    }

    @Test
    public void add1() {
        Vec2 a = new Vec2(1, 2);
        Vec2 b = new Vec2(3, 4);

        Vec2 r1 = new Vec2(4, 6);
        Vec2 r2 = Vec2.add(a, b);

        if (!r1.equals(r2))
            fail();
    }

    @Test
    public void substract1() {
        Vec2 a = new Vec2(1, 2);
        Vec2 b = new Vec2(3, 4);

        Vec2 r1 = new Vec2(-2, -2);
        Vec2 r2 = Vec2.substract(a, b);

        if (!r1.equals(r2))
            fail();
    }

    @Test
    public void sub1() {
        Vec2 a = new Vec2(1, 2);
        Vec2 b = new Vec2(3, 4);

        Vec2 r1 = new Vec2(-2, -2);
        Vec2 r2 = Vec2.sub(a, b);

        if (!r1.equals(r2))
            fail();
    }

    @Test
    public void multiply1() {
        Vec2 a = new Vec2(1, 2);
        int b = 5;

        Vec2 r1 = new Vec2(5, 10);
        Vec2 r2 = Vec2.multiply(a, b);

        if (!r1.equals(r2))
            fail();
    }

    @Test
    public void mul1() {
        Vec2 a = new Vec2(1, 2);
        int b = 5;

        Vec2 r1 = new Vec2(5, 10);
        Vec2 r2 = Vec2.mul(a, b);

        if (!r1.equals(r2))
            fail();
    }

    @Test
    public void divide1() {
        Vec2 a = new Vec2(24, 36);
        int b = 4;

        Vec2 r1 = new Vec2(6, 9);
        Vec2 r2 = Vec2.divide(a, b);

        if (!r1.equals(r2))
            fail();
    }

    @Test
    public void div1() {
        Vec2 a = new Vec2(24, 36);
        int b = 4;

        Vec2 r1 = new Vec2(6, 9);
        Vec2 r2 = Vec2.div(a, b);

        if (!r1.equals(r2))
            fail();
    }

    @Test
    public void negate1() {
        Vec2 a = new Vec2(24, 36);

        Vec2 r1 = new Vec2(-24, -36);
        Vec2 r2 = Vec2.negate(a);

        if (!r1.equals(r2))
            fail();
    }

    @Test
    public void neg1() {
        Vec2 a = new Vec2(24, 36);

        Vec2 r1 = new Vec2(-24, -36);
        Vec2 r2 = Vec2.neg(a);

        if (!r1.equals(r2))
            fail();
    }

    @Test
    public void magnitude1() {
        Vec2 a = new Vec2(4, 3);

        float r1 = 5;
        float r2 = Vec2.magnitude(a);

        if (r1 != r2)
            fail();
    }

    @Test
    public void sqrMagnitude1() {
        Vec2 a = new Vec2(4, 3);

        float r1 = 25;
        float r2 = Vec2.sqrMagnitude(a);

        if (r1 != r2)
            fail();
    }

    @Test
    public void normal() {
        Vec2 a = new Vec2(24, 36);

        Vec2 r = Vec2.normal(a);

        if (r.magnitude() != 1)
            fail();
    }

    @Test
    public void lerp() {
        Vec2 b = new Vec2(1 ,1);
        Vec2 e = new Vec2(5, 5);
        float s = 0.75f;

        Vec2 r1 = Vec2.lerp(b, e, s);
        Vec2 r2 = new Vec2(4,4);

        if (!r1.equals(r2))
            fail();
    }

    @Test
    public void getNormalVector() {
        float a1 = 0;
        Vec2 r11 = new Vec2(1, 0);
        Vec2 r12 = Vec2.getNormalVector(a1);

        if (!r11.equals(r12))
            fail();
    }
}