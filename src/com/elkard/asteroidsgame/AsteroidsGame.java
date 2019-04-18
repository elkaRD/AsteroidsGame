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

    public void run() {
        System.out.println("Game started");

        gameRenderer.showWindow();

        while (true) {
            handleInput();
            gameLogic.onUpdate(0.016f);
            gameRenderer.onUpdate(0.016f);
            Debug.FlushLog();
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