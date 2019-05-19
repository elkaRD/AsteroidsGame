package com.elkard.asteroidsgame;

import org.junit.Test;

import static org.junit.Assert.*;

public class Vec2iTest {

    @Test
    public void add() {
        Vec2i a = new Vec2i(1, 2);
        Vec2i b = new Vec2i(3, 4);

        Vec2i r1 = new Vec2i(4, 6);
        Vec2i r2 = new Vec2i(3, 4);

        a.add(b);

        if (!r1.equals(a) || !r2.equals(b))
            fail();
    }

    @Test
    public void substract() {
        Vec2i a = new Vec2i(1, 2);
        Vec2i b = new Vec2i(3, 4);

        Vec2i r1 = new Vec2i(-2, -2);
        Vec2i r2 = new Vec2i(3, 4);

        a.substract(b);

        if (!r1.equals(a) || !r2.equals(b))
            fail();
    }

    @Test
    public void sub() {
        Vec2i a = new Vec2i(1, 2);
        Vec2i b = new Vec2i(3, 4);

        Vec2i r1 = new Vec2i(-2, -2);
        Vec2i r2 = new Vec2i(3, 4);

        a.sub(b);

        if (!r1.equals(a) || !r2.equals(b))
            fail();
    }

    @Test
    public void multiply() {
        Vec2i a = new Vec2i(1, 2);
        int b = 3;

        Vec2i r1 = new Vec2i(3, 6);

        a.multiply(b);

        if (!r1.equals(a))
            fail();
    }

    @Test
    public void mul() {
        Vec2i a = new Vec2i(1, 2);
        int b = 3;

        Vec2i r1 = new Vec2i(3, 6);

        a.mul(b);

        if (!r1.equals(a))
            fail();
    }

    @Test
    public void divide() {
        Vec2i a = new Vec2i(9, 12);
        int b = 3;

        Vec2i r1 = new Vec2i(3, 4);

        a.divide(b);

        if (!r1.equals(a))
            fail();
    }

    @Test
    public void div() {
        Vec2i a = new Vec2i(9, 12);
        int b = 3;

        Vec2i r1 = new Vec2i(3, 4);

        a.div(b);

        if (!r1.equals(a))
            fail();
    }

    @Test
    public void negate() {
        Vec2i a = new Vec2i(9, 12);

        Vec2i r1 = new Vec2i(-9, -12);

        a.negate();

        if (!r1.equals(a))
            fail();
    }

    @Test
    public void neg() {
        Vec2i a = new Vec2i(9, 12);

        Vec2i r1 = new Vec2i(-9, -12);

        a.neg();

        if (!r1.equals(a))
            fail();
    }

    @Test
    public void magnitude() {
        Vec2i a = new Vec2i(3, 4);

        if (a.magnitude() != 5)
            fail();
    }

    @Test
    public void sqrMagnitude() {
        Vec2i a = new Vec2i(3, 4);

        if (a.sqrMagnitude() != 25)
            fail();
    }

    @Test
    public void add1() {
        Vec2i a = new Vec2i(1, 2);
        Vec2i b = new Vec2i(3, 4);

        Vec2i r1 = new Vec2i(4, 6);
        Vec2i r2 = Vec2i.add(a, b);

        if (!r1.equals(r2))
            fail();
    }

    @Test
    public void substract1() {
        Vec2i a = new Vec2i(1, 2);
        Vec2i b = new Vec2i(3, 4);

        Vec2i r1 = new Vec2i(-2, -2);
        Vec2i r2 = Vec2i.substract(a, b);

        if (!r1.equals(r2))
            fail();
    }

    @Test
    public void sub1() {
        Vec2i a = new Vec2i(1, 2);
        Vec2i b = new Vec2i(3, 4);

        Vec2i r1 = new Vec2i(-2, -2);
        Vec2i r2 = Vec2i.sub(a, b);

        if (!r1.equals(r2))
            fail();
    }

    @Test
    public void multiply1() {
        Vec2i a = new Vec2i(1, 2);
        int b = 5;

        Vec2i r1 = new Vec2i(5, 10);
        Vec2i r2 = Vec2i.multiply(a, b);

        if (!r1.equals(r2))
            fail();
    }

    @Test
    public void mul1() {
        Vec2i a = new Vec2i(1, 2);
        int b = 5;

        Vec2i r1 = new Vec2i(5, 10);
        Vec2i r2 = Vec2i.mul(a, b);

        if (!r1.equals(r2))
            fail();
    }

    @Test
    public void divide1() {
        Vec2i a = new Vec2i(24, 36);
        int b = 4;

        Vec2i r1 = new Vec2i(6, 9);
        Vec2i r2 = Vec2i.divide(a, b);

        if (!r1.equals(r2))
            fail();
    }

    @Test
    public void div1() {
        Vec2i a = new Vec2i(24, 36);
        int b = 4;

        Vec2i r1 = new Vec2i(6, 9);
        Vec2i r2 = Vec2i.div(a, b);

        if (!r1.equals(r2))
            fail();
    }

    @Test
    public void negate1() {
        Vec2i a = new Vec2i(24, 36);

        Vec2i r1 = new Vec2i(-24, -36);
        Vec2i r2 = Vec2i.negate(a);

        if (!r1.equals(r2))
            fail();
    }

    @Test
    public void neg1() {
        Vec2i a = new Vec2i(24, 36);

        Vec2i r1 = new Vec2i(-24, -36);
        Vec2i r2 = Vec2i.neg(a);

        if (!r1.equals(r2))
            fail();
    }

    @Test
    public void magnitude1() {
        Vec2i a = new Vec2i(4, 3);

        float r1 = 5;
        float r2 = Vec2i.magnitude(a);

        if (r1 != r2)
            fail();
    }

    @Test
    public void sqrMagnitude1() {
        Vec2i a = new Vec2i(4, 3);

        float r1 = 25;
        float r2 = Vec2i.sqrMagnitude(a);

        if (r1 != r2)
            fail();
    }

    @Test
    public void lerp() {
        Vec2i b = new Vec2i(1 ,1);
        Vec2i e = new Vec2i(5, 5);
        float s = 0.75f;

        Vec2i r1 = Vec2i.lerp(b, e, s);
        Vec2i r2 = new Vec2i(4,4);

        if (!r1.equals(r2))
            fail();
    }

    @Test
    public void getNormalVector() {
        float a1 = 0;
        Vec2 r11 = new Vec2(1, 0);
        Vec2 r12 = Vec2i.getNormalVector(a1);

        if (!r11.equals(r12))
            fail();
    }
}