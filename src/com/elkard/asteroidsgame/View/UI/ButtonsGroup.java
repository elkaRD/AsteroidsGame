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
/// File: ButtonsGroup.java


package com.elkard.asteroidsgame.View.UI;

import com.elkard.asteroidsgame.View.GameRenderer;
import com.elkard.asteroidsgame.View.IButtonClickListener;
import com.elkard.asteroidsgame.Vec2i;

import java.awt.*;
import java.util.ArrayList;

public class ButtonsGroup
{
    private ArrayList<Button> buttons = new ArrayList<>();
    private Button selectedButton = null;

    private IButtonClickListener listener = null;

    private boolean isVisible = true;
    public Vec2i position = new Vec2i();
    public String tag = "";

    public ButtonsGroup()
    {
        ButtonsManager.getInstance().registerGroup(this);
        setVisible(true);
    }

    public void add(Button newButton)
    {
        if (newButton.group != null)
            newButton.group.remove(newButton);

        newButton.group = this;
        buttons.add(newButton);

        if (buttons.size() == 0)
            selectedButton = newButton;
    }

    public void remove(Button toRemove)
    {
        toRemove.group = null;
        buttons.remove(toRemove);
    }

    public ButtonsGroup setVisible(boolean isVisible)
    {
        if (this.isVisible != isVisible && buttons.size() > 0)
            changeSelected(0);


        this.isVisible = isVisible;
        ButtonsManager.getInstance().groupChangedVisible(this, isVisible);

        return this;
    }

    public ButtonsGroup setListener(IButtonClickListener newListener)
    {
        listener = newListener;
        return this;
    }

    public ButtonsGroup setTag(String tag)
    {
        this.tag = tag;
        return this;
    }

    public String getTag()
    {
        return tag;
    }

    public boolean getVisible()
    {
        return isVisible;
    }

    public void draw(Graphics g, GameRenderer gameRenderer)
    {
        for (Button button : buttons)
        {
            button.draw(g, gameRenderer, position);
        }
    }

    public ButtonsGroup setPosition(int x, int y)
    {
        position.x = x;
        position.y = y;
        return this;
    }

    private void changeSelected(int index)
    {
        if (selectedButton != null)
            selectedButton.setSelected(false);

        selectedButton = buttons.get(index);
        selectedButton.setSelected(true);
    }

    public void nextButton()
    {
        int index = buttons.indexOf(selectedButton) + 1;
        index %= buttons.size();
        changeSelected(index);
    }

    public void prevButton()
    {
        int index = buttons.indexOf(selectedButton) - 1;
        if (index < 0) index = buttons.size() - 1;
        changeSelected(index);
    }

    public void pickButton()
    {
        if (listener == null || selectedButton == null) return;
        listener.onButtonClicked(this, selectedButton);
    }
}
