package com.elkard.asteroidsgame.Game;

public class RoundGun extends Weapon
{
    private final float minTimeSinceLastShoot = 0.4f;
    private float timeSinceLastShoot = 0f;

    private int amountOfBullets = 36;

    public RoundGun(GameLogic gl, GameObject parentObject)
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
            for (int i = 0; i < amountOfBullets; i++)
            {
                new Bullet(gameLogic, this, 360f/amountOfBullets*i)
                        .setRemainingDst(150)
                        .setSpeedMultiplier(2f);
            }
            timeSinceLastShoot = 0f;
            isShooting = false;
        }
    }
}
