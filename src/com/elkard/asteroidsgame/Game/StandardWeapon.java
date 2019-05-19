package com.elkard.asteroidsgame.Game;

public class StandardWeapon extends Weapon
{
    public StandardWeapon(GameLogic gl, GameObject parentObject)
    {
        super(gl, parentObject);
    }

    @Override
    public void update(float delta)
    {
        super.update(delta);

        if (isShooting)
        {
            isShooting = false;
            new Bullet(gameLogic, this);
        }
    }
}
