package com.elkard.asteroidsgame.Logic.GameObjects.Weapons;

import com.elkard.asteroidsgame.Logic.GameLogic;
import com.elkard.asteroidsgame.Logic.GameObject;

public class WeaponsFactory
{
    private GameLogic gameLogic;
    private int curIndex;

    public WeaponsFactory(GameLogic gameLogic)
    {
        this.gameLogic = gameLogic;
        reset();
    }

    public void reset()
    {
        curIndex = 0;
    }

    public Weapon getWeapon(GameObject parent)
    {
        switch (curIndex)
        {
            case 1: return new MachineGun(gameLogic, parent);
            case 2: return new ShotGun(gameLogic, parent);
            case 3: return new RoundGun(gameLogic, parent);
        }

        return new StandardWeapon(gameLogic, parent);
    }

    public void nextLevel()
    {
        curIndex++;
        curIndex %= 4;
    }
}
