package com.elkard.asteroidsgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameRenderer extends JFrame
{

    private static final int screenWidth = 1280;
    private static final int screenHeight = 720;

    private AsteroidsGame gameEngine;
    private KeyboardListener keyboardListener;

    JButton playButton;
    JButton exitButton;

    public GameRenderer(AsteroidsGame ag)
    {
        gameEngine = ag;
        buildWindow(screenWidth, screenHeight);

        keyboardListener = new KeyboardListener(gameEngine);

//        keyboardListener =

//        EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new KeyboardListener(gameEngine);
//            }
//        });
    }

    public void onUpdate(float deltaTime)
    {

    }

    private void buildWindow(int w, int h)
    {
        JPanel panel = new JPanel();
        getContentPane().add(panel);
        setSize(w, h);


        playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playClicked();
            }
        } );
        panel.add(playButton);

//        addKeyListener(keyboardListener);
        setFocusable(true);
//        setFocusTraversalKeysEnabled(false);

        addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.VK_F11 && event.isAltDown()) {
//                    dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                    System.out.println("pressed");
                }


            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("released");
            }

            @Override
            public void keyTyped(KeyEvent e) {
                System.out.println("typed");
            }
        });

    }

    public void showWindow()
    {
        setVisible(true);
    }

    public void hideWindow()
    {
        setVisible(false);
    }

    private void renderPlayer()
    {

    }

    private void renderAsteroids()
    {

    }

    private void renderBullets()
    {

    }

    private void renderUI()
    {

    }

    private void showMainMenu()
    {

    }

    private void hideMainMenu()
    {

    }

    private void playClicked()
    {
        gameEngine.menuButtonClicked(AsteroidsGame.MenuButton.PLAY);
    }

    private void exitClicked()
    {

    }

//    dfds
}