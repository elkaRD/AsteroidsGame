package com.elkard.asteroidsgame.Logic.GameObjects.Weapons;

import com.elkard.asteroidsgame.Logic.GameLogic;
import com.elkard.asteroidsgame.Logic.GameObject;
import com.elkard.asteroidsgame.Logic.GameObjects.Bullet;

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
