package com.elkard.asteroidsgame;

public class Bullet extends GameObject
{
    private Vec2 bulletVelocity;
    private Vec2 bulletDirection = new Vec2();
    private float speed = 500;
    private float bulletLen = 20;

    private float remainingDst = 500;

    private GameObject debug;

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

    int counter = 5;

    public void update(float delta)
    {
        super.update(delta);

        move(Vec2.mul(bulletVelocity, delta));
        //setPosition(debug.getPosition());

        counter--;
        if (counter > 0)
        {
            System.out.println("debug bullet pos: " + getPosition() + ",   " + getRotation());
        }

    }

//    public Vec2[] getRenderPoints()
//    {
//        Vec2[] renderPoints = new Vec2[2];
//        renderPoints[0] = new Vec2(getPosition());
//        renderPoints[1] = new Vec2(Vec2.sub(getPosition(), Vec2.mul(bulletDirection, bulletLen)));
//
//        if (counter > 0)
//        {
//            System.out.println("another debug: " + renderPoints[0]);
//        }
//
//        return renderPoints;
//    }

    protected PolarLayout[] renderPoints()
    {
        PolarLayout[] points = new PolarLayout[2];

        for (int i = 0; i < 2; i++)
            points[i] = new PolarLayout();

        points[0].dst = 0;      points[0].rot = 0;
        points[1].dst = 20;     points[1].rot = 180;

        return points;
    }
}
