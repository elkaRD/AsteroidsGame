package com.elkard.asteroidsgame.Game;

import com.elkard.asteroidsgame.Line;
import com.elkard.asteroidsgame.Vec2;

import java.util.ArrayList;

public final class Physics
{
    private ArrayList<PairOfGroups> pairs = new ArrayList<>();

    private static class PairOfGroups
    {
        public PairOfGroups(){}

        public PairOfGroups(ArrayList<GameObject> group1, ArrayList<GameObject> group2)
        {
            g1 = group1;
            g2 = group2;
        }

        public ArrayList<GameObject> g1;
        public ArrayList<GameObject> g2;
    }

    public Physics()
    {

    }

    public void updatePhysics(float delta)
    {
        for (PairOfGroups pair : pairs)
        {
            checkCollisionForGroups(pair.g1, pair.g2);
        }
    }

    public void addGroupsToCheck(ArrayList<GameObject> g1, ArrayList<GameObject> g2)
    {
        pairs.add(new PairOfGroups(g1, g2));
    }

    public void addGroupsToCheck(GameObject gameObject, ArrayList<GameObject> g2)
    {
        ArrayList<GameObject> temp = new ArrayList<>();
        temp.add(gameObject);
        pairs.add(new PairOfGroups(temp, g2));
    }
    
    private class PairOfObjects
    {
        public PairOfObjects(){}
        public PairOfObjects(ICollisionable o1, ICollisionable o2)
        {
            go1 = o1;
            go2 = o2;
        }
        public ICollisionable go1;
        public ICollisionable go2;
    }

    public void checkCollisionForGroups(ArrayList<GameObject> group1, ArrayList<GameObject> group2)
    {
        ArrayList<PairOfObjects> objectsToNotify = new ArrayList<>();
        
        for (ICollisionable object1 : group1)
        {
            if (!object1.isPhysicsEnabled()) continue;
            for (ICollisionable object2 : group2)
            {
                if (!object2.isPhysicsEnabled()) continue;
                if (checkCollision(object1, object2))
                {
                    objectsToNotify.add(new PairOfObjects(object1, object2));
                }
            }
        }

        for (PairOfObjects pair : objectsToNotify)
        {
            pair.go1.onCollisionEnter(pair.go2);
            pair.go2.onCollisionEnter(pair.go1);
        }
    }

    public boolean checkCollision(ICollisionable a, ICollisionable b)
    {
        Vec2 posDiff = Vec2.sub(a.getPosition(), b.getPosition());
        //TODO: fix this condition
        //if (a.getCollisionRadius() + b.getCollisionRadius() > posDiff.magnitude())
        //    return false;

        for (Line line1 : a.getCollisionLines())
        {
            for (Line line2 : b.getCollisionLines())
            {
                if (checkCollisionBetweenLines(line1, line2))
                    return true;
            }
        }
        
        return false;
    }

    //methods below base on algorithm found on: http://www.algorytm.edu.pl/algorytmy-maturalne/dwa-odcinki.html
    private float crossProduct(Vec2 X, Vec2 Y, Vec2 Z)
    {
        float x1 = Z.x - X.x;
        float y1 = Z.y - X.y;
        float x2 = Y.x - X.x;
        float y2 = Y.y - X.y;

        return x1*y2 - x2*y1;
    }

    //sprawdzenie, czy punkt Z(koniec odcinka pierwszego) leży na odcinku XY
    private boolean doesZBelongsToXY(Vec2 X, Vec2 Y, Vec2 Z)
    {
        return Math.min(X.x, Y.x) <= Z.x && Z.x <= Math.max(X.x, Y.x)
                && Math.min(X.y, Y.y) <= Z.y && Z.y <= Math.max(X.y, Y.y);
    }

    private boolean doLinesCross(Vec2 A, Vec2 B, Vec2 C, Vec2 D)
    {
        float v1 = crossProduct(C, D, A);
        float v2 = crossProduct(C, D, B);
        float v3 = crossProduct(A, B, C);
        float v4 = crossProduct(A, B, D);

        if((v1>0 && v2<0 || v1<0 && v2>0) && (v3>0 && v4<0 || v3<0 && v4>0)) return true;

        //sprawdzenie, czy koniec odcinka leży na drugim
        if(v1 == 0 && doesZBelongsToXY(C, D, A)) return true;
        if(v2 == 0 && doesZBelongsToXY(C, D, B)) return true;
        if(v3 == 0 && doesZBelongsToXY(A, B, C)) return true;
        if(v4 == 0 && doesZBelongsToXY(A, B, D)) return true;

        //odcinki nie mają punktów wspólnych
        return false;
    }

    private boolean checkCollisionBetweenLines(Line a, Line b)
    {
        return doLinesCross(a.b, a.e, b.b, b.e);
    }
}
