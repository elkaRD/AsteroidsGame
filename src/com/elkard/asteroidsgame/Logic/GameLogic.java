///
/// EN: Project for the Event-Driven Programming course
///     Warsaw University of Technology
///     Java Asteroids Game
///
/// PL: Projekt PROZ (Programowanie zdarzeniowe)
///     PW WEiTI 19L
///     Gra typu Asteroids napisana w Javie
///
/// Copyright (C) Robert Dudzinski 2019
///
/// File: GameLogic.java


package com.elkard.asteroidsgame.Logic;

import com.elkard.asteroidsgame.Controller.IGameController;
import com.elkard.asteroidsgame.Line;
import com.elkard.asteroidsgame.Logic.GameObjects.Asteroid;
import com.elkard.asteroidsgame.Logic.GameObjects.PlayerSpaceship;
import com.elkard.asteroidsgame.Logic.GameObjects.Weapons.WeaponsFactory;
import com.elkard.asteroidsgame.Vec2;
import com.elkard.asteroidsgame.View.AudioManager;

import java.util.*;

import static com.elkard.asteroidsgame.View.AudioManager.EXPLOSION;
import static com.elkard.asteroidsgame.View.AudioManager.PAUSE;

public class GameLogic
{
    public enum GameState
    {
        INIT,
        MAINMENU,
        GAMEPLAY,
        PAUSED,
        GAMEOVER,
        GOODBYE
    }

    public enum MenuButton
    {
        MAIN_PLAY,
        MAIN_EXIT,
        GAMEOVER_AGAIN,
        GAMEOVER_RETURN,
        PAUSE_RESUME,
        PAUSE_MENU
    }

    public enum ObjectType
    {
        PLAYER,
        BULLET,
        ASTEROID,
        BOOSTER
    }

    private PlayerSpaceship player;
    private Physics physics;
    private IGameController gameController;
    private IGameState gameStateListener;

    public final WeaponsFactory weaponsFactory = new WeaponsFactory(this);

    private HashMap<ObjectType, ArrayList<GameObject>> objects = new HashMap<>();

    private ArrayList<GameObject> gameObjects = new ArrayList<>();
    private ArrayList<GameObject> newObjects = new ArrayList<>();
    private ArrayList<GameObject> objectsToRemove = new ArrayList<>();

    private boolean isPaused = false;
    private boolean gameOver = false;
    private boolean newHighScore = false;

    private int originalScreenWidth = 1280;
    private int originalScreenHeight = 720;
    private int screenWidth = originalScreenWidth;
    private int screenHeight = originalScreenHeight;

    private int remainingLives;
    private int curLevel;
    private int curScore;

    private GameState curState = GameState.MAINMENU;
    private GameState prevState = GameState.INIT;

    private boolean debugMode = false;

    public GameLogic()
    {
        for (ObjectType type : ObjectType.values())
            objects.put(type, new ArrayList<>());

        launchPhysics();

        curState = GameState.MAINMENU;
    }

    private void launchPhysics()
    {
        physics = new Physics();
        physics.addGroupsToCheck(objects.get(ObjectType.PLAYER), objects.get(ObjectType.ASTEROID));
        physics.addGroupsToCheck(objects.get(ObjectType.BULLET), objects.get(ObjectType.ASTEROID));
    }

    public void onUpdate(float deltaTime)
    {
        if (curState == GameState.PAUSED) deltaTime = 0;

        updateObjects(deltaTime);
        checkGameState();
        physics.updatePhysics(deltaTime);
        handleStateChanged();
    }

    private void updateObjects(float deltaTime)
    {
        for (GameObject go : gameObjects)
            go.updateObject(deltaTime);

        for (GameObject go : newObjects)
            go.updateObject(deltaTime);

        for (GameObject go : newObjects)
            gameObjects.add(go);

        for (GameObject go : objectsToRemove)
            gameObjects.remove(go);

        newObjects.clear();
        objectsToRemove.clear();
    }

    private void checkGameState()
    {
        if (curState == GameState.GAMEPLAY)
        {
            if (objects.get(ObjectType.ASTEROID).size() == 0) nextLevel();
            if (remainingLives == 0 && !gameOver) onGameIsOver();
        }
    }

    private void handleStateChanged()
    {
        if (prevState != curState && gameStateListener != null)
        {
            gameStateListener.onStateChanged(prevState, curState);
            prevState = curState;
        }
    }

    public GameState getCurState()
    {
        return curState;
    }

    public int getWidth()
    {
        return screenWidth;
    }

    public int getHeight()
    {
        return screenHeight;
    }

