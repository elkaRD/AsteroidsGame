package com.elkard.asteroidsgame.View;

import com.elkard.asteroidsgame.AsteroidsGame;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener extends JFrame implements KeyListener
{
    private AsteroidsGame gameEngine;

    public KeyboardListener(AsteroidsGame ag)
    {
        System.out.println("Creating keyboard listener");
        gameEngine = ag;
        //addKeyListener(this);
        setFocusable(true);
    }

    @Override
    public void keyPressed(KeyEvent evt)
    {
        char c = evt.getKeyChar();
        gameEngine.keyPressed(c);
        System.out.println("pressed " + c);
    }

    @Override
    public void keyReleased(KeyEvent evt)
    {
        char c = evt.getKeyChar();
        gameEngine.keyReleased(c);
        System.out.println("released " + c);
    }

    @Override
    public void keyTyped(KeyEvent evt)
    {
        char c = evt.getKeyChar();
        System.out.println("typed " + c);
    }
}
