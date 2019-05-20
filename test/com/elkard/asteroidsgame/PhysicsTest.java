package com.elkard.asteroidsgame;

import com.elkard.asteroidsgame.Logic.GameLogic;
import com.elkard.asteroidsgame.Logic.GameObject;
import com.elkard.asteroidsgame.Logic.ICollisionable;
import com.elkard.asteroidsgame.Logic.Physics;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PhysicsTest
{
    private static final String tag = "PHYSICS_TESTS: ";

    @Test
    public void checkCollision1()
    {
        Physics physics = new Physics();

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
    }

    @Test
    public void checkCollision2()
    {
        Physics physics = new Physics();

        Vec2 p00 = new Vec2(0,0);
        Vec2 p10 = new Vec2(100,0);
        Vec2 p11 = new Vec2(100, 100);
        Vec2 p01 = new Vec2(0, 100);

        CollisionableLine a = new CollisionableLine(new Line(p00, p11));
        CollisionableLine b = new CollisionableLine(new Line(p01, p10));

        if (!physics.checkCollision(a, b))
        {
            fail(tag + "failed 2");
        }
    }

    @Test
    public void checkCollision3()
    {
        Physics physics = new Physics();

        Vec2 p00 = new Vec2(0,0);
        Vec2 p10 = new Vec2(100,0);
        Vec2 p20 = new Vec2(200, 0);
        Vec2 p30 = new Vec2(300, 0);

        CollisionableLine a = new CollisionableLine(new Line(p00, p10));
        CollisionableLine b = new CollisionableLine(new Line(p20, p30));

        if (physics.checkCollision(a, b))
        {
            fail(tag + "failed 3 - line 00-10 with 20-30");
        }

        a = new CollisionableLine(new Line(p00, p20));
        b = new CollisionableLine(new Line(p10, p30));

        if (!physics.checkCollision(a, b))
        {
            fail(tag + "failed 3 - line 00-20 with 10-30");
        }

        a = new CollisionableLine(new Line(p00, p30));
        b = new CollisionableLine(new Line(p10, p20));

        if (!physics.checkCollision(a, b))
        {
            fail(tag + "failed 3 - line 00-30 with 10-20");
        }
    }

    @Test
    public void checkCollision4()
    {
        Physics physics = new Physics();

        Vec2 p10 = new Vec2(100,0);
        Vec2 p20 = new Vec2(200,0);
        Vec2 p11 = new Vec2(100, 100);
        Vec2 p22 = new Vec2(200, 200);

        CollisionableLine a = new CollisionableLine(new Line(p10, p20));
        CollisionableLine b = new CollisionableLine(new Line(p11, p22));

        if (physics.checkCollision(a, b))
        {
            fail(tag + "failed 4");
        }
    }

    @Test
    public void checkIntegrationWithGameLogic()
    {
        Physics physics = new Physics();
        GameLogic gameLogic = new GameLogic();

        SimpleObject a = new SimpleObject(gameLogic);
        SimpleObject b = new SimpleObject(gameLogic);

        a.setRotation(45);
        b.setRotation(90);

        ArrayList<GameObject> g1 = new ArrayList<>();
        ArrayList<GameObject> g2 = new ArrayList<>();

        g1.add(a);
        g2.add(b);

        a.reset();
        b.reset();

        physics.addGroupsToCheck(g1, g2);
        gameLogic.onUpdate(0.016f);
        physics.updatePhysics(0);

        if (!a.detected || !b.detected)
        {
            fail(tag + "did not detect collision");
        }

        if (a.other != b || b.other != a)
        {
            fail(tag + "failed notification");
        }

        a.setPosition(50, 0);
        b.setPosition(100, 0);

        a.reset();
        b.reset();
        gameLogic.onUpdate(0.016f);
        physics.updatePhysics(0);

        if (a.detected || b.detected)
        {
            fail(tag + "detected fake collision");
        }

        a.setRotation(0);
        b.setRotation(0);

        a.reset();
        b.reset();
        gameLogic.onUpdate(0.016f);
        physics.updatePhysics(0);

        if (!a.detected || !b.detected)
        {
            fail(tag + "did not detect collision after rotate");
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

    private class SimpleObject extends GameObject
    {
        private PolarLayout[] points = new PolarLayout[2];

        public boolean detected = false;
        public SimpleObject other = null;

        public SimpleObject(GameLogic gl)
        {
            super(gl);

            reset();

            for (int i = 0; i < points.length; i++)
                points[i] = new PolarLayout();

            points[0].dst = 50;     points[0].rot = 0;
            points[1].dst = 50;     points[1].rot = 180;
        }

        protected PolarLayout[] renderPoints()
        {
            return points;
        }

        @Override
        public void onCollisionEnter(ICollisionable other)
        {
            super.onCollisionEnter(other);

            detected = true;

            if (other instanceof SimpleObject)
            {
                this.other = (SimpleObject) other;
            }
        }

        public void reset()
        {
            detected = false;
            other = null;
        }
    }
}