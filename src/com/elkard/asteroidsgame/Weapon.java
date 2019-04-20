package com.elkard.asteroidsgame;

public abstract class Weapon extends GameObject implements IWeapon
{
    protected GameObject parent;

    public Weapon(GameLogic gl, GameObject parentObject)
    {
        super(gl);

        parent = parentObject;
    }

    public void update(float delta)
    {
        setPosition(parent.getPosition());
        setRotation(parent.getRotation());
    }
}
