package com.elkard.asteroidsgame.Controller;

import com.elkard.asteroidsgame.Logic.GameLogic;
import jdk.internal.util.xml.impl.Input;
import org.junit.Test;

import static com.elkard.asteroidsgame.Controller.InputHandler.KEY_ESC;
import static com.elkard.asteroidsgame.Controller.InputHandler.KEY_SPACE;
import static org.junit.Assert.*;

public class InputTest
{
    @Test
    public void checkMoveForward()
    {
        InputHandler inputHandler = new InputHandler();
        TestController controller = new TestController();

        pressKey('W', inputHandler, controller);

        if (!controller.detectedAccelerate)
            fail("did not detect the pressed key");

        if (controller.gotAccelerate != 1f)
            fail("wrong accelerate force");

        if (controller.gotSlowDown != 0f || controller.gotTurn != 0f)
            fail("got wrong events");

        refresh(inputHandler, controller);

        if (!controller.detectedAccelerate)
            fail("did not detect the pressed key after refresh");

        if (controller.gotAccelerate != 1f)
            fail("wrong accelerate force after refresh");

        releaseKey('W', inputHandler, controller);

        if (controller.detectedAccelerate)
            fail("problem with releasing the key");

        if (controller.gotAccelerate != 0f)
            fail("problem with releasing the key - got a wrong force");

        if (controller.gotSlowDown != 0f || controller.gotTurn != 0f)
            fail("got wrong events after releasing");

        refresh(inputHandler, controller);

        if (controller.detectedAccelerate)
            fail("problem with releasing the key after refresh");

        if (controller.gotAccelerate != 0f)
            fail("problem with releasing the key - got a wrong force after refresh");
    }

    @Test
    public void checkMoveBack()
    {
        InputHandler inputHandler = new InputHandler();
        TestController controller = new TestController();

        pressKey('S', inputHandler, controller);

        if (!controller.detectedSlowDown)
            fail("did not detect the pressed key");

        if (controller.gotSlowDown != 1f)
            fail("wrong slowDown force");

        if (controller.gotAccelerate != 0f || controller.gotTurn != 0f)
            fail("got wrong events");

        refresh(inputHandler, controller);

        if (!controller.detectedSlowDown)
            fail("did not detect the pressed key after refresh");

        if (controller.gotSlowDown != 1f)
            fail("wrong slowDown force after refresh");

        releaseKey('S', inputHandler, controller);

        if (controller.detectedSlowDown)
            fail("problem with releasing the key");

        if (controller.gotSlowDown != 0f)
            fail("problem with releasing the key - got a wrong force");

        if (controller.gotAccelerate != 0f || controller.gotTurn != 0f)
            fail("got wrong events after releasing");

        refresh(inputHandler, controller);

        if (controller.detectedSlowDown)
            fail("problem with releasing the key after refresh");

        if (controller.gotSlowDown != 0f)
            fail("problem with releasing the key - got a wrong force after refresh");
    }

    @Test
    public void checkRotateLeft()
    {
        InputHandler inputHandler = new InputHandler();
        TestController controller = new TestController();

        pressKey('A', inputHandler, controller);

        if (!controller.detectedTurn)
            fail("did not detect the pressed key");

        if (controller.gotTurn != -1f)
            fail("wrong turn left force");

        if (controller.gotSlowDown != 0f || controller.gotAccelerate != 0f)
            fail("got wrong events");

        refresh(inputHandler, controller);

        if (!controller.detectedTurn)
            fail("did not detect the pressed key after refresh");

        if (controller.gotTurn != -1f)
            fail("wrong turn left force after refresh");

        releaseKey('A', inputHandler, controller);

        if (controller.gotTurn != 0f)
            fail("problem with releasing the key - got a wrong force");

        if (controller.gotSlowDown != 0f || controller.gotAccelerate != 0f)
            fail("got wrong events after releasing");

        refresh(inputHandler, controller);

        if (controller.gotTurn != 0f)
            fail("problem with releasing the key - got a wrong force after refresh");
    }

