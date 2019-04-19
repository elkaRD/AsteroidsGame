package com.elkard.asteroidsgame;

public class GameLogic {

    private PlayerSpaceship player;

    public enum GameState{
        MAINMENU,
        GAMEPLAY,
        PAUSED,
        GAMEOVER,
        GOODBYE
    }

    private boolean isPaused = false;

    private float accelerateForce = 0;
    private float slowDownForce = 0;
    private float turnForce = 0;

//    public enum MenuButton{
//        PLAY,
//        EXIT,
//        AGAIN,
//        EXITGAMEOVER,
//        MAINMENU
//    }

//    public enum GameoverButton
//    {
//        AGAIN,
//        EXIT,
//        MAINMENU
//    }

    private GameState curState = GameState.MAINMENU;

    public GameLogic() {
        player = new PlayerSpaceship();
    }

    public void onUpdate(float deltaTime)
    {
        player.update(deltaTime);
    }

    public void startGame()
    {
        onReset();
    }

    public void onReset()
    {

    }

    public void onPause()
    {

    }

    public void onAccelerate(float force)
    {
        Debug.Log("accelerating");
        //accelerateForce = force;

        player.onAccelerate(force);
    }

    public void onSlowDown(float force)
    {
        Debug.Log("slowing down");
        player.onSlowDown(force);
    }

    public void onTurn(float turn)
    {
        player.onRotate(turn);
    }

    public void onShoot()
    {
        Debug.Log("shoot");
    }

    private void onDeath()
    {

    }


    public PlayerSpaceship getPlayer()
    {
        return player;
    }

    public void onMenuButtonClicked(AsteroidsGame.MenuButton buttonClicked)
    {
//        if (curState != GameState.MAINMENU) return;
//

//        if (buttonClicked == MainMenuButton.PLAY)
//        {
//
//        }
//        else if (buttonClicked == MainMenuButton.EXIT)
//        {
//
//        }

    }

    public void onGameoverButtonClicked(AsteroidsGame.MenuButton buttonClicked)
    {
//        if (curState != GameState.GAMEOVER) return;
//
//        if (buttonClicked == GameoverButton.AGAIN)
//        {
//            curState = GameState.GAMEPLAY;
//            onReset();
//        }
//        else if (buttonClicked == GameoverButton.EXIT)
//        {
//            curState = GameState.GOODBYE;
//
//        }
//        else if (buttonClicked == GameoverButton.MAINMENU)
//        {
//
//        }
    }

    private void checkGameState()
    {
        GameState temp = curState;
        getNextState();
        if (temp != curState)
        {
            stateChanged();
        }
    }

    private void getNextState()
    {
        if (curState == GameState.MAINMENU)
        {

        }
        else if (curState == GameState.GAMEPLAY)
        {

        }
        else if (curState == GameState.GAMEOVER)
        {

        }
        else if (curState == GameState.PAUSED)
        {

        }
        else if (curState == GameState.GOODBYE)
        {

        }
    }

    private void stateChanged()
    {
        if (curState == GameState.MAINMENU)
        {

        }
        else if (curState == GameState.GAMEPLAY)
        {

        }
        else if (curState == GameState.GAMEOVER)
        {

        }
        else if (curState == GameState.PAUSED)
        {

        }
        else if (curState == GameState.GOODBYE)
        {

        }
    }

    public GameState getState()
    {
        return curState;
    }

    private void updateGameplay()
    {

    }

    private void changedUpdate()
    {

    }
}