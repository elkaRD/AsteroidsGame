package com.elkard.asteroidsgame.Logic;

import com.elkard.asteroidsgame.Controller.IGameController;
import com.elkard.asteroidsgame.Logic.GameObjects.Asteroid;
import com.elkard.asteroidsgame.Logic.GameObjects.Bullet;
import com.elkard.asteroidsgame.Logic.GameObjects.PlayerSpaceship;
import com.elkard.asteroidsgame.Logic.GameObjects.Weapons.StandardWeapon;
import com.elkard.asteroidsgame.Logic.GameObjects.Weapons.Weapon;
import com.elkard.asteroidsgame.RandomGenerator;
import com.elkard.asteroidsgame.Vec2;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.fail;

public class GameLogicTest
{
    private TestController controller;
    private GameLogic gameLogic;

    @Before
    public void initLogic()
    {
        controller = new TestController();
        gameLogic = controller.getGameLogic();
    }

    @Test
    public void checkPlayerMovement()
    {
        gameLogic.startTestGame(false, true, true, true);

        PlayerSpaceship player = gameLogic.getCurPlayer();
        Vec2 startPos = player.getPosition();

        controller.update(2f);

        if (!player.getPosition().equals(startPos))
            fail("player should have stayed in the same place");

        controller.onAccelerate(1f);
        controller.update(1f);

        if (player.getPosition().equals(startPos))
            fail("player should have moved");

        if (player.getPosition().x <= startPos.x || player.getPosition().y != startPos.y)
            fail("player should have move to right");

        controller.onTurn(-1f);
        controller.update(1f);
        controller.onAccelerate(1f);
        controller.update(1f);

        if (player.getPosition().y == startPos.y)
            fail("player should have changed direction");
    }

    @Test
    public void checkPlayerTurning()
    {
        gameLogic.startTestGame(false, true, true, true);

        PlayerSpaceship player = gameLogic.getCurPlayer();

        controller.update(0.1f);
        controller.onTurn(-1f);
        controller.update(0.1f);

        if (player.getRotation() == 0)
            fail("player did not rotate");

        if (player.getRotation() >= 0)
            fail("player did not rotate in the right direction");

        controller.onTurn(1f);
        controller.update(0.1f);

        if (player.getRotation() != 0)
            fail("player should have return to initial rotation");
    }

    @Test
    public void checkBoosterAppearing()
    {
        gameLogic.startTestGame(false, true, true, true);

        PlayerSpaceship player = gameLogic.getCurPlayer();

        controller.update(1f);

        if (gameLogic.getObjectsRenderLines(GameLogic.ObjectType.BOOSTER).length != 0)
            fail("booster should be invisible");

        controller.onAccelerate(1f);
        controller.update(0.01f);

        if (gameLogic.getObjectsRenderLines(GameLogic.ObjectType.BOOSTER).length == 0)
            fail("booster should be visible");

        controller.update(3f);

        if (gameLogic.getObjectsRenderLines(GameLogic.ObjectType.BOOSTER).length != 0)
            fail("booster should disappear");
    }

    @Test
    public void checkShootingBullets()
    {
        gameLogic.startTestGame(false, true, true, true);

        PlayerSpaceship player = gameLogic.getCurPlayer();

        if (gameLogic.getObjectsRenderLines(GameLogic.ObjectType.BULLET).length != 0)
            fail("should not be any bullet");

        controller.update(1f);
        controller.onStartShooting();
        controller.update(0.1f);
        controller.onEndShooting();
        controller.update(0.1f);

        if (gameLogic.getObjectsRenderLines(GameLogic.ObjectType.BULLET).length == 0)
            fail("should be at least one bullet");
    }

