package com.elkard.asteroidsgame.Logic;

import com.elkard.asteroidsgame.Controller.IGameController;
import com.elkard.asteroidsgame.Logic.GameObjects.PlayerSpaceship;
import com.elkard.asteroidsgame.Vec2;
import org.junit.Test;

import static junit.framework.TestCase.fail;

public class GameLogicTest
{
    @Test
    public void checkPlayerMovement()
    {
        TestController controller = new TestController();
        GameLogic gameLogic = controller.getGameLogic();
        gameLogic.startTestGame(false, true);

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
        TestController controller = new TestController();
        GameLogic gameLogic = controller.getGameLogic();
        gameLogic.startTestGame(false, true);

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
        TestController controller = new TestController();
        GameLogic gameLogic = controller.getGameLogic();
        gameLogic.startTestGame(false, true);

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
        TestController controller = new TestController();
        GameLogic gameLogic = controller.getGameLogic();
        gameLogic.startTestGame(false, true);

        PlayerSpaceship player = gameLogic.getCurPlayer();

        if (gameLogic.getObjectsRenderLines(GameLogic.ObjectType.BULLET).length != 0)
            fail("should not be any bullet");

        controller.update(1f);
        controller.onStartShooting();
        controller.update(0.1f);
        controller.update(0.1f);
        controller.onEndShooting();
        controller.update(0.1f);

        if (gameLogic.getObjectsRenderLines(GameLogic.ObjectType.BULLET).length == 0)
            fail("should be at least one bullet");
    }

    @Test
    public void checkDeath()
    {

    }

    @Test
    public void checkAsteroidDestruction()
    {

    }

    @Test
    public void checkScreenEdgesTransition()
    {

    }

    @Test
    public void checkScreenEdgesMirroring()
    {

    }

    @Test
    public void checkScoreUpdate()
    {

    }

    @Test
    public void checkPause()
    {

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
