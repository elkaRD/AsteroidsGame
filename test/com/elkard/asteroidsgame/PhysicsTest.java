package com.elkard.asteroidsgame;

import com.elkard.asteroidsgame.Game.ICollisionable;
import com.elkard.asteroidsgame.Game.Physics;
import org.junit.Test;

import static org.junit.Assert.*;

public class PhysicsTest {

    @Test
    public void checkCollision()
    {
        Physics physics = new Physics();
        String tag = "PHYSICS_CHECK_COLLISION: ";

        Vec2 p00 = new Vec2(0,0);
        Vec2 p10 = new Vec2(100,0);
        Vec2 p11 = new Vec2(100, 100);
        Vec2 p01 = new Vec2(0, 100);

        CollisionableLine a = new CollisionableLine(new Line(p00, p01));
        CollisionableLine b = new CollisionableLine(new Line(p10, p11));

        if (physics.checkCollision(a, b))
        {
            fail(tag + "failed 1");
        }

        a = new CollisionableLine(new Line(p00, p11));
        b = new CollisionableLine(new Line(p01, p10));

        if (!physics.checkCollision(a, b))
        {
            fail(tag + "failed 2");
        }
    }

    private class CollisionableLine implements ICollisionable
    {
        private Line[] line;
        private float radius;
        private Vec2 position;

        public boolean detected;

        public CollisionableLine(Line begLine)
        {
            line = new Line[1];
            line[0] = begLine;

            radius = Vec2.magnitude(Vec2.sub(begLine.b, begLine.e));
            position = begLine.getMiddle();

            detected = false;
        }

        public Line[] getCollisionLines()
        {
            return line;
        }
        public float getCollisionRadius()
        {
            return radius;
        }
        public Vec2 getPosition()
        {
            return position;
        }
        public boolean isPhysicsEnabled()
        {
            return true;
        }

        public void onCollisionEnter(ICollisionable other)
        {
            detected = true;
        }
    }
}