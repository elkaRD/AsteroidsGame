package com.elkard.asteroidsgame.Game;

public class ShotGun extends Weapon
{
    private final float minTimeSinceLastShoot = 0.3f;
    private float timeSinceLastShoot = 0f;

    private float bulletRange = 5f;

    public ShotGun(GameLogic gl, GameObject parentObject)
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
            new Bullet(gameLogic, this, bulletRange);
            new Bullet(gameLogic, this, -bulletRange);
            timeSinceLastShoot = 0f;
        }
    }
}
