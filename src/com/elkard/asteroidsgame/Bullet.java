package com.elkard.asteroidsgame;

public class Bullet extends GameObject
{
    private Vec2 bulletVelocity;
    private Vec2 bulletDirection = new Vec2();
    private float speed = 500;
    private float bulletLen = 20;

    private float remainingDst = 500;

    private GameObject debug;

    PolarLayout[] points = new PolarLayout[2];

    public Bullet(GameLogic gl, Weapon weapon, float speedMultiplier)
    {
        super(gl);

        setPosition(weapon.getPosition());
        setRotation(weapon.getRotation());

        speed *= speedMultiplier;
        bulletDirection.x = (float) Math.cos(Math.toRadians(getRotation()));
        bulletDirection.y = (float) Math.sin(Math.toRadians(getRotation()));

        bulletVelocity = bulletDirection.clone();
        bulletVelocity.mul(speed);

        gameEngine.addBullet(this);

        System.out.println("My pos: " + getPosition() + ",  " + getRotation());
        System.out.println("weapon: " + weapon.getPosition() + ",  " + weapon.getRotation());

        debug = weapon;

        for (int i = 0; i < 2; i++)
            points[i] = new PolarLayout();

        points[0].dst = 0;          points[0].rot = 0;
        points[1].dst = bulletLen;  points[1].rot = 180;
    }

    public Bullet(GameLogic gl, Weapon weapon)
    {
        this(gl, weapon, 1f);
    }

    public void cleanUp()
    {
        super.cleanUp();

        gameEngine.removeBullet(this);
    }

    public void update(float delta)
    {
        super.update(delta);

        move(Vec2.mul(bulletVelocity, delta));

        remainingDst -= speed * delta;
        if (remainingDst <= 0)
            cleanUp();
    }

    protected PolarLayout[] renderPoints()
    {
        return points;
    }


}
