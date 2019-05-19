package com.elkard.asteroidsgame.Game;

public abstract class Weapon extends GameObject implements IWeapon
{
    protected GameObject parent;
    protected boolean isShooting = false;

    public Weapon(GameLogic gl, GameObject parentObject)
    {
        super(gl);

        parent = parentObject;
    }

    @Override
    public void update(float delta)
    {
        setPosition(parent.getPosition());
        setRotation(parent.getRotation());
    }

    @Override
    public final void onStartShooting()
    {
        isShooting = true;
    }

    @Override
    public final void onEndShooting()
    {
        isShooting = false;
    }
}
