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
/// File: ButtonsManager.java


package com.elkard.asteroidsgame.View.UI;

import com.elkard.asteroidsgame.View.AudioManager;
import com.elkard.asteroidsgame.View.GameRenderer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

import static com.elkard.asteroidsgame.View.AudioManager.CLICK;
import static com.elkard.asteroidsgame.View.AudioManager.SELECT;

public class ButtonsManager extends JFrame implements IButtonsManager
{
    private static ButtonsManager instance = new ButtonsManager();
    private ArrayList<ButtonsGroup> groups = new ArrayList<>();
    private Stack<ButtonsGroup> activeGroup = new Stack<>();

    public static ButtonsManager getInstance()
    {
        return instance;
    }

    private ButtonsManager()
    {

    }

    public void registerGroup(ButtonsGroup group)
    {
        groups.add(group);
    }

    public void destroyGroup(ButtonsGroup group)
    {
        groups.remove(group);
    }

    public void draw(Graphics g, GameRenderer gameRenderer)
    {
        for (ButtonsGroup group : groups)
        {
            if (group.getVisible())
            {
                group.draw(g, gameRenderer);
            }
        }
    }

    @Override
    public void nextButton()
    {
        if (activeGroup.size() == 0) return;

        activeGroup.lastElement().nextButton();
        AudioManager.getInstance().playSound(CLICK);
    }

    @Override
    public void prevButton()
    {
        if (activeGroup.size() == 0) return;

        activeGroup.lastElement().prevButton();
        AudioManager.getInstance().playSound(CLICK);
    }

    @Override
    public void pickButton()
    {
        if (activeGroup.size() == 0) return;

        activeGroup.lastElement().pickButton();
        AudioManager.getInstance().playSound(SELECT);
    }

    public void groupChangedVisible(ButtonsGroup group, boolean isVisible)
    {
        if (activeGroup.contains(group))
            activeGroup.remove(group);

        if (isVisible)
            activeGroup.add(group);
    }
}
