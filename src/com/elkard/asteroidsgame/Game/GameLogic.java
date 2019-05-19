package com.elkard.asteroidsgame.Game;

import com.elkard.asteroidsgame.IGameController;
import com.elkard.asteroidsgame.Line;
import com.elkard.asteroidsgame.Vec2;

import java.util.*;

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

    public enum ObjectType
    {
        PLAYER,
        BULLET,
        ASTEROID
    }

    private boolean isPaused = false;
    private boolean gameOver = false;

    private int screenWidth = 1280;
    private int screenHeight = 720;

    private HashMap<ObjectType, ArrayList<GameObject>> objects = new HashMap<>();

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

    private GameState curState = GameState.MAINMENU;
    private GameState prevState = GameState.INIT;

    public GameLogic()
    {
        for (ObjectType type : ObjectType.values())
        {
            objects.put(type, new ArrayList<>());
        }
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

        for (GameObject go : gameObjects)
            go.updateObject(deltaTime);

        for (GameObject go : newObjects)
            gameObjects.add(go);

        for (GameObject go : objectsToRemove)
            gameObjects.remove(go);

        newObjects.clear();
        objectsToRemove.clear();

        if (objects.get(ObjectType.ASTEROID).size() == 0) nextLevel();
        if (remainingLives == 0 && !gameOver) onGameIsOver();

        physics.updatePhysics(deltaTime);

        if (prevState != curState && gameStateListener != null)
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
                returnToMenu();
                break;

            case MAIN_EXIT:
                exitGame();
                break;

            case PAUSE_RESUME:
                onPause();
                break;

            case PAUSE_MENU:
                returnToMenu();
                break;

            case GAMEOVER_AGAIN:
                startGame();
                break;

            case GAMEOVER_RETURN:
                returnToMenu();
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

        remainingLives = 3;
        curLevel = 5;
        curScore = 0;

        isPaused = false;
        gameOver = false;

        screenWidth = (int) (screenHeight * gameController.getScreenRatio());
    }

    public void onPause()
    {
        isPaused = !isPaused;
        if (isPaused) curState = GameState.PAUSED;
        else curState = GameState.GAMEPLAY;
    }

    public void onGameIsOver()
    {
        curState = GameState.GAMEOVER;
        gameOver = true;
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

    public Line[] getPlayerRenderLines()
    {
        ArrayList<Line> result = new ArrayList<>();
        for (GameObject p : objects.get(ObjectType.PLAYER))
        {
            result.addAll(Arrays.asList(p.getRenderLines()));
        }

        result.addAll(Arrays.asList(player.booster.getRenderLines()));

        Line[] lines = new Line[result.size()];
        lines = result.toArray(lines);
        return lines;
    }

    public Line[] getBulletsRenderLines()
    {
        ArrayList<Line> renderLines = new ArrayList<>();
        for (GameObject bullet : objects.get(ObjectType.BULLET))
        {
            Line[] temp = bullet.getRenderLines();
            renderLines.add(temp[0]);
            renderLines.add(temp[1]);
        }

        Line[] toReturn = new Line[renderLines.size()];
        for (int i = 0; i < renderLines.size(); i++)
            toReturn[i] = renderLines.get(i);

        return toReturn;
    }

    public Line[] getAsteroidsRenderLines()
    {
        ArrayList<Line> renderLines = new ArrayList<>();
        ArrayList<GameObject> asteroids = objects.get(ObjectType.ASTEROID);

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

    public void attachController(IGameController gc)
    {
        gameController = gc;
    }

    public void setStateChangedListener(IGameState listener)
    {
        gameStateListener = listener;
    }
}