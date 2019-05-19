package com.elkard.asteroidsgame.Game;

public class MachineGun extends Weapon
{
    private final float minTimeSinceLastShoot = 0.05f;
    private float timeSinceLastShoot = 0f;

    public MachineGun(GameLogic gl, GameObject parentObject)
    {
        super(gl, parentObject);
    }

    public void update(float delta)
    {
        super.update(delta);

        timeSinceLastShoot += delta;

        if (isShooting && timeSinceLastShoot > minTimeSinceLastShoot)
        {
            Bullet temp = new Bullet(gameEngine, this);
            timeSinceLastShoot = 0f;
        }
    }
}
