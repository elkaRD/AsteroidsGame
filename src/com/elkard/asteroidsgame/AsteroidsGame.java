package com.elkard.asteroidsgame;

public class AsteroidsGame implements IGameController{
    private GameLogic gameLogic;
    private GameRenderer gameRenderer;

    public enum MenuButton{
        PLAY,
        EXIT,
        AGAIN,
        EXITGAMEOVER,
        MAINMENU
    }

    public AsteroidsGame() {
        gameLogic = new GameLogic();
        gameRenderer = new GameRenderer(this);
    }

    public GameLogic getGameLogic()
    {
        return gameLogic;
    }

    public void run() {
        System.out.println("Game started");

        gameRenderer.showWindow();

        long lastTime = System.nanoTime();

        long prevTime = System.currentTimeMillis();

        while (true) {
//            long time = System.nanoTime();
//            int deltaMillis = (int) ((time - lastTime) / 1000000);
//            float deltaTime = (float) deltaMillis / 1000.0f;
//            lastTime = time;

            long curTime = System.currentTimeMillis();
            long elapsedTimeMillis = curTime - prevTime;
            float deltaTime = elapsedTimeMillis/1000F;
            prevTime = curTime;

            if (deltaTime < 0.013f) {
                try {
                    int toSleep  = (int) (13 - deltaTime * 1000);
                    Thread.sleep(toSleep);

                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }

            if (deltaTime > 0.25f) deltaTime = 0.25f;

            handleInput();
            gameLogic.onUpdate(deltaTime);
            gameRenderer.onUpdate(deltaTime);
            Debug.FlushLog();

            //System.out.println("delta time: " + deltaTime);
        }
    }

    private void handleInput()
    {
        gameRenderer.handleInput();
    }

    public void menuButtonClicked(MenuButton buttonClicked)
    {
        System.out.println("Clicked " + buttonClicked + " button");
    }

    public void keyPressed(char key)
    {
        System.out.println("pressed " + key);
    }

    public void keyReleased(char key)
    {
        System.out.println(("released " + key));

    }

    public void onAccelerate(float force)
    {
        gameLogic.onAccelerate(force);
    }

    public void onSlowDown(float force)
    {
        gameLogic.onSlowDown(force);
    }

    public void onTurn(float turn)
    {
        gameLogic.onTurn(turn);
    }

    public void onShoot()
    {
        gameLogic.onShoot();
    }

    public void onPause()
    {
        gameLogic.onPause();
    }
}