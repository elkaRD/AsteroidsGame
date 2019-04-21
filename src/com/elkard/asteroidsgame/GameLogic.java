package com.elkard.asteroidsgame;

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
    private ArrayList<Asteroid> asteroids = new ArrayList<>();

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

    public GameLogic()
    {
        player = new PlayerSpaceship(this);

        //TODO: remove this; just testing
//        for (int i = 0; i < 4; i++)
//            for (int j = 0; j < 3; j++)
//            {
//                Asteroid temp = new Asteroid(this);
//                temp.setPosition(150 + i*300, 50 + j*200);
//            }

        int amountAsteroids = 5;

        for (int i = 0; i < amountAsteroids; i++)
        {
            Vec2 pos = new Vec2(getWidth()/2, getHeight()/2);
            pos.x = (float) Math.cos(Math.toRadians(360f/amountAsteroids * i)) * getHeight();
            pos.y = (float) Math.sin(Math.toRadians(360f/amountAsteroids * i)) * getHeight();
            Asteroid temp = new Asteroid(this, pos);
        }
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

//    public void onSingleShoot()
//    {
//        Debug.Log("shoot");
//        player.onSingleShoot();
//    }

    public void onStartShooting()
    {
        player.onStartShooting();
    }

    public void onEndShooting()
    {
        player.onEndShooting();
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

    public void addAsteroid(Asteroid asteroid)
    {
        asteroids.add(asteroid);
    }

    public void removeAsteroid(Asteroid asteroid)
    {
        asteroids.remove(asteroid);
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
        //TODO: think about switching from arrays to ArrayLists

        Line[] toReturn = new Line[renderLines.size()];
        for (int i = 0; i < renderLines.size(); i++)
            toReturn[i] = renderLines.get(i);

        return toReturn;
    }

    public Line[] getAsteroidsRenderLines()
    {
        ArrayList<Line> renderLines = new ArrayList<>();
        for (Asteroid asteroid : asteroids)
        {
            Line[] temp = asteroid.getRenderLines();
            for (Line line : temp)
            {
                renderLines.add(line);
            }
        }
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