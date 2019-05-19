package com.elkard.asteroidsgame.View.UI;

import com.elkard.asteroidsgame.View.GameRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Stack;

public class ButtonsManager extends JFrame implements MouseListener
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
    public void mouseClicked(MouseEvent event)
    {
        System.out.println("Clicked: " + event.getX() + ", " + event.getY());

        for (ButtonsGroup group : groups)
        {
            if (group.getVisible())
            {
                group.onClick(event.getX(), event.getY());
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent arg0) { }

    @Override
    public void mouseExited(MouseEvent arg0) { }

    @Override
    public void mousePressed(MouseEvent arg0) { }

    @Override
    public void mouseReleased(MouseEvent arg0) { }

    public void nextButton()
    {
        if (activeGroup.size() == 0) return;

        activeGroup.lastElement().nextButton();
    }

    public void prevButton()
    {
        if (activeGroup.size() == 0) return;

        activeGroup.lastElement().prevButton();
    }

    public void pickButton()
    {
        if (activeGroup.size() == 0) return;

        activeGroup.lastElement().pickButton();
    }

    public void groupChangedVisible(ButtonsGroup group, boolean isVisible)
    {
        if (activeGroup.contains(group))
            activeGroup.remove(group);

        if (isVisible)
            activeGroup.add(group);
    }
}
