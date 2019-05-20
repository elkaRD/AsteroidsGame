package com.elkard.asteroidsgame.Logic;

public class MachineGun extends Weapon
{
    private final float minTimeSinceLastShoot = 0.05f;
    private float timeSinceLastShoot = 0f;

    public MachineGun(GameLogic gl, GameObject parentObject)
    {
        super(gl, parentObject);
    }

    @Override
    public void update(float delta)
    {
        super.update(delta);

        timeSinceLastShoot += delta;

        if (isShooting && timeSinceLastShoot > minTimeSinceLastShoot)
        {
            new Bullet(gameLogic, this);
            timeSinceLastShoot = 0f;
        }
    }
}
