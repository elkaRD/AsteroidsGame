package com.elkard.asteroidsgame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.Arrays;

public class GameLogic {

    private PlayerSpaceship player;
    private Physics physics;

    public enum GameState{
        INIT,
        MAINMENU,
        GAMEPLAY,
        PAUSED,
        GAMEOVER,
        GOODBYE
    }

    public enum MenuButton{
        MAIN_PLAY,
        MAIN_HIGHSCORES,
        MAIN_EXIT,
        GAMEOVER_AGAIN,
        GAMEOVER_RETURN,
        PAUSE_RESUME,
        PAUSE_MENU
    }

    private boolean isPaused = false;

    public final int screenWidth = 1280;
    public final int screenHeight = 720;

    private ArrayList<GameObject> players = new ArrayList<>();
    private ArrayList<GameObject> bullets = new ArrayList<>();
    private ArrayList<GameObject> asteroids = new ArrayList<>();

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

    private IGameController gameController;

    private int remainingLives;
    private int curLevel;
    private int curScore;

    private IGameState gameStateListener;

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
    private GameState prevState = GameState.INIT;

    public GameLogic()
    {
        //player = new PlayerSpaceship(this);

        launchPhysics();

        //TODO: remove this; just testing
//        for (int i = 0; i < 4; i++)
//            for (int j = 0; j < 3; j++)
//            {
//                Asteroid temp = new Asteroid(this);
//                temp.setPosition(150 + i*300, 50 + j*200);
//            }

//        int amountAsteroids = 5;
//
//        for (int i = 0; i < amountAsteroids; i++)
//        {
//            Vec2 pos = new Vec2(getWidth()/2, getHeight()/2);
//            pos.x += (float) Math.cos(Math.toRadians(360f/amountAsteroids * i)) * getHeight();
//            pos.y += (float) Math.sin(Math.toRadians(360f/amountAsteroids * i)) * getHeight();
//            Asteroid temp = new Asteroid(this, pos);
//        }



        //startGame();
        curState = GameState.MAINMENU;


    }

    private void launchPhysics()
    {
        physics = new Physics(this);
        physics.addGroupsToCheck(players, asteroids);
        physics.addGroupsToCheck(bullets, asteroids);
    }

    public void onUpdate(float deltaTime)
    {
//        player.update(deltaTime);
//
//        for (GameObject go : bullets)
//            go.update(deltaTime);

        if (curState == GameState.PAUSED) deltaTime = 0;

        for (GameObject go : gameObjects)
            go.updateObject(deltaTime);

        for (GameObject go : newObjects)
            gameObjects.add(go);

        for (GameObject go : objectsToRemove)
            gameObjects.remove(go);

        newObjects.clear();
        objectsToRemove.clear();

        ICollisionable[] tempArray = new ICollisionable[asteroids.size()];
        for (int i = 0; i < asteroids.size(); i++)
        {
            tempArray[i] = asteroids.get(i);
        }
        //physics.checkCollisionWithGroup(player, tempArray);

        if (asteroids.size() == 0) nextLevel();
        if (remainingLives == 0) onGameIsOver();

        physics.updatePhysics(deltaTime);

        if (prevState != curState)
        {
            gameStateListener.onStateChanged(prevState, curState);
            prevState = curState;
        }
    }

    public void menuButtonClicked(MenuButton buttonClicked)
    {
        System.out.println("Clicked " + buttonClicked + " button");

        switch (buttonClicked)
        {
            case MAIN_PLAY:
                startGame();
                break;

            case MAIN_HIGHSCORES:
                //TODO:
                break;

            case MAIN_EXIT:
                exitGame();
                break;

            case PAUSE_RESUME:
                isPaused = false;
                break;

            case PAUSE_MENU:
                //TODO:
                break;

            case GAMEOVER_AGAIN:
                startGame();
                break;

            case GAMEOVER_RETURN:
                //TODO:
                break;
        }
    }

    public void startGame()
    {
        onReset();
        curState = GameState.GAMEPLAY;
        player = new PlayerSpaceship(this);
        generateAsteroids();
    }

    public void exitGame()
    {

    }

    public void onReset()
    {
        players.clear();
        bullets.clear();
        asteroids.clear();
        gameObjects.clear();
        newObjects.clear();
        objectsToRemove.clear();

        remainingLives = 3;
        curLevel = 5;
        curScore = 0;
    }