    @Test
    public void checkRotateRight()
    {
        InputHandler inputHandler = new InputHandler();
        TestController controller = new TestController();

        pressKey('D', inputHandler, controller);

        if (!controller.detectedTurn)
            fail("did not detect the pressed key");

        if (controller.gotTurn != 1f)
            fail("wrong turn left force");

        if (controller.gotSlowDown != 0f || controller.gotAccelerate != 0f)
            fail("got wrong events");

        refresh(inputHandler, controller);

        if (!controller.detectedTurn)
            fail("did not detect the pressed key after refresh");

        if (controller.gotTurn != 1f)
            fail("wrong turn left force after refresh");

        releaseKey('D', inputHandler, controller);

        if (controller.gotTurn != 0f)
            fail("problem with releasing the key - got a wrong force");

        if (controller.gotSlowDown != 0f || controller.gotAccelerate != 0f)
            fail("got wrong events after releasing");

        refresh(inputHandler, controller);

        if (controller.gotTurn != 0f)
            fail("problem with releasing the key - got a wrong force after refresh");
    }

    @Test
    public void checkShooting()
    {
        InputHandler inputHandler = new InputHandler();
        TestController controller = new TestController();

        pressKey(KEY_SPACE, inputHandler, controller);

        if (!controller.detectedStartShooting)
            fail("did not detect start shooting");

        if (controller.detectedEndShooting)
            fail("detected end shooting");

        refresh(inputHandler, controller);

        if (controller.detectedStartShooting || controller.detectedEndShooting)
            fail("detected event when in idle");

        releaseKey(KEY_SPACE, inputHandler, controller);

        if (controller.detectedStartGame)
            fail("detected start shooting after key release");

        if (!controller.detectedEndShooting)
            fail("did not detect end shooting");

        refresh(inputHandler, controller);

        if (controller.detectedStartShooting || controller.detectedEndShooting)
            fail("detected event when in idle after release");
    }

    @Test
    public void checkPause()
    {
        InputHandler inputHandler = new InputHandler();
        TestController controller = new TestController();

        pressKey(KEY_ESC, inputHandler, controller);

        if (!controller.detectedPause)
            fail("did not detect pause");

        refresh(inputHandler, controller);

        if (controller.detectedPause)
            fail("detected pause when in idle");

        releaseKey(KEY_ESC, inputHandler, controller);

        if (controller.detectedPause)
            fail("detected pause after key release");

        refresh(inputHandler, controller);

        if (controller.detectedPause)
            fail("detected event when in idle after release");
    }

    private void pressKey(int key, InputHandler inputHandler, TestController controller)
    {
        controller.reset();
        inputHandler.onKeyPressed(key, true);
        inputHandler.handleInput(controller);
        inputHandler.refresh();
    }

    private void releaseKey(int key, InputHandler inputHandler, TestController controller)
    {
        controller.reset();
        inputHandler.onKeyPressed(key, false);
        inputHandler.handleInput(controller);
        inputHandler.refresh();
    }

    private void refresh(InputHandler inputHandler, TestController controller)
    {
        controller.reset();
        inputHandler.handleInput(controller);
        inputHandler.refresh();
    }

    private class TestController implements IGameController
    {
        public boolean detectedAccelerate;
        public boolean detectedSlowDown;
        public boolean detectedTurn;
        public boolean detectedStartShooting;
        public boolean detectedEndShooting;
        public boolean detectedPause;
        public boolean detectedStartGame;
        public boolean detectedCloseGame;

        public float gotAccelerate;
        public float gotSlowDown;
        public float gotTurn;
        
        public TestController()
        {
            reset();
        }
        
        public void reset()
        {
            detectedAccelerate = false;
            detectedSlowDown = false;
            detectedTurn = false;
            detectedStartShooting = false;
            detectedEndShooting = false;
            detectedPause = false;
            detectedStartGame = false;
            detectedCloseGame = false;

            gotAccelerate = 0f;
            gotSlowDown = 0f;
            gotTurn = 0f;
        }
        
        @Override
        public void onAccelerate(float force)
        {
            detectedAccelerate = true;
            gotAccelerate = force;
        }

        @Override
        public void onSlowDown(float force)
        {
            detectedSlowDown = true;
            gotSlowDown = force;
        }

        @Override
        public void onTurn(float turn)
        {
            detectedTurn = true;
            gotTurn = turn;
        }

        @Override
        public void onStartShooting()
        {
            detectedStartShooting = true;
        }

        @Override
        public void onEndShooting()
        {
            detectedEndShooting = true;
        }

        @Override
        public void onPause()
        {
            detectedPause = true;
        }

        @Override
        public void onStartGame()
        {
            detectedStartGame = true;
        }

        @Override
        public void onCloseGame()
        {
            detectedCloseGame = true;
        }

        @Override
        public float getScreenRatio()
        {
            return -1;
        }

        @Override
        public void run()
        {
            
        }

        @Override
        public GameLogic getGameLogic()
        {
            return null;
        }
    }
}