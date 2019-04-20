package com.elkard.asteroidsgame;

public class StandardWeapon extends Weapon implements IWeapon
{
    public StandardWeapon(GameLogic gl, GameObject parentObject)
    {
        super(gl, parentObject);
    }

    public void onShoot()
    {
        Bullet temp = new Bullet(gameEngine, this);
    }
}