    @Test
    public void checkDeath()
    {
        gameLogic.startTestGame(false, false, true, true);

        int lives = gameLogic.getRemainingLives();

        Asteroid asteroid = new Asteroid(gameLogic, new Vec2(gameLogic.getWidth() - 200, gameLogic.getHeight()/2));
        asteroid.setStatic(true);

        controller.update(1f);

        if (gameLogic.getRemainingLives() != lives)
            fail("wrong remaining lives: " + gameLogic.getRemainingLives() + "!=" + lives);

        for (int i = 0; i < 120; i++)
        {
            controller.onAccelerate(1f);
            controller.update(0.016f);
        }

        if (gameLogic.getRemainingLives() >= lives)
            fail("did not lost a live");
    }

    @Test
    public void checkAsteroidDestruction()
    {
        gameLogic.startTestGame(false, false, true, false);

        Asteroid asteroid = new Asteroid(gameLogic, new Vec2(400, gameLogic.getHeight()/2));
        asteroid.setStatic(true);

        controller.update(1f);

        if (gameLogic.getObjectsByType(GameLogic.ObjectType.ASTEROID).size() != 1)
            fail("should be only one asteroid in the current game");

        GameObject bulletSpot = new GameObject(gameLogic)
                .setPosition(new Vec2(200, gameLogic.getHeight()/2));
        Weapon weapon = new StandardWeapon(gameLogic, bulletSpot);

        controller.update(1f);

        new Bullet(gameLogic, weapon);

        controller.fewUpdates(120, 0.016f);

        if (gameLogic.getObjectsByType(GameLogic.ObjectType.ASTEROID).size() < 2)
            fail("asteroid should have been split into two smaller objects");

        controller.fewUpdates(1200, 0.016f);

        if (gameLogic.getObjectsByType(GameLogic.ObjectType.ASTEROID).size() != 2)
            fail("only 2 parts should remain after longer time");
    }

    @Test
    public void checkScreenEdgesTransition()
    {
        gameLogic.startTestGame(false, false, true, true);
        PlayerSpaceship player = gameLogic.getCurPlayer();

        int w = gameLogic.getWidth();
        int h = gameLogic.getHeight();
        controller.update(1f);

        float prevPosX = player.getPosition().x;
        boolean didTeleport = false;

        for (int i = 0; i < 1200; i++)
        {
            controller.onAccelerate(1f);
            controller.update(0.016f);

            if (player.getPosition().x > w)
                fail("player is out of screen");

            if (player.getPosition().x < prevPosX)
                didTeleport = true;

            prevPosX = player.getPosition().x;
        }

        if (!didTeleport)
            fail("did not detect teleport when reached edge");
    }

    @Test
    public void checkScreenEdgesMirroring()
    {
        gameLogic.startTestGame(false, false, true, false);

        int w = gameLogic.getWidth();
        int h = gameLogic.getHeight();

        Asteroid asteroid = new Asteroid(gameLogic, new Vec2(w/2, h/2));
        asteroid.setStatic(true);

        controller.update(1f);

        if (gameLogic.getObjectsByType(GameLogic.ObjectType.ASTEROID).size() != 1)
            fail("something went wrong when preparing test");

        int renderLines = gameLogic.getObjectsRenderLines(GameLogic.ObjectType.ASTEROID).length;
        String result = "";

        result += checkEdgeMirroring(controller, asteroid, "left", 5, h/2, 2*renderLines);
        result += checkEdgeMirroring(controller, asteroid, "right", w-5, h/2, 2*renderLines);
        result += checkEdgeMirroring(controller, asteroid, "top", w/2, 5, 2*renderLines);
        result += checkEdgeMirroring(controller, asteroid, "bottom", w/2, h-5, 2*renderLines);

        result += checkEdgeMirroring(controller, asteroid, "left-top", 5, 5, 4*renderLines);
        result += checkEdgeMirroring(controller, asteroid, "left-bottom", 5, h-5, 4*renderLines);
        result += checkEdgeMirroring(controller, asteroid, "right-top", w-5, 5, 4*renderLines);
        result += checkEdgeMirroring(controller, asteroid, "right-bottom", w-5, h-5, 4*renderLines);

        if (result.length() > 0)
            fail(result);
    }

