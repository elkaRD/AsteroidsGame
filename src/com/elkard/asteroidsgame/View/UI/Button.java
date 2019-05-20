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
/// File: Button.java


package com.elkard.asteroidsgame.View.UI;

import com.elkard.asteroidsgame.View.GameRenderer;
import com.elkard.asteroidsgame.Vec2i;

import java.awt.*;

public class Button
{
    public ButtonsGroup group = null;
    public boolean selected = false;

    public Vec2i position = new Vec2i();
    public Vec2i size = new Vec2i();
    public Vec2i lastRealSize = new Vec2i();
    public Vec2i lastRealPosition = new Vec2i();

    public String text = "";
    public int fontSize = 20;
    public int boundary = 10;

    public boolean centerText = false;

    public String tag = "";

    public Button()
    {

    }

    public Button setPosition(int x, int y)
    {
        position.x = x;
        position.y = y;
        return this;
    }

    public Button setSize(int x, int y)
    {
        size.x = x;
        size.y = y;
        return this;
    }

    public Button setText(String text)
    {
        this.text = text;
        return this;
    }

    public String getText()
    {
        return text;
    }

    public Button setFontSize(int fontSize)
    {
        this.fontSize = fontSize;
        return this;
    }

    public Button setBoundary(int boundary)
    {
        this.boundary = boundary;
        return this;
    }

    public Button setCenter(boolean isCenter)
    {
        centerText = isCenter;
        return this;
    }

    public Button setTag(String tag)
    {
        this.tag = tag;
        return this;
    }

    public String getTag()
    {
        return tag;
    }

    public void setSelected(boolean isSelected)
    {
        selected = isSelected;
    }

    public void draw(Graphics g, GameRenderer gameRenderer, Vec2i parentPos)
    {
        float factor = Math.min(gameRenderer.getScreenWidthFactor(), gameRenderer.getScreenHeightFactor());
        g.setFont(new Font("TimesRoman", Font.PLAIN, (int) (fontSize * factor)));

        int realSizeX = g.getFontMetrics().stringWidth(text) + 2*boundary;
        int realSizeY = fontSize + 2*boundary;
        int posX = (int) (position.x + boundary + parentPos.x);
        int posY = (int) (position.y + size.y + boundary + parentPos.y);
        if (centerText)
        {
            //TODO:
            //posX +=
        }
        if (size.x > realSizeX) realSizeX = (int) size.x;
        if (size.y > realSizeY) realSizeY = (int) size.y;

//        lastRealSize.x = realSizeX;
//        lastRealSize.y = realSizeY;

        //Vec2i lastRealPosition = new Vec2i();
        lastRealPosition.x = position.x + parentPos.x;
        lastRealPosition.y = position.y + parentPos.y;

        lastRealPosition.x *= gameRenderer.getScreenWidthFactor();
        lastRealPosition.y *= gameRenderer.getScreenHeightFactor();
        realSizeX *= gameRenderer.getScreenWidthFactor();
        realSizeY *= gameRenderer.getScreenHeightFactor();
        posX *= gameRenderer.getScreenWidthFactor();
        posY *= gameRenderer.getScreenHeightFactor();

        lastRealSize.x = realSizeX;
        lastRealSize.y = realSizeY;

        Stroke prevStroke = null;
        if (selected)
        {
            Graphics2D g2 = (Graphics2D) g;
            prevStroke = g2.getStroke();
            g2.setStroke(new BasicStroke(10));
        }

        g.drawRect(lastRealPosition.x, lastRealPosition.y, realSizeX, realSizeY);
        g.drawString(text, posX, posY);

        if (selected)
        {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(prevStroke);
        }
    }
}