    public void menuButtonClicked(MenuButton buttonClicked)
    {
        System.out.println("Clicked " + buttonClicked + " button");

        switch (buttonClicked)
        {
            case MAIN_PLAY:
            case GAMEOVER_AGAIN:
                startGame();
                //startTestGame(false, false, false, true);
                break;

            case GAMEOVER_RETURN:
            case PAUSE_MENU:
                returnToMenu();
                break;

            case MAIN_EXIT:
                exitGame();
                break;

            case PAUSE_RESUME:
                onPause();
                break;
        }
    }

    public void startGame()
    {
        onReset();
        gameController.onStartGame();
        curState = GameState.GAMEPLAY;
        player = new PlayerSpaceship(this);
        generateAsteroids();

        debugMode = false;
    }

    public void startTestGame(boolean enabledAsteroids, boolean godMode, boolean playerCollisionAtStart, boolean createPlayer)
    {
        onReset();
        gameController.onStartGame();
        curState = GameState.GAMEPLAY;

        if (createPlayer)
        {
            player = new PlayerSpaceship(this, playerCollisionAtStart);

            if (godMode)
                player.enablePhysics(false);
        }

        if (enabledAsteroids)
            generateAsteroids();

        debugMode = true;
    }

    public void exitGame()
    {
        curState = GameState.GOODBYE;
        gameController.onCloseGame();
    }

    public void returnToMenu()
    {
        curState = GameState.MAINMENU;
    }

    public void onReset()
    {
        clearObjects();
        gameObjects.clear();
        newObjects.clear();
        objectsToRemove.clear();

        weaponsFactory.reset();

        remainingLives = 3;
        curLevel = 5;
        curScore = 0;

        isPaused = false;
        gameOver = false;
        newHighScore = false;

        screenWidth = (int) (originalScreenHeight * gameController.getScreenRatio());
        if (screenWidth < originalScreenWidth)
        {
            screenWidth = originalScreenWidth;
            screenHeight = (int) (originalScreenWidth / gameController.getScreenRatio());
        }
    }

    public void onPause()
    {
        if  (curState != GameState.GAMEPLAY && curState != GameState.PAUSED) return;

        isPaused = !isPaused;
        if (isPaused) curState = GameState.PAUSED;
        else curState = GameState.GAMEPLAY;

        AudioManager.getInstance().playSound(PAUSE);
    }

    public void onLostFocus()
    {
        if (!isPaused)
            onPause();
    }

    public void onGameIsOver()
    {
        curState = GameState.GAMEOVER;
        gameOver = true;

        if (curScore > GameStats.getInstance().getHighScore())
        {
            GameStats.getInstance().setHighScore(curScore);
            newHighScore = true;
        }
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

        AudioManager.getInstance().playSound(EXPLOSION);
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
        if (debugMode) return;

        curLevel++;
        curScore += 200 * curLevel;
        if (curLevel % 2 == 0 && remainingLives < 5)
            remainingLives++;

        weaponsFactory.nextLevel();
        player.reset();
        generateAsteroids();
    }

    private void generateAsteroids()
    {
        float screenHalfPerimeter = screenHeight + screenWidth;
        for (int i = 0; i < curLevel; i++)
        {
            float whereToSpot = screenHalfPerimeter * i/curLevel + screenWidth / 2;
            whereToSpot %= screenHalfPerimeter;
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

            new Asteroid(this, posToSpot);
        }
    }

    public void onAsteroidDestroyed(Asteroid asteroid, int asteroidSize)
    {
        curScore += (asteroidSize + 1) * 10;
        AudioManager.getInstance().playSound(EXPLOSION);
    }

    public GameState getState()
    {
        return curState;
    }

    public void clearObjects()
    {
        for (ArrayList<GameObject> list : objects.values())
        {
            list.clear();
        }
    }

    public void addObject(GameObject gameObject, ObjectType objectType)
    {
        objects.get(objectType).add(gameObject);
    }

    public void removeObject(GameObject gameObject)
    {
        for (ArrayList<GameObject> list : objects.values())
        {
            if (list.contains(gameObject))
            {
                list.remove(gameObject);
                break;
            }
        }
    }

    public Line[] getObjectsRenderLines(ObjectType objectType)
    {
        ArrayList<Line> renderLines = new ArrayList<>();
        ArrayList<GameObject> objectsToRender = objects.get(objectType);

        for (int i = 0; i < objectsToRender.size(); i++)
        {
            Line[] temp = objectsToRender.get(i).getRenderLines();
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

    public ArrayList<GameObject> getObjectsByType(ObjectType type)
    {
        return objects.get(type);
    }

    public void registerObject(GameObject newObject)
    {
        newObjects.add(newObject);
    }

    public void attachController(IGameController gc)
    {
        gameController = gc;
    }

    public void setStateChangedListener(IGameState listener)
    {
        gameStateListener = listener;
    }

    public PlayerSpaceship getCurPlayer()
    {
        return player;
    }

    public boolean isNewHighScore()
    {
        return newHighScore;
    }
}