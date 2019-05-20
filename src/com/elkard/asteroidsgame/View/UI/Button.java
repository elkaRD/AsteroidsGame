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

        Vec2i borderSize = calcBorderSize(g, gameRenderer);
        Vec2i borderPosition = calcBorderPosition(gameRenderer, parentPos);
        Vec2i textPosition = calcTextPosition(gameRenderer, parentPos);

        Stroke prevStroke = null;
        if (selected)
        {
            Graphics2D g2 = (Graphics2D) g;
            prevStroke = g2.getStroke();
            g2.setStroke(new BasicStroke(10));
        }

        g.drawRect(borderPosition.x, borderPosition.y, borderSize.x, borderSize.y);
        g.drawString(text, textPosition.x, textPosition.y);

        if (selected)
        {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(prevStroke);
        }
    }

    private Vec2i calcBorderSize(Graphics g, GameRenderer gameRenderer)
    {
        Vec2i borderSize = new Vec2i();
        borderSize.x = g.getFontMetrics().stringWidth(text) + 2*boundary;
        borderSize.y = fontSize + 2*boundary;
        if (size.x > borderSize.x) borderSize.x = (int) size.x;
        if (size.y > borderSize.y) borderSize.y = (int) size.y;
        borderSize.x *= gameRenderer.getScreenWidthFactor();
        borderSize.y *= gameRenderer.getScreenHeightFactor();

        return borderSize;
    }

    private Vec2i calcBorderPosition(GameRenderer gameRenderer, Vec2i parentPos)
    {
        Vec2i borderPosition = new Vec2i();
        borderPosition.x = position.x + parentPos.x;
        borderPosition.y = position.y + parentPos.y;
        borderPosition.x *= gameRenderer.getScreenWidthFactor();
        borderPosition.y *= gameRenderer.getScreenHeightFactor();

        return borderPosition;
    }

    private Vec2i calcTextPosition(GameRenderer gameRenderer, Vec2i parentPos)
    {
        Vec2i textPosition = new Vec2i();
        textPosition.x = (int) (position.x + boundary + parentPos.x);
        textPosition.y = (int) (position.y + size.y + boundary + parentPos.y);
        if (centerText)
        {
            //TODO: future feature
        }
        textPosition.x *= gameRenderer.getScreenWidthFactor();
        textPosition.y *= gameRenderer.getScreenHeightFactor();

        return textPosition;
    }
}
