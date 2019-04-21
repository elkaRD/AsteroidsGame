package com.elkard.asteroidsgame;

public class StandardWeapon extends Weapon
{
    public StandardWeapon(GameLogic gl, GameObject parentObject)
    {
        super(gl, parentObject);
    }

    public void update(float delta)
    {
        super.update(delta);

        if (isShooting)
        {
            isShooting = false;
            Bullet temp = new Bullet(gameEngine, this);
        }
    }
}
