package com.elkard.asteroidsgame;

public class Physics
{
    private GameLogic gameEngine;

    public Physics(GameLogic gl)
    {
        gameEngine = gl;
    }

    public boolean checkCollision(ICollisionable a, ICollisionable b)
    {
        Vec2 posDiff = Vec2.sub(a.getPosition(), b.getPosition());
        if (posDiff.magnitude() < a.getCollisionRadius() + b.getCollisionRadius())
            return false;

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

    float iloczyn_wektorowy(Vec2 X, Vec2 Y, Vec2 Z)
    {
        float x1 = Z.x - X.x, y1 = Z.y - X.y,
                x2 = Y.x - X.x, y2 = Y.y - X.y;
        return x1*y2 - x2*y1;
    }
    //sprawdzenie, czy punkt Z(koniec odcinka pierwszego)
//leży na odcinku XY
    boolean sprawdz(Vec2 X, Vec2 Y, Vec2 Z)
    {
        return Math.min(X.x, Y.x) <= Z.x && Z.x <= Math.max(X.x, Y.x)
                && Math.min(X.y, Y.y) <= Z.y && Z.y <= Math.max(X.y, Y.y);
    }

    boolean czy_przecinaja(Vec2 A, Vec2 B, Vec2 C, Vec2 D)
    {
        float v1 = iloczyn_wektorowy(C, D, A),
                v2 = iloczyn_wektorowy(C, D, B),
                v3 = iloczyn_wektorowy(A, B, C),
                v4 = iloczyn_wektorowy(A, B, D);

        //sprawdzenie czy się przecinają - dla niedużych liczb
        //if(v1*v2 < 0 && v3*v4 < 0) return true;

        //sprawdzenie czy się przecinają - dla większych liczb
        if((v1>0&&v2<0||v1<0&&v2>0)&&(v3>0&&v4<0||v3<0&&v4>0)) return true;

        //sprawdzenie, czy koniec odcinka leży na drugim
        if(v1 == 0 && sprawdz(C, D, A)) return true;
        if(v2 == 0 && sprawdz(C, D, B)) return true;
        if(v3 == 0 && sprawdz(A, B, C)) return true;
        if(v4 == 0 && sprawdz(A, B, D)) return true;

        //odcinki nie mają punktów wspólnych
        return false;
    }

    private boolean checkCollisionBetweenLines(Line a, Line b)
    {
        return czy_przecinaja(a.b, a.e, b.b, b.e);
    }
}
