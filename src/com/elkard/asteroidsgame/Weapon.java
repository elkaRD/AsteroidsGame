package com.elkard.asteroidsgame;

public abstract class Weapon extends GameObject implements IWeapon
{
    protected GameObject parent;
    protected boolean isShooting = false;

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

    public final void onStartShooting()
    {
        isShooting = true;
    }

    public final void onEndShooting()
    {
        isShooting = false;
    }
}
