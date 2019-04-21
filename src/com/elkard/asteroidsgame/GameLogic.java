package com.elkard.asteroidsgame;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

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

    public final int screenWidth = 1280;
    public final int screenHeight = 720;

    private ArrayList<Bullet> bullets = new ArrayList<>();
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private ArrayList<GameObject> newObjects = new ArrayList<>();
    private ArrayList<GameObject> objectsToRemove = new ArrayList<>();

    public int getWidth()
    {
        return screenWidth;
    }

    public int getHeight()
    {
        return screenHeight;
    }

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
        player = new PlayerSpaceship(this);
    }

    public void onUpdate(float deltaTime)
    {
//        player.update(deltaTime);
//
//        for (GameObject go : bullets)
//            go.update(deltaTime);

        for (GameObject go : gameObjects)
            go.updateObject(deltaTime);

        for (GameObject go : newObjects)
            gameObjects.add(go);

        for (GameObject go : objectsToRemove)
            gameObjects.remove(go);

        newObjects.clear();
        objectsToRemove.clear();
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
        player.onShoot();
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

    public void addBullet(Bullet bullet)
    {
        bullets.add(bullet);
    }

    public void removeBullet(Bullet bullet)
    {
        bullets.remove(bullet);
    }

    public Line[] getBulletsRenderLines()
    {
        ArrayList<Line> renderLines = new ArrayList<>();
        for (Bullet bullet : bullets)
        {
            Line[] temp = bullet.getRenderLines();
            renderLines.add(temp[0]);
            renderLines.add(temp[1]);
        }
        //TODO: check if this solution works

        Line[] toReturn = new Line[renderLines.size()];
        for (int i = 0; i < renderLines.size(); i++)
            toReturn[i] = renderLines.get(i);

        return toReturn;
    }

    public void registerObject(GameObject newObject)
    {
        newObjects.add(newObject);
    }

    public void removeObject(GameObject toRemove)
    {
        objectsToRemove.add(toRemove);
    }
}