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
import com.elkard.asteroidsgame.Logic.GameLogic;
import com.elkard.asteroidsgame.Controller.IGameController;
import com.elkard.asteroidsgame.Line;
import com.elkard.asteroidsgame.View.UI.ButtonsManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameRenderer extends JFrame
{
    private int screenWidth = 1280;
    private int screenHeight = 720;

    private int curScreenWidth = 1280;
    private int curScreenHeight = 720;
    private float screenWidthFactor = 1f;
    private float screenHeightFactor = 1f;

    private final IGameController gameEngine;
    private final IInputHandler inputHandler;

    private Image frameBuffer;
    private Graphics g_frameBuffer;

    private Image imageLive;
    private String imageLiveFilename = "resources/heart.png";

    private boolean screenResized = true;

    public GameRenderer(AsteroidsGame controller, IInputHandler inputHandler)
    {
        gameEngine = controller;
        this.inputHandler = inputHandler;

        buildWindow(screenWidth, screenHeight);

        addComponentListener(new ResizeListener());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loadImages();
        setTitle("Asteroids - Projekt PROZ 19L");
    }

    public void onUpdate(float deltaTime)
    {
        revalidate();
        repaint();
    }

    public void paint(Graphics g)
    {
        if (screenResized)
        {
            screenResized = false;
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
        drawText(g, "ASTEROIDS", screenWidth/2, 200, 90, true);
        drawText(g, "by Robert Dudzinski", screenWidth/2, 220, 20, true);
    }

    private void drawGameplay(Graphics g)
    {
        for (GameLogic.ObjectType type : GameLogic.ObjectType.values())
            drawLines(g, gameEngine.getGameLogic().getObjectsRenderLines(type));
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
            g.drawImage(imageLive, 50 + 60*i, 50, this);

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

        initKeyListener();

        addWindowStateListener(new WindowStateListener() {
            public void windowStateChanged(WindowEvent arg0) {
                onWindowStateChanged(arg0);
            }
        });
    }

    private void initKeyListener()
    {
        addKeyListener(new KeyListener()
        {
            @Override
            public void keyPressed(KeyEvent event)
            {
                getOuter().inputHandler.onKeyPressed(event.getKeyCode(), true);
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
    }

    public void onWindowStateChanged(WindowEvent e)
    {
        if ((e.getNewState() & Frame.ICONIFIED) == Frame.ICONIFIED)
            gameEngine.getGameLogic().onLostFocus();
    }

    public GameRenderer getOuter()
    {
        return this;
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
            BufferedImage temp = ImageIO.read(new File(imageLiveFilename));
            imageLive = temp.getScaledInstance(48, 48, Image.SCALE_DEFAULT);
        } catch (IOException ex) {
            System.out.println("Can't load the imageLive");
            ex.printStackTrace();
        }
    }

    public void setScreenResolution()
    {
        screenWidth = curScreenWidth;
        screenHeight = curScreenHeight;
        screenWidthFactor = 1f;
        screenHeightFactor = 1f;
        screenResized = true;
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