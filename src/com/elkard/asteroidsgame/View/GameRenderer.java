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
/// File: GameRenderer.java


package com.elkard.asteroidsgame.View;

import com.elkard.asteroidsgame.Controller.AsteroidsGame;
import com.elkard.asteroidsgame.Controller.IInputHandler;
import com.elkard.asteroidsgame.Controller.InputHandler;
import com.elkard.asteroidsgame.Logic.GameLogic;
import com.elkard.asteroidsgame.Logic.IGameState;
import com.elkard.asteroidsgame.Controller.IGameController;
import com.elkard.asteroidsgame.Line;
import com.elkard.asteroidsgame.View.UI.Button;
import com.elkard.asteroidsgame.View.UI.ButtonsGroup;
import com.elkard.asteroidsgame.View.UI.ButtonsManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameRenderer extends JFrame implements IButtonClickListener, IGameState
{
    private int screenWidth = 1280;
    private int screenHeight = 720;

    private int curScreenWidth = 1280;
    private int curScreenHeight = 720;
    private float screenWidthFactor = 1f;
    private float screenHeightFactor = 1f;

    private static final String TAG_MENU_MAIN = "mainMenu";
    private static final String TAG_MENU_PAUSE = "pauseMenu";
    private static final String TAG_MENU_GAMEOVER = "gameoverMenu";

    private static final String BUTTON_MAIN_PLAY = "PLAY";
    private static final String BUTTON_MAIN_HIGHSCORES = "HI-SCORES";
    private static final String BUTTON_MAIN_EXIT = "EXIT";
    
    private static final String BUTTON_PAUSE_RESUME = "RESUME";
    private static final String BUTTON_PAUSE_MENU = "EXIT";

    private static final String BUTTON_GAMEOVER_AGAIN = "AGAIN";
    private static final String BUTTON_GAMEOVER_MENU = "RETURN";

    private final IGameController gameEngine;

    public final IInputHandler inputHandler;

    public Boolean isKeyPressed = false;

    private Image frameBuffer;
    private Graphics g_frameBuffer;

    private Image image;

    private ButtonsGroup buttonsMain;
    private ButtonsGroup buttonsPause;
    private ButtonsGroup buttonsGameover;

    public GameRenderer(AsteroidsGame ag)
    {
        gameEngine = ag;
        inputHandler = new InputHandler();
        buildWindow(screenWidth, screenHeight);

        addComponentListener(new ResizeListener());

        gameEngine.getGameLogic().setStateChangedListener(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loadImages();
    }

    public void onUpdate(float deltaTime)
    {
        revalidate();
        repaint();
    }

    public void paint(Graphics g)
    {
        if (frameBuffer == null)
        {
            frameBuffer = createImage(getWidth(), getHeight());
            g_frameBuffer = frameBuffer.getGraphics();
        }

        g_frameBuffer.clearRect(0, 0, getWidth(), getHeight());
        ((Graphics2D) g_frameBuffer).setStroke(new BasicStroke(2));

        GameLogic.GameState curState = gameEngine.getGameLogic().getState();

        if (curState == GameLogic.GameState.GAMEPLAY || curState == GameLogic.GameState.PAUSED || curState == GameLogic.GameState.GAMEOVER)
        {
            drawGameplay(g_frameBuffer);
            drawUI(g_frameBuffer);
        }

        if (curState == GameLogic.GameState.PAUSED)
        {
            drawPauseMenu(g_frameBuffer);
        }

        if (curState == GameLogic.GameState.GAMEOVER)
        {
            drawGameoverMenu(g_frameBuffer);
        }

        if (curState == GameLogic.GameState.MAINMENU)
        {
            drawMainMenu(g_frameBuffer);
        }

        ButtonsManager.getInstance().draw(g_frameBuffer, this);
        g.drawImage(frameBuffer, 0, 0, this);

    }

    private void drawPauseMenu(Graphics g)
    {
        drawText(g, "PAUSED", screenWidth/2, screenHeight/2, 100, true);
    }

    private void drawGameoverMenu(Graphics g)
    {
        drawText(g, "GAME OVER", screenWidth/2, screenHeight/2, 100, true);
    }

    private void drawMainMenu(Graphics g)
    {

    }

    private void drawGameplay(Graphics g)
    {
        drawLines(g, gameEngine.getGameLogic().getPlayerRenderLines());
        drawLines(g, gameEngine.getGameLogic().getBulletsRenderLines());
        drawLines(g, gameEngine.getGameLogic().getAsteroidsRenderLines());
    }

    private void drawLines(Graphics g, Line[] lines)
    {
        for (Line line : lines)
            g.drawLine((int) (line.b.x * screenWidthFactor), (int) (line.b.y * screenHeightFactor),
                       (int) (line.e.x * screenWidthFactor), (int) (line.e.y * screenHeightFactor));
    }

    private void drawUI(Graphics g)
    {
        int lives = gameEngine.getGameLogic().getRemainingLives();

        for (int i = 0; i < lives; i++)
            g.drawImage(image, 50 + 60*i, 50, this);

        drawText(g, "SCORE: " + gameEngine.getGameLogic().getCurScore(), screenWidth/2, 120, 50, true);
    }

    private void drawText(Graphics g, String msg, int posX, int posY, int size, boolean center)
    {
        float factor = Math.min(screenWidthFactor, screenHeightFactor);
        g.setFont(new Font("TimesRoman", Font.PLAIN, (int) (size * factor)));
        if (center)
        {
            posX -= g.getFontMetrics().stringWidth(msg) / 2;
            posY -= size / 2;
        }
        g.drawString(msg, (int)(posX*screenWidthFactor), (int)(posY*screenHeightFactor));
    }

    private void drawText(Graphics g, String msg, int posX, int posY, int size)
    {
        drawText(g, msg, posX, posY, size, false);
    }

    private void buildWindow(int w, int h)
    {
        JPanel panel = new JPanel();
        getContentPane().add(panel);
        setSize(w, h);

        setFocusable(true);

        addKeyListener(new KeyListener()
        {
            @Override
            public void keyPressed(KeyEvent event)
            {
                getOuter().inputHandler.onKeyPressed(event.getKeyCode(), true);
                getOuter().isKeyPressed = true;
            }

            @Override
            public void keyReleased(KeyEvent event)
            {
                inputHandler.onKeyPressed(event.getKeyCode(), false);
            }

            @Override
            public void keyTyped(KeyEvent e)
            {

            }
        });

        prepareButtons();
    }

    public GameRenderer getOuter()
    {
        return this;
    }

    private void prepareButtons()
    {
        prepareMainMenuButtons();
        preparePauseMenuButtons();
        prepareGameoverMenuButtons();
    }

    private void prepareMainMenuButtons()
    {
        buttonsMain = new ButtonsGroup()
                .setPosition(300,300)
                .setListener(this)
                .setTag(TAG_MENU_MAIN)
                .setVisible(false);

        buttonsMain.add(new Button()
                .setPosition(0, 0)
                .setSize(200,20)
                .setText(BUTTON_MAIN_PLAY));

        buttonsMain.add(new Button()
                .setPosition(0, 60)
                .setSize(200,20)
                .setText(BUTTON_MAIN_HIGHSCORES));

        buttonsMain.add(new Button()
                .setPosition(0, 120)
                .setSize(200,20)
                .setText(BUTTON_MAIN_EXIT));
    }

    private void preparePauseMenuButtons()
    {
        buttonsPause = new ButtonsGroup()
                .setPosition(400,500)
                .setListener(this)
                .setTag(TAG_MENU_PAUSE)
                .setVisible(false);

        buttonsPause.add(new Button()
                .setPosition(0, 0)
                .setSize(200,20)
                .setText(BUTTON_PAUSE_RESUME));

        buttonsPause.add(new Button()
                .setPosition(240, 0)
                .setSize(200,20)
                .setText(BUTTON_PAUSE_MENU));
    }

    private void prepareGameoverMenuButtons()
    {
        buttonsGameover = new ButtonsGroup()
                .setPosition(400,500)
                .setListener(this)
                .setTag(TAG_MENU_GAMEOVER)
                .setVisible(false);

        buttonsGameover.add(new Button()
                .setPosition(0, 0)
                .setSize(200,20)
                .setText(BUTTON_GAMEOVER_AGAIN));

        buttonsGameover.add(new Button()
                .setPosition(240, 0)
                .setSize(200,20)
                .setText(BUTTON_GAMEOVER_MENU));
    }

    public void handleInput()
    {
        inputHandler.handleInput(gameEngine);
        inputHandler.handleInput(ButtonsManager.getInstance());
        inputHandler.refresh();
    }

    public void showWindow()
    {
        setVisible(true);
    }

    public void hideWindow()
    {
        setVisible(false);
    }

    private void loadImages()
    {
        try {
            BufferedImage temp = ImageIO.read(new File("heart2.png"));
            image = temp.getScaledInstance(48, 48, Image.SCALE_DEFAULT);
        } catch (IOException ex) {
            System.out.println("Can't load the image");
            ex.printStackTrace();
        }
    }

    @Override
    public void onButtonClicked(ButtonsGroup group, Button clicked)
    {
        if (group.getTag().equals(TAG_MENU_MAIN))
        {
            if (clicked.getText().equals(BUTTON_MAIN_PLAY))
            {
                gameEngine.getGameLogic().menuButtonClicked(GameLogic.MenuButton.MAIN_PLAY);
            }
            else if (clicked.getText().equals(BUTTON_MAIN_HIGHSCORES))
            {
                gameEngine.getGameLogic().menuButtonClicked(GameLogic.MenuButton.MAIN_HIGHSCORES);
            }
            else if (clicked.getText().equals(BUTTON_MAIN_EXIT))
            {
                gameEngine.getGameLogic().menuButtonClicked(GameLogic.MenuButton.MAIN_EXIT);
            }
            
            return;
        }
        
        if (group.getTag().equals(TAG_MENU_PAUSE))
        {
            if (clicked.getText().equals(BUTTON_PAUSE_RESUME))
            {
                gameEngine.getGameLogic().menuButtonClicked(GameLogic.MenuButton.PAUSE_RESUME);
            }
            else if  (clicked.getText().equals(BUTTON_PAUSE_MENU))
            {
                gameEngine.getGameLogic().menuButtonClicked(GameLogic.MenuButton.PAUSE_MENU);
            }
            
            return;
        }

        if (group.getTag().equals(TAG_MENU_GAMEOVER))
        {
            if (clicked.getText().equals(BUTTON_GAMEOVER_AGAIN))
            {
                gameEngine.getGameLogic().menuButtonClicked(GameLogic.MenuButton.GAMEOVER_AGAIN);
            }
            else if  (clicked.getText().equals(BUTTON_GAMEOVER_MENU))
            {
                gameEngine.getGameLogic().menuButtonClicked(GameLogic.MenuButton.GAMEOVER_RETURN);
            }

            return;
        }
    }

    @Override
    public void onStateChanged(GameLogic.GameState prevState, GameLogic.GameState curState)
    {
        System.out.println("State changed from " + prevState + " to " + curState);

        switch (prevState)
        {
            case MAINMENU:
                buttonsMain.setVisible(false);
                break;

            case PAUSED:
                buttonsPause.setVisible(false);
                break;

            case GAMEOVER:
                buttonsGameover.setVisible(false);
                break;
        }

        switch (curState)
        {
            case MAINMENU:
                buttonsMain.setVisible(true);
                break;

            case PAUSED:
                buttonsPause.setVisible(true);
                break;

            case GAMEOVER:
                buttonsGameover.setVisible(true);
                break;
        }
    }

    public void setScreenResolution()
    {
        screenWidth = curScreenWidth;
        screenHeight = curScreenHeight;
        screenWidthFactor = 1f;
        screenHeightFactor = 1f;
    }

    public float getScreenRatio()
    {
        return (float) curScreenWidth / curScreenHeight;
    }

    public float getScreenWidthFactor()
    {
        return screenWidthFactor;
    }

    public float getScreenHeightFactor()
    {
        return screenHeightFactor;
    }

    public void cleanUp()
    {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    private class ResizeListener extends ComponentAdapter
    {
        public void componentResized(ComponentEvent e)
        {
            getOuter().curScreenHeight = e.getComponent().getHeight();
            getOuter().curScreenWidth = e.getComponent().getWidth();
            getOuter().screenHeightFactor = (float) getOuter().curScreenHeight / getOuter().screenHeight;
            getOuter().screenWidthFactor = (float) getOuter().curScreenWidth / getOuter().screenWidth;
        }
    }
}