    @Test
    public void checkScoreUpdate()
    {
        gameLogic.startTestGame(false, false, true, false);

        Asteroid asteroid = new Asteroid(gameLogic, new Vec2());
        asteroid.setStatic(true);

        int scoreAtBegin = gameLogic.getCurScore();

        controller.update(1f);
        asteroid.destroy();
        controller.update(1f);

        if (gameLogic.getCurScore() <= scoreAtBegin)
            fail("score did not increase");
    }

    @Test
    public void checkPause()
    {
        gameLogic.startTestGame(false, false, true, true);
        PlayerSpaceship player = gameLogic.getCurPlayer();

        Vec2 prevPos = player.getPosition().clone();
        controller.onAccelerate(1f);
        controller.onTurn(RandomGenerator.getFloat(-1f, 1f));
        controller.update(0.016f);

        for (int i = 0; i < 500; i++)
        {
            controller.onAccelerate(1f);
            controller.onTurn(RandomGenerator.getFloat(-1f, 1f));
            controller.update(0.016f);

            if (prevPos.equals(player.getPosition()))
                fail("unexpected problem: player did not change its position");

            prevPos = player.getPosition().clone();
        }

        controller.onPause();
        controller.update(0.016f);
        prevPos = player.getPosition().clone();
        float prevRot = player.getRotation();

        for (int i = 0; i < 500; i++)
        {
            controller.onAccelerate(1f);
            controller.onTurn(RandomGenerator.getFloat(-1f, 1f));
            controller.update(0.016f);

            if (!prevPos.equals(player.getPosition()) || prevRot != player.getRotation())
                fail("player changed its transform in pause");
        }
    }

    private String checkEdgeMirroring(TestController controller, Asteroid asteroid, String edgeName, int w, int h, int expectedLines)
    {
        GameLogic gameLogic = controller.getGameLogic();

        asteroid.setPosition(w, h);
        controller.update(1f);

        int gotLines = gameLogic.getObjectsRenderLines(GameLogic.ObjectType.ASTEROID).length;
        if (gotLines != expectedLines)
            return new String(edgeName + " edge: problem with mirroring (" + gotLines + "!=" + expectedLines + ")\n");

        return "";
    }

    private class TestController implements IGameController
    {
        private GameLogic gameLogic;

        public boolean detectedStartedGame;
        public boolean detectedClosedGame;

        public TestController()
        {
            gameLogic = new GameLogic();
            gameLogic.attachController(this);
            reset();
        }

        public void reset()
        {
            detectedStartedGame = false;
            detectedClosedGame = false;
        }

        public void update(float delta)
        {
            gameLogic.onUpdate(delta);
        }

        public void fewUpdates(int repetition, float delta)
        {
            for (int i = 0; i < repetition; i++)
                gameLogic.onUpdate(delta);
        }

        @Override
        public void onAccelerate(float force)
        {
            gameLogic.onAccelerate(force);
        }

        @Override
        public void onSlowDown(float force)
        {
            gameLogic.onSlowDown(force);
        }

        @Override
        public void onTurn(float turn)
        {
            gameLogic.onTurn(turn);
        }

        @Override
        public void onStartShooting()
        {
            gameLogic.onStartShooting();
        }

        @Override
        public void onEndShooting()
        {
            gameLogic.onEndShooting();
        }

        @Override
        public void onPause()
        {
            gameLogic.onPause();
        }

        @Override
        public void onStartGame()
        {
            detectedStartedGame = true;
        }

        @Override
        public void onCloseGame()
        {
            detectedClosedGame = true;
        }

        @Override
        public float getScreenRatio()
        {
            return 1.67f;
        }

        @Override
        public void run()
        {

        }

        @Override
        public GameLogic getGameLogic()
        {
            return gameLogic;
        }
    }
}