    public void onPause()
    {
        isPaused = !isPaused;
        if (isPaused) curState = GameState.PAUSED;
        else curState = GameState.GAMEPLAY;
    }

    public void onGameIsOver()
    {
//        if (gameController == null) return;
//        gameController.onGameIsOver();

        curState = GameState.GAMEOVER;
    }

    public void onAccelerate(float force)
    {
        if (curState != GameState.GAMEPLAY) return;

        player.onAccelerate(force);
    }

    public void onSlowDown(float force)
    {
        if (curState != GameState.GAMEPLAY) return;

        player.onSlowDown(force);
    }

    public void onTurn(float turn)
    {
        if (curState != GameState.GAMEPLAY) return;

        player.onRotate(turn);
    }

    public void onStartShooting()
    {
        if (curState != GameState.GAMEPLAY) return;

        player.onStartShooting();
    }

    public void onEndShooting()
    {
        if (curState != GameState.GAMEPLAY) return;

        player.onEndShooting();
    }

    public void onDeath()
    {
        remainingLives--;

        if (remainingLives > 0)
             player = new PlayerSpaceship(this);
    }

    public int getRemainingLives()
    {
        return remainingLives;
    }

    public int getCurScore()
    {
        return curScore;
    }

    private void nextLevel()
    {
        curLevel++;
        curScore += 200 * curLevel;
        if (curLevel % 2 == 0 && remainingLives < 5);
            remainingLives++;

        generateAsteroids();
    }

    private void generateAsteroids()
    {
        float screenPerimeter = 2*screenHeight + 2*screenWidth;
        for (int i = 0; i < curLevel; i++)
        {
            float whereToSpot = screenPerimeter * i/curLevel + screenWidth / 2;
            whereToSpot %= screenPerimeter;
            Vec2 posToSpot = new Vec2();

            if (whereToSpot < screenWidth)
            {
                posToSpot.x = whereToSpot;
                posToSpot.y = 0;
            }
            else if (whereToSpot < screenWidth + screenHeight)
            {
                posToSpot.x = screenWidth;
                posToSpot.y = whereToSpot - screenWidth;
            }
            else if (whereToSpot < 2*screenWidth + screenHeight)
            {
                posToSpot.x = whereToSpot - screenWidth - screenHeight;
                posToSpot.y = screenHeight;
            }
            else
            {
                posToSpot.x = 0;
                posToSpot.y = whereToSpot - 2*screenWidth - screenHeight;
            }

            Asteroid temp = new Asteroid(this, posToSpot);
        }
    }

    public PlayerSpaceship getPlayer()
    {
        return player;
    }

    public void onAsteroidDestroyed(Asteroid asteroid, int asteroidSize)
    {
        curScore += (asteroidSize + 1) * 10;

    }

    public void onMenuButtonClicked(MenuButton buttonClicked)
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

    public void onGameoverButtonClicked(MenuButton buttonClicked)
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
        System.out.println("CALLED REMOVE ASTEROID, size: " + asteroids.size());
    }

    public void addPlayer(PlayerSpaceship newPlayer)
    {
        players.add(newPlayer);
    }

    public void removePlayer(PlayerSpaceship toRemove)
    {
        players.remove(toRemove);
    }

    public Line[] getPlayerRenderLines()
    {
        ArrayList<Line> result = new ArrayList<>();
        for (GameObject p : players) {
            result.addAll(Arrays.asList(p.getRenderLines()));
        }
        //result.addAll(Arrays.asList(player.getRenderLines()));
        result.addAll(Arrays.asList(player.booster.getRenderLines()));

        Line[] lines = new Line[result.size()];
        lines = result.toArray(lines);
        return lines;
    }

    public Line[] getBulletsRenderLines()
    {
        ArrayList<Line> renderLines = new ArrayList<>();
        for (GameObject bullet : bullets)
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
//        for (GameObject asteroid : asteroids)
//        {
//            Line[] temp = asteroid.getRenderLines();
//            for (Line line : temp)
//            {
//                renderLines.add(line);
//            }
//        }
        for (int i = 0; i < asteroids.size(); i++)
        {
            Line[] temp = asteroids.get(i).getRenderLines();
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

    public void attachController(IGameController gc)
    {
        gameController = gc;
    }

    public void setStateChangedListener(IGameState listener)
    {
        gameStateListener = listener;
    }
}